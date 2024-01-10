package lk.ijse.coir.entity;

import lk.ijse.coir.dto.SupplierDetailDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SupplierDetail {
    private String supplierId;
    private String rawMaterialId;
    private LocalDate date;
    private BigDecimal unitPrice;
    private int qtyOnStock;

    public SupplierDetail(LocalDate stockDate, String supplierId, List<SupplierDetailDto> supplierDetail) {
    }
}
