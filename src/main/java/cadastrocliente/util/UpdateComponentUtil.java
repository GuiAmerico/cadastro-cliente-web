package cadastrocliente.util;

import java.io.Serializable;

import org.primefaces.PrimeFaces;

public class UpdateComponentUtil implements Serializable {

	private static final long serialVersionUID = 1L;

	public static void update(String... ids) {
		PrimeFaces.current().ajax().update(ids);
	}
}
