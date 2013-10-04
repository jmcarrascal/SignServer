package ar.gob.onti.ventanilla.services.impl.test;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TestInsertBinaryValue {

    /**
     * @param args
     */


    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Connection conn = null;

        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/signserver", "root", "root");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            File f = new File("C://signserver//inter.cer");
            byte[] image = new byte[(int) f.length()];
            FileInputStream in = new FileInputStream(f);
            in.read(image);

            String sql = "update certificadoconfiable set certificado = ? ";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setBytes(1, image);
            stmt.executeUpdate();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

