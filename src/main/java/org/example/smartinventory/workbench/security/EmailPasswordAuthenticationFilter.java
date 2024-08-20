package org.example.smartinventory.workbench.security;


import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailPasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter
{
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$");
    private ReactiveAuthenticationManager authenticationManager;
    private ServerAuthenticationConverter authenticationConverter;

    public EmailPasswordAuthenticationFilter(ReactiveAuthenticationManager authenticationManager){
        this.authenticationManager = authenticationManager;
        this.authenticationConverter = new EmailPasswordAuthenticationConverter();
    }

//    @Override
//    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
//        String username = request.getParameter("username");
//        String password = request.getParameter("password");
//        UsernamePasswordAuthenticationToken authRequest;
//        authRequest = new UsernamePasswordAuthenticationToken(username, password);
//        return getAuthenticationManager().authenticate(authRequest);
//    }


    private static class EmailPasswordAuthenticationConverter implements ServerAuthenticationConverter {
        @Override
        public Mono<Authentication> convert(ServerWebExchange exchange) {
            return exchange.getFormData().map(formData -> {
                String email = formData.getFirst("username");
                String password = formData.getFirst("password");
                return new UsernamePasswordAuthenticationToken(email, password);
            });
        }
    }

}
