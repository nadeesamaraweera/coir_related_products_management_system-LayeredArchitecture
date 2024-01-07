package lk.ijse.coir.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Employee {
        private String employeeId;
        private String employeeName;
        private String email;
        private String jobTitle;
        private String tel;
        private Double salary;
        private String date;
}
