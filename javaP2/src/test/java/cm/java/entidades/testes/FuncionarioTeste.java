package cm.java.entidades.testes;

import cm.java.entidades.Dependente;
import cm.java.entidades.Funcionario;
import org.junit.Test;
import java.util.Date;
import javax.persistence.Cache;
import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * 
 * @author Mauro Silva
 * @version 1.0
 * 
 * Classe de testes gerais para a entidade Funcionario
 * 
 */

public class FuncionarioTeste extends TestePersistenciaAbstrato {

  // ======================================
  // =           Testes Unitários         =
  // ======================================

    
  // testa pra verificar se o Funcionario está no cache
  @Test
  public void deveriaVerificarQueFuncionarioPossuiCache() throws Exception {

    Funcionario func = new Funcionario("Joao da Silva", "joao@sabao.com", new Date());

    // Persisto o objeto
    tx.begin();
    em.persist(func);
    tx.commit();

    assertNotNull(func.getId());

    Cache cache = emf.getCache();

    //O funcionario deveria estar no Cache
    assertTrue(cache.contains(Funcionario.class, func.getId()));

    cache.evict(Funcionario.class);

    // Depois de limpar o cache, o funcionario não deveria estar no cache
    assertFalse(cache.contains(Funcionario.class, func.getId()));
  }
  
  // Teste para verificar se o metodo de calcular idade está funcionando corretamente
  @Test
  public void deveriaVerificarIdadeFuncionario() throws Exception {

     // Instancio um objeto
    Funcionario func = new Funcionario("Joao da Silva", "joao@sabao.com", new Date());
    assertFalse(em.contains(func));

    // Persisto o objeto
    tx.begin();
    em.persist(func);
    tx.commit();
    assertTrue("deveria estar no contexto de persistencia depois de persistir", em.contains(func));
    assertEquals(new Integer(0), func.getIdade());

    // Encontro o ojeto
    func = em.find(Funcionario.class, func.getId());
    assertTrue("deveria estar no contexto de persistencia depois de encontrar", em.contains(func));
    assertEquals(new Integer(0), func.getIdade());

    // Removo a entidade
    tx.begin();
    em.remove(func);
    tx.commit();
    assertFalse("não deveria estar no contexto de persistencia após a remoção", em.contains(func));
  }
  
  
 // Teste para verificar se o ouvinte de validacao foi acionado e 
  // não permitiu que fosse inserido um funcionário com valor inválido
  //
  @Test(expected = IllegalArgumentException.class)
  public void deveriaLancarExcecaoParaNomeNulo() throws Exception {
      
    // Instancio o objeto
      Funcionario func = new Funcionario(null, "joao@sabao.com", new Date());

    // Persisto o objeto
    tx.begin();
    em.persist(func);
    tx.commit();
  }
  
  // Teste para localizar um Funcionario inserido
  @Test 
  public void deveriaEncontrarUmFuncionario() throws Exception {

    Funcionario func = new Funcionario("Joao da silva", "joao@sabao.com", 45);
    Dependente dep = new Dependente("Joaozinho", 10);
    func.setDependente(dep);

    // Persisto o objeto
    tx.begin();
    em.persist(func);
    em.persist(dep);
    tx.commit();

    assertNotNull(func.getId());
    assertNotNull(dep.getId());

    // Chamo o metodo clear
    em.clear();

    func = em.find(Funcionario.class, func.getId());
    assertNotNull(func);
  }
  
  // Teste pra verificar se o Funcionario está sendo persistido
  @Test
  public void deveriapersistirUmFuncionarioComDependente() throws Exception {

    // Cria um funcionario e adiciona um dependente
     
    Dependente dep = new Dependente("Joaozinho", new Date());
    Funcionario func = new Funcionario("Joao da Silva", "joao@sabao.com", new Date());
    func.setDependente(dep);
    
    tx.begin();
    em.persist(func);
    em.persist(dep);
    tx.commit();
    assertNotNull("O id não deveria ser nulo", func.getId());
    assertNotNull("O id não deveria ser nulo", dep.getId());

  }
 
  // teste de remoção dos objetos Funcionario e Dependente 
  @Test  
  public void deveriaRemoverUmFuncionario() throws Exception {

    Dependente dep = new Dependente("Joaozinho", new Date());
    Funcionario func = new Funcionario("Joao da Silva", "joao@sabao.com", new Date());
    func.setDependente(dep);

    // Persisto o objeto
    tx.begin();
    em.persist(func);
    em.persist(dep);
    tx.commit();

    assertNotNull(func.getId());
    assertNotNull(dep.getId());

    // Removo o objeto
    tx.begin();
    em.remove(func);
    em.remove(dep);
    tx.commit();

    assertEquals(func.getNome(), "Joao da Silva");
    assertEquals(dep.getNome(), "Joaozinho");

    func = em.find(Funcionario.class, func.getId());
    assertNull(func);
    dep = em.find(Dependente.class, dep.getId());
    assertNull(dep);
  }
 
   // Teste pra verificar se está pegando a referencia do objeto funcionario
   @Test 
  public void deveriaPegarUmaReferenciaDoFuncionario() throws Exception {

    Dependente dep = new Dependente("Joaozinho", new Date());
    Funcionario func = new Funcionario("Joao da Silva", "joao@sabao.com", new Date());
    func.setDependente(dep);

    // Persisto o objeto
    tx.begin();
    em.persist(func);
    em.persist(dep);
    tx.commit();

    assertNotNull(func.getId());
    assertNotNull(dep.getId());

    // Chamo o metodo Clear
    em.clear();

    func = em.getReference(Funcionario.class, func.getId());
    assertNotNull(func);
  }
   
  // Teste pra verificar o método reflesh no Funcionario
  @Test 
  public void deveriaPersistirFuncionarioEAtualizar() throws Exception {

    Dependente dep = new Dependente("Joaozinho", new Date());
    Funcionario func = new Funcionario("Joao da Silva", "joao@sabao.com", new Date());
    func.setDependente(dep);

    // Persisto o objeto
    tx.begin();
    em.persist(func);
    em.persist(dep);
    tx.commit();

    assertNotNull(func.getId());
    assertNotNull(dep.getId());
    
    func = em.find(Funcionario.class, func.getId());
    assertNotNull(func);
    assertEquals(func.getNome(),"Joao da Silva");

    func.setNome("Jose Xavier");
    assertEquals(func.getNome(), "Jose Xavier");

    em.refresh(func);
    assertEquals(func.getNome(), "Joao da Silva");
  }


  // Teste para verificar o método detach  em Funcionario
  @Test 
  public void deveriaIsolarUmFuncionario() throws Exception {

    Funcionario func = new Funcionario("Joao da Silva", "joao@sabao.com", new Date());


    // Persisto o objeto
    tx.begin();
    em.persist(func);
    tx.commit();
    
    assertTrue(em.contains(func));

    // isolo o object
    em.detach(func);

    assertFalse(em.contains(func));
  }

  // teste para testar os métodos de clear e merge
  @Test  
  public void deveriaLimparEDepoisMesclarUmFuncionario() throws Exception {

      Dependente dep = new Dependente("Joaozinho", new Date());
    Funcionario func = new Funcionario("Joao da Silva", "joao@sabao.com", new Date());
    func.setDependente(dep);

    // Persisto o objeto
    tx.begin();
    em.persist(func);
    em.persist(dep);
    tx.commit();

    assertTrue(em.contains(func));

    em.clear();
    assertFalse(em.contains(func));

    func.setNome("Joao da Silva");
    tx.begin();
    em.merge(func);
    tx.commit();

    em.clear();
    assertFalse(em.contains(func));

    func = em.find(Funcionario.class, func.getId());
    assertEquals(func.getNome(), "Joao da Silva");
    assertTrue(em.contains(func));

  }

  // teste pra verificar se está sendo atualizado o Funcionario
  @Test 
  public void deveriaAtualizarUmFuncionario() throws Exception {

    Funcionario func = new Funcionario("Joao da Silva", "joao@sabao.com", new Date());
    
    // Persisto o objeto
    tx.begin();
    em.persist(func);

    assertNotNull(func.getId());
    assertEquals(func.getNome(), "Joao da Silva");

    func.setNome("Xico Xavier");
    assertEquals(func.getNome(), "Xico Xavier");

    tx.commit();
  } 
  
}