package com.alpine.cnema.dto.request;


import com.alpine.cnema.utils.enums.Role;
import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.processing.Pattern;

public class UserDTO {
    @Data
    @Builder
    public static class Register{
        private String username;
        private String phone;
        private String address;
        private String email; 
        private String password;
        private Role role;
    }

    @Data
    @Builder
    public static class Login{
        private String username;
        private String password;
    }
}
