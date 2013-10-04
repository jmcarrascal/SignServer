package ar.gob.onti.ventanilla.services.impl;

import ar.gob.onti.ventanilla.dao.MensajeDAO;
import ar.gob.onti.ventanilla.model.Usuario;
import ar.gob.onti.ventanilla.model.UsuarioCertificado;
import ar.gob.onti.ventanilla.services.MessageService;
import ar.gob.onti.ventanilla.services.ValidateCertificateService;
import ar.gob.onti.ventanilla.util.Constants;
import ar.gob.onti.ventanilla.util.FileUtil;
import ar.gob.onti.ventanilla.util.ValidateCertificate;
import ar.gob.onti.ventanilla.ws.model.VentanillaResponse;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.security.PdfPKCS7;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.security.Security;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;

@Service
public class ValidateCertificateServiceImpl implements ValidateCertificateService {

    @Autowired
    private MensajeDAO mensajeDAO;


    @Autowired
    private MessageService messageService;


    public VentanillaResponse verifyCertificates(byte[] documento,
                                                 Usuario usuarioValido) {
        VentanillaResponse ventanillaResponse = null;

        try {
            Security.addProvider(new BouncyCastleProvider());
            PdfReader reader = new PdfReader(FileUtil.getInputStreamFromByte(documento));
            AcroFields af = reader.getAcroFields();
            ArrayList<String> names = af.getSignatureNames();
            boolean resultFirmas = false;

            if (names.size() < 1) {
                //Se expresa que el doc no tiene firmas
                ventanillaResponse = messageService.buildErrorVentanillaResponse(Constants.ID_MESSAGE_ERROR_NO_SIGN);
            } else {
                for (String name : names) {
                    System.out.println("Signature name: " + name);
                    InputStream ip = af.extractRevision(name);
                    PdfPKCS7 pk = af.verifySignature(name);
                    Certificate[] pkc = pk.getCertificates();
                    Certificate userCert = ValidateCertificate.getCertificateUser(pkc);
                    if (pkc.length > 0) {
                        for (UsuarioCertificado usuarioCertificado : usuarioValido.getUsuarioCertificadoList()) {
                            CertificateFactory certFactory = CertificateFactory.getInstance("X.509");
                            InputStream in = new ByteArrayInputStream(usuarioCertificado.getCertificadoConfiable().getCertificado());
                            X509Certificate x509Certificate = (X509Certificate) certFactory.generateCertificate(in);
                            resultFirmas = ValidateCertificate.isValidCA(userCert, x509Certificate);
                        }
                    }
                    if (!resultFirmas) {
                        break;
                    }
                }
                if (resultFirmas) {
                    //Se expresa que existe un problema al validar los certificados
                    ventanillaResponse = messageService.buildErrorVentanillaResponse(Constants.ID_MESSAGE_OK);
                } else {
                    //Se expresa que existe un problema al validar los certificados
                    ventanillaResponse = messageService.buildErrorVentanillaResponse(Constants.ID_MESSAGE_ERROR_NOT_CERTIFICATE_VALID);
                }
            }

        } catch (java.security.cert.CertificateException e) {
            e.printStackTrace();
            ventanillaResponse = messageService.buildErrorVentanillaResponse(Constants.ID_MESSAGE_ERROR_READ_CERT_ROOT);
        } catch (com.itextpdf.text.exceptions.InvalidPdfException i) {
            i.printStackTrace();
            ventanillaResponse = messageService.buildErrorVentanillaResponse(Constants.ID_MESSAGE_ERROR_DOC);
        } catch (java.lang.NullPointerException nu) {
            nu.printStackTrace();
            ventanillaResponse = messageService.buildErrorVentanillaResponse(Constants.ID_MESSAGE_ERROR_NULL_DOCUMENT);
        } catch (Exception e) {
            e.printStackTrace();
            //Se expresa que existe un problema al validar los certificados
            ventanillaResponse = messageService.buildErrorVentanillaResponse(Constants.ID_MESSAGE_ERROR_VALIDATE);
        }
        return ventanillaResponse;
    }


}
