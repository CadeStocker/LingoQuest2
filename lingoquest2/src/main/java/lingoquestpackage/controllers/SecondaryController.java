package lingoquestpackage.controllers;

import java.io.IOException;
import javafx.fxml.FXML;
import lingoquestpackage.lingoquest.*;
import lingoquestpackage.narriator.*;

public class SecondaryController {

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("/lingoquestpackage/primary");
    }
}
// The SecondaryController class is a simple JavaFX controller in the LingoQuest application that handles navigation from the secondary view back to the primary view.
// This class uses the App class to manage scene transitions, ensuring seamless navigation within the application.
// The class contains a single method, switchToPrimary, which is annotated with @FXML to link it to an action in the associated FXML file.
// When invoked, this method uses the App.setRoot method to load the primary.fxml file, effectively switching the user interface back to the primary view. 
// The method also throws an IOException to handle potential errors that might occur while loading the FXML file.
// This controller plays a minimal yet crucial role in enabling navigation between the application's primary and secondary views, demonstrating how JavaFX controllers can manage transitions with concise and focused functionality.






































































































































































