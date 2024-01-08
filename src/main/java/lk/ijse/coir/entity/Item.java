package lk.ijse.coir.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Item {
    private String itemId;
    private String itemName;
    private double unitPrice;
    private int qtyOnHand;
    private String rawMaterialId;
}
