package com.pfinance.pfinancefullstack.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pfinance.pfinancefullstack.models.Bucket;
import com.pfinance.pfinancefullstack.models.Group;
import com.pfinance.pfinancefullstack.models.User;
import com.pfinance.pfinancefullstack.repositories.BucketRepository;
import com.pfinance.pfinancefullstack.repositories.GroupRepository;
import com.pfinance.pfinancefullstack.repositories.UserRepository;
import com.pfinance.pfinancefullstack.utils.CalculateGroup;
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
        bucket.setUser(user);
        bucket.setGroup(group);
        bucketDao.save(bucket);
        CalculateGroup.addAmount(group, bucket, groupDao);
        return bucket;
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

    @PutMapping("/bucket/{id}")
    public Bucket updateBucketById(@PathVariable Long id, @RequestBody Bucket updatedBucket) throws JsonProcessingException {
        User user = UserUtils.currentUser(userDao);
        if(!bucketDao.existsById(id)) throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        Bucket bucket = bucketDao.findById(id).get();
        if(!user.getBuckets().contains(bucket)) throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        updatedBucket.setId(bucket.getId());
        updatedBucket.setGroup(bucket.getGroup());
        updatedBucket.setUser(user);
        bucketDao.save(updatedBucket);
        CalculateGroup.allAmounts(bucket.getGroup(), groupDao);
        return updatedBucket;
    }

    @DeleteMapping("/bucket/{id}")
    public Bucket deleteBucketById(@PathVariable Long id) throws JsonProcessingException {
        User user = UserUtils.currentUser(userDao);
        if(!bucketDao.existsById(id)) throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        Bucket bucket = bucketDao.findById(id).get();
        if(!user.getBuckets().contains(bucket)) throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        Group group = groupDao.findByBuckets(bucket);
        List<Bucket> groupBuckets = group.getBuckets();
        groupBuckets.remove(bucket);
        group.setBuckets(groupBuckets);
        groupDao.save(group);
        List<Bucket> userBuckets = user.getBuckets();
        userBuckets.remove(bucket);
        user.setBuckets(userBuckets);
        userDao.save(user);
        bucketDao.delete(bucket);
        CalculateGroup.allAmounts(group, groupDao);
        return bucket;
    }
}
