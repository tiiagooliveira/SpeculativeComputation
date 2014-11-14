package cguide.filters;

import java.io.IOException;
import java.util.Date;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jboss.logging.Logger;

import cguide.db.beans.AutenticacaoBean;
import cguide.db.beans.AutenticacaoManager;
import cguide.db.beans.UserManager;
import cguide.db.exception.DAOException;


public class AuthFilter implements Filter {

    protected static Logger logger = Logger.getLogger(AuthFilter.class.getName());
    private static boolean DEV_MODE = false;


    @Override
	public void init(FilterConfig filterConfig) throws ServletException {
        logger.info("AuthFilter is initializing");
    }

    @Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
        String authToken = null;

        if (DEV_MODE){
            // always set an admin as requester
            httpServletRequest.setAttribute("requestOwner", UserManager.getInstance().getUserBeanByID(1L));
        }else if (httpServletRequest.getCookies() != null && httpServletRequest.getCookies().length > 0){
            for (Cookie cookie : httpServletRequest.getCookies()) {
                if (cookie.getName().equals("auth-token")){
                    authToken = cookie.getValue();
                    break;
                }
            }
            if (authToken != null && authToken.length() > 0){
                try{
                    AutenticacaoBean[] autenticacaoBeans = AutenticacaoManager.getInstance().
                            loadByWhere(" where auth = '" + authToken + "';");
                    if (autenticacaoBeans != null && autenticacaoBeans.length > 0){
                        if (autenticacaoBeans[0].getDuracao().before(new Date())){
                            httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED,
                                    "Token provided is too old. Please renew it.");
                            return;
                        }else{
                            httpServletRequest.setAttribute("requestOwner", UserManager.getInstance().
                                    getUserBeanByID(autenticacaoBeans[0].getUtilizadorId()));
                        }
                        // TODO remove old tokens from database
                    }
                }catch (DAOException e){
                    logger.warn("Error while obtaining user from auth cookie");
                }
            }
        }
        filterChain.doFilter(servletRequest,servletResponse);
    }

    @Override
	public void destroy() {
        logger.info("AuthFilter destroyed");
    }
}
