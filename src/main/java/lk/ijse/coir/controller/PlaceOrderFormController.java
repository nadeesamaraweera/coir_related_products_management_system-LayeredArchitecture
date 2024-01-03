package lk.ijse.coir.controller;


import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.coir.db.DbConnection;
import lk.ijse.coir.dto.CustomerDto;
import lk.ijse.coir.dto.ItemDto;
import lk.ijse.coir.dto.PlaceOrderDto;
import lk.ijse.coir.dto.tm.CartTm;
import lk.ijse.coir.model.CustomerModel;
import lk.ijse.coir.model.ItemModel;
import lk.ijse.coir.model.OrderModel;
import lk.ijse.coir.model.PlaceOrderModel;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class PlaceOrderFormController {
    private final CustomerModel customerModel = new CustomerModel();
    private final ItemModel itemModel = new ItemModel();
    private final OrderModel orderModel = new OrderModel();
    private final ObservableList<CartTm> obList = FXCollections.observableArrayList();
    @FXML
    private JFXButton btnAddToCart;

    @FXML
    private JFXButton btnBack;

    @FXML
    private JFXButton btnPlaceorder;

    @FXML
    private JFXComboBox<String> cmbCustomerId;
    @FXML
    private JFXComboBox<String> cmbItemId;
    @FXML
    private TableColumn<?, ?> colAction;

    @FXML
    private TableColumn<?, ?> colItemName;
    @FXML
    private TableColumn<?, ?> colItemId;
    @FXML
    private TableColumn<?, ?> colQty;
    @FXML
    private TableColumn<?, ?> colTotal;
    @FXML
    private TableColumn<?, ?> colUnitPrice;
    @FXML
    private Label lblCustomerName;
    @FXML
    private Label lblDescription;
    @FXML
    private Label lblOrderDate;
    @FXML
    private Label lblOrderId;
    @FXML
    private Label lblQtyOnHand;
    @FXML
    private Label lblUnitPrice;
    @FXML
    private AnchorPane root;
    @FXML
    private TableView<CartTm> tblOrderCart;
    @FXML
    private TextField txtQty;
    @FXML
    private Label lblNetTotal;

    private final PlaceOrderModel placeOrderModel = new PlaceOrderModel();


    public void initialize() {
        setCellValueFactory();
        generateNextOrderId();
        setDate();
        loadCustomerIds();
        loadItemIds();
    }

    private void setCellValueFactory() {
        colItemId.setCellValueFactory(new PropertyValueFactory<>("itemId"));
        colItemName.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("tot"));
        colAction.setCellValueFactory(new PropertyValueFactory<>("btn"));
    }

    private void generateNextOrderId() {
        try {
            String orderId = orderModel.generateNextOrderId();
            lblOrderId.setText(orderId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadItemIds() {
        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            List<ItemDto> itemList = itemModel.loadAllItems();

            for (ItemDto itemDto : itemList) {
                obList.add(itemDto.getItemId());
            }

            cmbItemId.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadCustomerIds() {
        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            List<CustomerDto> cusList = customerModel.loadAllCustomers();

            for (CustomerDto dto : cusList) {
                obList.add(dto.getCustomerId());
            }
            cmbCustomerId.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setDate() {
        String date = String.valueOf(LocalDate.now());
        lblOrderDate.setText(date);
    }

    @FXML
    void btnAddToCartOnAction(ActionEvent event) {
        String itemId = cmbItemId.getValue();
        String itemName = lblDescription.getText();
        int qty = Integer.parseInt(txtQty.getText());
        int hand = Integer.parseInt(lblQtyOnHand.getText());
        if (qty < hand) {
            double unitPrice = Double.parseDouble(lblUnitPrice.getText());
            double total = qty * unitPrice;
            Button btn = new Button("remove");
            btn.setCursor(Cursor.HAND);

            btn.setOnAction((e) -> {
                ButtonType yes = new ButtonType("yes", ButtonBar.ButtonData.OK_DONE);
                ButtonType no = new ButtonType("no", ButtonBar.ButtonData.CANCEL_CLOSE);

                Optional<ButtonType> type = new Alert(Alert.AlertType.INFORMATION, "Are you sure to remove?", yes, no).showAndWait();


                if (type.orElse(no) == yes) {
                    int index = tblOrderCart.getSelectionModel().getSelectedIndex();
                    obList.remove(index);
                    tblOrderCart.refresh();

                    calculateNetTotal();
                }
            });

            for (int i = 0; i < tblOrderCart.getItems().size(); i++) {
                if (itemId.equals(colItemId.getCellData(i))) {
                    qty += (int) colQty.getCellData(i);
                    total = qty * unitPrice;

                    obList.get(i).setQty(qty);
                    obList.get(i).setTot(total);

                    tblOrderCart.refresh();
                    calculateNetTotal();
                    return;
                }
            }

            obList.add(new CartTm(
                    itemId,
                    itemName,
                    qty,
                    unitPrice,
                    total,
                    btn
            ));

            tblOrderCart.setItems(obList);
            calculateNetTotal();
            txtQty.clear();
        } else {
            new Alert(Alert.AlertType.ERROR, "The Stock hasn't much qty ").show();
            txtQty.setStyle("-fx-border-color: Red");

        }
    }

    private void calculateNetTotal() {
        double total = 0;
        for (int i = 0; i < tblOrderCart.getItems().size(); i++) {
            total += (double) colTotal.getCellData(i);
        }

        lblNetTotal.setText(String.valueOf(total));
    }

    @FXML
    void btnPlaceOrderOnAction(ActionEvent event) {
        String orderId = lblOrderId.getText();
        String customerId = cmbCustomerId.getValue();
        LocalDate date = LocalDate.parse(lblOrderDate.getText());

        List<CartTm> tmList = new ArrayList<>();

        for (CartTm cartTm : obList) {
            tmList.add(cartTm);
        }

        var placeOrderDto = new PlaceOrderDto(
                orderId,
                customerId,
                date,
                tmList
        );

        try {
            boolean isSuccess = placeOrderModel.placeOrder(placeOrderDto);
            if (isSuccess) {
                new Alert(Alert.AlertType.CONFIRMATION, "order completed!").show();


            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }

    @FXML
    void cmbItemOnAction(ActionEvent event) {
        String itemId = cmbItemId.getValue();

        txtQty.requestFocus();

        try {
            ItemDto dto = itemModel.searchItem(itemId);

            lblDescription.setText(dto.getItemName());
            lblUnitPrice.setText(String.valueOf(dto.getUnitPrice()));
            lblQtyOnHand.setText(String.valueOf(dto.getQtyOnHand()));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void cmbCustomerOnAction(ActionEvent event) throws SQLException {
        String customerId = cmbCustomerId.getValue();
        CustomerDto dto = customerModel.searchCustomer(customerId);

        lblCustomerName.setText(dto.getCustomerName());
    }

    @FXML
    void txtQtyOnAction(ActionEvent event) {

        btnAddToCartOnAction(event);

    }

    @FXML
    void btnNewCustomerOnAction(ActionEvent event) throws IOException {
        Parent anchorPane = FXMLLoader.load(getClass().getResource("/view/customerForm.fxml"));
        Scene scene = new Scene(anchorPane);

        Stage stage = new Stage();
        stage.setTitle("Customer Manage");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    @FXML
    void btnBackOnAction(ActionEvent event) throws IOException {
        Parent anchorPane = FXMLLoader.load(getClass().getResource("/view/dashboardForm.fxml"));
        Stage stage = (Stage) root.getScene().getWindow();

        stage.setScene(new Scene(anchorPane));
        stage.setTitle("Dashboard");
        stage.centerOnScreen();
    }

    @FXML
    void btnPrintOnAction(ActionEvent event)  {
        InputStream resource = this.getClass().getResourceAsStream("/reports/Blank_A4_2.jrxml");
        try {
            JasperReport jasperReport = JasperCompileManager.compileReport(resource);
            HashMap map=new HashMap<>();
            map.put("orderId",lblOrderId.getText());
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, map, DbConnection.getInstance().getConnection());
            JasperViewer.viewReport(jasperPrint, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}