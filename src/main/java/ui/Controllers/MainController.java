package ui.Controllers;

import app.Controller;
import app.Config;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import ui.App;


public class MainController {
    private App app;
    private Controller controller;

    @FXML
    private TextField sourceNumber;
    @FXML
    private TextField deviceNumber;
    @FXML
    private TextField requestsNum;
    @FXML
    private TextField bufferSizeText;
    @FXML
    private TextField alpha;
    @FXML
    private TextField beta;
    @FXML
    private TextField lambda;

    @FXML
    private RadioButton automode;

    public void provideApp(App app, Controller controller) {
        this.app = app;
        this.controller = controller;

        ToggleGroup modeGroup = new ToggleGroup();
        //automode.setToggleGroup(modeGroup);
    }

    @FXML
    public void startModulating() {
        accessField();
    }

    private void accessField() {
        if (sourceNumber.getText().isEmpty() || deviceNumber.getText().isEmpty()
                || requestsNum.getText().isEmpty() || bufferSizeText.getText().isEmpty()
                || alpha.getText().isEmpty() || beta.getText().isEmpty()) {
            app.showErrorAlert("Fill all fields");
            return;
        }

        try {
            Config.SOURCE_NUMBER = Integer.parseInt(sourceNumber.getText());
            Config.DEVICE_NUMBER = Integer.parseInt(deviceNumber.getText());
            Config.REQUESTS_NUMBER = Integer.parseInt(requestsNum.getText());
            Config.BUFFER_SIZE = Integer.parseInt(bufferSizeText.getText());
            Config.ALPHA = Double.parseDouble(alpha.getText());
            Config.BETA = Double.parseDouble(beta.getText());
            Config.LAMBDA = Double.parseDouble(lambda.getText());



        } catch (Exception e) {
            app.showErrorAlert("Some fields incorrect");
        }

        controller.start();
        app.showAutoModeWindow();
    }

    public Controller getController() {
        return controller;
    }
}