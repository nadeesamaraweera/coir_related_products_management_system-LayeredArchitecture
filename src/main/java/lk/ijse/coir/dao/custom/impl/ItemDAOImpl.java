package lk.ijse.coir.dao.custom.impl;

import lk.ijse.coir.dao.SQLUtil;
import lk.ijse.coir.dao.custom.ItemDAO;
import lk.ijse.coir.entity.Item;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ItemDAOImpl implements ItemDAO {

    @Override
    public ArrayList<Item> getAll() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute("SELECT * FROM item");
        ArrayList<Item> items = new ArrayList<>();

        while (resultSet.next()) {
            Item item = new Item(
                    resultSet.getString("item_id"),
                    resultSet.getString("item_name"),
                    resultSet.getBigDecimal("unit_price"),
                    resultSet.getInt("qty_on_hand"),
                    resultSet.getString("rawMaterial_id")

            );

            items.add(item);

        }
        return items;

    }


    @Override
    public boolean save(Item item) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("INSERT INTO item VALUES(?, ?, ? ,?, ?)",
                item.getItemId(),
                item.getItemName(),
                item.getUnitPrice(),
                item.getQtyOnHand(),
                item.getRawMaterialId()

        );

    }


    @Override
    public boolean update(Item item) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("UPDATE item SET item_name = ?, unit_price = ?, qty_on_hand = ?, rawMaterial_id = ? WHERE item_id = ?",
                item.getItemName(),
                item.getUnitPrice(),
                item.getQtyOnHand(),
                item.getRawMaterialId(),
                item.getItemId()


                );
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("DELETE FROM item WHERE item_id = ?", id);
    }


    @Override
    public boolean exist(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst=SQLUtil.execute("SELECT * FROM item WHERE item_id=?",id);
        return rst.next();

    }

    @Override
    public Item search(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM item WHERE item_id=?", id);
        while (rst.next()){
            Item item=new Item(rst.getString(1),rst.getString(2),rst.getBigDecimal(3),rst.getInt(4),rst.getString(5));
            return item;
        }
        return  null;
    }

    @Override
    public int totalItemTypes() throws ClassNotFoundException {
        String sql = "SELECT COUNT(*) FROM item";
        try {
            ResultSet resultSet = SQLUtil.execute(sql);
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle or log the exception as needed
        }
        return 0;


    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT item_id FROM item ORDER BY item_id DESC LIMIT 1");
        if (rst.next()) {
            String id = rst.getString("item_id");
            int newItemId = Integer.parseInt(id.replace("I00", "")) + 1;
            return String.format("I%03d", newItemId);
        } else {
            return "I001";
        }
    }
}



