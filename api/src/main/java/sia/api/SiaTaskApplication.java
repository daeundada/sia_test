package sia.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "sia") // sia 하위 전부 스캔
@EnableJpaRepositories(basePackages = "sia.domain.convert.repository") // Repository 스캔
@EntityScan(basePackages = "sia.domain.convert.entity") // Entity 스캔
public class SiaTaskApplication {

    public static void main(String[] args) {
        SpringApplication.run(SiaTaskApplication.class, args);
    }
}
