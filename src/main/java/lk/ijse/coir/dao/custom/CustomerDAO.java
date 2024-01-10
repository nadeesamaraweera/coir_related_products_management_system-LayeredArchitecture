package lk.ijse.coir.dao.custom;

import lk.ijse.coir.dao.CrudDAO;
import lk.ijse.coir.dto.CustomerDto;
import lk.ijse.coir.entity.Customer;

import java.sql.SQLException;
import java.util.List;

public interface CustomerDAO extends CrudDAO<Customer> {
    int totalCustomerCount() throws ClassNotFoundException;

    String generateNewID() throws SQLException, ClassNotFoundException;
    //static String totalCustomerCount() throws SQLException, ClassNotFoundException;

        //boolean exist(String id) throws SQLException, ClassNotFoundException;

    //boolean delete(Customer customer) throws SQLException, ClassNotFoundException;

    /*public List<Customer> getAll() throws SQLException, ClassNotFoundException;

     public List<Customer> loadAll() throws SQLException, ClassNotFoundException;


    boolean update(Customer customer) throws SQLException, ClassNotFoundException;

    boolean save(Customer customer) throws SQLException, ClassNotFoundException;

    boolean search(Customer customer) throws SQLException, ClassNotFoundException;*/


}
