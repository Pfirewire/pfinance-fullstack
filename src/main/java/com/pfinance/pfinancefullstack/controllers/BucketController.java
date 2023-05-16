package com.pfinance.pfinancefullstack.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pfinance.pfinancefullstack.models.Bucket;
import com.pfinance.pfinancefullstack.models.Group;
import com.pfinance.pfinancefullstack.models.User;
import com.pfinance.pfinancefullstack.repositories.BucketRepository;
import com.pfinance.pfinancefullstack.repositories.GroupRepository;
import com.pfinance.pfinancefullstack.repositories.UserRepository;
import com.pfinance.pfinancefullstack.utils.UserUtils;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class BucketController {

    ObjectMapper mapper = new ObjectMapper();
    private final UserRepository userDao;
    private final GroupRepository groupDao;
    private final BucketRepository bucketDao;

    public BucketController(UserRepository userDao, GroupRepository groupDao, BucketRepository bucketDao) {
        this.userDao = userDao;
        this.groupDao = groupDao;
        this.bucketDao = bucketDao;
    }

    @GetMapping("/buckets/{id}")
    public List<Bucket> getBucketsByGroupId(@PathVariable Long id) throws JsonProcessingException {
        User user = UserUtils.currentUser(userDao);
        if(!groupDao.existsById(id)) throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        Group group = groupDao.findById(id).get();
        if(!user.getGroups().contains(group)) throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        List<Bucket> buckets = bucketDao.findAllByGroup(group);
        System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(buckets));
        return buckets;
    }

    @PostMapping("/buckets/{id}")
    public Bucket addBucketByGroupId(@PathVariable Long id, @RequestBody Bucket bucket) throws JsonProcessingException {
        User user = UserUtils.currentUser(userDao);
        if(!groupDao.existsById(id)) throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        Group group = groupDao.findById(id).get();
        if(!user.getGroups().contains(group)) throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        bucketDao.save(bucket);
    }

    @GetMapping("/bucket/{id}")
    public Bucket getBucketById(@PathVariable Long id) throws JsonProcessingException {
        User user = UserUtils.currentUser(userDao);
        if(!bucketDao.existsById(id)) throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        Bucket bucket = bucketDao.findById(id).get();
        if(!user.getBuckets().contains(bucket)) throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(bucket));
        return bucket;
    }
}
