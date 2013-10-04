package ar.gob.onti.ventanilla.util;


import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class FileUtil {

    private static String basePath = "/temp/";

    /**
     * Convierte un byte[] en un objeto File
     *
     * @param byte[]
     * @return File
     */
    public static File getFileFromByteArray(byte[] doc, String nombreArchivo) {
        File file = new File(nombreArchivo);
        try {
            file.createNewFile();
        } catch (IOException e1) {

            e1.printStackTrace();
        }
        InputStream in = null;
        try {
            in = new FileInputStream(file);
            in.read(doc);
            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    public static String getFormatedDate(Timestamp date) {
        if (date != null) {
            SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
            return (date == null) ? " - " : format.format(new Date(date.getTime()));
        } else {
            return null;
        }

    }

    public static boolean getHoursCompareEntryToNow(Timestamp fecEntrada,
                                                    long hours) {
        if (fecEntrada == null) {
            return true;
        }

        boolean out = false;
        Date date = new Date();
        Timestamp fechaActual = new Timestamp(date.getTime());
        // 84600000 milliseconds in a day
        // long oneDay = (1 * 24 * 60 * 60 * 1000);

        long onehour = (1 * 60 * 60 * 1000);
        // long nDay = oneDay * days;
        // to add to the timestamp

        // System.out.println("fechaEntrada " + fecEntrada );
        // System.out.println("fechaActual " + fechaActual );

        long tsEntrada = fecEntrada.getTime();
        long tsActual = fechaActual.getTime();

        long tsResult = tsActual - tsEntrada;

        long hour = tsResult / onehour;

        if (hour > hours) {
            out = true;
        }
        return out;
    }

    /**
     * Convierte un objeto File en un byte[]
     *
     * @param file
     * @return
     * @throws IOException
     */
    public static byte[] getBytesFromFile(File file) throws IOException {
        InputStream is = new FileInputStream(file);

        // Get the size of the file
        long length = file.length();

        // You cannot create an array using a long type.
        // It needs to be an int type.
        // Before converting to an int type, check
        // to ensure that file is not larger than Integer.MAX_VALUE.
        if (length > Integer.MAX_VALUE) {
            // File is too large
        }

        // Create the byte array to hold the data
        byte[] bytes = new byte[(int) length];

        // Read in the bytes
        int offset = 0;
        int numRead = 0;
        while (offset < bytes.length
                && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
            offset += numRead;
        }

        // Ensure all the bytes have been read in
        if (offset < bytes.length) {
            throw new IOException("Could not completely read file "
                    + file.getName());
        }

        // Close the input stream and return bytes
        is.close();
        return bytes;
    }

    public static String parseExtension(String fileName) {

        if (fileName != null) {
            for (int i = 0; i < fileName.length(); i++) {
                char aux = fileName.charAt(i);
                if (aux == '.')
                    return fileName.substring(i + 1);
            }
        }
        return fileName;
    }

    public static String parseNameFile(String fileName) {

        if (fileName != null) {
            for (int i = 0; i < fileName.length(); i++) {
                char aux = fileName.charAt(i);
                if (aux == '.')
                    return fileName.substring(0, i);
            }
        }
        return fileName;
    }

    public static Boolean isImage(String completeName) {
        String img = "image";
        return completeName.contains(img);
    }

    public static String getStringFormatLeftSpace(String value, int longitud) {
        if (value != null) {
            while (value.length() < longitud) {
                value = " " + value;
            }
        }
        return value;
    }

    public static File createTempFile(String data, String nombre) {
        File outFile = new File(nombre);
        FileWriter out;

        try {
            out = new FileWriter(outFile);
            out.write(data);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return outFile;
    }

    public static String getWebappsPath() {
        String sep = System.getProperty("file.separator");
        return System.getProperty("catalina.base") + sep + "webapps";
    }

    public static void copy(File fileAs, File fileTo) throws IOException {
        if (fileTo.getParentFile() != null) {
            fileTo.getParentFile().mkdirs();
        }

        FileInputStream inputStream = new FileInputStream(fileAs);
        FileOutputStream outputStream = new FileOutputStream(fileTo);

        for (int bufSize = 0; bufSize != -1; ) {
            byte[] buf = new byte[1024];
            bufSize = inputStream.read(buf, 0, buf.length);

            if (bufSize != -1) {
                outputStream.write(buf, 0, bufSize);
            }
        }
        outputStream.flush();
        outputStream.close();
        inputStream.close();
    }

    public static List<String> generateXmlErrorConectRentas() {
        List<String> result = new ArrayList<String>();
        result.add("<?xml version='1.0' encoding='ISO-8859-1'?>");
        result.add("<TBError>");
        result.add("	<tipoError>NETWORK</tipoError>");
        result.add("	<codigoError>001</codigoError>");
        result.add("	<mensajeError>Error al intentar conectarse con el servidor de Arba</mensajeError>");
        result.add("</TBError>");
        return result;
    }

    public static List<String> generateXmlErrorLicencia() {
        List<String> result = new ArrayList<String>();
        result.add("<?xml version='1.0' encoding='ISO-8859-1'?>");
        result.add("<TBError>");
        result.add("	<tipoError>CoinseCOT</tipoError>");
        result.add("	<codigoError>002</codigoError>");
        result.add("	<mensajeError>Debe solicitar la incorporacion de la impresa con el cuit ingresado</mensajeError>");
        result.add("</TBError>");
        return result;
    }

    /**
     * Guarda el arhivo en el directorio temporal
     *
     * @param archivo
     */
    public static void writeFile(byte[] archivo, String nombre)
            throws Exception {

        File file = new File(nombre);

        FileOutputStream f = new FileOutputStream(file);

        try {
            f.write(archivo);
            f.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static BufferedInputStream castByteToBufferInpuStream(
            byte[] archivo, String nombre) throws Exception {

        BufferedInputStream bufin = new BufferedInputStream(
                new ByteArrayInputStream(archivo));

        return null;
    }

    /**
     * Agrega el Id de solicitud a la Imagen
     *
     * @param Texto a Agregar
     * @param data
     * @return
     */
    public static void addTextInImage(String text, String archivoOrig,
                                      String archivoTemp) {

        try {
            // Tomo imagen y la convierto en byte[]
            byte[] data = FileUtil.getBytesFromFile(new File(archivoOrig));

            // System.out.println("Tama�o imagen original " + data.length);

            // Objeto Image
            Image reference = new ImageIcon(data).getImage();

            // Tomo Tama�o de la imagen
            int widthImg = reference.getWidth(null);
            int heightImg = reference.getHeight(null);

            // Creo imagen del mismo tama�o
            BufferedImage bimg = new BufferedImage(widthImg, heightImg,
                    BufferedImage.TYPE_INT_RGB);

            // Convierto imagen nueva en obbjeto Graphics2D
            Graphics2D gr = (Graphics2D) bimg.getGraphics();
            gr.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);

            // pinto el fondo blanco
            gr.setColor(Color.white);

            // Seteo Ubicacion
            gr.fillRect(0, 0, 1, 1);

            // coloco la imagen original
            gr.drawImage(new ImageIcon(data).getImage(), 0, 0, null);

            // Seteo el color del String a Escribir
            gr.setPaint(Color.GRAY);

            // Seteo el tipo de letra
            Font font = new Font("arial", 1, 42);

            gr.setFont(font);

            gr.drawString(text, 50, heightImg - 14);

            // Finalizo
            ByteArrayOutputStream bas = new ByteArrayOutputStream();
            ImageIO.write(bimg, "jpg", bas);
            data = bas.toByteArray();

            try {
                FileUtil.writeFile(data, archivoTemp);
            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (IOException e) {
            e.printStackTrace();

        }

    }

    public static String getRandomNumber() {
        Random rand = new Random();
        int x = rand.nextInt(100000000);
        return String.valueOf(x).substring(0, 6);
    }

    public static String llenoConCeros(String valor, int longitud) {
        valor = sinNull(valor).trim();
        while (valor.length() < longitud) {
            valor = "0" + valor;
        }
        return valor;
    }

    public static String llenoConEspacios(String valor, int longitud) {
        while (valor.length() < longitud) {
            valor = " " + valor;
        }
        return valor;
    }

    public static String sinNull(String valor) {
        valor = valor.replaceAll("null", "    ");
        return valor;
    }

    public static byte[] getBytesFromArrayList(List<String> listFacturasPerdidas) {
        // Paso en contenido a un archivo
        String nombreArchivo = "T_" + System.currentTimeMillis() + ".txt";
        File outFile = new File(nombreArchivo);
        FileWriter out;

        try {
            out = new FileWriter(outFile);
            for (String factura : listFacturasPerdidas) {
                out.write(factura + "\r\n");
            }
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] array = null;
        try {
            array = getBytesFromFile(new File(nombreArchivo));
        } catch (IOException e) {
            e.printStackTrace();
        }
        File fileDelete = new File(nombreArchivo);
        fileDelete.delete();
        return array;
    }

    public static byte[] getBytesFromArrayListTest(
            List<String> listFacturasPerdidas) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            for (String linea : listFacturasPerdidas) {
                baos.write(linea.getBytes());
            }
            baos.flush();
            baos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static InputStream getInputStreamFromByte(byte[] byteArray) {

        InputStream is = new ByteArrayInputStream(byteArray);


        return is;

    }


}
