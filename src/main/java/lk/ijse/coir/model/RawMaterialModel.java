package lk.ijse.coir.model;

import lk.ijse.coir.db.DbConnection;
import lk.ijse.coir.dto.RawMaterialDto;
import lk.ijse.coir.dto.tm.MaterialCartTm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RawMaterialModel {


    public boolean saveraw(final RawMaterialDto dto) throws SQLException {
        ;
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO raw_material VALUES(?, ?, ?,? )";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, dto.getRawMaterialId());
        pstm.setString(2, dto.getMaterialName());
        pstm.setDouble(3, Double.parseDouble(String.valueOf(dto.getQtyOnStock())));
        pstm.setDouble(4, Double.parseDouble(String.valueOf(dto.getUnitPrice())));


        boolean isSaved = pstm.executeUpdate() > 0;

        return isSaved;
    }

    public static boolean updateraw(final RawMaterialDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "UPDATE raw_material SET material_name = ?, qty_on_stock =? , unit_price =?  WHERE rawMaterial_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, dto.getMaterialName());
        pstm.setDouble(2, Double.parseDouble(String.valueOf(dto.getQtyOnStock())));
        pstm.setDouble(3, Double.parseDouble(String.valueOf(dto.getUnitPrice())));
        pstm.setString(4, dto.getRawMaterialId());


        return pstm.executeUpdate() > 0;
    }

    public static RawMaterialDto searchRaw(String rawMaterialId) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM raw_material WHERE rawMaterial_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, rawMaterialId);

        ResultSet resultSet = pstm.executeQuery();
        RawMaterialDto dto = null;

        if (resultSet.next()) {
            String raw_id = resultSet.getString(1);
            String raw_name = resultSet.getString(2);
            Double qty = Double.valueOf(resultSet.getString(3));
            Double unit_price = Double.valueOf(resultSet.getString(4));

            dto = new RawMaterialDto(raw_id, raw_name, qty, unit_price);
        }

        return dto;
    }

    public List<RawMaterialDto> getAllMaterials() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM raw_material";
        PreparedStatement pstm = connection.prepareStatement(sql);

        List<RawMaterialDto> dtoList = new ArrayList<>();

        ResultSet resultSet = pstm.executeQuery();

        while (resultSet.next()) {
            String raw_id = resultSet.getString(1);
            String raw_name = resultSet.getString(2);
            Double qty = Double.valueOf(resultSet.getString(3));
            Double unit_price = Double.valueOf(resultSet.getString(4));


            var dto = new RawMaterialDto(raw_id, raw_name, qty, unit_price);
            dtoList.add(dto);
        }
        return dtoList;

    }

    public boolean deleteRaw(String rawMaterial_id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "DELETE FROM raw_material WHERE  rawMaterial_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, rawMaterial_id);

        return pstm.executeUpdate() > 0;
    }

    public static List<RawMaterialDto> loadAllMaterials() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM raw_material";
        ResultSet resultSet = connection.prepareStatement(sql).executeQuery();

        List<RawMaterialDto> rawList = new ArrayList<>();

        while (resultSet.next()) {
            rawList.add(new RawMaterialDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getDouble(3),
                    resultSet.getDouble(4)


            ));
        }
        return rawList;
    }

    public static boolean updateRawMaterial(List<MaterialCartTm> tmList) throws SQLException {
        for (MaterialCartTm materialCartTm : tmList) {
            if(!updateQty(materialCartTm)) {
                return false;
            }
        }
        return true;
    }



    private static boolean updateQty(MaterialCartTm materialCartTm) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "UPDATE raw_material SET qty_on_stock = qty_on_stock + ? WHERE rawMaterial_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setInt(1, materialCartTm.getQty());
        pstm.setString(2, materialCartTm.getRawMaterialId());

        return pstm.executeUpdate() > 0; //true
    }

}


