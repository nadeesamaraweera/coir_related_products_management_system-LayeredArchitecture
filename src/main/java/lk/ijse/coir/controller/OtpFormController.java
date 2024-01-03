package lk.ijse.coir.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import lk.ijse.coir.util.Navigation;

import java.io.IOException;

public class OtpFormController {

    @FXML
    private AnchorPane OtpForm;

    @FXML
    private Button btnBack;

    @FXML
    private Button btnVerify;

    @FXML
    private TextField txtOtp;

    @FXML
    void btnBackOnAction(ActionEvent event) throws IOException {
        btnBack.getScene().getWindow().hide();
        Navigation.switchNavigation("fogotPasswordForm.fxml",event);
    }
    private int otp;
    @FXML
    void btnVerifyOnAction(ActionEvent event) throws IOException {
        System.out.println(ForgotPasswordFormController.otp);
        if(String.valueOf(otp).equals(txtOtp.getText())){
            btnVerify.getScene().getWindow().hide();
            Navigation.switchNavigation("resetPassword.fxml",event);
        }else{
            new Alert(Alert.AlertType.ERROR,"OTP WRONG").show();
        }
    }
    public void initialize(){
        System.out.println(ForgotPasswordFormController.otp);
        this.otp = ForgotPasswordFormController.otp;
    }
}
