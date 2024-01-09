package lk.ijse.coir.bo.custom;

import lk.ijse.coir.bo.SuperBO;
import lk.ijse.coir.dto.CustomerDto;
import lk.ijse.coir.dto.ItemDto;
import lk.ijse.coir.dto.OrderDetailDto;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


   // boolean placeOrder(String orderId, LocalDate orderDate, String customerId, List<PlaceOrderDto> placeOrderDto) throws SQLException, ClassNotFoundException ;
//    CustomerDto searchCustomer(String id) throws SQLException, ClassNotFoundException;
//    ItemDto searchItem(String code) throws SQLException, ClassNotFoundException;
//
//    boolean existItem(String code)throws SQLException, ClassNotFoundException;
//
//    boolean existCustomer(String id)throws SQLException, ClassNotFoundException;
//
//    String generateOrderID()throws SQLException, ClassNotFoundException;
//
//    ArrayList<CustomerDto> getAllCustomer()throws SQLException, ClassNotFoundException;
//
//    ArrayList<ItemDto> getAllItems()throws SQLException, ClassNotFoundException;

    public interface PlaceOrderBO extends SuperBO {
        boolean placeOrder(String orderId, LocalDate orderDate, String customerId, List<OrderDetailDto> orderDetails) throws SQLException, ClassNotFoundException ;
        CustomerDto searchCustomer(String id) throws SQLException, ClassNotFoundException;
        ItemDto searchItem(String code) throws SQLException, ClassNotFoundException;

        boolean existItem(String code)throws SQLException, ClassNotFoundException;

        boolean existCustomer(String id)throws SQLException, ClassNotFoundException;

        String generateOrderID()throws SQLException, ClassNotFoundException;

        ArrayList<CustomerDto> getAllCustomer()throws SQLException, ClassNotFoundException;

        ArrayList<ItemDto> getAllItems()throws SQLException, ClassNotFoundException;

}
