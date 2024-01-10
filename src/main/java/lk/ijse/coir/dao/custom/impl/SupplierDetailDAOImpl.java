package lk.ijse.coir.dao.custom.impl;

import lk.ijse.coir.dao.SQLUtil;
import lk.ijse.coir.dao.custom.SupplierDetailDAO;
import lk.ijse.coir.entity.SupplierDetail;

import java.sql.SQLException;
import java.util.ArrayList;

public class SupplierDetailDAOImpl implements SupplierDetailDAO {
    @Override
    public ArrayList<SupplierDetail> getAll() throws SQLException, ClassNotFoundException {
        return null;
    }


    @Override
    public boolean save(SupplierDetail entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("INSERT INTO supplier_detail (supplier_id, rawMaterial_id,date,unit_price,qty_on_stock) VALUES (?,?,?,?,?)", entity.getSupplierId(), entity.getRawMaterialId(), entity.getDate(),entity.getUnitPrice(),entity.getQtyOnStock());
    }

    @Override
    public boolean update(SupplierDetail entity) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean exist(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public String generateID() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return  false;
    }

    @Override
    public SupplierDetail search(String id) throws SQLException, ClassNotFoundException {
        return null;
    }
}
