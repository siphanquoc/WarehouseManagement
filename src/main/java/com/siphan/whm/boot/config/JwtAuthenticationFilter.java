package com.siphan.whm.boot.config;

import com.siphan.whm.service.ServiceImp.UserDetailsServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component // Đăng ký filter này như một Spring Bean
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;           // Service để xử lý JWT (generate, validate, extract)
    private final UserDetailsServiceImpl userDetailsService; // Service để load thông tin user từ DB

    // Constructor injection
    public JwtAuthenticationFilter(JwtService jwtService, UserDetailsServiceImpl userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    /**
     * Hàm chính của filter, chạy cho mỗi request (OncePerRequestFilter đảm bảo chỉ chạy 1 lần/request)
     */
    @SuppressWarnings("null")
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        // Lấy header Authorization từ request
        final String authHeader = request = request.getHeader("Authorization");
        final String jwt;
        final String username;

        // Nếu không có header hoặc không bắt đầu bằng "Bearer " thì bỏ qua, cho request đi tiếp
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // Cắt chuỗi "Bearer " để lấy token
        jwt = authHeader.substring(7);
        // Lấy username từ token
        username = jwtService.extractUsername(jwt);

        // Nếu lấy được username và chưa có Authentication trong SecurityContext
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // Lấy thông tin user từ DB
            UserDetails userDetails =  userDetailsService.loadUserByUsername(username);
            if(userDetails == null) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "User not found");
                System.out.println("User not found for username: " + username);
                return;

            }
            // Kiểm tra token có hợp lệ không
            if (jwtService.isTokenValid(jwt, userDetails)) {
                // Tạo Authentication object cho Spring Security
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,               // principal (user)
                                null,                      // credentials (null vì đã xác thực bằng JWT)
                                userDetails.getAuthorities() // quyền (roles)
                        );

                // Gắn thêm thông tin chi tiết từ request (IP, session, ...)
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );

                // Đặt Authentication vào SecurityContext để Spring Security biết user đã login
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        // Cho request đi tiếp qua filter chain
        filterChain.doFilter(request, response);
    }
}