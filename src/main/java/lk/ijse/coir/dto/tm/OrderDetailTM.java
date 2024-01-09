package lk.ijse.coir.dto.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class OrderDetailTM {
    private String itemId;
    private String itemName;
    private int qty;
    private BigDecimal unitPrice;
    private BigDecimal total;


    @Override
    public String toString() {
        return "OrderDetailTM{" +
                "itemCode='" + itemId + '\'' +
                ", qty=" + qty +
                ", unitPrice=" + unitPrice +
                '}';
    }
}
