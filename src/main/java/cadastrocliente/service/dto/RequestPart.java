package cadastrocliente.service.dto;

import java.io.File;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class RequestPart {

	private String nome;
	private String valor;
	@Builder.Default
	private boolean isArquivo = false;
	private File arquivo;
}
