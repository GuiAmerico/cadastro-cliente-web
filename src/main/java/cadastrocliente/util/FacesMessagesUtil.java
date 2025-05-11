package cadastrocliente.util;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public class FacesMessagesUtil implements Serializable {

	private static final long serialVersionUID = 1L;

	
	public void add(String msg, FacesMessage.Severity severity) {
		FacesMessage facesMessage = new FacesMessage(msg); 
		facesMessage.setSeverity(severity);
		FacesContext.getCurrentInstance().addMessage(null, facesMessage);
	}
}
