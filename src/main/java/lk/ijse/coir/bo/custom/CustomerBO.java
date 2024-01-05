package lk.ijse.coir.bo.custom;

import lk.ijse.coir.bo.SuperBO;
import lk.ijse.coir.dto.CustomerDto;
import lk.ijse.coir.entity.Customer;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface CustomerBO extends SuperBO {

    ArrayList<CustomerDto> getAllCustomers() throws SQLException, ClassNotFoundException;

    boolean updateCustomer(CustomerDto customerDto) throws SQLException, ClassNotFoundException;

    boolean saveCustomer(CustomerDto customerDto) throws SQLException, ClassNotFoundException;

    boolean existCustomer(String id) throws SQLException, ClassNotFoundException;

    void deleteCustomer(String id) throws SQLException, ClassNotFoundException;

    CustomerDto searchCustomer(String id) throws SQLException, ClassNotFoundException;





}
