package lk.ijse.coir.bo.custom.impl;

import lk.ijse.coir.bo.custom.CustomerBO;
import lk.ijse.coir.dao.custom.CustomerDAO;
import lk.ijse.coir.dao.custom.impl.CustomerDAOImpl;
import lk.ijse.coir.dto.CustomerDto;
import lk.ijse.coir.entity.Customer;

import java.sql.SQLException;
import java.util.List;

public class CustomerBOImpl implements CustomerBO {

    CustomerDAO customerDAO = new CustomerDAOImpl();

    @Override
    public boolean update(CustomerDto customerDto) throws SQLException, ClassNotFoundException {
        return customerDAO.update(new Customer(customerDto.getCustomerId(),customerDto.getCustomerName(),customerDto.getAddress(),customerDto.getTel()));

    }

    @Override
    public List<Customer> getAllCustomers() throws SQLException, ClassNotFoundException {
        return customerDAO.getAll();
    }

}
