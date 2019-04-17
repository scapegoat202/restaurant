package cn.varfunc.restaurant;

import io.minio.MinioClient;
import io.minio.errors.InvalidEndpointException;
import io.minio.errors.InvalidPortException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class RestaurantConfig {
    private static MinioClient minioClient = null;

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("*")
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH", "OPTION");
            }
        };
    }

    @Bean
    public MinioClient minioClient(@Value("${cn.varfunc.restaurant.minio.endpoint}") String endpoint,
                                   @Value("${cn.varfunc.restaurant.minio.access-key}") String accessKey,
                                   @Value("${cn.varfunc.restaurant.minio.secret-key}") String secretKey) {
        if (minioClient != null) {
            return minioClient;
        }

        try {
            minioClient = new MinioClient(endpoint, accessKey, secretKey);
        } catch (InvalidEndpointException | InvalidPortException e) {
            e.printStackTrace();
        }
        return minioClient;
    }
}
