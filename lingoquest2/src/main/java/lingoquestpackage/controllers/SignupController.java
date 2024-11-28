package lingoquestpackage.controllers;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import lingoquestpackage.lingoquest.App;
import lingoquestpackage.models.LanguageGame;

public class SignupController {
    
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label messageLabel;

    private LanguageGame languageGame;

    public SignupController() {
        try {
            this.languageGame = LanguageGame.getInstance(); // initialize backend
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    @FXML
    private void handleSignup() throws IOException {
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();

        if (username.isEmpty() || password.isEmpty()) {
            messageLabel.setText("Username and password cannot be empty.");
            return;
        }

        if (password.length() < 6) {
            messageLabel.setText("Password must be at least 6 characters long.");
            return;
        }

        languageGame.createUser(username, password);
        messageLabel.setText("Account created successfully!");
        messageLabel.setTextFill(javafx.scene.paint.Color.GREEN);
        App.setRoot("/lingoquestpackage/home");
    }

    @FXML
    private void goToLogin() throws IOException {
        App.setRoot("/lingoquestpackage/login");
    }
}