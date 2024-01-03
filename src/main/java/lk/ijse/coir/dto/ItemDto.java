package lk.ijse.coir.dto;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ItemDto {
    private String itemId;
    private String itemName;
    private double unitPrice;
    private int qtyOnHand;
    private String rawMaterialId;

}