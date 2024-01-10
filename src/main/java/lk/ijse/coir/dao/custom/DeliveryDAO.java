package lk.ijse.coir.dao.custom;

import lk.ijse.coir.dao.CrudDAO;
import lk.ijse.coir.entity.Customer;
import lk.ijse.coir.entity.Delivery;

import java.sql.SQLException;
import java.util.ArrayList;

public interface DeliveryDAO extends CrudDAO<Delivery> {
    String generateNewID() throws SQLException, ClassNotFoundException;
}
