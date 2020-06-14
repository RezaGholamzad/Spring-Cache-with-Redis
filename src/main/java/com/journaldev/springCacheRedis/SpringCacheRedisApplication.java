package com.journaldev.springCacheRedis;

import com.journaldev.springCacheRedis.model.User;
import com.journaldev.springCacheRedis.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class SpringCacheRedisApplication implements CommandLineRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(SpringCacheRedisApplication.class);
    private UserRepository userRepository;

    public SpringCacheRedisApplication(UserRepository repository) {
        this.userRepository = repository;
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringCacheRedisApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        LOGGER.info("Saving users. Current user count is {}.", userRepository.count());
        User shubham = new User("Shubham", 2000L);
        User pankaj = new User("Pankaj", 29000L);
        User lewis = new User("Lewis", 550L);

        userRepository.save(shubham);
        userRepository.save(pankaj);
        userRepository.save(lewis);
        LOGGER.info("Done saving users. Data: {}.", userRepository.findAll());
    }
}
