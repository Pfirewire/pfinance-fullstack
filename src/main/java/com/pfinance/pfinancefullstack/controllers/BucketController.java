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
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class BucketController {

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
        ObjectMapper mapper = new ObjectMapper();
        User user = userDao.findByUsername(UserUtils.currentUsername());
        Group group = groupDao.findById(id).orElseThrow(() -> new EntityNotFoundException(String.format("Group with id %d was not found", id)));
        List<Bucket> buckets = bucketDao.findAllByGroup(group);
        System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(buckets));
        return buckets;
    }
}
