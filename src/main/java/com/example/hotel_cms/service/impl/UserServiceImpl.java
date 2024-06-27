package com.example.hotel_cms.service.impl;

import com.example.hotel_cms.exception.EntityNotFoundException;
import com.example.hotel_cms.exception.NotUniqUserException;
import com.example.hotel_cms.model.Role;
import com.example.hotel_cms.model.User;
import com.example.hotel_cms.model.kafka.UserEvent;
import com.example.hotel_cms.repository.UserRepository;
import com.example.hotel_cms.service.UserService;
import com.example.hotel_cms.utility.BeanUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    @Value("${app.kafka.kafkaUserTopic}")
    private String topicName;

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final KafkaTemplate<String, UserEvent> kafkaTemplate;

    @Override
    public User create(User user, Role role) {
        if (userRepository.existsByUsernameOrEmail(user.getUsername(), user.getEmail()))
            throw new NotUniqUserException("username and email should be uniq");
        user.setRole(role);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        role.setUser(user);

        User savedUser = userRepository.save(user);

        // Send event to Kafka
        UserEvent event = new UserEvent();
        event.setUserId(savedUser.getId());
        kafkaTemplate.send(topicName, event);

        return savedUser;
    }

    @Override
    public User update(Long id, User user) {
        User userToUpdate = findById(id);
        BeanUtils.copyProperties(userToUpdate,user);
        userToUpdate.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(userToUpdate);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(()-> new EntityNotFoundException(
                MessageFormat.format("user with name {0} not found", username)));
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(()-> new EntityNotFoundException(
                MessageFormat.format("user with id {0} not found", id)));
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }
}
