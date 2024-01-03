package lk.ijse.coir.dto;


import lombok.*;

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public class UserDto {
        private String user_id;
        private String user_name;
        private String email;
        private String password;
        private String employee_id;
    }


