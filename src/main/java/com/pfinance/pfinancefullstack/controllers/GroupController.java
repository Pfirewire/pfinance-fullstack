package com.pfinance.pfinancefullstack.controllers;

import com.pfinance.pfinancefullstack.models.Group;
import com.pfinance.pfinancefullstack.models.User;
import com.pfinance.pfinancefullstack.repositories.GroupRepository;
import com.pfinance.pfinancefullstack.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import retrofit2.http.Path;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:3000")
@RequestMapping("/api")
public class GroupController {

    private final GroupRepository groupDao;
    private final UserRepository userDao;

    public GroupController(GroupRepository groupDao, UserRepository userDao) {
        this.groupDao = groupDao;
        this.userDao = userDao;
    }

    @GetMapping("/groups/")
    public List<Group> getAllGroups() {
        User user = userDao.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        System.out.println("Inside getAllGroups");
        return groupDao.findAllByUser(user);
    }

    @GetMapping("/group/{id}")
    public Group getGroupById(@PathVariable Long id) {
        Group group = groupDao.findById(id)
                .orElseThrow(
                        () -> new EntityNotFoundException(String.format("Group with id %d was not found", id))
                );
        return group;
    }

    @PostMapping("/group/add")
    public Group addGroup(@RequestBody Group group) {
        User user = userDao.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        group.setUser(user);
        groupDao.save(group);
        return group;
    }
}
