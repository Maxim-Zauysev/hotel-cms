package com.example.hotel_cms.web.controller;

import com.example.hotel_cms.mapping.UserMapper;
import com.example.hotel_cms.model.Role;
import com.example.hotel_cms.model.RoleType;
import com.example.hotel_cms.model.User;
import com.example.hotel_cms.service.UserService;
import com.example.hotel_cms.web.request.UserRequest;
import com.example.hotel_cms.web.response.UserResponse;
import com.example.hotel_cms.web.response.UserResponseList;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.mapstruct.control.MappingControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @PostMapping()
    public ResponseEntity<UserResponse> create(@RequestBody @Valid UserRequest request, @RequestParam RoleType roleType){
        User newUser = userService.create(userMapper.requestToUser(request), Role.from(roleType));
        return ResponseEntity.status(HttpStatus.CREATED).body(userMapper.userToResponse(newUser));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> update(@PathVariable Long id, @RequestBody UserRequest request){
        User updatedUser = userService.update(id,userMapper.requestToUser(request));
        return ResponseEntity.ok(userMapper.userToResponse(updatedUser));
    }

    @GetMapping
    public ResponseEntity<?> findAllOrFindByUsername(@RequestParam(required = false) String username) {
        if (username != null) {
            return ResponseEntity.ok(userMapper.userToResponse(userService.findByUsername(username)));
        } else {
            return ResponseEntity.ok( userMapper.userListToUserResponseList(userService.findAll()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> findById(@PathVariable Long id){
        return ResponseEntity.ok(userMapper.userToResponse(userService.findById(id)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id){
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
