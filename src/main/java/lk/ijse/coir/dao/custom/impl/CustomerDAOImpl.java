package lk.ijse.coir.dao.custom.impl;

import lk.ijse.coir.dao.SQLUtil;
import lk.ijse.coir.dao.custom.CustomerDAO;
import lk.ijse.coir.entity.Customer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

  /*  @Override
    public static String to() throws SQLException, ClassNotFoundException {
      //  Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT COUNT(*) AS CustomerCount FROM customer";
        ResultSet resultSet =SQLUtil.execute( "SELECT COUNT(*) AS CustomerCount FROM customer") ;


        if (resultSet.next()) {
            return resultSet.getString(1);
        }
        return null;
    }*/
}



