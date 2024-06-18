package com.example.hotel_cms.service;

import com.example.hotel_cms.model.Role;
import com.example.hotel_cms.model.User;
import org.mapstruct.control.MappingControl;

import java.util.List;

public interface UserService {
    User create(User user, Role role);
    User update(Long id, User user);
    User findByUsername(String username);
    User findById(Long id);
    List<User> findAll();
    void delete(Long id);
}
