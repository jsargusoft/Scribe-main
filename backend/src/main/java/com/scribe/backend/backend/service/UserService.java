package com.scribe.backend.backend.service;

import com.scribe.backend.backend.dto.UserRegister;
import com.scribe.backend.backend.entity.User;

public interface UserService {

    void registerUser(UserRegister userRegister);

    public User getCurrentlyLoggedInUser();
}
