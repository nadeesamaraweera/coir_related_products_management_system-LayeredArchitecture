package lk.ijse.coir.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.math.BigDecimal;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SupplierDetail {
    private String supplierId;
    private String rawMaterialId;
    private LocalDate date;
    private BigDecimal unitPrice;
    private int qtyOnStock;
}
