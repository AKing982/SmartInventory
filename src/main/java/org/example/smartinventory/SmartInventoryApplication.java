package org.example.smartinventory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = "org.example.smartinventory.entities")
@EnableJpaRepositories(basePackages = "org.example.smartinventory.repository")
public class SmartInventoryApplication implements CommandLineRunner {

    @Autowired
    private Environment env;

    public static void main(String[] args) {
        SpringApplication.run(SmartInventoryApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Active profiles: " + String.join(", ", env.getActiveProfiles()));
        System.out.println("Database URL: " + env.getProperty("spring.datasource.url"));
    }
}
