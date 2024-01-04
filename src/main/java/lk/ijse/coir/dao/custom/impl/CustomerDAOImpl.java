package lk.ijse.coir.dao.custom.impl;

import lk.ijse.coir.dao.SQLUtil;
import lk.ijse.coir.dao.custom.CustomerDAO;
import lk.ijse.coir.db.DbConnection;
import lk.ijse.coir.dto.CustomerDto;
import lk.ijse.coir.entity.Customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAOImpl implements CustomerDAO {

    public List<Customer> getAll() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute("SELECT * FROM customer");
        List<Customer> customers = new ArrayList<>();

        while (resultSet.next()){
            Customer customer1 = new Customer();
            customer1.setCustomerId(resultSet.getString("customer_id"));
            customer1.setCustomerName(resultSet.getString("customer_name"));
            customer1.setAddress(resultSet.getString("address"));
            customer1.setTel(resultSet.getString("tel"));

            customers.add(customer1);

        }
        return customers;

    }
    public boolean save(Customer customer) throws SQLException, ClassNotFoundException {

       /* Connection connection = DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO customer VALUES(?, ?, ?, ?)";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, dto.getCustomerId());
        pstm.setString(2, dto.getCustomerName());
        pstm.setString(3, dto.getAddress());
        pstm.setString(4, dto.getTel());

        boolean isSaved = pstm.executeUpdate() > 0;

        return isSaved;*/

        return SQLUtil.execute("INSERT INTO customer VALUES(?, ?, ?, ?)",
                customer.getCustomerId(),
                customer.getCustomerName(),
                customer.getAddress(),
                customer.getTel()

        );

    }


    @Override
    public boolean update(Customer customer) throws SQLException, ClassNotFoundException {
       /* Connection connection = DbConnection.getInstance().getConnection();

        String sql = "UPDATE customer SET customer_name = ?, address = ?, tel = ? WHERE customer_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, dto.getCustomerName());
        pstm.setString(2, dto.getAddress());
        pstm.setString(3, dto.getTel());
        pstm.setString(4, dto.getCustomerId());

        return pstm.executeUpdate() > 0;*/

        return SQLUtil.execute("UPDATE customer SET customer_name = ?, address = ?, tel = ? WHERE customer_id = ?",
                customer.getCustomerName(),
                customer.getAddress(),
                customer.getTel(),
                customer.getCustomerId()
        );
    }

    public boolean delete(Customer customer) throws SQLException, ClassNotFoundException {
      /*  Connection connection = DbConnection.getInstance().getConnection();

        String sql = "DELETE FROM customer WHERE customer_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, customerId);

        return pstm.executeUpdate() > 0;*/
        return SQLUtil.execute("DELETE FROM customer WHERE customer_id = ?",
                customer.getCustomerId());

    }

    public boolean search(Customer customer) throws SQLException, ClassNotFoundException {
       /* Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM customer WHERE customer_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, customerId);

        ResultSet resultSet = pstm.executeQuery();

        CustomerDto dto = null;

        if (resultSet.next()) {
            String cus_id = resultSet.getString(1);
            String cus_name = resultSet.getString(2);
            String cus_address = resultSet.getString(3);
            String cus_tel = resultSet.getString(4);

            dto = new CustomerDto(cus_id, cus_name, cus_address, cus_tel);
        }

        return dto;*/
        return SQLUtil.execute("SELECT * FROM customer WHERE customer_id = ?",
                customer.getCustomerId()
        );
    }


}
