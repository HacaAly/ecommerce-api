package com.hikadobushido.ecommerce_java.config.middleware;

import com.hikadobushido.ecommerce_java.service.JwtService;
import com.hikadobushido.ecommerce_java.service.UserDetailsImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final UserDetailsImpl userDetailService;
    private final JwtService jwtService;
    private final HandlerExceptionResolver handlerExceptionResolver;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        // Menggunakan HTTP Header authorization
        final String authHeader = request.getHeader("Authorization");

        // Melakukan pengecekan untuk melakukan filtering
        // bearer : jenis token yang digunakan untuk autentikasi dan otorisasi
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            try {
                filterChain.doFilter(request, response);
            } catch (InsufficientAuthenticationException exception) {
                handlerExceptionResolver.resolveException(request, response, null, exception);
            }
            return;
        }

        try {
            // Mengambil token jwt dengan menghapus 7 element pertama yaitu bearer
            final String jwt = authHeader.substring(7);
            //melakukan validasi token denge
            if (jwtService.validateToken(jwt)) {
                final String userIdentifier = jwtService.getUsernameFromToken(jwt);

                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                if (userIdentifier != null && authentication == null) {
                    UserDetails userDetails = userDetailService.loadUserByUsername(userIdentifier);
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                            userDetails, null,  userDetails.getAuthorities()
                    );

                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }

                filterChain.doFilter(request, response); 
            }

        }catch (Exception exception) {
            handlerExceptionResolver.resolveException(request, response, null, exception);
        }
    }
}
