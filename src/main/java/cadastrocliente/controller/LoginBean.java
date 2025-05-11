package cadastrocliente.controller;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import com.fasterxml.jackson.databind.ObjectMapper;

import cadastrocliente.config.AppProperties;
import cadastrocliente.controller.dto.LoginRequest;
import cadastrocliente.controller.dto.LoginResponse;
import cadastrocliente.model.Usuario;
import cadastrocliente.util.FacesMessagesUtil;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

@Named
@SessionScoped
public class LoginBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private final Usuario usuario = new Usuario();
	@Inject
	private ObjectMapper mapper;
	@Inject
	private FacesMessagesUtil messages;

	public Usuario getUsuario() {
		return usuario;
	}

	public String efetuarLogin() {
		OkHttpClient client = new OkHttpClient();

		LoginRequest loginRequest = new LoginRequest(usuario.getEmail(), usuario.getSenha());
		String url = AppProperties.BASE_URL.concat("/publico/v1/autenticacao/login");

		try {
			String jsonString = mapper.writeValueAsString(loginRequest);
			RequestBody body = RequestBody.create(jsonString, MediaType.parse("application/json; charset=utf-8"));
			Request request = new Request.Builder().url(url).post(body).build();

			try (Response resp = client.newCall(request).execute()) {
				String json = resp.body().string();
				LoginResponse loginResponse = mapper.readValue(json, LoginResponse.class);
				setToken(loginResponse.getJwtToken());
			}
		} catch (Exception e) {
			messages.add("Erro ao efetuar login.", FacesMessage.SEVERITY_ERROR);
			return null;
		}
		return "Dashboard?faces-redirect=true";
	}

	public void setToken(String token) {
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("token", token);
	}
}
