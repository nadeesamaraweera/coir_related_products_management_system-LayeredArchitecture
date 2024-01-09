package lk.ijse.coir.dto.tm;


import javafx.scene.control.Button;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ItemTm {
        private String itemId;
        private String itemName;
        private BigDecimal unitPrice;
        private int qtyOnHand;
        private String rawMaterialId;
        private Button btn;

        @Override
        public String toString() {
                return "ItemTm{" +
                        "code='" + itemId + '\'' +
                        ", description='" + itemName + '\'' +
                        ", unitPrice=" + unitPrice +
                        ", qtyOnHand=" + qtyOnHand +
                        '}';

        }
}