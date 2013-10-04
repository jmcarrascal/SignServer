package ar.gob.onti.ventanilla.util;


public class Constants {


    //Unico Firmante
    public final static Integer ID_FIRMANTE_ = 1;

    //Valores Parametrizacion
    public final static Long ID_PARAM_USE_PROXY = 10l;
    public final static Long ID_PARAM_PROXY_URL = 11l;
    public final static Long ID_PARAM_PROXY_PORT = 12l;

    //Valores Id de Mensajes
    public final static Integer ID_MESSAGE_OK = 0;
    public final static Integer ID_MESSAGE_ERROR_DOC = 1;
    public final static Integer ID_MESSAGE_ERROR_NO_SIGN = 2;
    public final static Integer ID_MESSAGE_ERROR_VALIDATE = 3;
    public final static Integer ID_MESSAGE_ERROR_NOT_CERTIFICATE_VALID = 4;
    public final static Integer ID_MESSAGE_ERROR_NULL_DOCUMENT = 5;
    public final static Integer ID_MESSAGE_ERROR_TSA = 6;
    public final static Integer ID_MESSAGE_ERROR_INTERNAL_SIGN = 7;
    public final static Integer ID_MESSAGE_ERROR_READ_CERT_ROOT = 8;
    public final static Integer ID_MESSAGE_ERROR_READ_CERT_SIGNER = 9;
    public final static Integer ID_MSG_AUTH_FAILED = 10;


    // mensajes
    public final static String MSG_CRL_OBSOLETA = "La CRL est� obsoleta";
    public final static String MSG_CERTIFICADO_REVOCADO = "El certificado est� revocado";
    public final static String MSG_ERROR_VALIDACION_CRL = "La CRL no pudo validarse";

}

