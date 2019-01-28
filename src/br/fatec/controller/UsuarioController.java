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
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.fatec.dao.UsuarioDAO;
import br.fatec.model.Usuario;
import br.fatec.util.FacesUtil;
import br.fatec.util.SessionUtil;

@SuppressWarnings("serial")
@ManagedBean(name="usuarioController")
@SessionScoped
@Controller
public class UsuarioController extends HttpServlet{
	private String login;
	private String senha;
	private Boolean isLogged;
	private Usuario usuario;
	private Usuario newUsuario;
	private Usuario currentUsuario;
	private List<Usuario> usuarios;
	private UsuarioDAO usuarioDAO;
	private String pesquisa;
	private boolean showNewButton;
	
	public UsuarioController(){
		
	}
	
	@PostConstruct
	public void preparaDados()
	{
		this.usuario = new Usuario();
		this.usuarioDAO = new UsuarioDAO();
		this.currentUsuario = new Usuario();
		this.newUsuario = new Usuario();
		this.login = "";
		this.senha = "";
		this.mostrarSalvar();
	}

	public void entrar() throws ServletException, IOException {
		this.usuario = this.usuarioDAO.buscar(this.login);
		
		if (this.usuario != null){
			if (this.usuario.getLogin().equals(this.login) && this.usuario.getSenha().equals(this.senha)){
				SessionUtil.getSession();
				SessionUtil.setParam("user", this.usuario);
				
				HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
				
				ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
				String from = (String)externalContext.getSessionMap().get("from");
				
				System.out.println("from: " + from);
				Usuario usuario = (Usuario)SessionUtil.getParam("user");
				System.out.println("Usu�rio Username: " + usuario.getLogin());
				
				if (from != null && !from.isEmpty()) {
				    response.sendRedirect(from);
				} else {
				    response.sendRedirect("/pages/index.xhtml");
				}
			} else{
				FacesUtil.addErrorMessage("Usuário ou senha incorretos!");
			}
		}
		else{
			FacesUtil.addErrorMessage("Usuário ou senha incorretos!");
		}
	}
	
	public void logout() throws IOException{
		System.out.println("Logout");
		SessionUtil.getSession();
		SessionUtil.remove("user");
		//SessionUtil.invalidate();
		FacesContext.getCurrentInstance().getExternalContext().redirect("/pages/login.xhtml");
	}
	
	@RequestMapping(value = "/logout", method = RequestMethod.POST)
	public String logoutJSP(HttpSession session, HttpServletRequest request) throws IOException{
		session.removeAttribute("user");
		return "redirect:/pages/login.xhtml";
	}
	
	public void setIsLogged(Boolean isLogged) {
		this.isLogged = isLogged;
	}

	public Boolean getIsLogged(){
		this.isLogged = SessionUtil.getParam("user") == null?false:true;
		return this.isLogged;
	}

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

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Usuario getNewUsuario() {
		return newUsuario;
	}

	public void setNewUsuario(Usuario newUsuario) {
		this.newUsuario = newUsuario;
	}

	public Usuario getCurrentUsuario() {
		return currentUsuario;
	}

	public void setCurrentUsuario(Usuario currentUsuario) {
		this.currentUsuario = currentUsuario;
	}

	public List<Usuario> getUsuarios() {
		if (this.usuarios == null){
			this.usuarios = this.usuarioDAO.listar();
		}
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public UsuarioDAO getUsuarioDAO() {
		return usuarioDAO;
	}

	public void setUsuarioDAO(UsuarioDAO usuarioDAO) {
		this.usuarioDAO = usuarioDAO;
	}

	public String getPesquisa() {
		return pesquisa;
	}

	public void setPesquisa(String pesquisa) {
		this.pesquisa = pesquisa;
	}

	public boolean getShowNewButton() {
		return showNewButton;
	}

	public void setShowNewButton(boolean showNewButton) {
		this.showNewButton = showNewButton;
	}
	
	public void mostrarAlterar(){
		this.showNewButton = false;
	}
		  
	public void mostrarSalvar(){
	     this.showNewButton = true;
	}
	
	public void goToUsuario() throws Exception{
		this.limparCampos();
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		externalContext.redirect("usuario.xhtml");
	}
	
	//validar - m�todo cadastrar atualizado. Nesta vers�o � verificado se o m�todo cadastrarCampos verificou algum erro, caso sim, este m�todo exibir� os erros na p�gina.
	//As mesmas altera��es dever�o ser feitas no m�todo alterar.
	public void cadastrar() throws IOException, ParseException
	{
		String mensagem = validarCampos(this.newUsuario);
		if (mensagem.length() == 0){
			if (usuarioDAO.inserir(this.newUsuario)){
				setUsuario(null);
				System.out.println("Usu�rio inserido com sucesso!");
				this.newUsuario = new Usuario();
				
				//validar - importante adicionar o redirect com o par�metro origin=nome da entidade
				//(letras min�ssculas, sem espa�os ou caracteres especiais, por exemplo:
				ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
				externalContext.redirect("cadastroconcluido.xhtml?faces-redirect=true&origin=usuario");
			}
			else
				System.out.println("Erro na inser��o!");
			
			this.newUsuario = new Usuario();
		}
		else{
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erros!<br/>", mensagem));
		}
	}
	
	private void limparCampos(){
		
		this.getNewUsuario().setLogin(null);
		this.getNewUsuario().setSenha(null);
		
		this.mostrarSalvar();
	}
	
	public void iniciaAlterar() throws IOException
	{
		if (this.newUsuario != null)
		{
			try{
				this.getNewUsuario().setLogin(this.getCurrentUsuario().getLogin());
				this.getNewUsuario().setSenha(this.getCurrentUsuario().getSenha());
				
				mostrarAlterar();
				
				ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
				externalContext.redirect("usuario.xhtml?faces-redirect=true&redirect=1");
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
	public void alterar() throws IOException, ParseException
	{
		String mensagem = validarCampos(this.newUsuario);
		if (mensagem.length() == 0){
			if (usuarioDAO.alterar(this.newUsuario)){
				setUsuario(null);
				System.out.println("Usu�rio alterado com sucesso!");
				this.newUsuario = new Usuario();
				
				ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
				externalContext.redirect("cadastroconcluido.xhtml?faces-redirect=true&origin=usuario");
			}
			else
				System.out.println("Erro na altera��o!");
			}
		else{
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erros!<br/>", mensagem));
		}
	}
	
	public void excluir() throws IOException
	{
		if (usuarioDAO.excluir(this.currentUsuario)){
			setUsuario(null);
			ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
			externalContext.redirect("usuariolist.xhtml");
			System.out.println("Usu�rio excluido com sucesso!");
		}
		else
			System.out.println("Erro na exclus�o!");
	}
	
	//validar - m�todo para valida��o de atributos do objeto associado � view,
	//este m�todo ser� chamado pelo m�todo cadastrar e alterar
	public String validarCampos(Usuario usuario) throws ParseException{
		String mensagemErro = "";
		if (usuario.getLogin().trim().length() == 0)
			mensagemErro += "<br/>-Preencher login";
		if (usuario.getSenha().trim().length() == 0)
			mensagemErro += "<br/>-Preencher senha";
		return mensagemErro;
	}
	
	public void pesquisar(){
		this.usuarios = this.usuarioDAO.listar(this.pesquisa);
		if (this.usuarios == null){
			this.usuarios = this.getUsuarios();
		}
	}
}