package flowix.main.flowixlabfinall.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import flowix.main.flowixlabfinall.exceptions.InvalidRefreshToken;
import flowix.main.flowixlabfinall.exceptions.TokenException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;

@Component
public class FilterJWT extends OncePerRequestFilter {
    @Autowired
    FilterProvider filterProvider;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String token = filterProvider.resolve(request);
            System.out.println(token);
            if(token != null && filterProvider.checkRefToken(token) && filterProvider.checkValidationToken(token)){
                System.out.println(" ii m");
                Authentication authentication = filterProvider.authenticateToken(token);

                if(authentication != null) {
                    System.out.println(" sasa");
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    System.out.println(SecurityContextHolder.getContext());
                }
            }
            filterChain.doFilter(request, response);
        }
        catch (TokenException e){
            response.setStatus(400);
            response.getWriter().write(e.getMessage());
        }
        catch (InvalidRefreshToken e) {
            response.setStatus(405);
            response.getWriter().write(convertJson(e.getMessage()));
        }
    }

    private String convertJson(String responseText) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String responseJson = objectMapper.writeValueAsString(responseText);
        return responseJson;
    }

}
