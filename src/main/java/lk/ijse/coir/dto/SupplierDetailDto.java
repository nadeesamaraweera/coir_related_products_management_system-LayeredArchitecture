package lk.ijse.coir.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor

public class SupplierDetailDto {

    private String supplierId;
    private String rawMaterialId;

    private LocalDate date;

    private BigDecimal unitPrice;

    private int qtyOnStock;

    @Override
    public String toString() {
        return "SupplierDetailDto{" +
                "supplierId='" + supplierId + '\'' +
                ",rawMaterialId=" + rawMaterialId +
                ", date=" + date +
                ", unitPrice=" + unitPrice +
                ", qtyOnStock=" + qtyOnStock +
                '}';
    }
}
