package cadastrocliente.util;

import java.io.File;

import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

import org.primefaces.model.file.UploadedFile;

public class FileUtils {

	public static File toFile(UploadedFile uploaded) throws Exception {
		ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext()
				.getContext();

		// agora você pode:
		File tempDir = (File) servletContext.getAttribute("javax.servlet.context.tempdir");

		String originalName = uploaded.getFileName();

		// extrai prefixo e sufixo
		String prefix = originalName.contains(".") ? originalName.substring(0, originalName.lastIndexOf('.'))
				: "upload";
		String suffix = originalName.contains(".") ? originalName.substring(originalName.lastIndexOf('.')) : ".tmp";

		// cria o arquivo temporário dentro de tempDir
		File temp = File.createTempFile(prefix + "_", suffix, tempDir);

		// usa o write() interno para gravar todo o conteúdo
		uploaded.write(temp.getAbsolutePath());

		return temp;
	}
}
