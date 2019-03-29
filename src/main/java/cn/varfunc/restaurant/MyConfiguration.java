package cn.varfunc.restaurant;

import io.minio.MinioClient;
import io.minio.errors.InvalidEndpointException;
import io.minio.errors.InvalidPortException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MyConfiguration {
    private static MinioClient minioClient = null;

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("*")
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH");
            }

        };
    }

    @Bean
    public MinioClient minioClient() {
        if (minioClient != null) {
            return minioClient;
        }

        try {
            minioClient = new MinioClient("https://play.minio.io:9000",
                    "Q3AM3UQ867SPQQA43P2F",
                    "zuf+tfteSlswRu7BJ86wekitnifILbZam1KYY3TG");
        } catch (InvalidEndpointException | InvalidPortException e) {
            e.printStackTrace();
        }
        return minioClient;
    }
}
