package br.fatec.util;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

@ManagedBean(name="cadastroConcluidoController")
@RequestScoped
public class CadastroConcluido {
	private String pageMessage;
	private String template;
	
	public CadastroConcluido(){
		configurarPagina();
	}
	
	public String getPageMessage() {
		return pageMessage;
	}

	public void setPageMessage(String pageMessage) {
		this.pageMessage = pageMessage;
	}

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}
	
	private void configurarPagina(){
		/*FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpServletRequest req = (HttpServletRequest)facesContext.getExternalContext().getRequest();*/
		
		String templateUrl = "/template/";
		
		FacesContext facesContext = FacesContext.getCurrentInstance();
		String redirectValue = (String) facesContext.getExternalContext().getRequestParameterMap().get("origin");
		switch (redirectValue){
			
		case "usuario":
			setPageMessage("Usuário cadastrado com sucesso");
			setTemplate(templateUrl + "templateusuario.xhtml");
			break;
		case "comentario":
			setPageMessage("Comentário inserido com sucesso");
			setTemplate(templateUrl + "templateusuario.xhtml");
			break;
		default:
			setPageMessage("");
			setTemplate("");
		}
	}
}