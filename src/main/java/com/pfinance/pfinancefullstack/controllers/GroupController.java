package com.pfinance.pfinancefullstack.controllers;

import com.pfinance.pfinancefullstack.models.Group;
import com.pfinance.pfinancefullstack.repositories.GroupRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class GroupController {

    private final GroupRepository groupDao;

    public GroupController(GroupRepository groupDao) {
        this.groupDao = groupDao;
    }

    @GetMapping("/groups")
    public List<Group> getAllGroups() {
        return groupDao.findAll();
    }
}
