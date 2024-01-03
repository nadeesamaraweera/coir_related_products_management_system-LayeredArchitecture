package lk.ijse.coir.dao.custom.impl;

import lk.ijse.coir.dao.SQLUtil;
import lk.ijse.coir.dao.custom.CustomerDAO;
import lk.ijse.coir.dto.CustomerDto;
import lk.ijse.coir.entity.Customer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAOImpl implements CustomerDAO {
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
}
