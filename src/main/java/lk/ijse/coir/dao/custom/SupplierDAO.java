package lk.ijse.coir.dao.custom;

import lk.ijse.coir.dao.CrudDAO;
import lk.ijse.coir.entity.Supplier;

import java.sql.SQLException;

public interface SupplierDAO extends CrudDAO<Supplier> {

    String generateNewID() throws SQLException, ClassNotFoundException;

}
