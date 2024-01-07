
package lk.ijse.coir.dao.custom.impl;

import lk.ijse.coir.dao.SQLUtil;
import lk.ijse.coir.dao.custom.RawMaterialDAO;
import lk.ijse.coir.entity.Customer;
import lk.ijse.coir.entity.RawMaterial;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class RawMaterialDAOImpl implements RawMaterialDAO {

    @Override
    public ArrayList<RawMaterial> getAll() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute("SELECT * FROM raw_material");
        ArrayList<RawMaterial> rawMaterials = new ArrayList<>();

        while (resultSet.next()) {
            RawMaterial rawMaterial = new RawMaterial(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getDouble(3),
                    resultSet.getDouble(4));

            rawMaterials.add(rawMaterial);

        }
        return rawMaterials;

    }

    @Override
    public boolean save(RawMaterial rawMaterial) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("INSERT INTO raw_material VALUES(?, ?, ?, ?)",
                rawMaterial.getRawMaterialId(),
                rawMaterial.getMaterialName(),
                rawMaterial.getQtyOnStock(),
                rawMaterial.getUnitPrice()

        );
    }


        @Override
        public boolean update(RawMaterial rawMaterial) throws SQLException, ClassNotFoundException {
            return SQLUtil.execute("UPDATE raw_material SET material_name = ?, qty_on_stock =? , unit_price =?  WHERE rawMaterial_id = ?",
                    rawMaterial.getMaterialName(),
                    rawMaterial.getQtyOnStock(),
                    rawMaterial.getUnitPrice(),
                    rawMaterial.getRawMaterialId()

                    );
        }

    @Override
    public boolean exist(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst=SQLUtil.execute("SELECT * FROM raw_material WHERE rawMaterial_id=?",id);
        return rst.next();
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("DELETE FROM raw_material WHERE  rawMaterial_id = ?", id);

    }

    @Override
    public RawMaterial search(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM raw_material WHERE  rawMaterial_id = ?", id);
        while (rst.next()){
            RawMaterial rawMaterial =new RawMaterial(rst.getString(1),rst.getString(2),rst.getDouble(3),rst.getDouble(4));
            return rawMaterial;
        }
        return  null;
    }
}

