package lk.ijse.coir.bo.custom.impl;

import lk.ijse.coir.bo.custom.ItemBO;
import lk.ijse.coir.dao.DAOFactory;
import lk.ijse.coir.dao.custom.ItemDAO;
import lk.ijse.coir.dto.ItemDto;
import lk.ijse.coir.entity.Item;

import java.sql.SQLException;
import java.util.ArrayList;

public class ItemBOImpl implements ItemBO {


    ItemDAO itemDAO =
            (ItemDAO) DAOFactory.getDaoFactory().
                    getDAO(DAOFactory.DAOTypes.ITEM);

   @Override
   public ArrayList<ItemDto> getAllItems() throws SQLException, ClassNotFoundException {
       ArrayList<Item> items = itemDAO.getAll();
       ArrayList<ItemDto> itemDTOS = new ArrayList<>();
       for (Item item : items) {
           itemDTOS.add(new ItemDto(item.getItemId(),item.getItemName(),item.getUnitPrice(),item.getQtyOnHand(),item.getRawMaterialId()));
       }
       return itemDTOS;
   }

    @Override
    public boolean saveItem(ItemDto itemDto) throws SQLException, ClassNotFoundException {
        return itemDAO.save(new Item(itemDto.getItemId(),itemDto.getItemName(),itemDto.getUnitPrice(),itemDto.getQtyOnHand(),itemDto.getRawMaterialId()));

    }

    @Override
    public boolean updateItem(ItemDto itemDto) throws SQLException, ClassNotFoundException {
        return itemDAO.update(new Item(itemDto.getItemId(),itemDto.getItemName(),itemDto.getUnitPrice(),itemDto.getQtyOnHand(),itemDto.getRawMaterialId()));

    }
    @Override
    public boolean existItem(String id) throws SQLException, ClassNotFoundException {
        return itemDAO.exist(id);
    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        return itemDAO.generateNewID();
    }

    @Override
    public ItemDto searchItem(String id) throws SQLException, ClassNotFoundException {
        Item item = itemDAO.search(id);
        if (item != null) {
            return new ItemDto(item.getItemId(),item.getItemName(),item.getUnitPrice(),item.getQtyOnHand(),item.getRawMaterialId());

        } else {
            return null;
        }

    }
    @Override
    public int totalItemTypes() throws ClassNotFoundException, SQLException {
        return itemDAO.totalItemTypes();
    }
}
