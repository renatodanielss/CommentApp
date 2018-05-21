package br.fatec.dao;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import br.fatec.model.Usuario;

public class UsuarioDAO implements Serializable{
	private EntityManagerFactory factory;
	private EntityManager manager;
	private static final long serialVersionUID = 1L;
	
	public UsuarioDAO()
	{
		
	}
	
	public boolean inserir(Usuario usuario)
	{	
		try
		{
			open();
			this.manager.getTransaction().begin();    
	 		this.manager.persist(usuario);
			this.manager.getTransaction().commit();
			return true;
		}
		catch(Exception ex)
		{
			if (this.manager.getTransaction().isActive())
				this.manager.getTransaction().rollback();
			return false;
		}finally{
			close();
		}
	}
	
	public boolean alterar(Usuario usuario)
	{
		try
		{
			open();
			this.manager.getTransaction().begin();    
			this.manager.merge(usuario);
			this.manager.getTransaction().commit();
			return true;
		}
		catch(Exception ex)
		{
			if (this.manager.getTransaction().isActive())
				this.manager.getTransaction().rollback();
			return false;
		}finally{
			close();
		}
	}
	
	public Usuario buscar(String login)
	{
		Usuario usuario = null;
		String query = "select * from tbl_usuario where login = '" + login + "'";
		
		try
		{
			open();
			this.manager.getTransaction().begin();
	 		usuario = (Usuario)this.manager.createNativeQuery(query, new Usuario().getClass()).getSingleResult();
	 		this.manager.getTransaction().commit();
		}
		catch(Exception ex){
			usuario = null;
			if (this.manager.getTransaction().isActive())
				this.manager.getTransaction().rollback();
		}finally{
			close();
		}

		return usuario;
	}
	
	@SuppressWarnings("unchecked")
	public List<Usuario> listar()
	{		
		List<Usuario> listUsuario = null;
		String query = "select * from tbl_usuario";
		
		try
		{
			open();
			this.manager.getTransaction().begin();
			listUsuario = this.manager.createNativeQuery(query, new Usuario().getClass()).getResultList();
			this.manager.getTransaction().commit();
		}
		catch(Exception ex)
		{
			listUsuario = null;
			if (this.manager.getTransaction().isActive())
				this.manager.getTransaction().rollback();
		}finally{
			close();
		}
		
		return listUsuario;
	}
	
	@SuppressWarnings("unchecked")
	public List<Usuario> listar(String login)
	{		
		List<Usuario> listUsuario = null;
		String query = "SELECT * FROM tbl_usuario WHERE lower(login) LIKE lower('%" + login.toLowerCase() + "%')";
		
		try
		{
			open();
			this.manager.getTransaction().begin();
			listUsuario = this.manager.createNativeQuery(query, new Usuario().getClass()).getResultList();
			this.manager.getTransaction().commit();
		}
		catch(Exception ex)
		{
			listUsuario = null;
			if (this.manager.getTransaction().isActive())
				this.manager.getTransaction().rollback();
		}finally{
			close();
		}
		
		return listUsuario;
	}
	
	public boolean excluir(Usuario usuario)
	{
		try
		{
			open();
			this.manager.getTransaction().begin();
			this.manager.remove(manager.getReference(usuario.getClass(), usuario.getLogin()));
			this.manager.getTransaction().commit();
			return true;
		}
		catch(Exception ex)
		{
			if (this.manager.getTransaction().isActive())
				this.manager.getTransaction().rollback();
			return false;
		}finally{
			close();
		}
	}
	
	public void open()
	{
		this.factory = Persistence.createEntityManagerFactory("onepageenterprises");
		this.manager = factory.createEntityManager();
	}
	
	public void close()
	{
		this.manager.close();
		this.factory.close();
	}
}