package br.fatec.dao;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import br.fatec.model.Comentario;

public class ComentarioDAO implements Serializable{
	private EntityManagerFactory factory;
	private EntityManager manager;
	private static final long serialVersionUID = 1L;
	
	public ComentarioDAO() {
		open();
	}
	
	public boolean inserir(Comentario comentario)
	{
		try
		{
			this.manager.getTransaction().begin();
	 		this.manager.persist(comentario);
			this.manager.getTransaction().commit();
			return true;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			if (this.manager.getTransaction().isActive())
				this.manager.getTransaction().rollback();
			return false;
		}
	}
	
	public boolean alterar(Comentario comentario)
	{
		try
		{
			this.manager.getTransaction().begin();    
			this.manager.merge(comentario);
			this.manager.getTransaction().commit();
			return true;
		}
		catch(Exception ex)
		{
			if (this.manager.getTransaction().isActive())
				this.manager.getTransaction().rollback();
			ex.printStackTrace();
			return false;
		}
	}
	
	public Comentario buscar(Integer id)
	{
		Comentario comentario = null;
		
		try
		{
			this.manager.getTransaction().begin();
			comentario = this.manager.find(Comentario.class, id);
	 		this.manager.getTransaction().commit();
		}
		catch(Exception ex){
			comentario = null;
			if (this.manager.getTransaction().isActive())
				this.manager.getTransaction().rollback();
		}

		return comentario;
	}
	
	@SuppressWarnings("unchecked")
	public List<Comentario> listar()
	{		
		List<Comentario> listComentario = null;
		String query = "SELECT * FROM tbl_comentario ORDER BY datahora DESC";
		
		try
		{
			this.manager.getTransaction().begin();
			listComentario = this.manager.createNativeQuery(query, new Comentario().getClass()).getResultList();
			this.manager.getTransaction().commit();
		}
		catch(Exception ex)
		{
			listComentario = null;
			if (this.manager.getTransaction().isActive())
				this.manager.getTransaction().rollback();
		}
		
		return listComentario;
	}
	
	@SuppressWarnings("unchecked")
	public List<Comentario> listar(String login)
	{
		List<Comentario> listComentario = null;
		String query = "SELECT * FROM tbl_comentario WHERE usuario = " + login + " ORDER BY datahora DESC";
		
		try
		{
			this.manager.getTransaction().begin();
			listComentario = this.manager.createNativeQuery(query, new Comentario().getClass()).getResultList();
			this.manager.getTransaction().commit();
		}
		catch(Exception ex)
		{
			listComentario = null;
			if (this.manager.getTransaction().isActive())
				this.manager.getTransaction().rollback();
		}
		
		return listComentario;
	}
	
	public boolean excluir(Comentario comentario)
	{
		try
		{
			this.manager.getTransaction().begin();
			this.manager.remove(manager.getReference(comentario.getClass(), comentario.getId()));
			this.manager.getTransaction().commit();
			return true;
		}
		catch(Exception ex)
		{
			if (this.manager.getTransaction().isActive())
				this.manager.getTransaction().rollback();
			return false;
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