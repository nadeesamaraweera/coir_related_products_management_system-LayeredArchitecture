package lk.ijse.coir.model;

import lk.ijse.coir.db.DbConnection;
import lk.ijse.coir.dto.DeliveryDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DeliveryModel {
    public boolean saveDelivery(final DeliveryDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO delivery VALUES(?, ?, ?, ?,?, ?)";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, dto.getDeliveryId());
        pstm.setString(2, dto.getOrderId());
        pstm.setString(3, dto.getEmployeeId());
        pstm.setString(4, dto.getLocation());
        pstm.setString(5, dto.getDeliveryStatus());
        pstm.setString(6, dto.getEmail());

        boolean isSaved = pstm.executeUpdate() > 0;

        return isSaved;
    }

    public boolean updateDelivery(final DeliveryDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "UPDATE delivery SET order_id = ?, employee_id = ?, location = ?, delivery_status = ?, email = ? WHERE delivery_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);


        pstm.setString(1, dto.getOrderId());
        pstm.setString(2, dto.getEmployeeId());
        pstm.setString(3, dto.getLocation());
        pstm.setString(4, dto.getDeliveryStatus());
        pstm.setString(5, dto.getEmail());
        pstm.setString(6, dto.getDeliveryId());


        return pstm.executeUpdate() > 0;
    }

    public DeliveryDto searchDelivery(String deliveryId) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM delivery WHERE delivery_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, deliveryId);

        ResultSet resultSet = pstm.executeQuery();

        DeliveryDto dto = null;

        if(resultSet.next()) {
            String del_id = resultSet.getString(1);
            String od_id = resultSet.getString(2);
            String emp_id = resultSet.getString(3);
            String location = resultSet.getString(4);
            String delivery_status = resultSet.getString(5);
            String email = resultSet.getString(6);



            dto = new DeliveryDto(del_id, od_id, emp_id, location, delivery_status, email);
        }

        return dto;
    }

    public List<DeliveryDto> getAllDelivery() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM delivery";
        PreparedStatement pstm = connection.prepareStatement(sql);

        List<DeliveryDto> dtoList = new ArrayList<>();

        ResultSet resultSet = pstm.executeQuery();

        while (resultSet.next()) {
            String del_id = resultSet.getString(1);
            String od_id = resultSet.getString(2);
            String emp_id = resultSet.getString(3);
            String location = resultSet.getString(4);
            String delivery_status = resultSet.getString(5);
            String email = resultSet.getString(6);

            var dto = new DeliveryDto(del_id, od_id, emp_id, location, delivery_status,email);
            dtoList.add(dto);
        }
        return dtoList;
    }

    public boolean deleteDelivery(String deliveryId) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "DELETE FROM delivery WHERE delivery_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, deliveryId);

        return pstm.executeUpdate() > 0;
    }

    public List<DeliveryDto> loadAllDelivery() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM delivery";
        ResultSet resultSet = connection.prepareStatement(sql).executeQuery();

        List<DeliveryDto> delList = new ArrayList<>();

        while (resultSet.next()) {
            delList.add(new DeliveryDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getString(6)
            ));
        }
        return delList;
    }




}