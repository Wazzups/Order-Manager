package com.sibs.ordermanager.controller;

import com.sibs.ordermanager.model.User;
import com.sibs.ordermanager.service.interfaces.UserService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

  private final UserService userService;

  @Autowired
  public UserController(UserService userService) {
    this.userService = userService;
  }

  @PostMapping
  public ResponseEntity<User> createUser(@RequestBody User user) {
    User savedUser = userService.saveUser(user);
    return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
  }

  @GetMapping("/{id}")
  public ResponseEntity<User> getUserById(@PathVariable Long id) {
    return userService.getUserById(id).map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.notFound().build());
  }

  @GetMapping
  public ResponseEntity<List<User>> getAllUsers() {
    List<User> users = userService.getAllUsers();
    return new ResponseEntity<>(users, HttpStatus.OK);
  }

  @PutMapping("/{id}")
  public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User userDetails) {
    return userService.getUserById(id).map(user -> {
      user.setName(userDetails.getName());
      user.setEmail(userDetails.getEmail());
      User updatedUser = userService.saveUser(user);
      return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }).orElseGet(() -> ResponseEntity.notFound().build());
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
    return userService.getUserById(id).map(user -> {
      userService.deleteUser(id);
      return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }).orElseGet(() -> ResponseEntity.notFound().build());
  }
}
