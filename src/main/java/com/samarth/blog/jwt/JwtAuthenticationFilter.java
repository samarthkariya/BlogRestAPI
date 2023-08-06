package com.samarth.blog.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final Logger logger = LoggerFactory.getLogger(OncePerRequestFilter.class);

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtTokenHelper jwtTokenHelper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {


        String requestHeader = request.getHeader("Authorization");
        String username = null;
        String token = null;

        if (requestHeader != null && requestHeader.startsWith("Bearer")) {

            token = requestHeader.substring(7);

            try {
                username = jwtTokenHelper.getUsernameFromToken(token);
            } catch (IllegalArgumentException e) {
                logger.error("Illegal Argument while fetching the username !!");
            } catch (ExpiredJwtException e) {
                logger.error("Given jwt token is expired !!");
            } catch (MalformedJwtException e) {
                logger.error("Some changed has done in token !! Invalid Token");
            } catch (Exception e) {
                logger.error("error"+e.getMessage());
            }

        } else {
            logger.info("Invalid Header value !!");
        }


        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {


            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            Boolean validate = jwtTokenHelper.validateToken(token, userDetails);
            if (validate) {

                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            } else {
                logger.info("Validation Fails!!");
            }
        }

        filterChain.doFilter(request, response);
    }
}
