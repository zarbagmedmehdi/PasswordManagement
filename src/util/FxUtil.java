package util;

import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FxUtil {
      public static boolean isFilled(TextField textField){
          return !textField.getText().equals("");
      }
    public static boolean isFilled(TextArea textArea){
        return !textArea.getText().equals("");
    }


    public static boolean isLength(TextField textField,int minLength){
        return textField.getText().length() >= minLength;
    }
    public static boolean comboBox(TextField textField, ComboBox co){
        if((isFilled(textField)&& co.getSelectionModel().getSelectedItem().equals("Autre...")
                ||(!co.getSelectionModel().getSelectedItem().equals("Autre..."))
        )){
            if(!co.getSelectionModel().getSelectedItem().equals("Autre..."))
                textField.setText(co.getSelectionModel().getSelectedItem().toString());
            return true;
        }
        else return false;
      }
}
