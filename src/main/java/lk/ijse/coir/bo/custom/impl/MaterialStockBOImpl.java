
package lk.ijse.coir.bo.custom.impl;

import lk.ijse.coir.bo.custom.MaterialStockBO;
import lk.ijse.coir.dao.DAOFactory;
import lk.ijse.coir.dao.custom.*;
import lk.ijse.coir.db.DbConnection;
import lk.ijse.coir.dto.*;
import lk.ijse.coir.entity.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MaterialStockBOImpl implements MaterialStockBO {

    SupplierDAO supplierDAO = (SupplierDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.SUPPLIER);
    RawMaterialDAO rawMaterialDAO = (RawMaterialDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.RAWMATERIAL);
    SupplierDetailDAO supplierDetailsDAO = (SupplierDetailDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.SUPPLIER_DETAIL);
    QuaryDAO queryDAO= (QuaryDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.QUERY);


    @Override
    public boolean getOrder(LocalDate stockDate, String supplierId, List<SupplierDetailDto> supplierDetail) throws SQLException, ClassNotFoundException {

        Connection connection = null;
        connection= DbConnection.getInstance().getConnection();
        connection.setAutoCommit(false);
        for (SupplierDetailDto detail : supplierDetail) {
            boolean b3 = supplierDetailsDAO.save(new SupplierDetail(detail.getSupplierId(),detail.getRawMaterialId(),detail.getDate(),detail.getUnitPrice(),detail.getQtyOnStock()));
            if (!b3) {
                connection.rollback();
                connection.setAutoCommit(true);
                return false;
            }

            //Search & Update Item
            RawMaterialDto rawMaterial= findRaw(detail.getRawMaterialId());
            rawMaterial.setQtyOnStock(rawMaterial.getQtyOnStock() + detail.getQtyOnStock());

            //update item
            boolean b = rawMaterialDAO.update(new RawMaterial(rawMaterial.getRawMaterialId(), rawMaterial.getMaterialName(), rawMaterial.getQtyOnStock(), rawMaterial.getUnitPrice()));

            if (!b) {
                connection.rollback();
                connection.setAutoCommit(true);
                return false;
            }
        }

        connection.commit();
        connection.setAutoCommit(true);
        return true;
    }


    @Override
    public SupplierDto searchSupplier(String id) throws SQLException, ClassNotFoundException {
        Supplier supplier=supplierDAO.search(id);
        SupplierDto supplierDto=new SupplierDto(supplier.getSupplierId(),supplier.getSupplierName(),supplier.getAddress(),supplier.getTel());
        return supplierDto;
    }

    @Override
    public RawMaterialDto searchRaw(String id) throws SQLException, ClassNotFoundException {
        RawMaterial rawMaterial = rawMaterialDAO.search(id);
        return new RawMaterialDto(rawMaterial.getRawMaterialId(),rawMaterial.getMaterialName(),rawMaterial.getQtyOnStock(),rawMaterial.getUnitPrice());
    }

    @Override
    public boolean existRaw(String id) throws SQLException, ClassNotFoundException {
        return rawMaterialDAO.exist(id);
    }

    @Override
    public boolean existSupplier(String id) throws SQLException, ClassNotFoundException {
        return supplierDAO.exist(id);
    }

    @Override
    public ArrayList<SupplierDto> getAllSupplier() throws SQLException, ClassNotFoundException {
        ArrayList<Supplier> suppliers=supplierDAO.getAll();
        ArrayList<SupplierDto> supplierDTOS=new ArrayList<>();
        for (Supplier supplier:suppliers) {
            supplierDTOS.add(new SupplierDto(supplier.getSupplierId(),supplier.getSupplierName(),supplier.getAddress(),supplier.getTel()));
        }
        return supplierDTOS;
    }

    @Override
    public ArrayList<RawMaterialDto> getAllRaw() throws SQLException, ClassNotFoundException {
        ArrayList<RawMaterial> rawMaterials=rawMaterialDAO.getAll();
        ArrayList<RawMaterialDto> rawMaterialDtoS=new ArrayList<>();
        for (RawMaterial rawMaterial:rawMaterials) {
            rawMaterialDtoS.add(new RawMaterialDto(rawMaterial.getRawMaterialId(),rawMaterial.getMaterialName(),rawMaterial.getQtyOnStock(),rawMaterial.getUnitPrice()));
        }
        return rawMaterialDtoS;
    }

    public RawMaterialDto findRaw(String id) {
        try {
            RawMaterial rawMaterial= rawMaterialDAO.search(id);
            return new RawMaterialDto(rawMaterial.getRawMaterialId(),rawMaterial.getMaterialName(),rawMaterial.getQtyOnStock(),rawMaterial.getUnitPrice());
        } catch (SQLException e) {
            throw new RuntimeException("Failed to find the Item " + id, e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;


    }}
