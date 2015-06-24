package mbean.aula;


import com.sun.org.apache.bcel.internal.generic.GETFIELD;
import java.util.ArrayList;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import entity.aula.Usuario;
import java.io.IOException;
import javax.faces.context.FacesContext;

/**
 * 
 * @author Mauro Silva
 * 
 * Classe responsável por autenticar e cadastrar os Usuários na sessão
 */

@ManagedBean
@SessionScoped
public class UsuarioBean {

	private String usuario; 
        private String senha;
	private String email; 
        private Boolean logado = false;
	
	private ArrayList<Usuario> usuarios;
	
	
	public UsuarioBean(){
		usuarios = new ArrayList<Usuario>();
	}
	
        
        public void autenticar() throws IOException {  
            
            for (Usuario u: usuarios) {
                if (usuario.equals(u.getUsuario()) && senha.equals(u.getSenha())) {
                    FacesContext.getCurrentInstance().getExternalContext().dispatch("bemvindo.xhtml");
                    logado = true;
                    return;
                } 
            }
          
            logado = false;          
        }
        
        public void deslogar() {  
                logado = false;          
        }
        
	public String adicionarUsuario(){
			
		Usuario usu = new Usuario();
		usu.setUsuario(usuario);
		usu.setEmail(email);
                usu.setSenha(senha);
		
		usu.setId(Usuario.idSource++);
		
		usuarios.add(usu);
		
		return null;
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

        public Boolean getLogado() {
            return logado;
        }

        public void setLogado(Boolean result) {
            this.logado = result;
        }
        
	public ArrayList<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(ArrayList<Usuario> usuarios) {
		this.usuarios = usuarios;
	}
	
	
}
