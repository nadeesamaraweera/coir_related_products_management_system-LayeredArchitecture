/*package lk.ijse.coir.bo.custom.impl;

import lk.ijse.coir.bo.custom.PlaceOrderBO;
import lk.ijse.coir.dao.DAOFactory;
import lk.ijse.coir.dao.custom.*;
import lk.ijse.coir.db.DbConnection;
import lk.ijse.coir.dto.PlaceOrderDto;
import lk.ijse.coir.model.ItemModel;
import lk.ijse.coir.model.OrderModel;
import lk.ijse.coir.bo.custom.PlaceOrderBO;
import lk.ijse.coir.dao.DAOFactory;
import lk.ijse.coir.dao.custom.*;
import lk.ijse.coir.db.DbConnection;
import lk.ijse.coir.dto.CustomerDto;
import lk.ijse.coir.dto.ItemDto;
import lk.ijse.coir.dto.OrderDetailDto;
import lk.ijse.coir.entity.Customer;
import lk.ijse.coir.entity.Item;
import lk.ijse.coir.entity.Order;
import lk.ijse.coir.entity.OrderDetail;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PlaceOrderBOImpl implements PlaceOrderBO {
    OrderDAO orderDAO = (OrderDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ORDER);
    CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.CUSTOMER);
    ItemDAO itemDAO = (ItemDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ITEM);
    OrderDetailsDAO orderDetailsDAO = (OrderDetailsDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ORDER_DETAIL);
    QuaryDAO queryDAO= (QuaryDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.QUERY);

    public boolean placeOrder(String orderId, LocalDate orderDate, String customerId, List<PlaceOrderDto> PDto) throws SQLException, ClassNotFoundException {
        boolean result = false;
        Connection connection = null;
        try {
            connection = DbConnection.getInstance().getConnection();
            connection.setAutoCommit(false);

            boolean isOrderSaved = OrderModel.saveOrder(pDto.getOrderId(), pDto.getCustomerId(), pDto.getDate());
            if (isOrderSaved) {
                boolean isUpdated = ItemModel.updateItem(pDto.getTmList());
                if(isUpdated) {
                    boolean isOrderDetailSaved = orderDetailModel.saveOrderDetail(pDto.getOrderId(), pDto.getTmList());
                    if(isOrderDetailSaved) {
                        connection.commit();
                        result = true;
                    }
                }
            }
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        } finally {
            if (connection != null) {
                connection.setAutoCommit(true);
                // connection.close();
            }
        }
    *  return result;
    }*/


//..
package lk.ijse.coir.bo.custom.impl;

import lk.ijse.coir.bo.custom.PlaceOrderBO;
import lk.ijse.coir.dao.DAOFactory;
import lk.ijse.coir.dao.custom.*;
import lk.ijse.coir.db.DbConnection;
import lk.ijse.coir.dto.CustomerDto;
import lk.ijse.coir.dto.ItemDto;
import lk.ijse.coir.dto.OrderDetailDto;
import lk.ijse.coir.entity.Customer;
import lk.ijse.coir.entity.Item;
import lk.ijse.coir.entity.Order;
import lk.ijse.coir.entity.OrderDetail;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PlaceOrderBOImpl implements PlaceOrderBO {
    OrderDAO orderDAO = (OrderDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ORDER);
    CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.CUSTOMER);
    ItemDAO itemDAO = (ItemDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ITEM);
    OrderDetailsDAO orderDetailsDAO = (OrderDetailsDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ORDER_DETAIL);
    QuaryDAO queryDAO= (QuaryDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.QUERY);


    @Override
    public boolean placeOrder(String orderId, LocalDate orderDate, String customerId, List<OrderDetailDto> orderDetails) throws SQLException, ClassNotFoundException {
        /*Transaction*/
        Connection connection = null;
        connection= DbConnection.getInstance().getConnection();

        //Check order id already exist or not

        boolean b1 = orderDAO.exist(orderId);
        /*if order id already exist*/
        if (b1) {
            return false;
        }

        connection.setAutoCommit(false);

        //Save the Order to the order table
        boolean b2 = orderDAO.save(new Order(orderId,orderDate,customerId));

        if (!b2) {
            connection.rollback();
            connection.setAutoCommit(true);
            return false;
        }


        // add data to the Order Details table

        for (OrderDetailDto detail : orderDetails) {
            boolean b3 = orderDetailsDAO.save(new OrderDetail(detail.getOrderId(),detail.getItemId(),detail.getQty(),detail.getUnitPrice()));
            if (!b3) {
                connection.rollback();
                connection.setAutoCommit(true);
                return false;
            }

            //Search & Update Item
            ItemDto item = findItem(detail.getItemId());
            item.setQtyOnHand(item.getQtyOnHand() - detail.getQty());

            //update item
            boolean b = itemDAO.update(new Item(item.getItemId(), item.getItemName(), item.getUnitPrice(), item.getQtyOnHand(),item.getRawMaterialId()));

            if (!b) {
                connection.rollback();
                connection.setAutoCommit(true);
                return false;
            }
        }

        connection.commit();
        connection.setAutoCommit(true);
        return true;
    }

    @Override
    public CustomerDto searchCustomer(String id) throws SQLException, ClassNotFoundException {
        Customer customer=customerDAO.search(id);
        CustomerDto customerDTO=new CustomerDto(customer.getCustomerId(),customer.getCustomerName(),customer.getAddress(),customer.getTel());
        return customerDTO;
    }

    @Override
    public ItemDto searchItem(String code) throws SQLException, ClassNotFoundException {
        Item item = itemDAO.search(code);
        return new ItemDto(item.getItemId(),item.getItemName(),item.getUnitPrice(), item.getQtyOnHand(),item.getRawMaterialId());

    }

    @Override
    public boolean existItem(String code) throws SQLException, ClassNotFoundException {
        return itemDAO.exist(code);
    }

    @Override
    public boolean existCustomer(String id) throws SQLException, ClassNotFoundException {
        return customerDAO.exist(id);
    }

    @Override
    public String generateOrderID() throws SQLException, ClassNotFoundException {
        return orderDAO.generateID();
    }

    @Override
    public ArrayList<CustomerDto> getAllCustomer() throws SQLException, ClassNotFoundException {
        ArrayList<Customer> customers=customerDAO.getAll();
        ArrayList<CustomerDto> customerDTOS=new ArrayList<>();
        for (Customer customer:customers) {
            customerDTOS.add(new CustomerDto(customer.getCustomerId(),customer.getCustomerName(),customer.getAddress(),customer.getTel()));
        }
        return customerDTOS;
    }

    @Override
    public ArrayList<ItemDto> getAllItems() throws SQLException, ClassNotFoundException {
        ArrayList<Item> items=itemDAO.getAll();
        ArrayList<ItemDto> itemDTOS=new ArrayList<>();
        for (Item item:items) {
            itemDTOS.add(new ItemDto(item.getItemId(),item.getItemName(),item.getUnitPrice(),item.getQtyOnHand(),item.getRawMaterialId()));
        }
        return itemDTOS;
    }

    public ItemDto findItem(String code) {
        try {
            Item item = itemDAO.search(code);
            return new ItemDto(item.getItemId(),item.getItemName(),item.getUnitPrice(), item.getQtyOnHand(),item.getRawMaterialId());
        } catch (SQLException e) {
            throw new RuntimeException("Failed to find the Item " + code, e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}

