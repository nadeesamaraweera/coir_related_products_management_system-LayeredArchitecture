package lk.ijse.coir.dao.custom.impl;

import lk.ijse.coir.dao.SQLUtil;
import lk.ijse.coir.dao.custom.CustomerDAO;
import lk.ijse.coir.entity.Customer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerDAOImpl implements CustomerDAO {

    @Override
    public ArrayList<Customer> getAll() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute("SELECT * FROM customer");
        ArrayList<Customer> customers = new ArrayList<>();

        while (resultSet.next()) {
            Customer customer1 = new Customer(
                    resultSet.getString("customer_id"),
                    resultSet.getString("customer_name"),
                    resultSet.getString("address"),
                    resultSet.getString("tel"));

            customers.add(customer1);

        }
        return customers;

    }


    @Override
    public boolean save(Customer customer) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("INSERT INTO customer VALUES(?, ?, ?, ?)",
                customer.getCustomerId(),
                customer.getCustomerName(),
                customer.getAddress(),
                customer.getTel()

        );

    }


    @Override
    public boolean update(Customer customer) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("UPDATE customer SET customer_name = ?, address = ?, tel = ? WHERE customer_id = ?",
                customer.getCustomerName(),
                customer.getAddress(),
                customer.getTel(),
                customer.getCustomerId()
        );
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("DELETE FROM customer WHERE customer_id = ?", id);
    }


    @Override
    public boolean exist(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst=SQLUtil.execute("SELECT * FROM customer WHERE customer_id=?",id);
        return rst.next();

    }

    @Override
    public Customer search(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM customer WHERE customer_id=?", id);
        while (rst.next()){
            Customer customer =new Customer(rst.getString(1),rst.getString(2),rst.getString(3),rst.getString(4));
            return customer;
        }
        return  null;
    }

    @Override
    public int totalCustomerCount() throws ClassNotFoundException {
        String sql = "SELECT COUNT(*) FROM customer";
        try {
            ResultSet resultSet = SQLUtil.execute(sql);
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle or log the exception as needed
        }
        return 0;


    }
    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT customer_id FROM customer ORDER BY customer_id DESC LIMIT 1");
        if (rst.next()) {
            String id = rst.getString("customer_id");
            int newCustomerId = Integer.parseInt(id.replace("C00","")) + 1;
            return String.format("C%03d", newCustomerId);
        } else {
            return "C001";
        }

    }
}



