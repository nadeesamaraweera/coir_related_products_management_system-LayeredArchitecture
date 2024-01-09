/*
package lk.ijse.coir.model;

import lk.ijse.coir.db.DbConnection;
import lk.ijse.coir.dto.tm.CartTm;
import lk.ijse.coir.dto.tm.OrderDetailTM;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

    public class OrderDetailModel {
        public boolean saveOrderDetail(String orderId, List<OrderDetailTM> tmList) throws SQLException {
            for (OrderDetailTM orderDetailTM : tmList) {
                if(!saveOrderDetail(orderId, orderDetailTM)) {
                    return false;
                }
            }
            return true;
        }

        private boolean saveOrderDetail(String orderId, OrderDetailTM orderDetailTM) throws SQLException {
            Connection connection = DbConnection.getInstance().getConnection();

            String sql = "INSERT INTO Order_detail VALUES(?, ?, ?, ?)";
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, orderId);
            pstm.setString(2, OrderDetailTM.());
            pstm.setInt(3, Integer.parseInt(String.valueOf( cartTm.getQty())));
            pstm.setDouble(4,Double.parseDouble(String.valueOf( cartTm.getUnitPrice())));

            return pstm.executeUpdate() > 0;
        }
    }

*/
