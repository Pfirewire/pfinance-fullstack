package com.pfinance.pfinancefullstack.controllers;

import com.pfinance.pfinancefullstack.models.Group;
import com.pfinance.pfinancefullstack.models.User;
import com.pfinance.pfinancefullstack.repositories.GroupRepository;
import com.pfinance.pfinancefullstack.repositories.UserRepository;
import com.pfinance.pfinancefullstack.utils.UserUtils;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import retrofit2.http.Path;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class GroupController {

    private final GroupRepository groupDao;
    private final UserRepository userDao;

    public GroupController(GroupRepository groupDao, UserRepository userDao) {
        this.groupDao = groupDao;
        this.userDao = userDao;
    }

    @GetMapping("/groups")
    public List<Group> getAllCurrentUserGroups() {
        System.out.println("Inside getAllGroups");
        User user = userDao.findByUsername(UserUtils.currentUsername());
        return groupDao.findAllByUser(user);
    }

    @GetMapping("/group/{id}")
    public Group getGroupById(@PathVariable Long id) {
        return groupDao.findById(id).orElseThrow(() -> new EntityNotFoundException(String.format("Group with id %d was not found", id)));
    }

    @PostMapping("/group/add")
    public Group addGroup(@RequestBody Group group) {
        User user = userDao.findByUsername(UserUtils.currentUsername());
        group.setUser(user);
        groupDao.save(group);
        return group;
    }
}
