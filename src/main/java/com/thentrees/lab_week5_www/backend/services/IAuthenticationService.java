package com.thentrees.lab_week5_www.backend.services;

import com.thentrees.lab_week5_www.backend.models.User;

public interface IAuthenticationService {
    User login(String phone, String password);
//    String register(String phone, String password);
}
