package lk.ijse.coir.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.beans.property.ReadOnlyObjectWrapper;
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
import lk.ijse.coir.bo.custom.MaterialStockBO;
import lk.ijse.coir.bo.custom.RawMaterialBO;
import lk.ijse.coir.bo.custom.SupplierBO;
import lk.ijse.coir.bo.custom.impl.MaterialStockBOImpl;
import lk.ijse.coir.bo.custom.impl.RawMaterialBOImpl;
import lk.ijse.coir.bo.custom.impl.SupplierBOImpl;
import lk.ijse.coir.db.DbConnection;
import lk.ijse.coir.dto.*;

import lk.ijse.coir.dto.tm.SupplierDetailTm;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class MaterialStockFormController {

    @FXML
    private JFXButton btnAdd;

    @FXML
    private JFXButton btnPrint;

    @FXML
    private JFXComboBox<String> cmbSupplierId;

    @FXML
    private JFXComboBox<String> cmbRawId;

    @FXML
    private TableColumn<?, ?> colAction;

    @FXML
    private TableColumn<?, ?> colRawId;

    @FXML
    private TableColumn<?, ?> colIRawName;

    @FXML
    private TableColumn<?, ?> colQty;

    @FXML
    private TableColumn<?, ?> colTotal;

    @FXML
    private TableColumn<?, ?> colUnitPrice;

    @FXML
    private Label lblName;

    @FXML
    private Label lblNetTotal;

    @FXML
    private Label lblOrderDate;

    @FXML
    private Label lblQtyOnStock;

    @FXML
    private AnchorPane root;

    @FXML
    private Label lblSupplierName;

    @FXML
    private Label lblUnitPrice;

    @FXML
    private TableView<SupplierDetailTm> tblStockCart;

    @FXML
    private TextField txtMaterialName;

    @FXML
    private TextField txtQtyOnStock;

    @FXML
    private TextField txtUnitPrice;

    @FXML
    private TextField txtQty;

    @FXML
    private TextField txtSupplierName;

    @FXML
    private JFXButton btnstock;

    @FXML
    private Label lblTotal;

    ObservableList<SupplierDetailTm> obList1 = FXCollections.observableArrayList();

    RawMaterialBO rawMaterialBO = new RawMaterialBOImpl();
    SupplierBO supplierBO =new SupplierBOImpl();

    MaterialStockBO materialStockBO =new MaterialStockBOImpl();

public void initialize() throws SQLException, ClassNotFoundException {

    tblStockCart.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("rawMaterialId"));
    tblStockCart.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("MaterialName"));
    tblStockCart.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("qty"));
    tblStockCart.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
    tblStockCart.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("total"));
    TableColumn<SupplierDetailTm, Button> lastCol = (TableColumn<SupplierDetailTm, Button>) tblStockCart.getColumns().get(5);

    lastCol.setCellValueFactory(param -> {
        Button btnDelete = new Button("Delete");

        btnDelete.setOnAction(event -> {
            tblStockCart.getItems().remove(param.getValue());
            tblStockCart.getSelectionModel().clearSelection();
            calculateTotal();
            enableOrDisablePlaceOrderButton();
        });

        return new ReadOnlyObjectWrapper<>(btnDelete);
    });

    lblOrderDate.setText(LocalDate.now().toString());
    btnstock.setDisable(true);
    txtSupplierName.setFocusTraversable(false);
    txtSupplierName.setEditable(false);
    txtMaterialName.setFocusTraversable(false);
    txtMaterialName.setEditable(false);
    txtUnitPrice.setFocusTraversable(false);
    txtUnitPrice.setEditable(false);
    txtQtyOnStock.setFocusTraversable(false);
    txtQtyOnStock.setEditable(false);
    txtQty.setOnAction(event -> btnAdd.fire());
    txtQty.setEditable(false);
    btnAdd.setDisable(true);

    cmbSupplierId.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
        enableOrDisablePlaceOrderButton();
        if (newValue != null) {
            try {
                try {
                    if (!existSupplier(newValue + "")) {
                        //"There is no such customer associated with the id " + id
                        new Alert(Alert.AlertType.ERROR, "There is no such supplier associated with the id " + newValue + "").show();
                    }
                    //Search Customer
                    SupplierDto supplierDto= materialStockBO.searchSupplier(newValue + "");
                    lblSupplierName.setText(supplierDto.getSupplierName());

                } catch (SQLException e) {
                    new Alert(Alert.AlertType.ERROR, "Failed to find the customer " + newValue + "" + e).show();
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            txtSupplierName.clear();
        }
    });


    cmbRawId.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newRawId) -> {
        txtQty.setEditable(newRawId!= null);
        btnAdd.setDisable(newRawId == null);

        if (newRawId!= null) {

            /*Find Item*/
            try {
                if (!existRawMaterial(newRawId + "")) {
//                        throw new NotFoundException("There is no such item associated with the id " + code);
                }

                //Search Item
                RawMaterialDto rawMaterial= materialStockBO.searchRaw(newRawId + "");

                txtMaterialName.setText(rawMaterial.getMaterialName());
                txtUnitPrice.setText(rawMaterial.getUnitPrice().setScale(2).toString());

//                  txtQtyOnHand.setText(tblOrderDetails.getItems().stream().filter(detail-> detail.getCode().equals(item.getCode())).<Integer>map(detail-> item.getQtyOnHand() - detail.getQty()).findFirst().orElse(item.getQtyOnHand()) + "");
                Optional<SupplierDetailTm> optSupplierDetail = tblStockCart.getItems().stream().filter(detail -> detail.getRawMaterialId().equals(newRawId)).findFirst();
                txtQtyOnStock.setText((optSupplierDetail.isPresent() ? rawMaterial.getQtyOnStock() + optSupplierDetail.get().getQty() : rawMaterial.getQtyOnStock()) + "");

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        } else {
            txtMaterialName.clear();
            txtQty.clear();
            txtQtyOnStock.clear();
            txtUnitPrice.clear();
        }
    });

    tblStockCart.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, selectedOrderDetail) -> {

        if (selectedOrderDetail != null) {
            cmbRawId.setDisable(true);
            cmbRawId.setValue(selectedOrderDetail.getRawMaterialId());
            btnAdd.setText("Update");
            txtQtyOnStock.setText(Integer.parseInt(txtQtyOnStock.getText()) + selectedOrderDetail.getQty() + "");
            txtQty.setText(selectedOrderDetail.getQty() + "");
        } else {
            btnAdd.setText("Add");
            cmbRawId.setDisable(false);
            cmbRawId.getSelectionModel().clearSelection();
            txtQty.clear();
        }

    });

    loadAllSupplierIds();
    loadAllRawMaterials();
}

    private boolean existRawMaterial(String id) throws SQLException, ClassNotFoundException {
        return materialStockBO.existRaw(id);
    }

    boolean existSupplier(String id) throws SQLException, ClassNotFoundException {
        return materialStockBO.existSupplier(id);
    }


    private void loadAllSupplierIds() {
        try {
            ArrayList<SupplierDto> allSuppliers = materialStockBO.getAllSupplier();
            for (SupplierDto c : allSuppliers) {
                cmbSupplierId.getItems().add(c.getSupplierId());
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to load supplier ids").show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void loadAllRawMaterials() {
        try {
            /*Get all items*/

            ArrayList<RawMaterialDto> allRaw = materialStockBO.getAllRaw();
            for (RawMaterialDto i : allRaw) {
                cmbRawId.getItems().add(i.getRawMaterialId());
            }

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void btnAddToStockOnAction(ActionEvent actionEvent) {
    if (!txtQty.getText().matches("\\d+") || Integer.parseInt(txtQty.getText()) <= 0 ||
            Double.parseDouble(txtQty.getText()) > Double.parseDouble(txtQtyOnStock.getText())) {
        new Alert(Alert.AlertType.ERROR, "Invalid qty").show();
        txtQty.requestFocus();
        txtQty.selectAll();
        return;
    }

    String RawId = cmbRawId.getSelectionModel().getSelectedItem();
    String  name = txtMaterialName.getText();
    BigDecimal unitPrice = new BigDecimal(txtUnitPrice.getText()).setScale(2);
    int qty = Integer.parseInt(txtQty.getText());
    BigDecimal total = unitPrice.multiply(new BigDecimal(qty)).setScale(2);

    boolean exists = tblStockCart.getItems().stream().anyMatch(detail -> detail.getRawMaterialId().equals(RawId));

    if (exists) {
        SupplierDetailTm supplierDetailTm= tblStockCart.getItems().stream().filter(detail -> detail.getRawMaterialId().equals(RawId)).findFirst().get();

        if (btnAdd.getText().equalsIgnoreCase("Update")) {
            supplierDetailTm.setQty(qty);
            supplierDetailTm.setTotal(total);
            tblStockCart.getSelectionModel().clearSelection();
        } else {
            supplierDetailTm.setQty(supplierDetailTm.getQty() + qty);
            total = new BigDecimal(supplierDetailTm.getQty()).multiply(unitPrice).setScale(2);
            supplierDetailTm.setTotal(total);
        }
        tblStockCart.refresh();
    } else {
        tblStockCart.getItems().add(new SupplierDetailTm(RawId, name, qty, unitPrice, total));
    }
    cmbRawId.getSelectionModel().clearSelection();
    cmbRawId.requestFocus();
    calculateTotal();
    enableOrDisablePlaceOrderButton();
}

    private void calculateTotal() {
        BigDecimal total = new BigDecimal(0);

        for (SupplierDetailTm supplierDetailTm : tblStockCart.getItems()) {
            total = total.add(supplierDetailTm.getTotal());
        }
        lblTotal.setText("Total: " + total);
    }

    private void enableOrDisablePlaceOrderButton() {
        btnstock.setDisable(!(cmbSupplierId.getSelectionModel().getSelectedItem() != null && !tblStockCart.getItems().isEmpty()));
    }

    public void txtQtyOnAction(ActionEvent actionEvent) {
    }

    public void btnMaterialStockOnAction(ActionEvent actionEvent) {
        boolean b = saveOrder( LocalDate.now(), cmbRawId.getValue(),
                tblStockCart.getItems().stream().map(tm -> new SupplierDetailDto( tm.getSupplierId(), tm.getRawMaterialId(),LocalDate.now(),tm.getUnitPrice(),tm.getQty())).collect(Collectors.toList()));

        if (b) {
            new Alert(Alert.AlertType.INFORMATION, "Stock order has been placed successfully").show();
        } else {
            new Alert(Alert.AlertType.ERROR, "stock order has not been placed successfully").show();
        }

        cmbSupplierId.getSelectionModel().clearSelection();
        cmbRawId.getSelectionModel().clearSelection();
        tblStockCart.getItems().clear();
        txtQty.clear();
        calculateTotal();
    }

    public boolean saveOrder( LocalDate stockDate, String supplierId, List<SupplierDetailDto> supplierDetails) {
        try {
            return materialStockBO.getOrder(stockDate,supplierId,supplierDetails);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void cmbRawIdOnAction(ActionEvent event) throws ClassNotFoundException {
        String rawId = cmbRawId.getValue();

        txtQty.requestFocus();

        try {
            RawMaterialDto dto = rawMaterialBO.searchRawMaterial(rawId);

            txtMaterialName.setText(dto.getMaterialName());
            txtUnitPrice.setText(String.valueOf(dto.getUnitPrice()));
            txtQtyOnStock.setText(String.valueOf(dto.getQtyOnStock()));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void cmbSupplierOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String supplierId = cmbSupplierId.getValue();
        SupplierDto dto = supplierBO.searchSupplier(supplierId);

        txtSupplierName.setText(dto.getSupplierName());
    }


    @FXML
    void btnNewSupplierOnAction(ActionEvent event) throws IOException {
        Parent anchorPane = FXMLLoader.load(getClass().getResource("/view/supplierForm.fxml"));
        Scene scene = new Scene(anchorPane);

        Stage stage = new Stage();
        stage.setTitle("Supplier Manage");
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
    void btnPrintOnAction(ActionEvent event) {
        InputStream resource = this.getClass().getResourceAsStream("/reports/StockDetail.jrxml");
        try {
            JasperReport jasperReport = JasperCompileManager.compileReport(resource);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, DbConnection.getInstance().getConnection());
            JasperViewer.viewReport(jasperPrint, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}


