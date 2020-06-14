package com.journaldev.springCacheRedis.controller;

import com.journaldev.springCacheRedis.model.User;
import com.journaldev.springCacheRedis.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

/*
    Controllers are the place where Redis cache is called for action.
    Actually, this is the best place to do so because as a cache is directly associated with it.
 */
@RestController
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /*
        a person into a cache named as ‘users’, identifies that person by the key as ‘userId’
        and will only store a user with followers greater than 12000.
        This makes sure that cache is populated with users who are very popular and are often queried for.

        the request won’t even have to enter the service code to wait for cached results.
     */
    @Cacheable(value = "users", key = "#userId", unless = "#result.followers < 1200")
    @GetMapping("/{userId}")
    public User getUser(@PathVariable String userId) throws Exception {
        LOGGER.info("Getting user with ID {}.", userId);
        return userRepository.findById(Long.valueOf(userId))
                .orElseThrow(()->new Exception("not found user"));
    }

    @CachePut(value = "users", key = "#user.id")
    @PutMapping("/update")
    public User updateUser(@RequestBody User user){
        return userRepository.save(user);
    }

    @CacheEvict(value = "users", key = "#id") // allEntries = true -> all deleted
    @DeleteMapping("/delete/{id}")
    public void deleteUser(@PathVariable Long id){
        LOGGER.info("deleting person with id {}", id);
        userRepository.deleteById(id);
    }

}
