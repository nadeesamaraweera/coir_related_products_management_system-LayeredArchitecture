package lk.ijse.coir.bo.custom.impl;

import lk.ijse.coir.bo.custom.DeliveryBO;
import lk.ijse.coir.dao.DAOFactory;
import lk.ijse.coir.dao.custom.DeliveryDAO;
import lk.ijse.coir.dto.DeliveryDto;
import lk.ijse.coir.entity.Delivery;

import java.sql.SQLException;
import java.util.ArrayList;

public class DeliveryBOImpl implements  DeliveryBO {
    DeliveryDAO deliveryDAO =
            (DeliveryDAO) DAOFactory.getDaoFactory().
                    getDAO(DAOFactory.DAOTypes.DELIVERY);


    @Override
    public ArrayList<DeliveryDto> getAllDeliverys() throws SQLException, ClassNotFoundException {
        ArrayList<Delivery> deliveries = deliveryDAO.getAll();
        ArrayList<DeliveryDto> deliveryDtos = new ArrayList<>();
        for (Delivery delivery : deliveries) {
            deliveryDtos.add(new DeliveryDto(delivery.getDeliveryId(), delivery.getOrderId(), delivery.getEmployeeId(), delivery.getDeliveryStatus(),delivery.getLocation(),delivery.getEmail()));
        }
        return deliveryDtos;
    }

    @Override
    public boolean updateDelivery(DeliveryDto deliveryDto) throws SQLException, ClassNotFoundException {
        return deliveryDAO.update(new Delivery(deliveryDto.getDeliveryId(), deliveryDto.getOrderId(), deliveryDto.getEmployeeId(), deliveryDto.getLocation(),deliveryDto.getDeliveryStatus(),deliveryDto.getEmail()));

    }

    @Override
    public boolean saveDelivery(DeliveryDto deliveryDto) throws SQLException, ClassNotFoundException {
        return deliveryDAO.save(new Delivery(deliveryDto.getDeliveryId(),deliveryDto.getOrderId(), deliveryDto.getEmployeeId(),deliveryDto.getLocation(),deliveryDto.getDeliveryStatus(),deliveryDto.getEmail()));
    }


    @Override
    public boolean existDelivery(String id) throws SQLException, ClassNotFoundException {
        return deliveryDAO.exist(id);
    }

    @Override
    public void deleteDelivery(String id) throws SQLException, ClassNotFoundException {
        deliveryDAO.delete(id);

    }

    @Override
    public DeliveryDto searchDelivery(String id) throws SQLException, ClassNotFoundException {

        Delivery delivery= deliveryDAO.search(id);
        if (delivery != null) {
            return new DeliveryDto(delivery.getDeliveryId(),delivery.getOrderId(), delivery.getEmployeeId(),delivery.getDeliveryStatus(),delivery.getLocation(),delivery.getEmail());

        } else {
            return null;
        }

    }

}
