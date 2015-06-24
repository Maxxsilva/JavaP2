package cm.java.entidades.testes;


import cm.java.entidades.Dependente;
import cm.java.entidades.Funcionario;
import java.text.ParseException;
import org.junit.Test;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;
import org.junit.After;

import static org.junit.Assert.assertEquals;
import org.junit.Before;

/**
 * 
 * @author Mauro Silva
 * 
 * Classe de responsável em fazer testes Dinâmicos e nomeadas com jpql
 */


public class ConsultasTesteIntegridade extends TestePersistenciaAbstrato {

  // ======================================
  // =              Constantes            =
  // ======================================
  private static final int TODOS = 6;
  
  // ======================================
  // =              Atributos             =
  // ======================================
  private Funcionario funcionario1;
  private Funcionario funcionario2;
  private Funcionario funcionario3;
  private Funcionario funcionario4;
  private Funcionario funcionario5;
  private Funcionario funcionario6;


  // ======================================
  // =     Metodos do Ciclo de Vida       =
  // ======================================

  @Before
  public void persistirFuncionarios() throws ParseException {
    
    tx = em.getTransaction();

    funcionario1 = new Funcionario("Joao", "joao@sabao.com", 45);
    Dependente dependente1 = new Dependente("Joaozinho", 10);    
    funcionario1.setDependente(dependente1);
    
    funcionario2 = new Funcionario("Maria", "maria@gmail.com", 35);
    Dependente dependente2 = new Dependente("Mariazinha", 2);    
    funcionario2.setDependente(dependente2);
    
    funcionario3 = new Funcionario("Jose", "jose@gmail.com", 32);
    Dependente dependente3 = new Dependente("Zezinho", 9);    
    funcionario3.setDependente(dependente3);
    
    funcionario4 = new Funcionario("Marcos", "marcos@gmail.com", 29);
    Dependente dependente4 = new Dependente("Marquinhos", 1);    
    funcionario4.setDependente(dependente4);
    
    funcionario5 = new Funcionario("lucas", "lucas@gmail.com", 23);
    Dependente dependente5 = new Dependente("luquinha", 5);    
    funcionario5.setDependente(dependente5);
    
    funcionario6 = new Funcionario("Joao", "joao@sabao.com", 45);
    Dependente dependente6 = new Dependente("Jaozinho", 10);    
    funcionario6.setDependente(dependente6);

    // Persisto o objeto
    tx.begin();
    em.persist(funcionario1);
    em.persist(funcionario2);
    em.persist(funcionario3);
    em.persist(funcionario4);
    em.persist(funcionario5);
    em.persist(funcionario6);
    tx.commit();
  }

  @After
  public void removerFuncionarios() {

    // Removo os objetos
    tx.begin();
    em.remove(funcionario1);
    em.remove(funcionario2);
    em.remove(funcionario3);
    em.remove(funcionario4);
    em.remove(funcionario5);
    em.remove(funcionario6);
    tx.commit();
  }

  // ======================================
  // =           Testes Unitários         =
  // ======================================

  @Test
  public void deveriaEncontrarTodosComConsultaDinâmica() throws Exception {
    // Consultas
    Query consulta = em.createQuery("select f from Funcionario f");
    List<Funcionario> funcionarios = consulta.getResultList();
    assertEquals(TODOS, funcionarios.size());

    consulta = em.createQuery("select f from Funcionario f");
    consulta.setMaxResults(3);
    assertEquals(3, consulta.getResultList().size());

    boolean algumCriterio = true;
    String consultaJpql = "select f from Funcionario f";
    if (algumCriterio)
      consultaJpql += " where f.Nome = 'joao'";
    consulta = em.createQuery(consultaJpql);
    assertEquals(2, consulta.getResultList().size());

    consultaJpql = "select f from Funcionario f";
    if (algumCriterio)
      consultaJpql += " where f.Nome = :pnome";
    consulta = em.createQuery(consultaJpql);
    consulta.setParameter("pnome", "Joao");
    assertEquals(2, consulta.getResultList().size());

    consultaJpql = "select f from ConsumidorConsultas f";
    if (algumCriterio)
      consultaJpql += " where f.Nome = ?1";
    consulta = em.createQuery(consultaJpql);
    consulta.setParameter(1, "Joao");
    assertEquals(2, consulta.getResultList().size());

    consulta = em.createQuery("select f from Funcionario f  where f.Nome = :pnome");
    consulta.setParameter("pnome", "Joao");
    consulta.setMaxResults(1);
    assertEquals(1, consulta.getResultList().size());

    consulta = em.createQuery("select f from Funcionario f where f.Nome = :pnome").setParameter("pnome", "Joao").setMaxResults(1);
    assertEquals(1, consulta.getResultList().size());

    // Consulta Tipada - TypedQuery
    TypedQuery<Funcionario> consultaTipada = em.createQuery("select f from Funcionario f", Funcionario.class);
    funcionarios = consultaTipada.getResultList();
    assertEquals(TODOS, funcionarios.size());

    consultaTipada = em.createQuery("select f from Funcionario f", Funcionario.class);
    consultaTipada.setMaxResults(3);
    assertEquals(3, consultaTipada.getResultList().size());

    consultaJpql = "select f from Funcionario f";
    if (algumCriterio)
      consultaJpql += " where f.Nome = 'Joao'";
    consultaTipada = em.createQuery(consultaJpql, Funcionario.class);
    assertEquals(2, consultaTipada.getResultList().size());

    consultaJpql = "select f from Funcionario f";
    if (algumCriterio)
      consultaJpql += " where f.Nome = :pnome";
    consultaTipada = em.createQuery(consultaJpql, Funcionario.class);
    consultaTipada.setParameter("pnome", "Joao");
    assertEquals(2, consultaTipada.getResultList().size());

    consultaJpql = "select f from Funcionario f";
    if (algumCriterio)
      consultaJpql += " where f.Nome = ?1";
    consultaTipada = em.createQuery(consultaJpql, Funcionario.class);
    consultaTipada.setParameter(1, "Joao");
    assertEquals(2, consultaTipada.getResultList().size());

    consultaTipada = em.createQuery("select f from Funcionario f  where f.Nome = :pnome", Funcionario.class);
    consultaTipada.setParameter("pnome", "Joao");
    consultaTipada.setMaxResults(1);
    assertEquals(1, consultaTipada.getResultList().size());

    consultaTipada = em.createQuery("select f from Funcionario f  where f.Nome = :pnome", Funcionario.class).setParameter("pnome", "Joao").setMaxResults(1);
    assertEquals(1, consultaTipada.getResultList().size());

  }

  @Test
  public void deveriaEncontrarTodosComUmaConsultaNomeada() throws Exception {

    // Consultas
    Query consulta = em.createNamedQuery("encontreTodos");
    List<Funcionario> consumidores = consulta.getResultList();
    assertEquals(TODOS, consumidores.size());

    consulta = em.createNamedQuery(Funcionario.ENCONTRE_TODOS);
    assertEquals(TODOS, consulta.getResultList().size());

    consulta = em.createNamedQuery("encontreTodos");
    consulta.setMaxResults(3);
    assertEquals(3, consulta.getResultList().size());

    consulta = em.createNamedQuery("encontreJoao");
    assertEquals(2, consulta.getResultList().size());

    consulta = em.createNamedQuery("encontreComParametro");
    consulta.setParameter("pnome", "Joao");
    assertEquals(2, consulta.getResultList().size());

    // Consulta Tipada - TypedQuery
    TypedQuery<Funcionario> consultaTipada = em.createNamedQuery("encontreTodos", Funcionario.class);
    consumidores = consultaTipada.getResultList();
    assertEquals(TODOS, consumidores.size());

    consultaTipada = em.createNamedQuery(Funcionario.ENCONTRE_TODOS, Funcionario.class);
    assertEquals(TODOS, consultaTipada.getResultList().size());

    consultaTipada = em.createNamedQuery("encontreTodos", Funcionario.class);
    consultaTipada.setMaxResults(3);
    assertEquals(3, consultaTipada.getResultList().size());

    consultaTipada = em.createNamedQuery("encontreJoao", Funcionario.class);
    assertEquals(2, consultaTipada.getResultList().size());

    consultaTipada = em.createNamedQuery("encontreComParametro", Funcionario.class);
    consultaTipada.setParameter("pnome", "Joao");
    assertEquals(2, consultaTipada.getResultList().size());

  }

  @Test
  public void deveriaEncontrarTodosComConsultaNativa() throws Exception {
       // Consultas
    Query consulta = em.createNativeQuery("select * from t_funcionario", Funcionario.class);
    List<Funcionario> consumidores = consulta.getResultList();
    assertEquals(TODOS, consumidores.size());
  }

  
}
