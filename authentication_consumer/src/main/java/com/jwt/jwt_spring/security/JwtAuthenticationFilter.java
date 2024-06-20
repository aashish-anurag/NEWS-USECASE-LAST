package com.jwt.jwt_spring.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
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

    private Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    @Autowired
    private JwtHelper jwtHelper;

    @Autowired
    private UserDetailsService userDetailsService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String requestHeader = request.getHeader("Authorization");
        logger.info("Header: {}",requestHeader);
        String username = null;
        String token = null;

        if(requestHeader !=null && requestHeader.startsWith("Bearer")){
            token = requestHeader.substring(7);
            logger.info("Token====> {}",token);
            try{
                username = this.jwtHelper.getUsernameFromToken(token);
            }catch(IllegalArgumentException e){
                logger.info("Illegal argument while fetching the username !!");
                e.printStackTrace();
            }
            catch (ExpiredJwtException e) {
                logger.info("JWT token is expired !! ");
            e.printStackTrace();
            } catch (MalformedJwtException e) {
                logger.info("Invalid JWT token, Some changes has been done in token !!");
            e.printStackTrace();
            } catch (SignatureException e) {
                logger.info("JWT token is has Signature issue");
                e.printStackTrace();
            }
        }else{
            logger.info("======Invalid header value !!=====");
        }

        if(username != null && SecurityContextHolder.getContext().getAuthentication() == null){
            //fetch user details from username

            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
            Boolean validateToken = this.jwtHelper.validateToken(token, userDetails);
            if(validateToken){
                //set the authentication
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }else{
                logger.info("Validation fails !!");
            }
        }
        filterChain.doFilter(request,response);

    }
}
