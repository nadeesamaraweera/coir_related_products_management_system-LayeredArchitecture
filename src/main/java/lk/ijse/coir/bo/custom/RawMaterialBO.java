package lk.ijse.coir.bo.custom;

import lk.ijse.coir.bo.SuperBO;
import lk.ijse.coir.dto.CustomerDto;
import lk.ijse.coir.dto.RawMaterialDto;
import lk.ijse.coir.entity.RawMaterial;

import java.sql.SQLException;
import java.util.List;

public interface RawMaterialBO extends SuperBO {
     List<RawMaterialDto> getAllMaterials() throws SQLException, ClassNotFoundException;
     boolean saveRawMaterial(RawMaterialDto rawMaterialDto) throws SQLException, ClassNotFoundException;
     boolean updateRawMaterial(RawMaterialDto rawMaterialDto) throws SQLException, ClassNotFoundException;
     boolean existRawMaterial(String id) throws SQLException, ClassNotFoundException;
     void deleteRawMaterial(String id) throws SQLException, ClassNotFoundException;
     RawMaterialDto searchRawMaterial(String id) throws SQLException, ClassNotFoundException;
     String generateNewID() throws SQLException, ClassNotFoundException;

}
