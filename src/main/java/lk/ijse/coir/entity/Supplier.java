package lk.ijse.coir.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class Supplier {
    private String supplierId;
    private String supplierName;
    private String address;
    private String tel;
}
