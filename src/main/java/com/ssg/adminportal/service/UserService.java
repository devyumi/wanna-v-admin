package com.ssg.adminportal.service;

import com.ssg.adminportal.domain.User;

import java.util.List;

public interface UserService {
    List<User> getAll();

    User getUser(Long userId);
}
