package com.sibs.ordermanager.service.interfaces;

import com.sibs.ordermanager.model.User;
import java.util.List;
import java.util.Optional;

public interface UserService {

  User saveUser(User user);

  Optional<User> getUserById(Long id);

  List<User> getAllUsers();

  void deleteUser(Long id);
}