package cm.java.entidades.testes;

import cm.java.entidades.Dependente;
import cm.java.entidades.Funcionario;
import java.text.ParseException;
import javax.persistence.TypedQuery;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.EntityType;

import static org.junit.Assert.assertEquals;

/**
 * 
 * @author Mauro Silva
 * 
 * Classe de responsável em fazer testes utilizando API Criteria
 */

public class APICriteriaTesteIntegridade extends TestePersistenciaAbstrato {

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
  public void deveriaEncontrarFuncionarioJoao() throws Exception {

    // select f from Funcionario f where f.Nome = 'joao'
    CriteriaBuilder montador = em.getCriteriaBuilder();
    CriteriaQuery<Funcionario> consultaCriteria = montador.createQuery(Funcionario.class);
    Root<Funcionario> f = consultaCriteria.from(Funcionario.class);
    consultaCriteria.select(f).where(montador.equal(f.get("nome"), "Joao"));
    assertEquals(2, em.createQuery(consultaCriteria).getResultList().size());
  }

  
  @Test
  public void deveriaEncontrarFuncionarioJoaoComMetaModelo() throws Exception {

    // select f from Funcionario f where f.Nome= 'Joao'
    CriteriaBuilder montador = em.getCriteriaBuilder();
    CriteriaQuery<Funcionario> consulta = montador.createQuery(Funcionario.class);
    Root<Funcionario> f = consulta.from(Funcionario.class);
    EntityType<Funcionario> f_ = f.getModel();
    consulta.select(f).where(montador.equal(f.get(f_.getSingularAttribute("nome")), "Joao"));
    assertEquals(2, em.createQuery(consulta).getResultList().size());
  }

  @Test
  public void deveriaEncontrarFuncionariosMaioresQue40() throws Exception {
    // select f from Funcionario f where f.idade > 40
    CriteriaBuilder montador = em.getCriteriaBuilder();
    CriteriaQuery<Funcionario> consulta = montador.createQuery(Funcionario.class);
    Root<Funcionario> f = consulta.from(Funcionario.class);
    consulta.select(f).where(montador.greaterThan(f.get("idade").as(Integer.class), 40));
    assertEquals(2, em.createQuery(consulta).getResultList().size());
  }


  @Test
  public void deveriaEncontrarTodosComConsultaDinamica() throws Exception {

    // Montador de um Criteria 
    CriteriaBuilder montadorCriteria = em.getCriteriaBuilder();
    CriteriaQuery<Funcionario> consultaCriteria = montadorCriteria.createQuery(Funcionario.class); ;
    TypedQuery<Funcionario> consulta;
    Root<Funcionario> funcionario = consultaCriteria.from(Funcionario.class);
    EntityType<Funcionario> funcionario_ = funcionario.getModel();

    // select f from Funcionario f
    consultaCriteria = montadorCriteria.createQuery(Funcionario.class);
    consultaCriteria.from(Funcionario.class);
    assertEquals(TODOS, em.createQuery(consultaCriteria).getResultList().size());

    // select f from Funcionario f (setMaxResults(3))
    consulta = em.createQuery(consultaCriteria);
    consulta.setMaxResults(3);
    assertEquals(3, consulta.getResultList().size());

    // select f from Funcionario f where f.Nome = 'Joao'
    consultaCriteria = montadorCriteria.createQuery(Funcionario.class);
    funcionario = consultaCriteria.from(Funcionario.class);
    consultaCriteria.select(funcionario).where(montadorCriteria.equal(funcionario.get("nome"), "Joao"));
    assertEquals(2, em.createQuery(consultaCriteria).getResultList().size());

    // select f from Funcionario f where f.Nome = 'Joao' (usando meta-modelo)
    String pNome = "Joao";
    consultaCriteria = montadorCriteria.createQuery(Funcionario.class);
    funcionario = consultaCriteria.from(Funcionario.class);
    consultaCriteria.select(funcionario).where(montadorCriteria.equal(funcionario.get(funcionario_.getSingularAttribute("nome")), pNome));
    assertEquals(2, em.createQuery(consultaCriteria).getResultList().size());

    // select f from Funcionario f where f.Nome = 'Joao' (usando meta-modelo & parametros)
    consultaCriteria = montadorCriteria.createQuery(Funcionario.class);
    funcionario = consultaCriteria.from(Funcionario.class);
    consultaCriteria.select(funcionario).where(montadorCriteria.equal(funcionario.get(funcionario_.getSingularAttribute("nome")), "Joao"));
    assertEquals(2, em.createQuery(consultaCriteria).getResultList().size());

    // select f from Funcionario f where f.Nome = 'Joao' (usando um predicado)
    consultaCriteria = montadorCriteria.createQuery(Funcionario.class);
    funcionario = consultaCriteria.from(Funcionario.class);
    Predicate predicado = montadorCriteria.equal(funcionario.get("Nome"), "Joao");
    consultaCriteria.select(funcionario).where(predicado);
    assertEquals(2, em.createQuery(consultaCriteria).getResultList().size());

    // select f from Funcionario f where f.idade > 40
    consultaCriteria = montadorCriteria.createQuery(Funcionario.class);
    funcionario = consultaCriteria.from(Funcionario.class);
    consultaCriteria.select(funcionario).where(montadorCriteria.greaterThan(funcionario.get("idade").as(Integer.class), 40));
    assertEquals(2, em.createQuery(consultaCriteria).getResultList().size());

    // select f from Funcionario f where f.idade > 40 (usando meta-modelo)
    consultaCriteria = montadorCriteria.createQuery(Funcionario.class);
    funcionario = consultaCriteria.from(Funcionario.class);
    consultaCriteria.select(funcionario).where(montadorCriteria.greaterThan(funcionario.get(funcionario_.getSingularAttribute("idade")).as(Integer.class), 40));
    assertEquals(2, em.createQuery(consultaCriteria).getResultList().size());
   
  }
}
