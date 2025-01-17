package com.agrestina.dto.user;

import com.agrestina.domain.user.User;
import com.agrestina.domain.user.UserRole;

public record GetUserDTO(String id, String name, String login, UserRole userRole, Boolean active) {
    public GetUserDTO(User user){
        this(user.getId(), user.getName(), user.getLogin(), user.getUserRole(), user.isActive());
    }
}
