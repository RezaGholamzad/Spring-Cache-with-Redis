package com.journaldev.springCacheRedis.controller;

import com.journaldev.springCacheRedis.model.User;
import com.journaldev.springCacheRedis.repository.UserRepository;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Caching;
import org.springframework.web.bind.annotation.*;

@Data
@RestController
public class UpdateUserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    private final UserRepository userRepository;

    //    update entry in cache
    @Caching(evict = @CacheEvict(value = "users", allEntries = true), put = @CachePut(value = "user", key = "#user.id"))
//    @CachePut(value = "users", key = "#user.id")
//    @CacheEvict(value = "users", allEntries = true)
    @PutMapping("/update")
    public User updateUser(@RequestBody User user){
        return userRepository.save(user);
    }



}
