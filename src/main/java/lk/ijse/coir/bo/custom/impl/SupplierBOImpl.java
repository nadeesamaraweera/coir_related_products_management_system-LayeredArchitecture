package lk.ijse.coir.bo.custom.impl;

import lk.ijse.coir.bo.custom.SupplierBO;
import lk.ijse.coir.dao.DAOFactory;
import lk.ijse.coir.dao.custom.SupplierDAO;
import lk.ijse.coir.dto.SupplierDto;
import lk.ijse.coir.entity.Supplier;

import java.sql.SQLException;
import java.util.ArrayList;

public class SupplierBOImpl implements SupplierBO {

    SupplierDAO supplierDAO=
            (SupplierDAO) DAOFactory.getDaoFactory().
                    getDAO(DAOFactory.DAOTypes.SUPPLIER);


    @Override
    public ArrayList<SupplierDto> getAllSuppliers() throws SQLException, ClassNotFoundException {
        ArrayList<Supplier> suppliers= supplierDAO.getAll();
        ArrayList<SupplierDto> supplierDTOS = new ArrayList<>();
        for (Supplier supplier : suppliers) {
            supplierDTOS.add(new SupplierDto(supplier.getSupplierId(), supplier.getSupplierName(), supplier.getAddress(), supplier.getTel()));
        }
        return supplierDTOS;
    }

    @Override
    public boolean updateSupplier(SupplierDto supplierDto) throws SQLException, ClassNotFoundException {
        return supplierDAO.update(new Supplier(supplierDto.getSupplierId(), supplierDto.getSupplierName(), supplierDto.getAddress(), supplierDto.getTel()));

    }

    @Override
    public boolean saveSupplier(SupplierDto supplierDto) throws SQLException, ClassNotFoundException {
        return supplierDAO.save(new Supplier(supplierDto.getSupplierId(), supplierDto.getSupplierName(), supplierDto.getAddress(), supplierDto.getTel()));
    }


    @Override
    public boolean existSupplier(String id) throws SQLException, ClassNotFoundException {
        return supplierDAO.exist(id);
    }

    @Override
    public void deleteSupplier(String id) throws SQLException, ClassNotFoundException {
        supplierDAO.delete(id);

    }

    @Override
    public SupplierDto searchSupplier(String id) throws SQLException, ClassNotFoundException {

        Supplier supplier = supplierDAO.search(id);
        if (supplier != null) {
            return new SupplierDto(supplier.getSupplierId(), supplier.getSupplierName(), supplier.getAddress(), supplier.getTel());

        } else {
            return null;
        }

    }


}
