package lk.ijse.coir.dao.custom;

import lk.ijse.coir.dao.CrudDAO;
import lk.ijse.coir.entity.Customer;
import lk.ijse.coir.entity.Delivery;

import java.sql.SQLException;
import java.util.ArrayList;

public interface DeliveryDAO extends CrudDAO<Delivery> {
    String generateNewID() throws SQLException, ClassNotFoundException;
    /*oolean save(Customer customer) throws SQLException, ClassNotFoundException;

    ArrayList<Delivery> getAll() throws SQLException, ClassNotFoundException;

    boolean save(Delivery delivery) throws SQLException, ClassNotFoundException;

    boolean update(Delivery delivery) throws SQLException, ClassNotFoundException;

    boolean delete(String id) throws SQLException, ClassNotFoundException;

    boolean exist(String id) throws SQLException, ClassNotFoundException;

    Delivery search(String id) throws SQLException, ClassNotFoundException;*/
}
