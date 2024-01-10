package lk.ijse.coir.dao.custom;

import lk.ijse.coir.dao.CrudDAO;
import lk.ijse.coir.entity.Customer;
import lk.ijse.coir.entity.Item;

import java.sql.SQLException;

public interface ItemDAO extends CrudDAO<Item> {
    int totalItemTypes() throws ClassNotFoundException;

    String generateNewID() throws SQLException, ClassNotFoundException;


}
