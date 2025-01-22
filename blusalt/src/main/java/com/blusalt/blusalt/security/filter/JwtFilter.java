package com.blusalt.blusalt.security.filter;



import com.blusalt.blusalt.entity.BaseUser;
import com.blusalt.blusalt.security.jwtutils.JwtService;
import com.blusalt.blusalt.service.JpaService.BaseUserJPAService;
import com.blusalt.blusalt.utils.SpringContext;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collection;
import java.util.Optional;

import static com.blusalt.blusalt.security.SecurityConstants.*;


/**
 * Filter class that handles JWT token validation.
 * @author Chukwudile David
 */

@Slf4j
@RequiredArgsConstructor
@Configuration
public class JwtFilter extends OncePerRequestFilter {
    private final UserDetailsService userDetailsService;
    private final BaseUserJPAService baseUserJPAService;
    private final JwtService jwtUtil ;

//    = SpringContext.getBean(JwtService.class)

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        final String authorizationHeader = request.getHeader(HEADER_STRING);
        log.info("This is the header :{}",authorizationHeader);
        String email = null;
        String jwt = null;
        BaseUser baseUser = null;
        Collection<? extends GrantedAuthority> authorities = null;
        if(authorizationHeader!= null && authorizationHeader.startsWith(TOKEN_PREFIX)){
            jwt = authorizationHeader.substring(TOKEN_BEGIN_INDEX);
            log.info("This is the token :{}",jwt);
            email = jwtUtil.extractUsername(jwt);
            log.info("This is the email :{}",email);
            Optional<BaseUser> optionalEmail = baseUserJPAService.findByEmail(email);
            if (optionalEmail.isPresent()) {
                baseUser  = optionalEmail.get();
                baseUser.getRole().getName();
                authorities = baseUser.getAuthorities();

            }
        }

        if(email != null && SecurityContextHolder.getContext().getAuthentication() == null){
//            UserDetails userDetails = this.userDetailsService.loadUserByUsername(email);
            assert baseUser != null;
            if(jwtUtil.isTokenValid(jwt, baseUser)){
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(baseUser, null,authorities);
                usernamePasswordAuthenticationToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        filterChain.doFilter(request, response);
    }


}
