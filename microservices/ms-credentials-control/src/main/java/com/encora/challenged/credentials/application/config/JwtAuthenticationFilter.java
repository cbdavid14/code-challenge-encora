package com.encora.challenged.credentials.application.config;

import com.encora.challenged.credentials.application.impl.JwtService;
import com.encora.challenged.http.constants.ErrorMessage;
import com.encora.challenged.http.exceptions.BadCredentialException;
import com.encora.challenged.http.exceptions.ExpiredTokenException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
  @Autowired
  private HandlerExceptionResolver handlerExceptionResolver;
  @Autowired
  private JwtService jwtService;
  @Autowired
  private UserDetailsService userDetailsService;

  @Override
  protected void doFilterInternal(
    @NonNull HttpServletRequest request,
    @NonNull HttpServletResponse response,
    @NonNull FilterChain filterChain
  ) throws ServletException, IOException {
    final String authHeader = request.getHeader("Authorization");

    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
      filterChain.doFilter(request, response);
      return;
    }
    final String jwt = authHeader.substring(7);

    try {
      long tokenExpireIn = jwtService.tokenExpireIn(jwt);
      if (tokenExpireIn == 0) {
        throw new ExpiredTokenException(ErrorMessage.EXPIRED_TOKEN.getText());
      }

      final String user = jwtService.extractUsername(jwt);
      final String cluster = jwtService.extractClusterKey(jwt);

      Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

      if (user != null && authentication == null) {
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(user + cluster);
        if (jwtService.isTokenValid(jwt, userDetails)) {
          UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
            userDetails,
            null,
            userDetails.getAuthorities()
          );
          request.setAttribute("tokenExpireIn", tokenExpireIn);

          authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
          SecurityContextHolder.getContext().setAuthentication(authToken);
        } else {
          throw new BadCredentialException(ErrorMessage.BAD_CREDENTIALS.getText());
        }
      }

      filterChain.doFilter(request, response);
    } catch (Exception exception) {
      handlerExceptionResolver.resolveException(request, response, null, exception);
    }
  }
}
