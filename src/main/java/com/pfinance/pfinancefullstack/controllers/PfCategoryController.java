package com.pfinance.pfinancefullstack.controllers;

import com.pfinance.pfinancefullstack.models.PfCategory;
import com.pfinance.pfinancefullstack.models.User;
import com.pfinance.pfinancefullstack.repositories.PfCategoryRepository;
import com.pfinance.pfinancefullstack.repositories.UserRepository;
import com.pfinance.pfinancefullstack.utils.UserUtils;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class PfCategoryController {

    private final PfCategoryRepository pfCategoryDao;
    private final UserRepository userDao;

    public PfCategoryController(PfCategoryRepository pfCategoryDao, UserRepository userDao) {
        this.pfCategoryDao = pfCategoryDao;
        this.userDao = userDao;
    }

    @GetMapping("/categories")
    public List<PfCategory> getAllCurrentUserGroups() {
        System.out.println("Inside getAllGroups");
        User user = UserUtils.currentUser(userDao);
        return pfCategoryDao.findAllByUser(user);
    }

    @PostMapping("/categories")
    public PfCategory addGroup(@RequestBody PfCategory pfCategory) {
        User user = UserUtils.currentUser(userDao);
        pfCategory.setUser(user);
        pfCategoryDao.save(pfCategory);
        return pfCategory;
    }

    @GetMapping("/category/{id}")
    public PfCategory getGroupById(@PathVariable Long id) {
        return pfCategoryDao.findById(id).orElseThrow(() -> new EntityNotFoundException(String.format("Group with id %d was not found", id)));
    }

    @PutMapping("/category/{id}")
    public PfCategory updateGroupById(@PathVariable Long id, @RequestBody PfCategory updatedPfCategory) {
        PfCategory pfCategory = pfCategoryDao.findById(id).orElseThrow(() -> new EntityNotFoundException(String.format("Group with id %d was not found", id)));
        pfCategory.setName(updatedPfCategory.getName());
        pfCategoryDao.save(pfCategory);
        return pfCategory;
    }

    @DeleteMapping("/category/{id}")
    public PfCategory deleteGroupById(@PathVariable Long id) {
        PfCategory pfCategory = pfCategoryDao.findById(id).orElseThrow(() -> new EntityNotFoundException(String.format("Group with id %d was not found", id)));
        pfCategoryDao.delete(pfCategory);
        return pfCategory;
    }

}
