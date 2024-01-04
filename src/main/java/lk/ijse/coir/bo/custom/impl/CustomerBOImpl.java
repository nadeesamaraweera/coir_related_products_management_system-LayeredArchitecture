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
    public List<Customer> getAllCustomers() throws SQLException, ClassNotFoundException {
        return customerDAO.getAll();
    }

    @Override
    public boolean updateCustomer(CustomerDto customerDto) throws SQLException, ClassNotFoundException {
        return customerDAO.update(new Customer(customerDto.getCustomerId(),customerDto.getCustomerName(),customerDto.getAddress(),customerDto.getTel()));

    }

    @Override
    public boolean saveCustomer(CustomerDto customerDto) throws SQLException, ClassNotFoundException {
        return customerDAO.save(new Customer(customerDto.getCustomerId(),customerDto.getCustomerName(),customerDto.getAddress(),customerDto.getTel()));

    }

    @Override
    public boolean deleteCustomer(CustomerDto customerDto) throws SQLException, ClassNotFoundException {
        return customerDAO.delete(new Customer(customerDto.getCustomerId(),customerDto.getCustomerName(),customerDto.getAddress(),customerDto.getTel()));

    }

    @Override
    public CustomerDto searchCustomer(String customerDto) throws SQLException, ClassNotFoundException {
        return customerDAO.search(new Customer())
    }

    public boolean searchCustomer(CustomerDto customerDto) throws SQLException, ClassNotFoundException {
        return customerDAO.search(new Customer(customerDto.getCustomerId(),customerDto.getCustomerName(),customerDto.getAddress(),customerDto.getTel()));

    }



}
