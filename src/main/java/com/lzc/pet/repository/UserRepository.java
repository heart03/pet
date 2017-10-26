package com.lzc.pet.repository;

import com.lzc.pet.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository  extends JpaRepository<User, Integer> {
    public List<User> findByEmail (String email);
    public List<User> findByUname(String name);
}
