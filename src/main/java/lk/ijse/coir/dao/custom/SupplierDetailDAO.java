package lk.ijse.coir.dao.custom;

import lk.ijse.coir.dao.CrudDAO;
import lk.ijse.coir.entity.Order;
import lk.ijse.coir.entity.SupplierDetail;

import java.sql.SQLException;

public interface SupplierDetailDAO extends CrudDAO<SupplierDetail> {
    String generateID() throws SQLException, ClassNotFoundException;

}
