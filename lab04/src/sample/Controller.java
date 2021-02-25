package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class Controller {
    @FXML
    private Text action_target;

    @FXML
    private TextField full_name_field;

    @FXML
    private TextField email_field;

    @FXML
    private TextField phone_field;

    @FXML
    private DatePicker date_field;

    public void submitActionButton(ActionEvent actionEvent) {
        System.out.println(full_name_field.getText() );
        System.out.println(email_field.getText() );
        System.out.println(phone_field.getText() );
        System.out.println(date_field.getValue());

        action_target.setText("Successfully Registered.");
    }

}
