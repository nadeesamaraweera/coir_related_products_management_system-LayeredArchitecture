package lk.ijse.coir.model;

import lk.ijse.coir.db.DbConnection;
import lk.ijse.coir.dto.tm.CartTm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

    public class OrderDetailModel {
        public boolean saveOrderDetail(String orderId, List<CartTm> tmList) throws SQLException {
            for (CartTm cartTm : tmList) {
                if(!saveOrderDetail(orderId, cartTm)) {
                    return false;
                }
            }
            return true;
        }

        private boolean saveOrderDetail(String orderId, CartTm cartTm) throws SQLException {
            Connection connection = DbConnection.getInstance().getConnection();

            String sql = "INSERT INTO Order_detail VALUES(?, ?, ?, ?)";
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, orderId);
            pstm.setString(2, cartTm.getItemId());
            pstm.setInt(3, Integer.parseInt(String.valueOf( cartTm.getQty())));
            pstm.setDouble(4,Double.parseDouble(String.valueOf( cartTm.getUnitPrice())));

            return pstm.executeUpdate() > 0;
        }
    }

