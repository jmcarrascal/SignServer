package ar.gob.onti.ventanilla.services.impl;

import ar.gob.onti.ventanilla.dao.FirmanteDAO;
import ar.gob.onti.ventanilla.dao.ParametrizacionDAO;
import ar.gob.onti.ventanilla.model.Firmante;
import ar.gob.onti.ventanilla.model.Mensaje;
import ar.gob.onti.ventanilla.model.Usuario;
import ar.gob.onti.ventanilla.services.AuthenticationService;
import ar.gob.onti.ventanilla.services.MessageService;
import ar.gob.onti.ventanilla.services.SignDocumentService;
import ar.gob.onti.ventanilla.services.ValidateCertificateService;
import ar.gob.onti.ventanilla.util.Constants;
import ar.gob.onti.ventanilla.ws.ServiceVentanillaWS;
import ar.gob.onti.ventanilla.ws.model.VentanillaRequest;
import ar.gob.onti.ventanilla.ws.model.VentanillaResponse;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.ExceptionConverter;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfSignatureAppearance;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.security.*;
import com.itextpdf.text.pdf.security.MakeSignature.CryptoStandard;
import org.apache.log4j.Logger;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;

@Service
public class SignDocumentServiceImpl implements SignDocumentService {

    private static final Logger logger = Logger.getLogger(ServiceVentanillaWS.class);

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private FirmanteDAO firmanteDAO;

    @Autowired
    private ParametrizacionDAO parametrizacionDAO;

    @Autowired
    private ValidateCertificateService validateCertificateService;


    /**
     * Este m�todo valida el documento y firma con timestamp
     */
    public VentanillaResponse signDocument(VentanillaRequest ventanillaRequest) {

        //Autenticaci�n
        Usuario usuarioValido = authenticationService.authenticate(ventanillaRequest);
        if (usuarioValido != null) {
            VentanillaResponse ventanillaResponse = new VentanillaResponse();

            //Validar que el documento enviado contenga un formato PDF

            //Obtener los certificados de los firmantes del documento

            //Verificar que los certificados hallan sido generados por un ROOT confiable
            ventanillaResponse = validateCertificateService.verifyCertificates(ventanillaRequest.getDocumento(), usuarioValido);
            if (ventanillaResponse.getMessage().getIdMensaje().equals(Constants.ID_MESSAGE_OK)) {
                //Firmar documento con Obtencion de TSA
                ventanillaResponse = signPdf(ventanillaRequest.getDocumento());
            }

            return ventanillaResponse;

        } else {
            Mensaje message = messageService.buildMessage(Constants.ID_MSG_AUTH_FAILED);
            VentanillaResponse ventanillaResponse = new VentanillaResponse(message);
            return ventanillaResponse;

        }

    }


    @Transactional
    private VentanillaResponse signPdf(byte[] document) {
        VentanillaResponse ventanillaResponse = null;

        //Obtengo el firmante
        Firmante firmante = firmanteDAO.getFirmanteByPK(Constants.ID_FIRMANTE_);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        KeyStore ks;
        try {
            Security.addProvider(new BouncyCastleProvider());
            ks = KeyStore.getInstance("PKCS12", "BC");
            //temp
            ks.load(new FileInputStream(firmante.getRutaKeyStore()), firmante.getPasswordKeyStore().toCharArray());
            String alias = (String) ks.aliases().nextElement();
            PrivateKey pk = (PrivateKey) ks.getKey(alias, firmante.getPasswordKeyStore().toCharArray());
            Certificate[] chain = ks.getCertificateChain(alias);
            PdfReader reader = new PdfReader(document);
            PdfStamper stp = PdfStamper.createSignature(reader, out, '\0', null, true);
            PdfSignatureAppearance sap = stp.getSignatureAppearance();
            sap.setReason(firmante.getRazon());
            sap.setLocation(firmante.getLocacion());
            sap.setVisibleSignature(new Rectangle(72, 732, 144, 780), 1, null);
            ExternalSignature es = new PrivateKeySignature(pk, DigestAlgorithms.SHA256, "BC");
            TSAClient tsc = null;

            if (firmante.getUsaTSA()) {
                //Set Proxy
                setProxy();
                tsc = new TSAClientBouncyCastle(firmante.getUrlTSA(), firmante.getUserTSA(), firmante.getPasswordTSA());
            }
            OcspClient ocsp = null;

            ExternalDigest digest = new BouncyCastleDigest();

            MakeSignature.signDetached(sap, digest, es, chain, null, ocsp, tsc, 0, CryptoStandard.CMS);

            ventanillaResponse = messageService.buildErrorVentanillaResponse(Constants.ID_MESSAGE_OK);

            ventanillaResponse.setDocumentoFirmado(out.toByteArray());

        } catch (KeyStoreException e) {

            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            //Sale por no encontrar el certificado firmante
            ventanillaResponse = messageService.buildErrorVentanillaResponse(Constants.ID_MESSAGE_ERROR_DOC);
            e.printStackTrace();
        } catch (UnrecoverableKeyException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            ventanillaResponse = messageService.buildErrorVentanillaResponse(Constants.ID_MESSAGE_ERROR_DOC);
            e.printStackTrace();
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        } catch (ExceptionConverter e) {
            ventanillaResponse = messageService.buildErrorVentanillaResponse(Constants.ID_MESSAGE_ERROR_TSA);
            e.printStackTrace();
        } catch (Exception e) {
            ventanillaResponse = messageService.buildErrorVentanillaResponse(Constants.ID_MESSAGE_ERROR_INTERNAL_SIGN);
            e.printStackTrace();
        }


        return ventanillaResponse;
    }

    public void setProxy() {

        try {
            //Pregunto si usa Proxy
            if (parametrizacionDAO.getParametrizacionByPK(Constants.ID_PARAM_USE_PROXY).getValor().trim().equals("true")) {
                System.setProperty("http.proxyHost", parametrizacionDAO.getParametrizacionByPK(Constants.ID_PARAM_PROXY_URL).getValor());
                System.setProperty("http.proxyPort", parametrizacionDAO.getParametrizacionByPK(Constants.ID_PARAM_PROXY_PORT).getValor());
            }

        } catch (Exception e) {
            logger.debug("No se obtuvo los datos esperados de Parametrizacion para evaluar el uso de Proxy");
        }
    }


}
