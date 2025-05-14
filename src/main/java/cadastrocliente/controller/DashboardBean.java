package cadastrocliente.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.file.UploadedFile;

import cadastrocliente.controller.dto.RespostaRequisicao;
import cadastrocliente.model.Cliente;
import cadastrocliente.model.Endereco;
import cadastrocliente.service.ClienteService;
import cadastrocliente.util.ContextParamUtil;
import cadastrocliente.util.FacesMessagesUtil;
import cadastrocliente.util.UpdateComponentUtil;

@Named
@ViewScoped
public class DashboardBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private ClienteService clienteService;
	@Inject
	private FacesMessagesUtil messages;

	private Cliente cliente;

	private UploadedFile file;

	private String arquivoNome;

	private List<String> enderecos = new ArrayList<>();

	public String validarLogin() {
		String token = (String) ContextParamUtil.get("token");
		if (token == null) {
			return "/Login.xhtml?faces-redirect=true";
		}
		return null; 
	}

	public List<String> getEnderecos() {
		return enderecos;
	}

	public List<Cliente> getClientes() {
		List<Cliente> buscarClientes = clienteService.buscarClientes();
		return buscarClientes;
	}

	public void instanciarNovoCliente() {
		setCliente(new Cliente());
		cliente.setEnderecos(new ArrayList<>());
		cliente.getEnderecos().add(new Endereco(""));
		arquivoNome = null;
	}

	public void salvarCliente() {
		if(isClienteSelecionado()) {
			atualizarCliente();
			return;
		}
		criarCliente();
	}
	public void criarCliente() {

		if (file == null) {
			messages.add("Logotipo é obrigatório, não foi possível criar cliente", FacesMessage.SEVERITY_ERROR);
			UpdateComponentUtil.update("messages");
			return;
		}

		RespostaRequisicao<Cliente> respostaRequisicao = clienteService.criarCliente(cliente, file);

		if (!respostaRequisicao.isSucesso()) {
			messages.add(respostaRequisicao.getMessagem(), FacesMessage.SEVERITY_ERROR);
			UpdateComponentUtil.update("messages");
			return;
		}

		messages.add("Cliente criado com sucesso", FacesMessage.SEVERITY_INFO);
		UpdateComponentUtil.update("clientesDataTable", "messages");
	}

	public void deletarCliente() {
		if (!isClienteSelecionado()) {
			return;
		}
		boolean clienteDeletado = clienteService.deletarCliente(cliente.getId());
		if (!clienteDeletado) {
			messages.add("Não foi possível deletar criar cliente", FacesMessage.SEVERITY_ERROR);
		}

		cliente = null;
		messages.add("Cliente deletado com sucesso", FacesMessage.SEVERITY_INFO);
		UpdateComponentUtil.update("clientesDataTable", "messages");

	}
	
	public void prepararEdicao() {
		enderecos = cliente.getEnderecos().stream().map(endereco -> endereco.getLogradouro()).collect(Collectors.toList());
	}

	public void atualizarCliente() {
		if (!isClienteSelecionado()) {
			return;
		}

		RespostaRequisicao<Void> respostaRequisicao = clienteService.atualizarCliente(cliente, file);
		if (!respostaRequisicao.isSucesso()) {
			messages.add(respostaRequisicao.getMessagem(), FacesMessage.SEVERITY_ERROR);
			UpdateComponentUtil.update("clientesDataTable", "messages");
			return;
		}

		messages.add("Cliente atualizado com sucesso", FacesMessage.SEVERITY_INFO);
		UpdateComponentUtil.update("clientesDataTable", "messages");
	}

	public void uploadLogotipo(FileUploadEvent event) {
		UploadedFile file = event.getFile();
		if (file != null && file.getContent() != null && file.getContent().length > 0 && file.getFileName() != null) {
			setFile(file);
			setArquivoNome(file.getFileName());
		}
	}

	public String getNomeLogotipo() {
		if (file == null) {
			return null;
		}
		return file.getFileName();
	}

	public void adicionarCampo() {
		cliente.getEnderecos().add(new Endereco(""));
	}

	public void removerCampo(int index) {
		if (cliente.getEnderecos().size() >= 1) {
			cliente.getEnderecos().remove(index);
		}
	}

	public boolean isClienteSelecionado() {
		return cliente != null && cliente.getId() != null;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public UploadedFile getFile() {
		return file;
	}

	public void setFile(UploadedFile file) {
		this.file = file;
	}

	public String getArquivoNome() {
		return arquivoNome;
	}

	public void setArquivoNome(String arquivoNome) {
		this.arquivoNome = arquivoNome;
	}
}