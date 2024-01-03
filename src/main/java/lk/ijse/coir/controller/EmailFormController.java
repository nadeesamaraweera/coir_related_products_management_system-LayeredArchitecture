package lk.ijse.coir.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import javax.mail.MessagingException;

public class EmailFormController {


    @FXML
    private JFXButton btnsend;

    @FXML
    private TextField txtmessage;

    @FXML
    private TextField txtsubject;

    @FXML
    void btnsendOnAction(ActionEvent event) throws MessagingException {
            String email = txtsubject.getText();
            String body = txtmessage.getText();
            EmailController.sendEmail(email, "", body);

        }

    }

