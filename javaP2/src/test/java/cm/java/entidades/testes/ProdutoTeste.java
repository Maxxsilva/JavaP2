package cm.java.entidades.testes;

import cm.java.entidades.Produto;
import org.junit.Test;

import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;
import java.util.Date;
import javax.persistence.Cache;
import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * 
 * @author Mauro Silva
 * @version 1.0
 * 
 * Classe de testes gerais para a entidade Produto
 * 
 */

public class ProdutoTeste extends TestePersistenciaAbstrato {

  // ======================================
  // =           Testes Unitários         =
  // ======================================

  
   // Teste pra verificar se o produto está sendo persistido
  @Test
  public void deveriaPersitirUmProduto() throws Exception {

    Produto prod = new Produto("ARROZ", 12.5F);
    
    tx.begin();
    em.persist(prod);
    tx.commit();
    assertNotNull("O id não deveria ser nulo", prod.getId());
    
  }
    // Testa a Stored Procedure de Produto chamando pelo nome
  @Test 
  public void deveChamarUmProcedimentoArmazenadoPeloNome() throws Exception {

    StoredProcedureQuery consulta = em.createNamedStoredProcedureQuery("vendasMensal");

    // Insere os parametros e executa
    consulta.setParameter("dataGeracao", new Date());
    consulta.setParameter("valorTotal", 1500);
    consulta.execute();
  }

   // Testa a Stored Procedure de Produto 
  @Test 
  public void deveChamarUmProcedimentoArmazenado() throws Exception {

    StoredProcedureQuery consulta = em.createStoredProcedureQuery("sp_vendas_mensal");
    consulta.registerStoredProcedureParameter("dataGeracao", Date.class, ParameterMode.IN);
    consulta.registerStoredProcedureParameter("valorTotal", Integer.class, ParameterMode.IN);

    // Insere os parametros e executa
    consulta.setParameter("dataGeracao", new Date());
    consulta.setParameter("valorTotal", 1500);
    consulta.execute();
  }
  
  // testa pra verificar se o Produto está no cache
  @Test
  public void deveriaVerificarQueProdutoPossuiCache() throws Exception {

    Produto prod = new Produto("Feijão", 5.00f);

    // Persisto o objeto
    tx.begin();
    em.persist(prod);
    tx.commit();

    assertNotNull(prod.getId());

    Cache cache = emf.getCache();

    //O produto deveria estar no Cache
    assertTrue(cache.contains(Produto.class, prod.getId()));

    cache.evict(Produto.class);

    // Depois de limpar o cache, o produto não deveria estar no cache
    assertFalse(cache.contains(Produto.class, prod.getId()));
  }
  
  // Teste pra verificar de acordo com o 
  @Test
  public void deveriaCriarUmProdutoEAumentarOPreco() throws Exception {

    // Cria um produto e o valor da versao (infração)deveria ser 1
    Produto prod = new Produto("ARROZ", 12.5F);
    
    tx.begin();
    em.persist(prod);
    tx.commit();
    assertNotNull("O id não deveria ser nulo", prod.getId());
    assertEquals("O numero da versão (infração) deveria ser 1", new Integer(1), prod.getInfracao());

    // Atualizo o mesmo livro
    tx.begin();
    prod.aumentoOPrecoEmCincoPorcento();
    tx.commit();
    assertEquals("O numero de versão (infração) deveria ser 2", new Integer(2), prod.getInfracao());
  }
    
  
}