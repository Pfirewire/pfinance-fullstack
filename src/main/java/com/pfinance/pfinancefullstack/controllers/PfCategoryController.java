package com.pfinance.pfinancefullstack.controllers;

import com.pfinance.pfinancefullstack.models.PfBudget;
import com.pfinance.pfinancefullstack.models.PfCategory;
import com.pfinance.pfinancefullstack.repositories.PfBudgetRepository;
import com.pfinance.pfinancefullstack.repositories.PfCategoryRepository;
import com.pfinance.pfinancefullstack.repositories.UserRepository;
import com.pfinance.pfinancefullstack.services.Validate;
import com.pfinance.pfinancefullstack.utils.CalculatePfCategory;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class PfCategoryController {

    @Autowired
    private Validate validate;

    private final UserRepository userDao;
    private final PfBudgetRepository pfBudgetDao;
    private final PfCategoryRepository pfCategoryDao;

    public PfCategoryController(PfCategoryRepository pfCategoryDao, UserRepository userDao, PfBudgetRepository pfBudgetDao) {
        this.pfCategoryDao = pfCategoryDao;
        this.userDao = userDao;
        this.pfBudgetDao = pfBudgetDao;
    }

    @GetMapping("/categories/{id}")
    public List<PfCategory> getAllPfCategoriesByPfBudgetId(@PathVariable Long id) {
        PfBudget pfBudget = validate.userOwnsPfBudget(id);
        return pfBudget.getPfCategories();
    }

    @PostMapping("/categories/{id}")
    public PfCategory addPfCategoryByPfBudgetId(@PathVariable long id, @RequestBody PfCategory pfCategory) {
        PfBudget pfBudget = validate.userOwnsPfBudget(id);
        pfCategory.setPfBudget(pfBudget);
        pfCategoryDao.save(pfCategory);
        List<PfCategory> pfCategories = pfBudget.getPfCategories();
        pfCategories.add(pfCategory);
        pfBudget.setPfCategories(pfCategories);
        pfBudgetDao.save(pfBudget);
        return pfCategory;
    }

    @GetMapping("/category/{id}")
    public PfCategory getPfCategoryById(@PathVariable Long id) {
        return validate.userOwnsPfCategory(id);
    }

    @PutMapping("/category/{id}")
    public PfCategory updatePfCategoryById(@PathVariable Long id, @RequestBody PfCategory updatedPfCategory) {
        PfCategory pfCategory = validate.userOwnsPfCategory(id);
        pfCategory.setName(updatedPfCategory.getName());
        pfCategoryDao.save(pfCategory);
        return pfCategory;
    }

    @DeleteMapping("/category/{id}")
    public PfCategory deletePfCategoryById(@PathVariable Long id) {
        PfCategory pfCategory = validate.userOwnsPfCategory(id);
        pfCategoryDao.delete(pfCategory);
        return pfCategory;
    }

}
