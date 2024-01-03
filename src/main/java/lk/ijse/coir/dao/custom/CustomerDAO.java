package lk.ijse.coir.dao.custom;

import lk.ijse.coir.dto.CustomerDto;
import lk.ijse.coir.entity.Customer;

import java.sql.SQLException;
import java.util.List;

public interface CustomerDAO {
    boolean update(Customer customer) throws SQLException, ClassNotFoundException;

    public List<Customer> getAll() throws SQLException, ClassNotFoundException;
}
