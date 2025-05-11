package cadastrocliente.model;

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

	private String logotipo;
}
