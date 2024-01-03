package lk.ijse.coir.dto.tm;


import javafx.scene.control.Button;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ItemTm {
        private String itemId;
        private String itemName;
        private double unitPrice;
        private int qtyOnHand;
        private String rawMaterialId;
        private Button btn;
}