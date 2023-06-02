package com.journaldev.springCacheRedis.controller;

import com.journaldev.springCacheRedis.repository.UserRepository;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Data
@RestController
public class EvictUserContoller {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    private final UserRepository userRepository;

    //    Remove entry from cache

    @Caching(evict = {@CacheEvict(value = "user", key = "#id"), @CacheEvict(value = "users", allEntries = true)})
//    @CacheEvict(value = "users", key = "#id")
//    @CacheEvict(value = "users", allEntries = true)
    @DeleteMapping("/delete/{id}")
    public void deleteUser(@PathVariable Long id){
        LOGGER.info("deleting person with id {}", id);
        userRepository.deleteById(id);
    }

    //    Remove all entry from cache
    @CacheEvict(value = "users", allEntries = true)
    @GetMapping("/evict")
    public void evictUsers(){
        LOGGER.info("evict all users of cache");
    }
}
