package br.fatec.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import br.fatec.model.Usuario;

@Controller
@ManagedBean(name="webController")
@SessionScoped
public class WebController {
	
	@PostConstruct
	public void preparaDados() {
		
	}
	
	@RequestMapping(value = "/usuario/comentarios", method = RequestMethod.GET)
	public String index() {
		return "comentarios";
	}

	@RequestMapping(value = "/createComment", method = RequestMethod.POST)
	public String createComment(HttpSession session, @RequestParam("comentario") String comentario) throws ClientProtocolException, IOException {
		Usuario usuario = (Usuario)session.getAttribute("user");
		
		if (usuario != null && !usuario.getLogin().equals("") && !comentario.equals("")){
			HttpClient httpclient = HttpClients.createDefault();
			HttpPost httppost = new HttpPost("http://localhost:3000/api/comments/create");
	
			// Request parameters and other properties.
			List<NameValuePair> params = new ArrayList<NameValuePair>(2);
			params.add(new BasicNameValuePair("comentario", comentario));
			params.add(new BasicNameValuePair("usuario", usuario.getLogin()));
			httppost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
	
			//Execute and get the response.
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
	
			if (entity != null) {
			    InputStream instream = entity.getContent();
			    try {
			    } finally {
			        instream.close();
			    }
			}
		}
		return "comentarios";
	}
	
	@RequestMapping(value = "/editComment", method = RequestMethod.POST)
	public String editComment(@RequestParam("id") String id, @RequestParam("editComentario") String editComentario) throws ClientProtocolException, IOException {
		if (id != null){
			HttpClient httpclient = HttpClients.createDefault();
			HttpPost httppost = new HttpPost("http://localhost:3000/api/comments/update");
	
			// Request parameters and other properties.
			List<NameValuePair> params = new ArrayList<NameValuePair>(2);
			params.add(new BasicNameValuePair("comentario", editComentario));
			params.add(new BasicNameValuePair("id", id));
			httppost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
	
			//Execute and get the response.
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
	
			if (entity != null) {
			    InputStream instream = entity.getContent();
			    try {
			    } finally {
			        instream.close();
			    }
			}
		}
		return "comentarios";
	}
	
	@RequestMapping(value = "/deleteComment", method = RequestMethod.POST)
	public String deleteComment(@RequestParam("id") String id) throws ClientProtocolException, IOException {
		if (id != null){
			HttpClient httpclient = HttpClients.createDefault();
			HttpPost httppost = new HttpPost("http://localhost:3000/api/comments/delete");
	
			// Request parameters and other properties.
			List<NameValuePair> params = new ArrayList<NameValuePair>(2);
			params.add(new BasicNameValuePair("id", id));
			httppost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
	
			//Execute and get the response.
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
	
			if (entity != null) {
			    InputStream instream = entity.getContent();
			    try {
			    } finally {
			        instream.close();
			    }
			}
		}
		return "comentarios";
	}
	
	@RequestMapping(value = "/logout", method = RequestMethod.POST)
	public String logout(HttpSession session, HttpServletRequest request) throws IOException{
		session.removeAttribute("user");
		return "redirect:/pages/login.xhtml";
	}
}