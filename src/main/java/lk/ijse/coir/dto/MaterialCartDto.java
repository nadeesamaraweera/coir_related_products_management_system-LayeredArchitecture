package lk.ijse.coir.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor

public class MaterialCartDto {

    private String supplierId;
    private String rawMaterialId;

    private LocalDate date;

    private  Double unitPrice;

    private int qtyOnStock;
}
