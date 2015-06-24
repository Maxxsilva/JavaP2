package cm.java.entidades;

import java.util.Date;
import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * 
 * @author Mauro Silva
 * @version 1.0
 * 
 * Entidade Dependente sem cache utilizada para testar a manipulação de entidades
 * com relacionamento unidirecinal de Funcionario e Dependente
 */

@Entity
@Table(name = "t_dependente")
@Cacheable(false)
public class Dependente {

  // ======================================
  // =             Atributos              =
  // ======================================

  @Id
  @GeneratedValue
  private Long id;
  private String nome;
  @Temporal(TemporalType.DATE)
  private Date dataNasc;
  private transient Integer idade;
  


  // ======================================
  // =            Construtores            =
  // ======================================

  public Dependente() {
  }

  public Dependente(String nomeParam, Date dataNascParam) {
    nome = nomeParam;
    dataNasc = dataNascParam;
  }
  
   public Dependente(String nomeParam, Integer idadeParam) {
    nome = nomeParam;
    idade = idadeParam;
  }

  // ======================================
  // =          Getters & Setters         =
  // ======================================

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Date getDataNasc() {
        return dataNasc;
    }

    public void setDataNasc(Date dataNasc) {
        this.dataNasc = dataNasc;
    }

    public Integer getIdade() {
        return idade;
    }

    public void setIdade(Integer idade) {
        this.idade = idade;
    }


}