package cm.java.entidades;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.PostLoad;
import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 * 
 * @author Mauro Silva
 * @version 1.0
 * 
 * Classe Produto possui os exemplos:
 * - Uso da notação @Cacheable(true) para armazenar em cache 
 * - Uso do ouvinteValidação para validar o Nome 
 * - Uso de @NamedQueries para fazer varios tipos de consultas
 * - Manipulação de entidadecom relacionamento unidirecinal de Funcionario e Dependente
 * - Utilização de notação do ciclo de vida para calcular a idade do funcionario
 */


@Entity
@EntityListeners({OuvinteValidacao.class})
@Table(name = "t_funcionario")
@Cacheable(true)
@NamedQueries({
        @NamedQuery(name = "encontreTodos", query = "select f from Funcionario f"),
        @NamedQuery(name = Funcionario.ENCONTRE_TODOS, query = "select f from Funcionario f"),
        @NamedQuery(name = "encontreJoao", query = "select f from Funcionario f where f.nome = 'Joao'"),
        @NamedQuery(name = "encontreComParametro", query = "select f from Funcionario f where f.nome = :pnome")
})
@NamedNativeQueries({
        @NamedNativeQuery(name = "encontreTodosNativo", query = "select * from JPQL_FUNCIONARIO", resultClass = Funcionario.class)
})
public class Funcionario {

    public static final String ENCONTRE_TODOS = "Funcionario.encontreTodos";
  // ======================================
  // =             Atributos              =
  // ======================================

  @Id
  @GeneratedValue
  private Long id;
  private String nome;
  private String email;
  @Temporal(TemporalType.DATE)
  private Date dataNascimento;
  @Transient
  private Integer idade;
  @OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
  @JoinColumn(name = "c_dependente")
  private Dependente dependente;

  // ======================================
  // =            Construtores            =
  // ======================================

  public Funcionario() {
  }

  public Funcionario(String nomeParam, String emailParam, Date dataNascParam) {
    nome = nomeParam;
    email = emailParam;
    dataNascimento = dataNascParam;
  }
  
    public Funcionario(String nomeParam, String emailParam, Integer idadeParam) {
    nome = nomeParam;
    email = emailParam;
    idade = idadeParam;
  }
  
   // ======================================
  // =       Metodos Ciclo de Vida        =
  // ======================================

  @PostLoad
  @PostPersist
  @PostUpdate
  public void calculaIdade() {
    System.out.println("calculaIdade()");
    if (dataNascimento == null) {
      idade = null;
      return;
    }

    Calendar nascimento = new GregorianCalendar();
    nascimento.setTime(dataNascimento);
    Calendar hoje = new GregorianCalendar();
    hoje.setTime(new Date());
    int ajuste = 0;
    if (hoje.get(Calendar.DAY_OF_YEAR) - nascimento.get(Calendar.DAY_OF_YEAR) < 0) {
      ajuste = -1;
    }
    idade = hoje.get(Calendar.YEAR) - nascimento.get(Calendar.YEAR) + ajuste;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public Integer getIdade() {
        return idade;
    }

    public void setIdade(Integer idade) {
        this.idade = idade;
    }

    public Dependente getDependente() {
        return dependente;
    }

    public void setDependente(Dependente dependente) {
        this.dependente = dependente;
    }

  
  
}