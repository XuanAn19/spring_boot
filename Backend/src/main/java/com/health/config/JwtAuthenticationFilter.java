package com.health.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.health.service.UserDetailsServiceImpl;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component // Đánh dấu lớp này là một thành phần của Spring để nó có thể được quét và sử dụng.
public class JwtAuthenticationFilter extends OncePerRequestFilter { // Lớp này mở rộng OncePerRequestFilter để xử lý yêu cầu một lần duy nhất cho mỗi request.

    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl; // Service để lấy thông tin người dùng từ cơ sở dữ liệu.

    @Autowired
    private JwtUtil jwtUtil; // Tiện ích để xử lý token JWT (như xác thực, giải mã).

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // Lấy header chứa token từ yêu cầu.
        String requestTokenHeader = request.getHeader("Authorization");

        String email = null; // Biến lưu email được lấy từ token.
        String jwtToken = null; // Biến lưu JWT token từ header.

        // Kiểm tra header không rỗng và có đúng định dạng "Bearer <token>".
        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            // Loại bỏ tiền tố "Bearer " để lấy token thực tế.
            jwtToken = requestTokenHeader.substring(7);

            try {
                // Lấy email (subject) từ token.
                email = this.jwtUtil.getEmailFromToken(jwtToken);

            } catch (Exception e) {
                // Bắt lỗi trong quá trình xử lý token (nếu token không hợp lệ).
                e.printStackTrace();
            }

            // Kiểm tra nếu email tồn tại và chưa được xác thực trong SecurityContextHolder.
            if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {

                // Tải thông tin người dùng từ cơ sở dữ liệu dựa trên email.
                UserDetails userDetails = this.userDetailsServiceImpl.loadUserByUsername(email);

                // Tạo đối tượng xác thực dựa trên thông tin người dùng và quyền.
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());

                // Gắn thêm thông tin chi tiết của yêu cầu HTTP vào token xác thực.
                usernamePasswordAuthenticationToken
                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // Thiết lập xác thực cho SecurityContext.
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

            } else {
                // In thông báo nếu token không hợp lệ.
                System.out.println("Token is not validated..");
            }
        }

        // Tiếp tục chuyển yêu cầu qua các bộ lọc khác trong chuỗi filter.
        filterChain.doFilter(request, response);
    }
}
