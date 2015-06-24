package cm.java.entidades;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

/**
 * 
 * @author Mauro Silva
 * @version 1.0
 * 
 * Classe utilizada para validação do Nome da entidade Funcionario
 * verifica se o nome não está preenchido e retorna uma mensagem
 */


public class OuvinteValidacao {

    // ======================================
    // =      Metodos Ciclo de Vida         =
    // ======================================

    @PrePersist
    @PreUpdate
    private void validacao(Funcionario func) {
        System.out.println("OuvinteValidacao validacao()");
        if (func.getNome() == null || "".equals(func.getNome()))
            throw new IllegalArgumentException("Nome vazio ou invalido");
    }
}