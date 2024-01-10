package lk.ijse.coir.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import lk.ijse.coir.bo.custom.LoginBO;
import lk.ijse.coir.bo.custom.impl.LoginBOImpl;
import lk.ijse.coir.util.Navigation;

import java.io.IOException;
import java.sql.SQLException;

public class ResetPasswordController {

    @FXML
    private Button btnLogin;

    @FXML
    private Button btnReset;

    @FXML
    private TextField txtConfirmPassword;

    @FXML
    private TextField txtPassword;

    LoginBO loginBO =new LoginBOImpl();
    @FXML
    void btnOnActionLogin(ActionEvent event) throws IOException {
        Navigation.switchNavigation("loginPageForm.fxml",event);
    }

    @FXML
    void btnOnActionResetPassword(ActionEvent event) throws SQLException, ClassNotFoundException {


        if(txtPassword.getText().equals(txtConfirmPassword.getText())) {
            boolean isUpdated = loginBO.updatePassword(ForgotPasswordFormController.username, txtPassword.getText());
            if (isUpdated) {
                System.out.println("OK");
                new Alert(Alert.AlertType.CONFIRMATION, "OK!").show();

            } else {
                System.out.println("WRONG");
            }
        }else {
            System.out.println("CONFIRMATION NOT MATCHED!");
            new Alert(Alert.AlertType.ERROR,"CONFIRM PASSWORD NOT MATCHED!").show();
            txtConfirmPassword.setStyle("-fx-border-color: Red");


        }
    }
    public void initialize(){
        System.out.println(ForgotPasswordFormController.username);

    }
}

