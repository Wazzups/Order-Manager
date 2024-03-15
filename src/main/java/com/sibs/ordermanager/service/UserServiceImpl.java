package com.sibs.ordermanager.service;

import com.sibs.ordermanager.model.User;
import com.sibs.ordermanager.repository.UserRepository;
import com.sibs.ordermanager.service.interfaces.UserService;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;

  @Autowired
  public UserServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public User saveUser(User user) {
    return userRepository.save(user);
  }

  @Override
  public Optional<User> getUserById(Long id) {
    return userRepository.findById(id);
  }

  @Override
  public List<User> getAllUsers() {
    return userRepository.findAll();
  }

  @Override
  public void deleteUser(Long id) {
    userRepository.deleteById(id);
  }
}