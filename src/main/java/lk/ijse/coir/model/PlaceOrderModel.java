package lk.ijse.coir.model;


import lk.ijse.coir.db.DbConnection;
import lk.ijse.coir.dto.PlaceOrderDto;

import java.sql.Connection;
import java.sql.SQLException;

    public class PlaceOrderModel {
        private final OrderModel orderModel = new OrderModel();
        // private final ItemModel itemModel = new ItemModel();
        private final OrderDetailModel orderDetailModel = new OrderDetailModel();

        public boolean placeOrder(PlaceOrderDto pDto) throws SQLException {
            boolean result = false;
            Connection connection = null;
            try {
                connection = DbConnection.getInstance().getConnection();
                connection.setAutoCommit(false);

                boolean isOrderSaved = OrderModel.saveOrder(pDto.getOrderId(), pDto.getCustomerId(), pDto.getDate());
                if (isOrderSaved) {
                     boolean isUpdated = ItemModel.updateItem(pDto.getTmList());
                      if(isUpdated) {
                    boolean isOrderDetailSaved = orderDetailModel.saveOrderDetail(pDto.getOrderId(), pDto.getTmList());
                    if(isOrderDetailSaved) {
                        connection.commit();
                        result = true;
                    }
                }
                }
            } catch (SQLException e) {
                connection.rollback();
                throw e;
            } finally {
                if (connection != null) {
                    connection.setAutoCommit(true);
                    // connection.close();
                }
            }
            return result;
        }
    }

