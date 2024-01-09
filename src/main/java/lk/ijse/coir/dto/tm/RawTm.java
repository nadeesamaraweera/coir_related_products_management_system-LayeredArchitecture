package lk.ijse.coir.dto.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RawTm {
    private String rawMaterialId;
    private String materialName;
    private Double qtyOnStock;
    private BigDecimal unitPrice;

    @Override
    public String toString() {
        return "RawTm{" +
                "rawMaterialId='" + rawMaterialId + '\'' +
                ", materialName='" + materialName + '\'' +
                ", qtyOnStock=" + qtyOnStock +
                ", unitPrice=" + unitPrice +

                '}';

    }


}




