package lk.ijse.coir.dao.custom.impl;

import lk.ijse.coir.dao.SQLUtil;
import lk.ijse.coir.dao.custom.SupplierDAO;
import lk.ijse.coir.entity.Customer;
import lk.ijse.coir.entity.RawMaterial;
import lk.ijse.coir.entity.Supplier;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SupplierDAOImpl implements SupplierDAO {

    @Override
    public ArrayList<Supplier> getAll() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute("SELECT * FROM supplier");
        ArrayList<Supplier> suppliers = new ArrayList<>();

        while (resultSet.next()) {
            Supplier supplier = new Supplier(
                    resultSet.getString("supplier_id"),
                    resultSet.getString("supplier_name"),
                    resultSet.getString("address"),
                    resultSet.getString("tel"));

            suppliers.add(supplier);

        }
        return suppliers;

    }


    @Override
    public boolean save(Supplier supplier) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("INSERT INTO supplier VALUES(?, ?, ?, ?)",
                supplier.getSupplierId(),
                supplier.getSupplierName(),
                supplier.getAddress(),
                supplier.getTel()

        );

    }


    @Override
    public boolean update(Supplier supplier) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("UPDATE supplier SET supplier_name = ?, address = ?, tel = ? WHERE supplier_id = ?",
                supplier.getSupplierName(),
                supplier.getAddress(),
                supplier.getTel(),
                supplier.getSupplierId()

        );
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("DELETE FROM supplier WHERE supplier_id = ?", id);
    }


    @Override
    public boolean exist(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst=SQLUtil.execute("SELECT * FROM supplier WHERE supplier_id=?",id);
        return rst.next();

    }

    @Override
    public Supplier search(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM supplier WHERE supplier_id=?", id);
        while (rst.next()){
            Supplier supplier =new Supplier(rst.getString(1),rst.getString(2),rst.getString(3),rst.getString(4));
            return supplier;
        }
        return  null;
    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT supplier_id FROM supplier ORDER BY supplier_id DESC LIMIT 1");
        if (rst.next()) {
            String id = rst.getString("supplier_id");
            int newSupplierId = Integer.parseInt(id.replace("S00", "")) + 1;
            return String.format("S%03d", newSupplierId);
        } else {
            return "S001";
        }
    }

}
