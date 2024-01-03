package lk.ijse.coir.dto.tm;



import javafx.scene.control.Button;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class CartTm {
    private String itemId;
    private String itemName;
    private int qty;
    private double unitPrice;
    private double tot;
    private Button btn;
}