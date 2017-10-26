package com.lzc.pet.service;

import com.lzc.pet.domain.User;

public interface UserService {
    public String email(String url);
    public String checked(User user,String email);
    public String pass(String message);
}
