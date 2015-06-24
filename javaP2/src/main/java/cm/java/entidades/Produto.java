package cm.java.entidades;

import java.util.Date;
import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.Table;
import javax.persistence.Version;

/**
 * 
 * @author Mauro Silva
 * @version 1.0
 * 
 * Classe Produto possui os exemplos:
 * - Uso da notação @Cacheable(true) para armazenar em cache  
 * - Uso da notação @version para criar um versionamento e simular uma "infração" aumentando o preço do produto
 * - Uso da notação @NamedStoredProcedureQuery para utilizar um procedimento de venda mensal de produtos
 */

@Entity
@Table(name = "t_produto")
@Cacheable(true)
@NamedStoredProcedureQuery(name = "vendasMensal", procedureName = "sp_vendas_mensal",
        parameters = {
                @StoredProcedureParameter(name = "dataGeracao", mode = ParameterMode.IN, type = Date.class),
                @StoredProcedureParameter(name = "valorTotal", mode = ParameterMode.IN, type = Integer.class)
        })

public class Produto {

  // ======================================
  // =             Atributos              =
  // ======================================

  @Id
  @GeneratedValue
  private Long id;
  private String descricao;
  private Float preco;
  @Version
  private Integer infracao;



  // ======================================
  // =            Construtores            =
  // ======================================

  public Produto() {
  }
  

  public Produto(String descricaoParam, Float precoParam) {
    descricao = descricaoParam;
    preco = precoParam;
  }
  
  
  public void aumentoOPrecoEmCincoPorcento() {
    preco = preco * 1.05f;
  }

  public void aumentoPrecoEmDezPorcento() {
    preco = preco + 1.10f;
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
  
   public String getDescricao() {
    return descricao;
  }

  public void setDescricao(String descricao) {
    this.descricao = descricao;
  }

  public Float getPreco() {
    return preco;
  }

  public void setPreco(Float preco) {
    this.preco = preco;
  }

   public Integer getInfracao() {
        return infracao;
    }

    public void setInfracao(Integer infracao) {
        this.infracao = infracao;
    }


}