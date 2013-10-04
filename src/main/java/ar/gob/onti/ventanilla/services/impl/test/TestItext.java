package ar.gob.onti.ventanilla.services.impl.test;

/*
 * This class is part of the book "iText in Action - 2nd Edition"
 * written by Bruno Lowagie (ISBN: 9781935182610)
 * For more info, go to: http://itextpdf.com/examples/
 * This example only works with the AGPL version of iText.
 */

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.Security;
import java.security.cert.CRL;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Properties;

import javax.security.cert.CertificateParsingException;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfSignatureAppearance;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.security.BouncyCastleDigest;
import com.itextpdf.text.pdf.security.CertificateInfo;
import com.itextpdf.text.pdf.security.ExternalDigest;
import com.itextpdf.text.pdf.security.ExternalSignature;
import com.itextpdf.text.pdf.security.MakeSignature;
import com.itextpdf.text.pdf.security.MakeSignature.CryptoStandard;
import com.itextpdf.text.pdf.security.PdfPKCS7;
import com.itextpdf.text.pdf.security.PrivateKeySignature;
import com.itextpdf.text.pdf.security.VerificationException;

public class TestItext {

    /** The resulting PDF */
    public static String ORIGINAL = "results/part3/chapter12/hello.pdf";
    /** The resulting PDF */
    public static String SIGNED1 = "results/part3/chapter12/signature_1.pdf";
    /** The resulting PDF */
    public static String SIGNED2 = "results/part3/chapter12/signature_2.pdf";
    /** Info after verification of a signed PDF */
    public static String VERIFICATION = "results/part3/chapter12/verify.txt";
    /** The resulting PDF */
    public static String REVISION = "results/part3/chapter12/revision_1.pdf";

    /**
     * A properties file that is PRIVATE.
     * You should make your own properties file and adapt this line.
     */
    public static String PATH = "c:/home/blowagie/key.properties";
    /** Some properties used when signing. */
    public static Properties properties = new Properties();
    /** One of the resources. */
    public static final String RESOURCE = "resources/img/logo.gif";
    
    /**
     * Creates a PDF document.
     * @param filename the path to the new PDF document
     * @throws DocumentException 
     * @throws IOException 
     */
    public void createPdf(String filename) throws IOException, DocumentException {
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(filename));
        document.open();
        document.add(new Paragraph("Hello World!"));
        document.close();
    }
    
    /**
     * Manipulates a PDF file src with the file dest as result
     * @param src the original PDF
     * @param dest the resulting PDF
     * @throws IOException
     * @throws DocumentException
     * @throws GeneralSecurityException 
     */
    public void signPdfFirstTime(String src, String dest)
        throws IOException, DocumentException, GeneralSecurityException {
        String path = properties.getProperty("PRIVATE");
        String keystore_password = properties.getProperty("PASSWORD");
        String key_password = properties.getProperty("PASSWORD");
        KeyStore ks = KeyStore.getInstance("pkcs12", "BC");
        ks.load(new FileInputStream(path), keystore_password.toCharArray());
        String alias = (String)ks.aliases().nextElement();
        PrivateKey pk = (PrivateKey) ks.getKey(alias, key_password.toCharArray());
        Certificate[] chain = ks.getCertificateChain(alias);
        // reader and stamper
        PdfReader reader = new PdfReader(src);
        FileOutputStream os = new FileOutputStream(dest);
        PdfStamper stamper = PdfStamper.createSignature(reader, os, '\0');
        // appearance
        PdfSignatureAppearance appearance = stamper .getSignatureAppearance();
        appearance.setImage(Image.getInstance(RESOURCE));
        appearance.setReason("I've written this.");
        appearance.setLocation("Foobar");
        appearance.setVisibleSignature(new Rectangle(72, 732, 144, 780), 1,    "first");
        // digital signature
        ExternalSignature es = new PrivateKeySignature(pk, "SHA-256", "BC");
        ExternalDigest digest = new BouncyCastleDigest();
        MakeSignature.signDetached(appearance, digest, es, chain, null, null, null, 0, CryptoStandard.CMS);
    }
    
    /**
     * Manipulates a PDF file src with the file dest as result
     * @param src the original PDF
     * @param dest the resulting PDF
     * @throws IOException
     * @throws DocumentException
     * @throws GeneralSecurityException 
     */
    public void signPdfSecondTime(String src, String dest)
        throws IOException, DocumentException, GeneralSecurityException {
        String path = "resources/encryption/.keystore";
        String keystore_password = "f00b4r";
        String key_password = "f1lmf3st";
        String alias = "foobar";
        KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
        ks.load(new FileInputStream(path), keystore_password.toCharArray());
        PrivateKey pk = (PrivateKey) ks.getKey(alias, key_password.toCharArray());
        Certificate[] chain = ks.getCertificateChain(alias);
        // reader / stamper
        PdfReader reader = new PdfReader(src);
        FileOutputStream os = new FileOutputStream(dest);
        PdfStamper stamper = PdfStamper.createSignature(reader, os, '\0', null, true);
        // appearance
        PdfSignatureAppearance appearance = stamper
                .getSignatureAppearance();
        appearance.setReason("I'm approving this.");
        appearance.setLocation("Foobar");
        appearance.setVisibleSignature(new Rectangle(160, 732, 232, 780), 1, "second");
        // digital signature
        ExternalSignature es = new PrivateKeySignature(pk, "SHA-256", "BC");
        ExternalDigest digest = new BouncyCastleDigest();
        MakeSignature.signDetached(appearance, digest, es, chain, null, null, null, 0, CryptoStandard.CMS);

    }
    
    /**
     * Verifies the signatures of a PDF we've signed twice.
     * @throws GeneralSecurityException
     * @throws IOException
     */
    public void verifySignatures() throws GeneralSecurityException, IOException {
        KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
        ks.load(null, null);
//        CertificateFactory cf = CertificateFactory.getInstance("X509");
//        FileInputStream is1 = new FileInputStream(properties.getProperty("ROOTCERT"));
//        X509Certificate cert1 = (X509Certificate) cf.generateCertificate(is1);
//        ks.setCertificateEntry("cacert", cert1);
//        FileInputStream is2 = new FileInputStream("resources/encryption/foobar.cer");
//        X509Certificate cert2 = (X509Certificate) cf.generateCertificate(is2);
//        ks.setCertificateEntry("foobar", cert2);
        
       // PrintWriter out = new PrintWriter(new FileOutputStream("c://algo_nuevo.pdf"));

        PdfReader reader = new PdfReader("c://docFirmado1.pdf");
        AcroFields af = reader.getAcroFields();
        ArrayList<String> names = af.getSignatureNames();
        for (String name : names) {
            System.out.println("Signature name: " + name);
            System.out.println("Signature covers whole document: " + af.signatureCoversWholeDocument(name));
            System.out.println("Document revision: " + af.getRevision(name) + " of " + af.getTotalRevisions());
            InputStream ip = af.extractRevision(name);
            PdfPKCS7 pk = af.verifySignature(name);
            Calendar cal = pk.getSignDate();
            Certificate[] pkc = pk.getCertificates();
            System.out.println("Subject: " + CertificateInfo.getSubjectFields(pk.getSigningCertificate()));
            System.out.println("Revision modified: " + !pk.verify());
            List<VerificationException> errors  = null;
			try {
				errors = verifyCertificates(pkc, ks, null, cal);
			} catch (CertificateParsingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            if (errors.size() == 0)
            	System.out.println("Certificates verified against the KeyStore");
            else
            	System.out.println(errors);    
        }
    }
    
    /**
     * Extracts the first revision of a PDF we've signed twice.
     * @throws IOException
     */
    public void extractFirstRevision() throws IOException {
        PdfReader reader = new PdfReader(SIGNED2);
        AcroFields af = reader.getAcroFields();
        FileOutputStream os = new FileOutputStream(REVISION);
        byte bb[] = new byte[1028];
        InputStream ip = af.extractRevision("first");
        int n = 0;
        while ((n = ip.read(bb)) > 0)
            os.write(bb, 0, n);
        os.close();
        ip.close();
    }
    
    /**
     * Main method.
     *
     * @param    args    no arguments needed
     * @throws DocumentException 
     * @throws IOException
     * @throws GeneralSecurityException 
     */
    public static void main(String[] args)
        throws IOException, DocumentException, GeneralSecurityException {
        Security.addProvider(new BouncyCastleProvider());
//        properties.load(new FileInputStream(PATH));
        TestItext signatures = new TestItext();
//        signatures.createPdf(ORIGINAL);
//        signatures.signPdfFirstTime(ORIGINAL, SIGNED1);
//        signatures.signPdfSecondTime(SIGNED1, SIGNED2);
        signatures.verifySignatures();
//        signatures.extractFirstRevision();
    }

    /**
     * Verifies a certificate chain against a KeyStore.
     * @param certs the certificate chain
     * @param keystore the <CODE>KeyStore</CODE>
     * @param crls the certificate revocation list or <CODE>null</CODE>
     * @param calendar the date or <CODE>null</CODE> for the current date
     * @return <CODE>null</CODE> if the certificate chain could be validated or a
     * <CODE>Object[]{cert,error}</CODE> where <CODE>cert</CODE> is the
     * failed certificate and <CODE>error</CODE> is the error message
     * @throws CertificateParsingException 
     * @throws java.security.cert.CertificateParsingException 
     */
    public static List<VerificationException> verifyCertificates(Certificate certs[], KeyStore keystore, Collection<CRL> crls, Calendar calendar) throws java.security.cert.CertificateParsingException, CertificateParsingException {
        List<VerificationException> result = new ArrayList<VerificationException>();
            if (calendar == null)
            calendar = new GregorianCalendar();
        for (int k = 0; k < certs.length; ++k) {
            X509Certificate cert = (X509Certificate)certs[k];
            String err = verifyCertificate(cert, crls, calendar);
            if (err != null)
                result.add(new VerificationException(cert, err));
            try {
                for (Enumeration<String> aliases = keystore.aliases(); aliases.hasMoreElements();) {
                    try {
                        String alias = aliases.nextElement();
                        if (!keystore.isCertificateEntry(alias))
                            continue;
                        X509Certificate certStoreX509 = (X509Certificate)keystore.getCertificate(alias);
                        if (verifyCertificate(certStoreX509, crls, calendar) != null)
                            continue;
                        try {
                            cert.verify(certStoreX509.getPublicKey());
                            return result;
                        }
                        catch (Exception e) {
                            continue;
                        }
                    }
                    catch (Exception ex) {
                    }
                }
            }
            catch (Exception e) {
            }
            int j;
            for (j = 0; j < certs.length; ++j) {
                if (j == k)
                    continue;
                X509Certificate certNext = (X509Certificate)certs[j];
                try {
                    cert.verify(certNext.getPublicKey());
                    break;
                }
                catch (Exception e) {
                }
            }
            if (j == certs.length) {
                    result.add(new VerificationException(cert, "Cannot be verified against the KeyStore or the certificate chain"));
            }
        }
        if (result.size() == 0)
            result.add(new VerificationException(null, "Invalid state. Possible circular certificate chain"));
        return result;
    }
    
    /**
     * Verifies a single certificate.
     * @param cert the certificate to verify
     * @param crls the certificate revocation list or <CODE>null</CODE>
     * @param calendar the date or <CODE>null</CODE> for the current date
     * @return a <CODE>String</CODE> with the error description or <CODE>null</CODE>
     * if no error
     * @throws java.security.cert.CertificateParsingException 
     */
    public static String verifyCertificate(X509Certificate cert, Collection<CRL> crls, Calendar calendar) throws CertificateParsingException, java.security.cert.CertificateParsingException {
        if (calendar == null)
            calendar = new GregorianCalendar();
        if (cert.hasUnsupportedCriticalExtension()) {
                for (String oid : cert.getCriticalExtensionOIDs()) {
                        // KEY USAGE and DIGITAL SIGNING is ALLOWED
                        if ("2.5.29.15".equals(oid) && cert.getKeyUsage()[0]) {
                                continue;
                        }
                        // EXTENDED KEY USAGE and TIMESTAMPING is ALLOWED
						if ("2.5.29.37".equals(oid) && cert.getExtendedKeyUsage().contains("1.3.6.1.5.5.7.3.8")) {
						        continue;
						}
                return "Has unsupported critical extension";
                }
        }
        try {
            cert.checkValidity(calendar.getTime());
        }
        catch (Exception e) {
            return e.getMessage();
        }
        if (crls != null) {
            for (CRL crl : crls) {
                if (crl.isRevoked(cert))
                    return "Certificate revoked";
            }
        }
        return null;
    }
}