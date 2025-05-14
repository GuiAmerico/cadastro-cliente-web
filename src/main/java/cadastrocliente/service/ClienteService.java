package cadastrocliente.service;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import org.primefaces.model.file.UploadedFile;

import com.fasterxml.jackson.core.type.TypeReference;

import cadastrocliente.config.AppProperties;
import cadastrocliente.controller.dto.RespostaRequisicao;
import cadastrocliente.model.Cliente;
import cadastrocliente.model.Endereco;
import cadastrocliente.service.dto.RequestPart;
import cadastrocliente.service.enums.HttpMethod;
import cadastrocliente.util.ContextParamUtil;

//@RequestScoped
public class ClienteService implements Serializable {

	private static final long serialVersionUID = 1L;
	@Inject
	private OkHttpServiceImpl okHttpServiceImpl;

	public RespostaRequisicao<Cliente> criarCliente(Cliente cliente, UploadedFile uploadedFile) {
		final String url = AppProperties.BASE_URL.concat("/v1/clientes");
		final String token = (String) ContextParamUtil.get("token");
		HashMap<String, String> headers = new HashMap<>();
		headers.put("Authorization", "Bearer ".concat(token));

		List<RequestPart> parts = new ArrayList<RequestPart>();
		try {
			Path tempFile = Files.createTempFile(uploadedFile.getFileName(), ".png");

			File file = Files.write(tempFile, uploadedFile.getContent()).toFile();
			parts.add(RequestPart.builder().nome("nome").valor(cliente.getNome()).build());
			parts.add(RequestPart.builder().nome("email").valor(cliente.getEmail()).build());

			for (int i = 0; i < cliente.getEnderecos().size(); i++) {
				Endereco endereco = cliente.getEnderecos().get(i);
				parts.add(RequestPart.builder().nome("enderecos[" + i + "].logradouro").valor(endereco.getLogradouro())
						.build());
			}
			parts.add(RequestPart.builder().nome("logotipo").isArquivo(true).arquivo(file).build());
			okHttpServiceImpl.efetuarRequest(null, parts, url, HttpMethod.POST, headers);
			file.delete();
			return new RespostaRequisicao<Cliente>(true);
		} catch (Exception e) {
			return new RespostaRequisicao<Cliente>(false, e.getMessage());
		}
	}

	public RespostaRequisicao<Void> atualizarCliente(Cliente cliente, UploadedFile uploadedFile) {
		final String url = AppProperties.BASE_URL.concat("/v1/clientes/").concat(cliente.getId().toString());
		final String token = (String) ContextParamUtil.get("token");
		HashMap<String, String> headers = new HashMap<>();
		headers.put("Authorization", "Bearer ".concat(token));

		try {
			List<RequestPart> parts = new ArrayList<RequestPart>();
			parts.add(RequestPart.builder().nome("nome").valor(cliente.getNome()).build());
			parts.add(RequestPart.builder().nome("email").valor(cliente.getEmail()).build());
			for (int i = 0; i < cliente.getEnderecos().size(); i++) {
				Endereco endereco = cliente.getEnderecos().get(i);
				parts.add(RequestPart.builder().nome("enderecos[" + i + "].logradouro").valor(endereco.getLogradouro())
						.build());
			}

			if (uploadedFile != null) {
				Path tempFile = Files.createTempFile(uploadedFile.getFileName(), ".png");
				File file = Files.write(tempFile, uploadedFile.getContent()).toFile();
				parts.add(RequestPart.builder().nome("logotipo").isArquivo(true).arquivo(file).build());
			}

			okHttpServiceImpl.efetuarRequest(null, parts, url, HttpMethod.PUT, headers);
			return new RespostaRequisicao<Void>(true);
		} catch (Exception e) {
			return new RespostaRequisicao<Void>(false, e.getMessage());
		}
	}

	public boolean deletarCliente(Long id) {
		final String url = AppProperties.BASE_URL.concat("/v1/clientes/").concat(id.toString());
		final String token = (String) ContextParamUtil.get("token");
		HashMap<String, String> headers = new HashMap<>();
		headers.put("Authorization", "Bearer ".concat(token));

		try {
			okHttpServiceImpl.efetuarRequest(null, null, url, HttpMethod.DELETE, headers);
			return true;
		} catch (IOException e) {
			return false;
		}
	}

	public List<Cliente> buscarClientes() {
		final String url = AppProperties.BASE_URL.concat("/v1/clientes");
		final String token = (String) ContextParamUtil.get("token");
		HashMap<String, String> headers = new HashMap<>();
		headers.put("Authorization", "Bearer ".concat(token));

		try {
			return okHttpServiceImpl.efetuarRequest(new TypeReference<List<Cliente>>() {
			}, null, url, HttpMethod.GET, headers);
		} catch (IOException e) {
			return Collections.emptyList();
		}
	}
}
