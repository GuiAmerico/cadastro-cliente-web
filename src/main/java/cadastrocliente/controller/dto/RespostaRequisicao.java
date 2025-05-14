package cadastrocliente.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class RespostaRequisicao<T> {

	private boolean sucesso;
	private String messagem;
	private T corpoRequisição;

	public RespostaRequisicao(boolean sucesso) {
		this.sucesso = sucesso;
	}

	public RespostaRequisicao(boolean sucesso, String messagem) {
		this.sucesso = sucesso;
		this.messagem = messagem;
	}

}
