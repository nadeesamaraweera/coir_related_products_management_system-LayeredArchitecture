package lk.ijse.coir.model;


import lk.ijse.coir.db.DbConnection;
import lk.ijse.coir.dto.ItemDto;
import lk.ijse.coir.dto.tm.CartTm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemModel {
    public boolean saveItem(ItemDto itemDto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "INSERT INTO item VALUES(?, ?, ?, ?, ?)";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, itemDto.getItemId());
        pstm.setString(2, itemDto.getItemName());
        pstm.setDouble(3, itemDto.getUnitPrice());
        pstm.setInt(4, itemDto.getQtyOnHand());
        pstm.setString(5, itemDto.getRawMaterialId());

        return pstm.executeUpdate() > 0;
    }

    public boolean updateItem(ItemDto itemtDto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "UPDATE item SET item_name = ?, unit_price = ?, qty_on_hand = ?, rawMaterial_id = ? WHERE item_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, itemtDto.getItemName());
        pstm.setDouble(2, itemtDto.getUnitPrice());
        pstm.setInt(3, itemtDto.getQtyOnHand());
        pstm.setString(4, itemtDto.getRawMaterialId());
        pstm.setString(5, itemtDto.getItemId());

        return pstm.executeUpdate() > 0;
    }

    public static boolean updateItem(List<CartTm> tmList) throws SQLException {
        for (CartTm cartTm : tmList) {
            if (!updateQty(cartTm)) {
                return false;
            }
        }
        return true;
    }


    private static boolean updateQty(CartTm cartTm) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "UPDATE item SET qty_on_hand = qty_on_hand - ? WHERE item_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setInt(1, cartTm.getQty());
        pstm.setString(2, cartTm.getItemId());

        return pstm.executeUpdate() > 0; //true
    }

    public ItemDto searchItem(String ItemId) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "SELECT * FROM item WHERE item_id = ?";

        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, ItemId);

        ResultSet resultSet = pstm.executeQuery();

        ItemDto dto = null;

        if (resultSet.next()) {
            dto = new ItemDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getDouble(3),
                    resultSet.getInt(4),
                    resultSet.getString(5)
            );
        }
        return dto;
    }

    public boolean deleteItem(String ItemId) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "DELETE FROM item WHERE item_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, ItemId);

        return pstm.executeUpdate() > 0;
    }

    public List<ItemDto> loadAllItems() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM item";
        PreparedStatement pstm = connection.prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        List<ItemDto> dtoList = new ArrayList<>();

        while (resultSet.next()) {
            var dto = new ItemDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getDouble(3),
                    resultSet.getInt(4),
                    resultSet.getString(5)
            );

            dtoList.add(dto);
        }

        return dtoList;
    }

    public static String totalItemTypes() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT COUNT(*) AS ItemTypes FROM item";
        ResultSet resultSet = connection.prepareStatement(sql).executeQuery();


        if (resultSet.next()) {
            return resultSet.getString(1);
        }
        return null;
    }
}