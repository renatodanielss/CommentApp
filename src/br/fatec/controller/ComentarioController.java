package br.fatec.controller;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import br.fatec.dao.ComentarioDAO;
import br.fatec.model.Comentario;
import br.fatec.model.Usuario;
import br.fatec.util.SessionUtil;

@ManagedBean(name="comentarioController")
@SessionScoped
public class ComentarioController {
	private Comentario comentario;
	private Comentario newComentario;
	private Comentario currentComentario;
	private List<Comentario> comentarios;
	private ComentarioDAO comentarioDao;
	private String pesquisa;
	private boolean showNewButton;
	
	public ComentarioController()
	{
		
	}
	
	@PostConstruct
	public void preparaDados()
	{
		this.comentarioDao = new ComentarioDAO();
		this.newComentario = new Comentario();
		this.currentComentario = new Comentario();
		mostrarSalvar();
	}

	public Comentario getNewComentario() {
		return newComentario;
	}

	public Comentario getComentario() {
		return comentario;
	}

	public void setComentario(Comentario comentario) {
		this.comentario = comentario;
	}

	public void setNewComentario(Comentario newComentario) {
		this.newComentario = newComentario;
	}

	public Comentario getCurrentComentario() {
		return currentComentario;
	}

	public void setCurrentComentario(Comentario currentComentario) {
		this.currentComentario = currentComentario;
	}

	public ComentarioDAO getComentarioDao() {
		return comentarioDao;
	}

	public void setComentarioDao(ComentarioDAO comentarioDao) {
		this.comentarioDao = comentarioDao;
	}

	public String getPesquisa() {
		return pesquisa;
	}

	public void setPesquisa(String pesquisa) {
		this.pesquisa = pesquisa;
	}

	public List<Comentario> getComentarios() 
	{
		if (this.comentarios == null){
			this.comentarios = comentarioDao.listar();
		}
		return comentarios;
	}

	public void setComentarios(List<Comentario> comentarios) {
		this.comentarios = comentarios;
	}
	
	public boolean getShowNewButton(){
		return showNewButton;
	}
	
	public void mostrarAlterar(){
		this.showNewButton = false;
	}
		  
	public void mostrarSalvar(){
	     this.showNewButton = true;
	}
	
	public void cadastrar() throws IOException, ParseException
	{
		String mensagem = validarCampos(this.newComentario);
		if (mensagem.length() == 0){
			this.newComentario.setUsuario((Usuario)SessionUtil.getParam("user"));
			if (comentarioDao.inserir(this.newComentario)){
				System.out.println("Comentário inserido com sucesso!");
				FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("comentarioController");
				
				ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
				externalContext.redirect("cadastroconcluido.xhtml?faces-redirect=true&origin=comentario");
			}
			else{
				System.out.println("Erro na inserção!");
			}
		}
		else{
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erros!<br/>", mensagem));
		}
	}
	
	public void iniciaAlterar() throws IOException
	{
		if (this.newComentario != null)
		{
			try{
				this.newComentario.setId(this.getCurrentComentario().getId());
				this.newComentario.setComentario(this.getCurrentComentario().getComentario());
				this.newComentario.setUsuario(this.getCurrentComentario().getUsuario());
				
				mostrarAlterar();
				ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
				externalContext.redirect("comentario.xhtml?faces-redirect=true&redirect=1");
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
	public void alterar() throws IOException, ParseException
	{
		String mensagem = validarCampos(this.newComentario);
		if (mensagem.length() == 0){
			Usuario usuarioSessao = (Usuario)SessionUtil.getParam("user");
			System.out.println(usuarioSessao.getLogin() + " = " + this.getNewComentario().getUsuario());
			if (usuarioSessao.equals(this.getNewComentario().getUsuario())){
				this.newComentario.setUsuario((Usuario)SessionUtil.getParam("user"));
				if (comentarioDao.alterar(this.newComentario)){
					setComentarios(null);
					System.out.println("Comentário alterado com sucesso!");
					this.newComentario = new Comentario();
					FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("comentarioController");
					
					ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
					externalContext.redirect("cadastroconcluido.xhtml?faces-redirect=true&origin=comentario");
				}
				else
					System.out.println("Erro na alteração!");
			}
			else{
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro!<br/>", "Só é permitido editar comentários que você mesmo criou!"));
			}
		}
		else{
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erros!<br/>", mensagem));
		}
	}
	
	private void limparCampos(){
		this.newComentario.setId(null);;
		this.newComentario.setComentario(null);
		this.newComentario.setUsuario(null);
		
		this.mostrarSalvar();
	}
	
	public void excluir() throws IOException
	{
		Usuario usuarioSessao = (Usuario)SessionUtil.getParam("user");
		if (usuarioSessao.equals(this.getCurrentComentario().getUsuario())){
			if (comentarioDao.excluir(this.currentComentario)){
				setComentarios(null);
				ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
				externalContext.redirect("comentariolist.xhtml");
				System.out.println("Comentário excluido com sucesso!");
			}
			else{
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro!<br/>", "Não foi possível excluir!<br>&nbsp;Provavelmente há provas ou outros elementos associados a esta questão."));
				System.out.println("Erro na exclusão!");
			}
		}
		else{
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro!<br/>", "Só é permitido excluir questões que você mesmo criou!"));
		}
	}
	
	public void goToComentario() throws Exception{
		limparCampos();
		System.out.println("goToComentario");
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		externalContext.redirect("Comentario.xhtml");
	}

	public String validarCampos(Comentario comentario) throws ParseException{
		String mensagemErro = "";
		if (comentario.getComentario().trim().length() == 0)
			mensagemErro += "<br/>-Preencher comentário";
		return mensagemErro;
	}
	
	public void pesquisar(){
		this.comentarios = this.comentarioDao.listar(this.pesquisa);
		if (this.comentarios == null){
			this.comentarios = this.getComentarios();
		}
	}
}