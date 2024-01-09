package lk.ijse.coir.dao.custom.impl;

import lk.ijse.coir.dao.SQLUtil;
import lk.ijse.coir.dao.custom.OrderDAO;
import lk.ijse.coir.entity.Order;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderDAOImpl implements OrderDAO {
    @Override
    public ArrayList<Order> getAll() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean save(Order entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("INSERT INTO `orders` (Order_id, date, customer_id) VALUES (?,?,?)",entity.getOrderId(), Date.valueOf(entity.getOrderDate()),entity.getCustomerId());
    }

    @Override
    public boolean update(Order dto) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean exist(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT Order_id FROM `orders` WHERE Order_id=?",id);
        return rst.next();
    }

    @Override
    public String generateID() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT Order_id FROM `orders` ORDER BY Order_id DESC LIMIT 1;");
        return rst.next() ? String.format("O%03d", (Integer.parseInt(rst.getString("Order_id").replace("O", "")) + 1)) : "O001";
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("DELETE Order_id FROM `orders` WHERE Order_id=?",id);
        return rst.next();
    }


    @Override
    public Order search(String id) throws SQLException, ClassNotFoundException {
        return null;
    }
}
