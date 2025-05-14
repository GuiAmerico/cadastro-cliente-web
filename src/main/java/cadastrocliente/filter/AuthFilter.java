package cadastrocliente.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import cadastrocliente.util.ContextParamUtil;

@WebFilter(filterName = "AuthFilter", urlPatterns = { "/GestaoEmpresas.xhtml" })
public class AuthFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // inicialização opcional
    }

    @Override
    public void doFilter(ServletRequest req,
                         ServletResponse res,
                         FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest  request  = (HttpServletRequest)  req;
        HttpServletResponse response = (HttpServletResponse) res;

        String token = (String) ContextParamUtil.get("token");
        // se não há token, redireciona para login
        if (token == null) {
            response.sendRedirect(request.getContextPath() + "/Login.xhtml");
            return; // interrompe a cadeia
        }

        // segue normalmente
        chain.doFilter(req, res);
    }

    @Override
    public void destroy() {
        // limpeza opcional
    }
}
