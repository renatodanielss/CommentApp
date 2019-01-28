package br.fatec.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="tbl_questao")
public class Comentario implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "COMENTARIO_ID")
	@SequenceGenerator(name="COMENTARIO_ID", sequenceName="COMENTARIO_SEQ", allocationSize=1)
	@Column(name="id")
	private Integer id;
	
	@Column(name="comentario")
	private String comentario;
	
	@ManyToOne
	@JoinColumn(name="usuario")
	private Usuario usuario;	
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	@Override
	public boolean equals(Object object) {
        if (object == this)
            return true;
        if ((object == null) || !(object instanceof Comentario))
            return false;
 
        final Comentario comentario = (Comentario)object;
 
        if (this.id != null && comentario.getId() != null) {
            return this.id.equals(comentario.getId());
        }
        return false;
    }
}