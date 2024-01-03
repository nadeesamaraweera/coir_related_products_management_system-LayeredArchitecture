package lk.ijse.coir.model;

import lk.ijse.coir.db.DbConnection;

import java.sql.*;
import java.time.LocalDate;

    public class OrderModel {
        public static boolean saveOrder(String orderId, String customerId, LocalDate date) throws SQLException {
            Connection connection = DbConnection.getInstance().getConnection();

            String sql = "INSERT INTO orders VALUES(?, ?, ?)";
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, orderId);
            pstm.setDate(2, Date.valueOf(date));
            pstm.setString(3, customerId);

            return pstm.executeUpdate() > 0;
        }

        public String generateNextOrderId() throws SQLException {
            Connection connection = DbConnection.getInstance().getConnection();

            String sql = "SELECT order_id FROM orders ORDER BY order_id DESC LIMIT 1";
            ResultSet resultSet = connection.prepareStatement(sql).executeQuery();

            String currentOrderId = null;

            if (resultSet.next()) {
                currentOrderId = resultSet.getString(1);
                return splitOrderId(currentOrderId);
            }
            return splitOrderId(null);
        }

        private String splitOrderId(String currentOrderId) {    //O008
            if (currentOrderId != null) {
                String[] split = currentOrderId.split("O");
                int id = Integer.parseInt(split[1]);    //008
                    id++;
                    return "O00" + id;
                }

                return "O001";
            }

        }