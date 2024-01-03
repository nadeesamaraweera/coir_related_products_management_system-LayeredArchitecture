package lk.ijse.coir.dto;

import lk.ijse.coir.dto.tm.CartTm;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public class PlaceOrderDto {
        private String orderId;
        private String customerId;
        private LocalDate date;
        private List<CartTm> tmList = new ArrayList<>();
    }

