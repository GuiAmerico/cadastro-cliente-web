package cadastrocliente.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import cadastrocliente.controller.dto.ErroRequisicao;
import cadastrocliente.exception.RequisicaoException;
import cadastrocliente.service.dto.RequestPart;
import cadastrocliente.service.enums.HttpMethod;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OkHttpServiceImpl {

	private ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	private OkHttpClient client = new OkHttpClient();

	public <T> T efetuarRequest(TypeReference<T> typeRef, Object requestObj, String url, HttpMethod method,
			Map<String, String> headers) throws IOException {
		RequestBody body = createJsonRequestBody(requestObj);
		Request req = buildRequest(url, method, body, headers);
		return executeRequest(req, typeRef);
	}

	public <T> T efetuarRequest(TypeReference<T> typeRef, List<RequestPart> requestParts, String url, HttpMethod method,
			Map<String, String> headers) throws IOException {
		RequestBody body = createMultipartRequestBody(requestParts);
		Request req = buildRequest(url, method, body, headers);
		return executeRequest(req, typeRef);
	}

	private RequestBody createJsonRequestBody(Object requestObj) throws JsonProcessingException {
		final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
		if (requestObj == null)
			return null;
		String json = mapper.writeValueAsString(requestObj);
		return RequestBody.create(json, JSON);
	}

	private RequestBody createMultipartRequestBody(List<RequestPart> parts) {
		if (parts == null || parts.isEmpty())
			return null;

		MultipartBody.Builder mp = new MultipartBody.Builder().setType(MultipartBody.FORM);

		for (RequestPart part : parts) {
			if (part.isArquivo()) {
				mp.addFormDataPart(part.getNome(), part.getArquivo().getName(),
						RequestBody.create(part.getArquivo(), MediaType.parse("application/octet-stream")));
			} else {
				mp.addFormDataPart(part.getNome(), part.getValor());
			}
		}
		return mp.build();
	}

	private Request buildRequest(String url, HttpMethod method, RequestBody body, Map<String, String> headers) {
		Request.Builder b = new Request.Builder().url(url);

		switch (method) {
		case GET:
			b.get();
			break;
		case POST:
			b.post(body);
			break;
		case PUT:
			b.put(body);
			break;
		case PATCH:
			b.patch(body);
			break;
		case DELETE:
			b.delete();
			break;
		default:
			throw new IllegalArgumentException("Método HTTP não suportado: " + method);
		}

		if (headers != null) {
			headers.forEach(b::addHeader);
		}

		return b.build();
	}

	private <T> T executeRequest(Request request, TypeReference<T> typeRef) throws IOException {
		try (Response resp = client.newCall(request).execute()) {
			if (!resp.isSuccessful()) {
				ErroRequisicao erroRequisicao = mapper.readValue(resp.body().byteStream(), ErroRequisicao.class);
				throw new RequisicaoException(erroRequisicao.getDescricao());
			}
			if (typeRef == null) {
				return null;
			}
			return mapper.readValue(resp.body().byteStream(), typeRef);
		}
	}

}
