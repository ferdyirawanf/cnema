package com.alpine.cnema.dto.request;


import com.alpine.cnema.utils.enums.Role;
import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.processing.Pattern;

public class UserDTO {
//  main userDTO, it works - irfan (24/9/2024)
    @Data
    @Builder
    public static class Register{
        private String username;
        // under construction - irfan (24/9/2024)
            private String phone;
            private String address;
        // end
        private String email; // irfan (24/9/2024)
        private String password;
        private Role role;

//    mencoba untuk menambahkan validasi - under construction < irfan (24/9/2024) >
//    @Data
//    @Builder
//    public static class Register{
//        private String username;
//        @Pattern(
//                regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$",
//                message = "Invalid email format"
//        )

    }

    @Data
    @Builder
    public static class Login{
        private String username;
        private String password;
    }
}
