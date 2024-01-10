package lk.ijse.coir.dao.custom;

import lk.ijse.coir.dao.CrudDAO;
import lk.ijse.coir.dto.CustomerDto;
import lk.ijse.coir.entity.Customer;

import java.sql.SQLException;
import java.util.List;

public interface CustomerDAO extends CrudDAO<Customer> {
    int totalCustomerCount() throws ClassNotFoundException;

    String generateNewID() throws SQLException, ClassNotFoundException;

}
