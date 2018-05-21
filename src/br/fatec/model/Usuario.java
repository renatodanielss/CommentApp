package br.fatec.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="tbl_usuario")
public class Usuario implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="login")
	private String login;
	
	@Column(name="senha")
	private String senha;

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	@Override
	public boolean equals(Object object) {
        if (object == this)
            return true;
        if ((object == null) || !(object instanceof Usuario))
            return false;
 
        final Usuario usuario = (Usuario)object;
 
        if (this.login != null && usuario.getLogin() != null) {
            return this.login.equals(usuario.getLogin());
        }
        return false;
    }
}