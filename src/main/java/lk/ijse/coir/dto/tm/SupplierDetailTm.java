package lk.ijse.coir.dto.tm;

import javafx.scene.control.Button;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class SupplierDetailTm {

    private String supplierId;
    private String rawMaterialId;
    private String materialName;
    private int qty;
    private BigDecimal unitPrice;
    private BigDecimal total;

    public SupplierDetailTm(String materialId, String materialName, int qty, BigDecimal unitPrice, BigDecimal total) {
        this.rawMaterialId = materialId;
        this.materialName = materialName;
        this.qty =qty;
        this.unitPrice =unitPrice;
        this.total =total;

    }

}