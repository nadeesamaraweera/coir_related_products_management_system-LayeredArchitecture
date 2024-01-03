package lk.ijse.coir.dto;

import lk.ijse.coir.dto.tm.MaterialCartTm;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class GetOrderDto {
    private String supplierId;
    private String  rawMaterialId;
    private LocalDate date;
    private List<MaterialCartTm> tmList = new ArrayList<>();

}
