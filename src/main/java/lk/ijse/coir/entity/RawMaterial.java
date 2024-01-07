package lk.ijse.coir.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RawMaterial {
        private String rawMaterialId;
        private String materialName;
        private Double qtyOnStock;
        private Double unitPrice;




    }


