package cm.java.entidades.testes;

import java.util.Random;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Before;

/**
 * Classe que realiza a conexão com o banco de dados de teste
 * @author Mauro Silva
 */
public abstract class TestePersistenciaAbstrato {

    protected static EntityManagerFactory emf
            = Persistence.createEntityManagerFactory("cmJavaJPATeste");

    protected EntityManager em;
    protected EntityTransaction tx;

    // ======================================
    // =      Metodos do Ciclo de Vida      =
    // ======================================
    
    @Before
    public void iniciaGerenciadorEntidades() throws Exception {
        em = emf.createEntityManager();
        tx = em.getTransaction();
    }

    @After
    public void encerraGerenciadorEntidades() throws Exception {
        if (em != null) {
            em.close();
        }
    }

    public Long getIdRandomico() {
        return Math.abs(new Random().nextLong());
    }

}
