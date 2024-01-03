package lk.ijse.coir.dto.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class EmployeeTm {

        private String employeeId;
        private String employeeName;
        private String email;
        private String jobTitle;
        private String tel;
        private Double salary;
        private String date;
    }


