package com.asie_back;

import com.asie_back.auth.Auth;
import com.asie_back.auth.AuthRepository;
import com.asie_back.product.ProdStatistics;
import com.asie_back.product.Product;
import com.asie_back.product.ProductRepository;
import com.asie_back.product.ProductStatisticsRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.Arrays;


@SpringBootApplication
public class StartAsieBackApplication {

    // start everything
    public static void main(String[] args) {
        SpringApplication.run(StartAsieBackApplication.class, args);
    }


    @Profile("demo")
    @Bean
    CommandLineRunner initDatabaseProduct(ProductRepository repository, ProductStatisticsRepository productStatisticsRepository) {
        return args -> {
            Product p = new Product("prod1", "desc prod1", new ArrayList<>(Arrays.asList("stats1", "statistics1")));
            repository.save(p);
            ProdStatistics ps = new ProdStatistics("name1-1", "desc1-1");
            productStatisticsRepository.save(ps);
            ProdStatistics ps2 = new ProdStatistics("name1-2", "desc1-2");
            productStatisticsRepository.save(ps2);
            p.setProdStatisticsList(new ArrayList<>());
            p.getProdStatisticsList().add(ps);
            p.getProdStatisticsList().add(ps2);
            repository.save(p);

            p = new Product("prod2", "desc prod2", new ArrayList<>(Arrays.asList("stats2", "statistics2")));
            repository.save(p);
            ps = new ProdStatistics("name2-1", "desc2-1");
            productStatisticsRepository.save(ps);
            ps2 = new ProdStatistics("name2-2", "desc2-2");
            productStatisticsRepository.save(ps2);
            p.setProdStatisticsList(new ArrayList<>());
            p.getProdStatisticsList().add(ps);
            p.getProdStatisticsList().add(ps2);
            repository.save(p);

            p = new Product("prod3", "desc prod3", new ArrayList<>(Arrays.asList("stats3", "statistics3")));
            repository.save(p);
            ps = new ProdStatistics("name3-1", "desc3-1");
            productStatisticsRepository.save(ps);
            ps2 = new ProdStatistics("name3-2", "desc3-2");
            productStatisticsRepository.save(ps2);
            p.setProdStatisticsList(new ArrayList<>());
            p.getProdStatisticsList().add(ps);
            p.getProdStatisticsList().add(ps2);
            repository.save(p);


            p = new Product("prod4", "desc prod4", new ArrayList<>(Arrays.asList("stats4", "statistics4")));
            repository.save(p);
            ps = new ProdStatistics("name4-1", "desc4-1");
            productStatisticsRepository.save(ps);
            ps2 = new ProdStatistics("name4-2", "desc4-2");
            productStatisticsRepository.save(ps2);
            p.setProdStatisticsList(new ArrayList<>());
            p.getProdStatisticsList().add(ps);
            p.getProdStatisticsList().add(ps2);
            repository.save(p);
        };
    }

    @Profile("demo")
    @Bean
    CommandLineRunner initDatabaseUsers(AuthRepository repository) {
        return args -> {
            repository.save(new Auth("admin", "123456", "planning_create, planning_read, planning_update, planning_delete"));
            repository.save(new Auth("dev01", "123456", "planning_update"));
            repository.save(new Auth("user01", "123456", "planning_read"));
        };
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedOrigins("*").allowedMethods("GET", "PUT", "POST", "PATCH", "DELETE", "OPTIONS");
            }
        };
    }
}