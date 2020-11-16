package io.ruke.backend.demo.controller;

import io.ruke.backend.demo.entity.User;
import io.ruke.backend.demo.exception.ResourceNotFoundException;
import io.ruke.backend.demo.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Get all users
     *
     * @return User's list
     */
    @GetMapping
    public List<User> getAll() {
        return userRepository.findAll();
    }

    /**
     * Get user by ID
     *
     * @param id user ID
     * @return User with specified ID
     */
    @GetMapping("/{id}")
    public User getById(@PathVariable("id") long id) {
        return userRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User " + id + " not found"));
    }

    /**
     * Create new user
     *
     * @param user User object request
     * @return created user
     */
    @PostMapping
    public User create(@Valid @RequestBody User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    /**
     * Create new user
     *
     * @param user User object request
     * @return created user
     */
    @PutMapping("/{id}")
    public User update(@Valid @RequestBody User user, @PathVariable("id") long id) {
        User storedUser = userRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User " + id + " not found"));
        storedUser.setFirstName(user.getFirstName());
        storedUser.setLastName(user.getLastName());
        storedUser.setEmail(user.getEmail());
        return userRepository.save(storedUser);
    }

    /**
     * Delete stored User
     *
     * @param id User's ID
     * @return Response
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<User> delete(@PathVariable("id") long id) {
        User storedUser = userRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User " + id + " not found"));
        userRepository.delete(storedUser);
        return ResponseEntity.ok().build();
    }
}
