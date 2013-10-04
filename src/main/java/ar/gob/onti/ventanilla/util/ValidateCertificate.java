package ar.gob.onti.ventanilla.util;

import sun.security.x509.*;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.security.*;
import java.security.cert.*;
import java.security.cert.Certificate;
import java.util.List;

public class ValidateCertificate {

    /**
     * Valida un certificado digital (Expiraci�n, CRL y CA permitida en ese
     * orden )
     * @param certificate
     * @param x509Certificate
     * @return
     */
    //public String validateCertificate(Certificate certificate, X509Certificate x509Certificate) {
        /*
         * String result = ""; try { if
		 * (this.isValidExpirationDate(certificate)){ if
		 * (this.isValidCRL(certificate)){ if(!this.isValidCA(certificate,
		 * x509Certificate)){ result =
		 * "El certificado no fue emitido por la CA esperada"; } }else{ result =
		 * "El certificado se encuentra Revocado"; } }else{ result =
		 * "El certificado se encuentra Expirado"; } } catch (KeyStoreException
		 * e) { e.printStackTrace(); } catch (CRLException e) {
		 * e.printStackTrace(); } return result;
		 */
    //return "";
    //}

    /**
     * Valida un certificado digital (Expiraci�n y CRL en ese orden)
     *
     * @param certificate - certificado a validar
     * @return un string que determina la validez de la revocacion del certificado y la validez de la CRL.
     *         Si el string retornado es vacio la la validacion se considera correcta. Caso contrario, el string posee una descripcion del fallo de la validacion.
     */
    public String validateCertificate(Certificate certificate) {
        String result = "";
        try {
            if (this.isValidExpirationDate(certificate)) {
                // TODO: comentar cuando no hay internet
                String salida = validateCRL(certificate);
                //String salida = "";
                //if (!this.isValidCRL(certificate)) {
                if (!salida.equals("")) {
                    result = salida;
                    System.out.println(result);
                } else {
                    System.out.println("La validaci�n de la CRL fue exitosa");
                }
            } else {
                result = "El certificado se encuentra expirado";
                System.out.println(result);
            }
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (CRLException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Determina si un certificado se encuentra o no expirado
     *
     * @param certificate - certificado a validar
     * @return true si es certificado NO esta expirado, false en caso contrario
     * @throws KeyStoreException
     */
    public boolean isValidExpirationDate(Certificate certificate) throws KeyStoreException {
        X509Certificate x509Certificate = (X509Certificate) certificate;
        try {
            x509Certificate.checkValidity();
        } catch (CertificateExpiredException e) {
            return false;
        } catch (CertificateNotYetValidException e) {
            return false;
        }
        return true;
    }

    /**
     * Este metodo se utiliza para validar que el certificado que esta a punto de
     * firmar un documento haya sido emitido por la CA que esta habilitada para su rol
     *
     * @param certificate
     * @param x509Certificate
     * @return
     */
    public static boolean isValidCA(Certificate certificate, X509Certificate x509Certificate) {
        try {
            certificate.verify(x509Certificate.getPublicKey());
        } catch (CertificateExpiredException e) {
            return false;
        } catch (CertificateNotYetValidException e) {
            return false;
        } catch (InvalidKeyException e) {
            return false;
        } catch (CertificateException e) {
            return false;
        } catch (NoSuchAlgorithmException e) {
            return false;
        } catch (NoSuchProviderException e) {
            return false;
        } catch (SignatureException e) {
            return false;
        }
        return true;
    }


    /**
     * Determina la validez de un certificado contra la CRL y que la validez de la CRL en si misma.
     * La validacion del certificado la realiza contra la CRL que publica la CA del certificado.
     * La validacion de la CRL consiste en validar que la fecha de proxima emision sea posterior a la fecha actual.
     *
     * @param certificate - certificado a validar
     * @return un string que determina la validez de la revocacion del certificado y la validez de la CRL.
     *         Si el string retornado es vacio la la validacion se considera correcta. Caso contrario, el string posee una descripcion del fallo de la validacion.
     * @throws KeyStoreException
     * @throws CRLException
     */
    private String validateCRL(Certificate certificate) throws KeyStoreException, CRLException {
        // este string es para manejar elmensaje que se le mostrara al usuario y ademas sirve para
        String salida = "";
        try {
            // crea una fabrica de certicidos X.509
            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
            // convierte el certificado a un X509CertImpl
            X509CertImpl x509CertificateImpl = (X509CertImpl) (X509Certificate) certificate;
            // obtiene la CRL del certificado
            CRLDistributionPointsExtension crlDistributionPointsExtension = x509CertificateImpl.getCRLDistributionPointsExtension();
            if (crlDistributionPointsExtension != null) {
                // para cada punto de distribuciion de la CRL verifica que el
                // certificado del token no este revocado
                @SuppressWarnings("unchecked") List<DistributionPoint> distributionPoints = (List<DistributionPoint>) crlDistributionPointsExtension.get(CRLDistributionPointsExtension.POINTS);
                for (DistributionPoint distributionPoint : distributionPoints) {
                    List<GeneralName> generalNames = distributionPoint.getFullName().names();
                    for (GeneralName generalName : generalNames) {
                        String generalNameString = generalName.toString();
                        if (generalNameString.startsWith("URIName:")) {
                            String crlURLString = generalNameString.substring(9);
                            if (crlURLString.startsWith("http")) {
                                X509CRLImpl crlImpl = null;
                                /* TODO: Estos datos son para cuando se corre desde el eclipse, no me toma los datos de configuracion del proxy desde la configuracion en el eclipse...*/
                                Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("10.3.8.1", 80));
                                InputStream crlInputStream = new URL(crlURLString).openConnection(proxy).getInputStream();
								/* */
                                //InputStream crlInputStream = new URL(crlURLString).openConnection().getInputStream();
                                try {
                                    crlImpl = (X509CRLImpl) certificateFactory.generateCRL(crlInputStream);
                                    // valida que la proxima actualizacion de la CRL no este vencida
                                    // lo cual es equivalente a decir que la CRL no esta vencida
                                    // la fecha actual (para realizar la comparacion) se recupera desde la BD
                                    if (crlImpl != null) {
                                        if (!crlImpl.getNextUpdate().after(DateUtil.getCurrentDate())) {
                                            salida = Constants.MSG_CRL_OBSOLETA;
                                            System.out.println(salida);
                                            return salida;
                                        } else {
                                            break;
                                        }
                                    } else {
                                        salida = Constants.MSG_ERROR_VALIDACION_CRL;
                                        System.out.println(salida);
                                    }
                                } finally {
                                    crlInputStream.close();
                                }
                                // verifica si el certificado esta revocado en la CRL
                                if (crlImpl.isRevoked(certificate)) {
                                    salida = Constants.MSG_CERTIFICADO_REVOCADO;
                                    System.out.println(salida);
                                    return salida;
                                }
                            }
                        }
                    }
                }
            }
        } catch (CertificateException e) {
            salida = "EXCEPCION SGI: Se produjo una excepci�n del tipo CertificateException";
            System.out.println(salida);
            e.printStackTrace();
            return salida;
        } catch (IOException e) {
            salida = "EXCEPCION SGI: Se produjo una excepci�n del tipo IOException";
            System.out.println(salida);
            e.printStackTrace();
            return salida;
        }
        return salida;
    }

    public static Certificate getCertificateUser(Certificate[] certificateList) {
        Certificate result = null;
        int i = 0;
        for (Certificate cert : certificateList) {
            if (((X509Certificate) cert).getBasicConstraints() == -1) {
                result = certificateList[i];
            }
            i++;
        }
        return result;
    }
}
