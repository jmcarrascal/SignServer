package ar.gob.onti.ventanilla.services.impl.test;

import org.bouncycastle.asn1.eac.RSAPublicKey;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openssl.PEMReader;

import java.io.IOException;
import java.io.StringReader;
import java.security.Security;


public class TestGenRSA {

    /**
     * @param args
     */
    public static void main(String[] args) {

        Security.addProvider(new BouncyCastleProvider());
        String s = "-----BEGIN RSA PUBLIC KEY-----" +
                "MIIBCgKCAQEA+xGZ/wcz9ugFpP07Nspo6U17l0YhFiFpxxU4pTk3Lifz9R3zsIsu" +
                "ERwta7+fWIfxOo208ett/jhskiVodSEt3QBGh4XBipyWopKwZ93HHaDVZAALi/2A" +
                "+xTBtWdEo7XGUujKDvC2/aZKukfjpOiUI8AhLAfjmlcD/UZ1QPh0mHsglRNCmpCw" +
                "mwSXA9VNmhz+PiB+Dml4WWnKW/VHo2ujTXxq7+efMU4H2fny3Se3KYOsFPFGZ1TN" +
                "QSYlFuShWrHPtiLmUdPoP6CV2mML1tk+l7DIIqXrQhLUKDACeM5roMx0kLhUWB8P" +
                "+0uj1CNlNN4JRZlC7xFfqiMbFRU9Z4N6YwIDAQAB-----END RSA PUBLIC KEY-----" +
                "";

        System.out.println(s);
        //String pemKey = "----BEGIN RSA PUBLIC KEY-----MIGJAoGBAJ5MfMlnhrI+DLKILVl+M5lunlbWKbyEVvc2KpcHZ+AoORzur+qFC9t8UkudeysFWjcCEa1vkWF+y5aKvWnosW+aj8+o5xgTZWOCJLDB7Fo9Lvrm1b7jX/BsiUc6Clk17O6vKEoS0YRoQ+S+Y8su0nEE/5zH+rWzFypCMkquM1knAgMBAAE=-----END RSA PUBLIC KEY-----";
        PEMReader pemReader = new PEMReader(new StringReader(s));
        RSAPublicKey rsaPubKey;


        try {
            rsaPubKey = (RSAPublicKey) pemReader.readObject();
            System.out.println("Public key: " + rsaPubKey);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


    }

}
