package com.health.config;

import org.springframework.stereotype.Component;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.cglib.core.internal.Function;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {

    // ID dùng để đảm bảo tương thích khi tuần tự hóa
    private static final long serialVersionUID = -2550185165626007488L;

    // Thời gian sống của token (5 giờ tính bằng giây)
    public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;

    // Khóa bí mật để ký token (nên bảo mật, không lưu trực tiếp trong mã nguồn)
    private String secret = "java";

    // Lấy email (hoặc tên người dùng) từ JWT token
    public String getEmailFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    // Lấy ngày hết hạn từ JWT token
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    // Lấy một claim cụ thể từ JWT token
    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    // Lấy tất cả các claim từ JWT token bằng khóa bí mật
    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    // Kiểm tra token có hết hạn hay không
    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    // Tạo JWT token mới dựa trên thông tin người dùng
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return doGenerateToken(claims, userDetails.getUsername());
    }

    /*
     * Các bước tạo JWT token:
     * 1. Định nghĩa các claim (Issuer, Expiration, Subject, ID).
     * 2. Ký token bằng thuật toán HS512 và khóa bí mật.
     * 3. Chuyển token thành chuỗi gọn.
     */
    private String doGenerateToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims) // Thêm dữ liệu bổ sung vào token
                .setSubject(subject) // Đặt chủ thể của token (email hoặc tên người dùng)
                .setIssuedAt(new Date(System.currentTimeMillis())) // Thời điểm phát hành token
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000)) // Thời điểm hết hạn
                .signWith(SignatureAlgorithm.HS512, secret) // Ký token bằng thuật toán và khóa bí mật
                .compact(); // Chuyển token thành chuỗi gọn
    }

    // Xác thực token dựa trên thông tin người dùng
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String email = getEmailFromToken(token); // Lấy email từ token
        return (email.equals(userDetails.getUsername()) && !isTokenExpired(token)); // Kiểm tra tính hợp lệ và chưa hết hạn
    }
}
