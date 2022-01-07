package com.example2.demo2.database;

import com.example2.demo2.models.Product;
import com.example2.demo2.repository.ProductRepository;
import java.util.logging.Logger;

import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.logging.Logger;

@Configuration
public class Database {
    //LOGGER
    private static final Logger logger= (Logger) LoggerFactory.getLogger(Database.class);
    @Bean
    CommandLineRunner initDatabase(ProductRepository productRepository){
        return new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {
                Product A=new Product("iphone",2010,24.0,"");
                Product B=new Product("iphone",2010,24.0,"");
                Product C=new Product("iphone",2010,24.0,"");
                logger.info("insert data: "+productRepository.save(A));
                logger.info("insert data: "+productRepository.save(B));
                logger.info("insert data: "+productRepository.save(C));
            }
        };
    }
}
