package lk.ijse.coir.bo.custom;

import lk.ijse.coir.bo.SuperBO;
import lk.ijse.coir.dto.CustomerDto;
import lk.ijse.coir.entity.Customer;

import java.sql.SQLException;
import java.util.List;

public interface CustomerBO extends SuperBO {
    boolean update(CustomerDto customerDto) throws SQLException, ClassNotFoundException;

    List<Customer> getAllCustomers() throws SQLException, ClassNotFoundException;
}
