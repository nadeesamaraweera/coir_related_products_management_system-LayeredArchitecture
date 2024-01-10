package lk.ijse.coir.dao.custom.impl;

import lk.ijse.coir.dao.SQLUtil;
import lk.ijse.coir.dao.custom.DeliveryDAO;
import lk.ijse.coir.entity.Delivery;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DeliveryDAOImpl implements DeliveryDAO {


    @Override
    public ArrayList<Delivery> getAll() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute("SELECT * FROM delivery");
        ArrayList<Delivery> deliveries= new ArrayList<>();

        while (resultSet.next()) {
            Delivery delivery = new Delivery(
                    resultSet.getString("delivery_id"),
                    resultSet.getString("order_id"),
                    resultSet.getString("employee_id"),
                    resultSet.getString("location"),
                    resultSet.getString("delivery_status"),
                    resultSet.getString("email"));


            deliveries.add(delivery);

        }
        return deliveries;

    }


    @Override
    public boolean save(Delivery delivery) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("INSERT INTO delivery VALUES(?, ?, ?, ?,?,?)",
                delivery.getDeliveryId(),
                delivery.getOrderId(),
                delivery.getEmployeeId(),
                delivery.getLocation(),
                delivery.getDeliveryStatus(),
                delivery.getEmail()

        );

    }


    @Override
    public boolean update(Delivery delivery) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("UPDATE delivery SET order_id = ?, employee_id = ?, location = ? , delivery_status = ? ,email =? WHERE delivery_id = ?",
                delivery.getOrderId(),
                delivery.getEmployeeId(),
                delivery.getLocation(),
                delivery.getDeliveryStatus(),
                delivery.getEmail(),
                delivery.getDeliveryId()
        );
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("DELETE FROM delivery WHERE delivery_id = ?", id);
    }


    @Override
    public boolean exist(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst=SQLUtil.execute("SELECT * FROM delivery WHERE delivery_id=?",id);
        return rst.next();

    }

    @Override
    public Delivery search(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM delivery WHERE delivery_id=?", id);
        while (rst.next()){
            Delivery delivery =new Delivery(rst.getString(1),rst.getString(2),rst.getString(3),rst.getString(4),rst.getString(5),rst.getString(6));
            return delivery;
        }
        return  null;
    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT delivery_id FROM delivery ORDER BY delivery_id DESC LIMIT 1");
        if (rst.next()) {
            String id = rst.getString("delivery_id");
            int newItemId = Integer.parseInt(id.replace("D00", "")) + 1;
            return String.format("D%03d", newItemId);
        } else {
            return "D001";
        }
    }

}
