package lk.ijse.coir.controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import lk.ijse.coir.bo.custom.CustomerBO;
import lk.ijse.coir.bo.custom.EmployeeBO;
import lk.ijse.coir.bo.custom.ItemBO;
import lk.ijse.coir.bo.custom.impl.CustomerBOImpl;
import lk.ijse.coir.bo.custom.impl.EmployeeBOImpl;
import lk.ijse.coir.bo.custom.impl.ItemBOImpl;
import lk.ijse.coir.dao.custom.CustomerDAO;
import lk.ijse.coir.dao.custom.ItemDAO;
import lk.ijse.coir.util.DateTimeUtil;
import lk.ijse.coir.util.Navigation;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import static lk.ijse.coir.util.DateTimeUtil.timeNow;
import static lk.ijse.coir.util.Navigation.switchNavigation;
import static lk.ijse.coir.util.Navigation.switchPaging;

public class DashboardFormController implements Initializable {


    @FXML
    private Label txtTime;

    @FXML
    private Label txtdate;

    @FXML
    private AnchorPane pagingPane;

    @FXML
    private Label txtCustomercount;

    @FXML
    private Label txtEmployeeCount;

    @FXML
    private Label txtItemTypes;


    CustomerBO customerBO =new CustomerBOImpl();

    ItemBO itemBO =new ItemBOImpl();

    EmployeeBO employeeBO = new EmployeeBOImpl();

    @FXML
    void btnLogoutOnAction(ActionEvent event) throws IOException {
        switchNavigation("loginPageForm.fxml", event);

    }

    @FXML
    void btncustomeronAction(ActionEvent event) throws IOException {
        switchPaging(pagingPane, "customerForm.fxml", "Customer Form");
    }

    @FXML
    void btndeliveryOnAction(ActionEvent event) throws IOException {
       Navigation.switchPaging(pagingPane, "deliveryForm.fxml", "Delivery Form");
    }

    @FXML
    void btnemployeeOnAction(ActionEvent event) throws IOException {
        Navigation.switchPaging(pagingPane, "employeeForm.fxml", "Employee Form");
    }

    @FXML
    void btnhomeOnAction(ActionEvent event) throws IOException {
        switchPaging(pagingPane, "dashFormForm.fxml", "Home Form");
    }

    @FXML
    void btnhomeOnMouseExited(MouseEvent event) {

    }

    @FXML
    void btnplaceOrderOnAction(ActionEvent event) throws IOException {
        Navigation.switchPaging(pagingPane, "placeOrderForm.fxml", "Place Order Form");
    }

    @FXML
    void btnitemOnAction(ActionEvent event) throws IOException {
        Navigation.switchPaging(pagingPane, "itemForm.fxml", "item Form");
    }

    @FXML
    void btnrawmaterialOnAction(ActionEvent event) throws IOException {
        switchPaging(pagingPane, "rawMaterialForm.fxml", "Raw Material Form");
    }

    @FXML
    void btnsupplierOnAction(ActionEvent event) throws IOException {
        switchPaging(pagingPane, "supplierForm.fxml", "Supplier Form");
    }

    @FXML
    void btnstockOnAction(ActionEvent event) throws IOException {
       Navigation.switchPaging(pagingPane, "materialStockForm.fxml", "Stock Form");

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //CustomerModel customerModel = new CustomerModel();

        txtdate.setText(DateTimeUtil.dateNow());
        // txtTime.setText(timeNow());
        loadTime();


        try {
            txtCustomercount.setText(String.valueOf(customerBO.totalCustomerCount()));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        try {
            try {
                txtItemTypes.setText(String.valueOf(itemBO.totalItemTypes()));
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            txtEmployeeCount.setText(String.valueOf(employeeBO.totalEmployeeCount()));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    private void loadTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm:ss");
        txtdate.setText(LocalDate.now().toString());
        Timeline timeline = new Timeline(new
                KeyFrame(javafx.util.Duration.ZERO, e -> {

            txtTime.setText(timeNow());
            // lblTime.setText(LocalDateTime.now().format(formatter));

        }),
                new KeyFrame(Duration.seconds(1)));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();


    }

}



