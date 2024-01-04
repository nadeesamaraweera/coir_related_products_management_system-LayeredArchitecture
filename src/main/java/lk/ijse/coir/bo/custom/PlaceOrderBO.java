package lk.ijse.coir.bo.custom;

import lk.ijse.coir.bo.SuperBO;
import lk.ijse.coir.dto.CustomerDto;

import java.sql.SQLException;

public interface PlaceOrderBO extends SuperBO {
    CustomerDto searchCustomer(String id) throws SQLException, ClassNotFoundException;
}
