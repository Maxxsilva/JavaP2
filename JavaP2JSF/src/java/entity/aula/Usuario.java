package entity.aula;

import java.io.Serializable;

/**
 * 
 * @author Mauro Silva
 * 
 * Classe de Usuario utilizada na tela de login
 */


public class Usuario implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7218436701209511575L;
	
	public static int idSource = 0;
	
	private Integer id;
	private String usuario;
	private String email;
	private String senha;
	
		
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getSenha() {
            return senha;
        }

        public void setSenha(String senha) {
            this.senha = senha;
        }

	
	

}
