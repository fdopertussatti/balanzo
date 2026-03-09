package br.com.balanzo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan("br.com.balanzo.domain")
@EnableJpaRepositories("br.com.balanzo.infrastructure.persistence")
public class BalanzoApplication {

    public static void main(String[] args) {
        SpringApplication.run(BalanzoApplication.class, args);
    }
}
