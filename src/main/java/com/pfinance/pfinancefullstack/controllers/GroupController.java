package com.pfinance.pfinancefullstack.controllers;

import com.pfinance.pfinancefullstack.models.Group;
import com.pfinance.pfinancefullstack.repositories.GroupRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:3000")
@RequestMapping("/api")
public class GroupController {

    private final GroupRepository groupDao;

    public GroupController(GroupRepository groupDao) {
        this.groupDao = groupDao;
    }

    @GetMapping("/groups")
    public List<Group> getAllGroups() {
        System.out.println("Inside getAllGroups");
        return groupDao.findAll();
    }

    @GetMapping("/group/{id}")
    public Group getGroupById(@PathVariable Long id) {
        Group group = groupDao.findById(id).get();
        return group;
    }
}
