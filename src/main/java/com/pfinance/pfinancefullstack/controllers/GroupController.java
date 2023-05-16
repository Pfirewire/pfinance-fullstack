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
        User user = UserUtils.currentUser(userDao);
        return groupDao.findAllByUser(user);
    }

    @PostMapping("/groups")
    public Group addGroup(@RequestBody Group group) {
        User user = UserUtils.currentUser(userDao);
        group.setUser(user);
        groupDao.save(group);
        return group;
    }

    @GetMapping("/group/{id}")
    public Group getGroupById(@PathVariable Long id) {
        return groupDao.findById(id).orElseThrow(() -> new EntityNotFoundException(String.format("Group with id %d was not found", id)));
    }

    @PutMapping("/group/{id}")
    public Group updateGroupById(@PathVariable Long id, @RequestBody Group updatedGroup) {
        Group group = groupDao.findById(id).orElseThrow(() -> new EntityNotFoundException(String.format("Group with id %d was not found", id)));
        group.setName(updatedGroup.getName());
        groupDao.save(group);
        return group;
    }

    @DeleteMapping("/group/{id}")
    public Group deleteGroupById(@PathVariable Long id) {
        Group group = groupDao.findById(id).orElseThrow(() -> new EntityNotFoundException(String.format("Group with id %d was not found", id)));
        groupDao.delete(group);
        return group;
    }

}
