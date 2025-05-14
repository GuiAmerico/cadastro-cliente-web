package cadastrocliente.model;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Cliente {


	private Long id;

	private String nome;

	private String email;

//	private String logotipo;
	
	private List<Endereco> enderecos; 
	
	
	public String getEnderecosFormatado(){
//		List<String> logradouros = enderecos.stream().map(endereco -> endereco.getLogradouro()).collect(Collectors.toList());
//		return String.join("; ", logradouros);
		return "Rua A; Rua B";
	}
}
