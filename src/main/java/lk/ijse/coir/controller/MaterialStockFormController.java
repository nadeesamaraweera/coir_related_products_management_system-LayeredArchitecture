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
import lk.ijse.coir.dto.GetOrderDto;
import lk.ijse.coir.dto.RawMaterialDto;
import lk.ijse.coir.dto.SupplierDto;
import lk.ijse.coir.dto.tm.MaterialCartTm;
import lk.ijse.coir.model.GetOrderModel;
import lk.ijse.coir.model.RawMaterialModel;
import lk.ijse.coir.model.SupplierModel;
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
import java.util.List;
import java.util.Optional;

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
    private TableView<MaterialCartTm> tblStockCart;

    @FXML
    private TextField txtQty;

    private final GetOrderModel getOrderModel = new GetOrderModel();

    private final RawMaterialModel rawMaterialModel = new RawMaterialModel();

    private final SupplierModel supplierModel = new SupplierModel();
    ObservableList<MaterialCartTm> obList1 = FXCollections.observableArrayList();


    public void initialize() {
        setCellValueFactory();
        setDate();
        loadSupplierIds();
        loadRawMaterialIds();
    }

    private void setCellValueFactory() {
        colRawId.setCellValueFactory(new PropertyValueFactory<>("rawMaterialId"));
        colIRawName.setCellValueFactory(new PropertyValueFactory<>("materialName"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("tot"));
        colAction.setCellValueFactory(new PropertyValueFactory<>("btn"));
    }

    private void loadRawMaterialIds() {
        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            List<RawMaterialDto> rawList = rawMaterialModel.loadAllMaterials();

            for (RawMaterialDto rawMaterialDto : rawList) {
                obList.add(rawMaterialDto.getRawMaterialId());
            }

            cmbRawId.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadSupplierIds() {
        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            List<SupplierDto> supList = supplierModel.loadAllSuppliers();

            for (SupplierDto dto : supList) {
                obList.add(dto.getSupplierId());
            }
            cmbSupplierId.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setDate() {
        String date = String.valueOf(LocalDate.now());
        lblOrderDate.setText(date);
    }


    @FXML
    void btnAddToStockOnAction(ActionEvent event) {

        String materialId = cmbRawId.getValue();
        String materialName = lblName.getText();
        int qty = Integer.parseInt(txtQty.getText());
        double hand = Double.parseDouble(lblQtyOnStock.getText());
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
                    int index = tblStockCart.getSelectionModel().getSelectedIndex();
                    obList1.remove(index);
                    tblStockCart.refresh();

                    calculateNetTotal();
                }
            });

            for (int i = 0; i < tblStockCart.getItems().size(); i++) {
                if (materialId.equals(colRawId.getCellData(i))) {
                    qty += (int) colQty.getCellData(i);
                    total = qty * unitPrice;

                    obList1.get(i).setQty(qty);
                    obList1.get(i).setTot(total);

                    tblStockCart.refresh();
                    calculateNetTotal();
                    return;
                }
            }

            obList1.add(new MaterialCartTm(
                    materialId,
                    materialName,
                    qty,
                    unitPrice,
                    total,
                    btn
            ));

            tblStockCart.setItems(obList1);
            calculateNetTotal();
            txtQty.clear();
        } else {
            new Alert(Alert.AlertType.ERROR, "The stock haven't ").show();
            txtQty.setStyle("-fx-border-color: Red");

        }

    }

    private void calculateNetTotal() {
        double total = 0;
        for (int i = 0; i < tblStockCart.getItems().size(); i++) {
            total += (double) colTotal.getCellData(i);
        }

        lblNetTotal.setText(String.valueOf(total));
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
    void btnMaterialStockOnAction(ActionEvent event) {
        String supplierId =  cmbSupplierId.getValue();
        String rawMaterialId =cmbRawId.getValue();
        LocalDate date = LocalDate.parse(lblOrderDate.getText());

        List<MaterialCartTm> tmList = new ArrayList<>();

        for (MaterialCartTm materialCartTm : obList1) {
            tmList.add(materialCartTm);
        }

        var getOrderDto = new GetOrderDto(

                supplierId,
                rawMaterialId,
                date,
                tmList
        );

        try {
            boolean isSuccess = getOrderModel.getOrder(getOrderDto);
            if (isSuccess) {
                new Alert(Alert.AlertType.CONFIRMATION, "order completed!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
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


    @FXML
    void cmbSupplierOnAction(ActionEvent event) throws SQLException {
        String supplierId =cmbSupplierId.getValue();
        SupplierDto dto = supplierModel.searchSupplier(supplierId);

        lblSupplierName.setText(dto.getSupplierName());


    }


    @FXML
    void cmbRawIdOnAction(ActionEvent event) {
        String rawMaterialId =cmbRawId.getValue();

        txtQty.requestFocus();

        try {
            RawMaterialDto dto = rawMaterialModel.searchRaw(rawMaterialId);

            lblName.setText(dto.getMaterialName());
            lblUnitPrice.setText(String.valueOf(dto.getUnitPrice()));
            lblQtyOnStock.setText(String.valueOf(dto.getQtyOnStock()));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }


    @FXML
    void txtQtyOnAction(ActionEvent event) {
        btnAddToStockOnAction(event);
    }

}


