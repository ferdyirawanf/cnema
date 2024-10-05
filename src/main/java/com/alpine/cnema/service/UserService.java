package com.alpine.cnema.service;

import com.alpine.cnema.dto.request.UserDTO;
import com.alpine.cnema.model.User;

public interface UserService {
    User register(UserDTO.Register req);
    String login(UserDTO.Login req);
}
