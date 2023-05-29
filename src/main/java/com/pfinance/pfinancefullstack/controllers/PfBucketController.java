package com.pfinance.pfinancefullstack.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pfinance.pfinancefullstack.models.PfBucket;
import com.pfinance.pfinancefullstack.models.PfCategory;
import com.pfinance.pfinancefullstack.models.User;
import com.pfinance.pfinancefullstack.repositories.PfBucketRepository;
import com.pfinance.pfinancefullstack.repositories.PfCategoryRepository;
import com.pfinance.pfinancefullstack.repositories.UserRepository;
import com.pfinance.pfinancefullstack.utils.CalculateGroup;
import com.pfinance.pfinancefullstack.utils.UserUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class PfBucketController {

    ObjectMapper mapper = new ObjectMapper();
    private final UserRepository userDao;
    private final PfCategoryRepository groupDao;
    private final PfBucketRepository pfBucketDao;

    public PfBucketController(UserRepository userDao, PfCategoryRepository groupDao, PfBucketRepository pfBucketDao) {
        this.userDao = userDao;
        this.groupDao = groupDao;
        this.pfBucketDao = pfBucketDao;
    }

    @GetMapping("/buckets/{id}")
    public List<PfBucket> getBucketsByGroupId(@PathVariable Long id) throws JsonProcessingException {
        User user = UserUtils.currentUser(userDao);
        if(!groupDao.existsById(id)) throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        PfCategory pfCategory = groupDao.findById(id).get();
        if(!user.getGroups().contains(pfCategory)) throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        List<PfBucket> pfBuckets = pfBucketDao.findAllByGroup(pfCategory);
        System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(pfBuckets));
        return pfBuckets;
    }

    @PostMapping("/buckets/{id}")
    public PfBucket addBucketByGroupId(@PathVariable Long id, @RequestBody PfBucket pfBucket) throws JsonProcessingException {
        User user = UserUtils.currentUser(userDao);
        if(!groupDao.existsById(id)) throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        PfCategory pfCategory = groupDao.findById(id).get();
        if(!user.getGroups().contains(pfCategory)) throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        pfBucket.setUser(user);
        pfBucket.setGroup(pfCategory);
        pfBucketDao.save(pfBucket);
        CalculateGroup.addAmount(pfCategory, pfBucket, groupDao);
        return pfBucket;
    }

    @GetMapping("/bucket/{id}")
    public PfBucket getBucketById(@PathVariable Long id) throws JsonProcessingException {
        User user = UserUtils.currentUser(userDao);
        if(!pfBucketDao.existsById(id)) throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        PfBucket pfBucket = pfBucketDao.findById(id).get();
        if(!user.getBuckets().contains(pfBucket)) throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(pfBucket));
        return pfBucket;
    }

    @PutMapping("/bucket/{id}")
    public PfBucket updateBucketById(@PathVariable Long id, @RequestBody PfBucket updatedPfBucket) throws JsonProcessingException {
        User user = UserUtils.currentUser(userDao);
        if(!pfBucketDao.existsById(id)) throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        PfBucket pfBucket = pfBucketDao.findById(id).get();
        if(!user.getBuckets().contains(pfBucket)) throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        updatedPfBucket.setId(pfBucket.getId());
        updatedPfBucket.setGroup(pfBucket.getGroup());
        updatedPfBucket.setUser(user);
        pfBucketDao.save(updatedPfBucket);
        CalculateGroup.allAmounts(pfBucket.getGroup(), groupDao);
        return updatedPfBucket;
    }

    @DeleteMapping("/bucket/{id}")
    public PfBucket deleteBucketById(@PathVariable Long id) throws JsonProcessingException {
        User user = UserUtils.currentUser(userDao);
        if(!pfBucketDao.existsById(id)) throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        PfBucket pfBucket = pfBucketDao.findById(id).get();
        if(!user.getBuckets().contains(pfBucket)) throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        PfCategory pfCategory = groupDao.findByBuckets(pfBucket);
        List<PfBucket> groupPfBuckets = pfCategory.getBuckets();
        groupPfBuckets.remove(pfBucket);
        pfCategory.setBuckets(groupPfBuckets);
        groupDao.save(pfCategory);
        List<PfBucket> userPfBuckets = user.getBuckets();
        userPfBuckets.remove(pfBucket);
        user.setBuckets(userPfBuckets);
        userDao.save(user);
        pfBucketDao.delete(pfBucket);
        CalculateGroup.allAmounts(pfCategory, groupDao);
        return pfBucket;
    }
}
