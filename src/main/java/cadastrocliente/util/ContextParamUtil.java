package cadastrocliente.util;

import java.io.Serializable;

import javax.faces.context.FacesContext;

public class ContextParamUtil implements Serializable {

	private static final long serialVersionUID = 1L;

	public static Object get(String param) {
		return FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(param);
	}
}
