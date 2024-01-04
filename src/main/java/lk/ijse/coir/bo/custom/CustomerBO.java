package lk.ijse.coir.bo.custom;

import lk.ijse.coir.bo.SuperBO;
import lk.ijse.coir.dto.CustomerDto;
import lk.ijse.coir.entity.Customer;

import java.sql.SQLException;
import java.util.List;

public interface CustomerBO extends SuperBO {

    List<Customer> getAllCustomers() throws SQLException, ClassNotFoundException;
    boolean updateCustomer(CustomerDto customerDto) throws SQLException, ClassNotFoundException;

    boolean saveCustomer(CustomerDto customerDto) throws SQLException, ClassNotFoundException;

    boolean deleteCustomer(CustomerDto customerDto) throws SQLException, ClassNotFoundException;

    CustomerDto searchCustomer(String customerDto) throws SQLException, ClassNotFoundException;





}
