package ar.gob.onti.ventanilla.test;

import ar.gob.onti.ventanilla.model.Mensaje;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Created with IntelliJ IDEA.
 * User: esalamanca
 * Date: 04-10-13
 * Time: 06:52 PM
 * Tests de persistencia de datos.
 */
public class PersistenciaTest {

    private static EntityManager em;

    public static void main(String[] argumentos) {

        //Crear entity manager factory
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("mysqlDS-test");
        em = entityManagerFactory.createEntityManager();

        try {

            em.getTransaction().begin();

            //Crear nuevas placas
            Mensaje mensaje = new Mensaje();
            mensaje.setIdMensaje(1);
            mensaje.setDescrip("Suzuki Kizashi");
            em.persist(mensaje);

            em.getTransaction().commit();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
    }
}
