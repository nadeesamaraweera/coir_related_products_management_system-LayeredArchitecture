package lk.ijse.coir.dto.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RawTm {
    private String rawMaterialId;
    private String materialName;
    private Double qtyOnStock;
    private Double unitPrice;


}




