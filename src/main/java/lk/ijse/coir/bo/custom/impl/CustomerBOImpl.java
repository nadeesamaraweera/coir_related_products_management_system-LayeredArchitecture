package lk.ijse.coir.bo.custom.impl;

import lk.ijse.coir.bo.custom.CustomerBO;
import lk.ijse.coir.dao.DAOFactory;
import lk.ijse.coir.dao.custom.CustomerDAO;
import lk.ijse.coir.dto.CustomerDto;
import lk.ijse.coir.entity.Customer;

import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerBOImpl implements CustomerBO {


    CustomerDAO customerDAO =
            (CustomerDAO) DAOFactory.getDaoFactory().
                    getDAO(DAOFactory.DAOTypes.CUSTOMER);


    @Override
    public ArrayList<CustomerDto> getAllCustomers() throws SQLException, ClassNotFoundException {
        ArrayList<Customer> customers = customerDAO.getAll();
        ArrayList<CustomerDto> customerDTOS = new ArrayList<>();
        for (Customer customer : customers) {
            customerDTOS.add(new CustomerDto(customer.getCustomerId(), customer.getCustomerName(), customer.getAddress(), customer.getTel()));
        }
        return customerDTOS;
    }

    @Override
    public boolean updateCustomer(CustomerDto customerDto) throws SQLException, ClassNotFoundException {
        return customerDAO.update(new Customer(customerDto.getCustomerId(), customerDto.getCustomerName(), customerDto.getAddress(), customerDto.getTel()));

    }

    @Override
    public boolean saveCustomer(CustomerDto customerDto) throws SQLException, ClassNotFoundException {
        return customerDAO.save(new Customer(customerDto.getCustomerId(), customerDto.getCustomerName(), customerDto.getAddress(), customerDto.getTel()));
    }


    @Override
    public boolean existCustomer(String id) throws SQLException, ClassNotFoundException {
        return customerDAO.exist(id);
    }

    @Override
    public void deleteCustomer(String id) throws SQLException, ClassNotFoundException {
        customerDAO.delete(id);

    }

    @Override
    public CustomerDto searchCustomer(String id) throws SQLException, ClassNotFoundException {

        Customer customer = customerDAO.search(id);
        if (customer != null) {
            return new CustomerDto(customer.getCustomerId(), customer.getCustomerName(), customer.getAddress(), customer.getTel());

        } else {
            return null;
        }

    }

  /*  public String generateId() throws SQLException, ClassNotFoundException {
        String lastNumber = customerDAO.get ();
        if (lastNumber != null) {
            int newLastnb = Integer.parseInt(lastNumber.substring(2)) + 1;
            return String.format("R-%05d", newLastnb);
        } else {
            return "C001";
        }
    }*/
}
