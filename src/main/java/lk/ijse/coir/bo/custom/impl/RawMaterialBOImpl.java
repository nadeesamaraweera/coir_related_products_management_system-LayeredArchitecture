package lk.ijse.coir.bo.custom.impl;

import lk.ijse.coir.bo.custom.RawMaterialBO;
import lk.ijse.coir.dao.DAOFactory;
import lk.ijse.coir.dao.custom.RawMaterialDAO;
import lk.ijse.coir.dto.CustomerDto;
import lk.ijse.coir.dto.RawMaterialDto;
import lk.ijse.coir.entity.Customer;
import lk.ijse.coir.entity.RawMaterial;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RawMaterialBOImpl implements RawMaterialBO {


    RawMaterialDAO rawMaterialDAO =
            (RawMaterialDAO) DAOFactory.getDaoFactory().
                    getDAO(DAOFactory.DAOTypes.RAWMATERIAL);

    @Override
    public List<RawMaterialDto> getAllMaterials() throws SQLException, ClassNotFoundException {
        List<RawMaterial> rawMaterials= rawMaterialDAO.getAll();
        List<RawMaterialDto> rawMaterialDtos =new ArrayList<>();
        for(RawMaterial rawMaterial : rawMaterials){
            RawMaterialDto rawMaterialDto =new RawMaterialDto();
            rawMaterialDto.setRawMaterialId(rawMaterial.getRawMaterialId());
            rawMaterialDto.setMaterialName(rawMaterial.getMaterialName());
            rawMaterialDto.setQtyOnStock(rawMaterial.getQtyOnStock());
            rawMaterialDto.setUnitPrice(rawMaterial.getUnitPrice());
            rawMaterialDtos.add(rawMaterialDto);
        }
        return rawMaterialDtos;
    }

    @Override
    public boolean saveRawMaterial(RawMaterialDto rawMaterialDto) throws SQLException, ClassNotFoundException {
        return rawMaterialDAO.save(new RawMaterial(rawMaterialDto.getRawMaterialId(), rawMaterialDto.getMaterialName(),rawMaterialDto.getQtyOnStock(), rawMaterialDto.getUnitPrice()));

    }



    @Override
    public boolean updateRawMaterial(RawMaterialDto rawMaterialDto) throws SQLException, ClassNotFoundException {
        return rawMaterialDAO.update(new RawMaterial(rawMaterialDto.getRawMaterialId(),rawMaterialDto.getMaterialName(),rawMaterialDto.getQtyOnStock(),rawMaterialDto.getUnitPrice()));

    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        return rawMaterialDAO.generateNewID();
    }



    @Override
    public boolean existRawMaterial(String id) throws SQLException, ClassNotFoundException {
        return rawMaterialDAO.exist(id);
    }

    @Override
    public void deleteRawMaterial(String id) throws SQLException, ClassNotFoundException {
        rawMaterialDAO.delete(id);

    }
    @Override
    public RawMaterialDto searchRawMaterial(String id) throws SQLException, ClassNotFoundException {
        RawMaterial rawMaterial = rawMaterialDAO.search(id);
        if (rawMaterial != null) {
            return new RawMaterialDto(rawMaterial.getRawMaterialId(), rawMaterial.getMaterialName(),rawMaterial.getQtyOnStock(), rawMaterial.getUnitPrice());

        } else {
            return null;
        }

    }
}
