package cadastrocliente.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ErroRequisicao {
	private String descricao;
	private String codigoErro;

}
