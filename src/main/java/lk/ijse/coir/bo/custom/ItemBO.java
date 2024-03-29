package lk.ijse.coir.bo.custom;

import lk.ijse.coir.bo.SuperBO;
import lk.ijse.coir.dto.ItemDto;
import lk.ijse.coir.dto.RawMaterialDto;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface ItemBO  extends SuperBO {

     ArrayList<ItemDto> getAllItems() throws SQLException, ClassNotFoundException;

     boolean saveItem(ItemDto itemDto) throws SQLException, ClassNotFoundException;

     boolean updateItem(ItemDto itemDto) throws SQLException, ClassNotFoundException;

     boolean existItem(String id) throws SQLException, ClassNotFoundException;

     String generateNewID() throws SQLException, ClassNotFoundException;

     ItemDto searchItem(String id) throws SQLException, ClassNotFoundException;

     int totalItemTypes() throws ClassNotFoundException, SQLException;
}
