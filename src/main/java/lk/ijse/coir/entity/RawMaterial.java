package lk.ijse.coir.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RawMaterial {
        private String rawMaterialId;
        private String materialName;
        private Double qtyOnStock;
        private BigDecimal unitPrice;




    }


