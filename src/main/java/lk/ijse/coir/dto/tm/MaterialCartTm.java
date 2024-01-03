package lk.ijse.coir.dto.tm;

import javafx.scene.control.Button;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor

public class MaterialCartTm {

    private String supplierId;
    private String rawMaterialId;
    private String materialName;
    private int qty;
    private double unitPrice;
    private double tot;
    private Button btn;

    public MaterialCartTm(String materialId, String materialName, int qty, double unitPrice, double total, Button btn) {
        this.rawMaterialId = materialId;
        this.materialName = materialName;
        this.qty =qty;
        this.unitPrice =unitPrice;
        this.tot =total;
        this.btn =btn;
    }
}