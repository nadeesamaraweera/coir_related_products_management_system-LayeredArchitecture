package lk.ijse.coir.model;


import lk.ijse.coir.db.DbConnection;
import lk.ijse.coir.dto.tm.MaterialCartTm;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class SupplierDetailModel {
    public boolean saveSupplierDetail(String supplierId, String rawMaterialId, LocalDate date, List<MaterialCartTm> tmList) throws SQLException {
        for (MaterialCartTm materialCartTm: tmList) {
            if(!saveMaterialDetail(supplierId,rawMaterialId,date,materialCartTm)) {
                return false;
            }
        }
        return true;
    }

    private boolean saveMaterialDetail(String supplierId, String rawMaterialId, LocalDate date, MaterialCartTm supplierDetailDto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO supplier_detail VALUES(?, ?, ? ,? ,?)";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1,supplierId);
        pstm.setString(2, rawMaterialId);
        pstm.setDate(3, Date.valueOf(date));
        pstm.setDouble(4, supplierDetailDto.getUnitPrice());
        pstm.setInt(5,supplierDetailDto.getQty());

        return pstm.executeUpdate() > 0;
    }
}
