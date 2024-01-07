package lk.ijse.coir.bo.custom;

import lk.ijse.coir.bo.SuperBO;
import lk.ijse.coir.dto.CustomerDto;
import lk.ijse.coir.dto.DeliveryDto;
import lk.ijse.coir.entity.Delivery;

import java.sql.SQLException;
import java.util.ArrayList;

public interface DeliveryBO extends SuperBO {
    ArrayList<DeliveryDto> getAllDeliverys() throws SQLException, ClassNotFoundException;

    boolean saveDelivery(DeliveryDto deliveryDto) throws SQLException, ClassNotFoundException;

    boolean existDelivery(String id) throws SQLException, ClassNotFoundException;

    void deleteDelivery(String id) throws SQLException, ClassNotFoundException;

    //public String generateId() throws SQLException, ClassNotFoundException;
    DeliveryDto searchDelivery(String id) throws SQLException, ClassNotFoundException;

    boolean updateDelivery(DeliveryDto deliveryDto) throws SQLException, ClassNotFoundException;
}
