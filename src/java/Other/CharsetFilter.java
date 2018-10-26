/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Other;

import java.io.IOException;
import javax.servlet.*;

/**
 *
 * @author Aiglu
 */
public class CharsetFilter implements Filter{
    private String encoding;
    
    @Override
    public void init(FilterConfig config) throws ServletException{        
        encoding = config.getInitParameter("requestEncoding");
        
        if (encoding == null){
            encoding = "Cp1251";
        }        
    }
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain next)
                throws IOException, ServletException{
        request.setCharacterEncoding(encoding);
        next.doFilter(request, response);
    }
    
    @Override
    public void destroy(){
        
    }
}
