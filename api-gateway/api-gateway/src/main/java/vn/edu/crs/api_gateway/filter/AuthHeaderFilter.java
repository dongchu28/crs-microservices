package vn.edu.crs.api_gateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class AuthHeaderFilter implements GlobalFilter, Ordered {

    // 1. Mở rộng cho toàn bộ các endpoint xác thực auth
    private static final List<String> OPEN_PATHS = List.of(
            "/api/auth",
            "/api/public/courses"
    );

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();

        // 2. CHO PHÉP TẤT CẢ REQUEST OPTIONS (Preflight) ĐI QUA ĐỂ TRÌNH DUYỆT KHÔNG BỊ LỖI CORS
        if (request.getMethod() == HttpMethod.OPTIONS) {
            return chain.filter(exchange);
        }

        String path = request.getURI().getPath();

        boolean isOpen = OPEN_PATHS.stream().anyMatch(path::startsWith);
        // GET /api/courses là public (xem môn học không cần đăng nhập)
        boolean isPublicCourseRead = path.startsWith("/api/courses") && request.getMethod() == HttpMethod.GET;

        if (isOpen || isPublicCourseRead) {
            return chain.filter(exchange);
        }

        // Kiểm tra Header Authorization đối với các request nghiệp vụ cần bảo mật
        if (request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION) == null) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return -1; // Chạy sớm, trước khi request được định tuyến đi
    }
}