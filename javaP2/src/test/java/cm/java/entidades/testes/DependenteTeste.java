
package cm.java.entidades.testes;

import cm.java.entidades.Dependente;
import java.util.Date;
import javax.persistence.Cache;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 * 
 * @author Mauro Silva
 * @version 1.0
 * 
 * Classe de testes gerais para a entidade Dependente
 * 
 */

public class DependenteTeste extends TestePersistenciaAbstrato{
 
  // Teste para verificar se está persistindo o dado  
  @Test
  public void deveriaPersistirUmDependente() throws Exception {

    Dependente dep = new Dependente("Joaozinho", new Date());
  
    // Persisto o objeto
    tx.begin();
    em.persist(dep);
    tx.commit();

    assertNotNull(dep.getId());
  }
  
  // Teste pra verificar a inserção e remoção do objeto
   @Test 
  public void deveriaVerificarSeContemUmDependente() throws Exception {

     Dependente dep = new Dependente("Joaozinho", new Date());
 
    // Persisto o objeto
    tx.begin();
    em.persist(dep);
    tx.commit();

    assertTrue(em.contains(dep));

    // Removo o objeto
    tx.begin();
    em.remove(dep);
    tx.commit();

    assertFalse(em.contains(dep));
  }

  
   // testa pra verificar se o Dependente não está no cache
  @Test
  public void deveriaVerificarQueDependenteNãoPossuiCache() throws Exception {

   Dependente dep = new Dependente("Joaozinho", new Date());

    // Persisto o objeto
    tx.begin();
    em.persist(dep);
    tx.commit();

    assertNotNull(dep.getId());

    Cache cache = emf.getCache();

    //O dependente não deveria estar no Cache
    assertFalse(cache.contains(Dependente.class, dep.getId()));

   
  }
    
}
