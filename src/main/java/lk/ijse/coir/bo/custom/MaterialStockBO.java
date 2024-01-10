package lk.ijse.coir.bo.custom;

import lk.ijse.coir.bo.SuperBO;
import lk.ijse.coir.dto.*;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public interface MaterialStockBO extends SuperBO {



    boolean getOrder(LocalDate stockDate, String supplierId, List<SupplierDetailDto> supplierDetail) throws SQLException, ClassNotFoundException;


    SupplierDto searchSupplier(String id) throws SQLException, ClassNotFoundException;

    RawMaterialDto searchRaw(String id) throws SQLException, ClassNotFoundException;

    boolean existRaw(String id) throws SQLException, ClassNotFoundException;

    boolean existSupplier(String id) throws SQLException, ClassNotFoundException;

    ArrayList<SupplierDto> getAllSupplier() throws SQLException, ClassNotFoundException;

    ArrayList<RawMaterialDto> getAllRaw() throws SQLException, ClassNotFoundException;

}
