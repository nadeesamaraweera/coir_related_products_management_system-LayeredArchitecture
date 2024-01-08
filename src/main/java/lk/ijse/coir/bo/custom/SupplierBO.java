package lk.ijse.coir.bo.custom;

import lk.ijse.coir.bo.SuperBO;
import lk.ijse.coir.dto.CustomerDto;
import lk.ijse.coir.dto.SupplierDto;

import java.sql.SQLException;
import java.util.ArrayList;

public interface SupplierBO extends SuperBO {

    ArrayList<SupplierDto> getAllSuppliers() throws SQLException, ClassNotFoundException;

    boolean updateSupplier(SupplierDto supplierDto) throws SQLException, ClassNotFoundException;

    boolean saveSupplier(SupplierDto supplierDto) throws SQLException, ClassNotFoundException;

    boolean existSupplier(String id) throws SQLException, ClassNotFoundException;

    void deleteSupplier(String id) throws SQLException, ClassNotFoundException;

    SupplierDto searchSupplier(String id) throws SQLException, ClassNotFoundException;
}
