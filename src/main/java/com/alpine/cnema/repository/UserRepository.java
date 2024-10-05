package com.alpine.cnema.repository;

import com.alpine.cnema.model.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends BaseRepository<User, Integer>{
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email); 
    Optional<User> findByPhone(String phone); 
}
