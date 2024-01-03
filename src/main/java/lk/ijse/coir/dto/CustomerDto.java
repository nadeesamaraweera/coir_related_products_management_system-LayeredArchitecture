package lk.ijse.coir.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CustomerDto {
    private String customerId;
    private String customerName;
    private String address;
    private String tel;


}
