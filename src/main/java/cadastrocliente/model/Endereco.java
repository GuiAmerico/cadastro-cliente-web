package cadastrocliente.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Endereco implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;

	private String logradouro;

	public Endereco(String logradouro) {
		this.logradouro = logradouro;
	}
	
	
}
