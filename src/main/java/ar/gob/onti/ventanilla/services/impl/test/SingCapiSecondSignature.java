package ar.gob.onti.ventanilla.services.impl.test;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfSignatureAppearance;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.security.*;
import com.itextpdf.text.pdf.security.MakeSignature.CryptoStandard;
import sun.security.mscapi.SunMSCAPI;

import java.io.FileOutputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.Security;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;


public class SingCapiSecondSignature {

    public static void main(String[] args) {
        try {
            SunMSCAPI providerMSCAPI = new SunMSCAPI();
            Security.addProvider(providerMSCAPI);

            //Security.addProvider(new BouncyCastleProvider());
            System.out.println(KeyStore.getDefaultType());
            KeyStore ks = KeyStore.getInstance("Windows-MY", "SunMSCAPI");
            ks.load(null, null);
            java.util.Enumeration<String> en = ks.aliases();
            while (en.hasMoreElements()) {
                String aliasKey = (String) en.nextElement();
                System.out.println("---> alias : " + aliasKey);
                Certificate[] chain = ks.getCertificateChain(aliasKey);
                for (Certificate cert : chain) {
                    X509Certificate x509 = (X509Certificate) cert;
                    System.out.println("  Certificat : " + x509.getSubjectX500Principal());
                    System.out.println("  Certificat : " + x509.getSerialNumber());
                    //x509.checkValidity();

//					PdfReader reader = new PdfReader("c://sgi//doc_sin_firma.pdf");
                    //String alias = ks.aliases().nextElement();
                    System.out.println(aliasKey);

                    PrivateKey key = (PrivateKey) ks.getKey(aliasKey, null);
                    System.out.println(key.getAlgorithm());

//					FileOutputStream os = new FileOutputStream("c://sgi//doc_firmado_nacion.pdf");
//					
//					PdfStamper stamper = PdfStamper.createSignature(reader, os, '\0', null, true);
//					PdfSignatureAppearance appearance = stamper.getSignatureAppearance();
//					appearance.setCrypto(key, chain, null, PdfSignatureAppearance.CERTIFIED_NO_CHANGES_ALLOWED);
//					appearance.setLayer2Text("");
//					appearance.setVisibleSignature(new Rectangle(150, 500, 500, 150), reader.getNumberOfPages(), aliasKey);
//					appearance.setAcro6Layers(true);
//					appearance.setLayer2Text(aliasKey);
//					appearance.setImageScale(1f);  
//					stamper.close();


                    signAgain("c://signserver//doc_firmado_nacion.pdf", "c://signserver//doc_firmado_nacion_SI.pdf", chain, key, DigestAlgorithms.SHA256, providerMSCAPI.getName(), CryptoStandard.CMS, "", "");


                }
            }
        } catch (Exception ioe) {
            ioe.printStackTrace();
        }
    }


    public static void signAgain(String src, String dest, Certificate[] chain, PrivateKey pk,
                                 String digestAlgorithm, String provider, CryptoStandard subfilter,
                                 String reason, String location)
            throws GeneralSecurityException, IOException, DocumentException {
        // Creating the reader and the stamper
        PdfReader reader = new PdfReader(src);
        FileOutputStream os = new FileOutputStream(dest);
        PdfStamper stamper = PdfStamper.createSignature(reader, os, '\0', null, true);
        // Creating the appearance
        PdfSignatureAppearance appearance = stamper.getSignatureAppearance();
        appearance.setReason(reason);
        appearance.setLocation(location);
        appearance.setVisibleSignature(
                new Rectangle(36, 700, 144, 732), 1, "Signature2");
        // Creating the signature
        ExternalSignature pks = new PrivateKeySignature(pk, digestAlgorithm, provider);
        ExternalDigest digest = new BouncyCastleDigest();
        MakeSignature.signDetached(appearance, digest, pks, chain,
                null, null, null, 0, subfilter);
    }
}