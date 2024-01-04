package lk.ijse.coir.dao.custom;

import lk.ijse.coir.dto.CustomerDto;
import lk.ijse.coir.entity.Customer;

import java.sql.SQLException;
import java.util.List;

public interface CustomerDAO {

    public List<Customer> getAll() throws SQLException, ClassNotFoundException;

    boolean update(Customer customer) throws SQLException, ClassNotFoundException;

    boolean save(Customer customer) throws SQLException, ClassNotFoundException;

     boolean delete(Customer customer) throws SQLException, ClassNotFoundException;

    boolean search(Customer customer) throws SQLException, ClassNotFoundException;


}
