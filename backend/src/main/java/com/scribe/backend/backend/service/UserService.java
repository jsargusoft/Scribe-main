package com.scribe.backend.backend.service;

import java.util.List;

import com.scribe.backend.backend.dto.UserRegister;
import com.scribe.backend.backend.entity.Stories;
import com.scribe.backend.backend.entity.User;

public interface UserService {

    void registerUser(UserRegister userRegister);

    public User getCurrentlyLoggedInUser();

    public User getUserById(Integer userId);

    public User updateUser(Integer userId, UserRegister userRegister);

    public void deleteUser(Integer userId);

    public List<Stories> getStoryById(Integer userId);
}
