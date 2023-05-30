package com.pfinance.pfinancefullstack.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pfinance.pfinancefullstack.models.PfBucket;
import com.pfinance.pfinancefullstack.models.PfCategory;
import com.pfinance.pfinancefullstack.repositories.PfBucketRepository;
import com.pfinance.pfinancefullstack.repositories.PfBudgetRepository;
import com.pfinance.pfinancefullstack.repositories.PfCategoryRepository;
import com.pfinance.pfinancefullstack.repositories.UserRepository;
import com.pfinance.pfinancefullstack.services.Validate;
import com.pfinance.pfinancefullstack.utils.CalculatePfCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class PfBucketController {

    ObjectMapper mapper = new ObjectMapper();
    private final UserRepository userDao;
    private final PfBudgetRepository pfBudgetDao;
    private final PfCategoryRepository pfCategoryDao;
    private final PfBucketRepository pfBucketDao;

    @Autowired
    private Validate validate;

    public PfBucketController(UserRepository userDao, PfBudgetRepository pfBudgetDao, PfCategoryRepository pfCategoryDao, PfBucketRepository pfBucketDao) {
        this.userDao = userDao;
        this.pfBudgetDao = pfBudgetDao;
        this.pfCategoryDao = pfCategoryDao;
        this.pfBucketDao = pfBucketDao;
    }

    @GetMapping("/buckets/{id}")
    public List<PfBucket> getBucketsByPfCategoryId(@PathVariable Long id) throws JsonProcessingException {
        PfCategory pfCategory = validate.userOwnsPfCategory(id);
        List<PfBucket> pfBuckets = pfBucketDao.findAllByPfCategory(pfCategory);
        System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(pfBuckets));
        return pfBuckets;
    }

    @PostMapping("/buckets/{id}")
    public PfBucket addBucketByPfCategoryId(@PathVariable Long id, @RequestBody PfBucket pfBucket) throws JsonProcessingException {
        PfCategory pfCategory = validate.userOwnsPfCategory(id);
        pfBucket.setPfCategory(pfCategory);
        pfBucketDao.save(pfBucket);
        CalculatePfCategory.addAmount(pfCategory, pfBucket, pfCategoryDao);
        return pfBucket;
    }

    @GetMapping("/bucket/{id}")
    public PfBucket getPfBucketById(@PathVariable Long id) throws JsonProcessingException {
        PfBucket pfBucket = validate.userOwnsPfBucket(id);
        System.out.println((userDao.findByPfBudgets_PfCategories_PfBuckets(pfBucket)).getUsername());
        System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(pfBucket));
        return pfBucket;
    }

    @PutMapping("/bucket/{id}")
    public PfBucket updatePfBucketById(@PathVariable Long id, @RequestBody PfBucket updatedPfBucket) throws JsonProcessingException {
        PfBucket pfBucket = validate.userOwnsPfBucket(id);
        updatedPfBucket.setId(pfBucket.getId());
        updatedPfBucket.setPfCategory(pfBucket.getPfCategory());
        pfBucketDao.save(updatedPfBucket);
        CalculatePfCategory.allAmounts(pfBucket.getPfCategory(), pfCategoryDao);
        return updatedPfBucket;
    }

    @DeleteMapping("/bucket/{id}")
    public PfBucket deletePfBucketById(@PathVariable Long id) throws JsonProcessingException {
        PfBucket pfBucket = validate.userOwnsPfBucket(id);
        PfCategory pfCategory = pfCategoryDao.findByPfBuckets(pfBucket);
        List<PfBucket> pfCategoryPfBuckets = pfCategory.getPfBuckets();
        pfCategoryPfBuckets.remove(pfBucket);
        pfCategory.setPfBuckets(pfCategoryPfBuckets);
        pfCategoryDao.save(pfCategory);
        pfBucketDao.delete(pfBucket);
        CalculatePfCategory.allAmounts(pfCategory, pfCategoryDao);
        return pfBucket;
    }
}
