import java.net.URL;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;


import com.sun.istack.internal.NotNull;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class Controller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ToggleGroup tgSymbol;

    @FXML
    private ToggleGroup tgUnaryOperator;
    @FXML
    private ToggleGroup tgBinaryOperator;

    @FXML
    private ToggleGroup tgClear;

    @FXML
    private TextField tfFirstOperand;

    @FXML
    private TextField tfSecondOperand;

    @FXML
    private Label lbAction;

    @FXML
    private Label lbResult;
    private String lastFocused ="first";

    @FXML
    void btnChangeSign(ActionEvent event) {

    }

    @FXML
    void btnComma(ActionEvent event) {

    }

    private void configureControls(){
        tgClear.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            String symbol;
            if (newValue!=null)
                symbol=((ToggleButton)newValue).getText();
            else
                symbol=((ToggleButton)oldValue).getText();
            if (!Objects.equals(symbol, "C")){
                tfSecondOperand.clear();
                tfFirstOperand.clear();
                lbResult.setText("");
                lbAction.setText("");
            }
            if (Objects.equals(symbol, "C")&& Objects.equals(lastFocused, "first"))
                tfFirstOperand.setText(Optional.ofNullable(tfFirstOperand.getText())
                        .filter(str -> str.length() != 0)
                        .map(str -> str.substring(0, str.length() - 1))
                        .orElse(tfFirstOperand.getText()));
            if (Objects.equals(symbol, "C")&& Objects.equals(lastFocused, "second"))
                tfFirstOperand.setText(Optional.ofNullable(tfSecondOperand.getText())
                        .filter(str -> str.length() != 0)
                        .map(str -> str.substring(0, str.length() - 1))
                        .orElse(tfSecondOperand.getText()));
        });
        tfSecondOperand.setOnMouseClicked(e->{
            lastFocused ="second";
        });
        tfFirstOperand.setOnMouseClicked(e->{
            lastFocused ="first";
        });
        tgSymbol.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            String symbol;
            if (newValue!=null)
                symbol=((ToggleButton)newValue).getText();
            else
                symbol=((ToggleButton)oldValue).getText();
            if (lastFocused.equals("first") &&tfFirstOperand.getText().length()<10)
                tfFirstOperand.setText(tfFirstOperand.getText()+symbol);
            else if (lastFocused.equals("second") &&tfSecondOperand.getText().length()<10)
                tfSecondOperand.setText(tfSecondOperand.getText()+symbol);

        });
        tgBinaryOperator.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            String action;
            if (newValue!=null)
                action=((ToggleButton)newValue).getText();
            else
                action=((ToggleButton)oldValue).getText();
            if (tfFirstOperand.getText().length()==0
                    ||tfSecondOperand.getText().length()==0
                    ||lbAction.getText().length()==0) {
                lbAction.setText(action);
            }else {
                System.out.println("else");
                applyPreviousFunction();
                lbAction.setText(action);
            }
            lastFocused="second";
        });
        tgUnaryOperator.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            String action;
            if (newValue!=null)
                action=((ToggleButton)newValue).getText();
            else
                action=((ToggleButton)oldValue).getText();
            if (lbAction.getText().length()!=0) {
                applyPreviousFunction();
                lbAction.setText(action);
            }else {
                lbAction.setText(action);
            }
            tgUnaryOperator.getSelectedToggle().setSelected(false);
        });
    }

    private void changeText() {
        String[] array=new String[]{" "," "," "};
        StringBuilder textToSet = new StringBuilder();
//        array[0]=selectNumberType(tfFirstOperand);
//        array[1]=operator.toString();
//        array[2]=selectNumberType(tfSecondOperand);
        for (String anArray : array) {
            textToSet.append(anArray).append("\n");
        }
//        taCalc.setText(textToSet.toString());

    }

    private String selectNumberType(StringBuilder stringNumber) {

        Number num;
        try {
            if (Math.floor(Double.valueOf(stringNumber.toString()))==Double.valueOf(stringNumber.toString())) {
                num=Integer.valueOf(stringNumber.toString());
                System.out.println("ravno bliat");
            }
            else
                num = Double.valueOf(stringNumber.toString());
        } catch (Exception e) {
            return " ";
        }
        return String.valueOf(num);
    }

    private void applyPreviousFunction() {
//        System.out.println("|"+operator+"|");
        Double result=0.;
        switch (lbAction.getText()){
            case "+":{
                result=calcSum();
                break;
            }
            case "-":{
                System.out.println("minus bliat");
                result=calcSub();
                break;
            }
            case "x":{
                result=calcMul();
                break;
            }
            case "/":{
                result=calcDiv();
                break;
            }
            case "1/n":{
                result=calcReverse();
                break;
            }
            case "n!":{
                result=calcFactorial();
                break;
            }
            case "%":{
                result=calcPercent();
                break;
            }
            case "√n":{
                result=calcRoot();
                break;
            }
            case "n^x":{
                result=calcPower();
                break;
            }
            case "=":{
//                result=calcEqal();
                break;
            }
        }
        System.out.println("res="+result);
        lbResult.setText(String.valueOf(result));

    }

    @NotNull
    private Double calcPower() {
        return Math.pow(convertToNumber(tfFirstOperand),convertToNumber(tfSecondOperand));
    }

    @NotNull
    private Double calcRoot() {
        return Math.sqrt(convertToNumber(tfFirstOperand));
    }

    @NotNull
    private Double calcPercent() {
        return convertToNumber(tfFirstOperand)/100*convertToNumber(tfSecondOperand);
    }

    @NotNull
    private Double calcFactorial() {
        try {
            Integer integer=Integer.getInteger(tfFirstOperand.toString());
            Integer res=1;
            for (int i = 1; i < 1+integer; i++) {
                res=res*i;
            }
            return Double.valueOf(res);
        } catch (Exception e) {
            e.printStackTrace();
            return 0.;
        }
    }

    @NotNull
    private Double calcReverse() {
        return 1/convertToNumber(tfFirstOperand);
    }

    @NotNull
    private Double calcDiv() {
        return (convertToNumber(tfFirstOperand)/convertToNumber(tfSecondOperand));
    }

    @NotNull
    private Double calcMul() {
        return (convertToNumber(tfFirstOperand)*convertToNumber(tfSecondOperand));
    }

    @NotNull
    private Double calcSub() {
        return (convertToNumber(tfFirstOperand)-convertToNumber(tfSecondOperand));
    }

    @NotNull
    private Double calcSum() {
        return (convertToNumber(tfFirstOperand)+convertToNumber(tfSecondOperand));
    }

    @NotNull
    private Double convertToNumber(TextField operand) {
        try {
            return Double.valueOf(operand.getText());
        } catch (NumberFormatException e) {
            return 0.;
        }
    }

    @FXML
    void initialize() {
        assert tgSymbol != null : "fx:id=\"tgSymbol\" was not injected: check your FXML file 'Form.fxml'.";
        assert tgUnaryOperator != null : "fx:id=\"tgUnaryOperator\" was not injected: check your FXML file 'Form.fxml'.";
        assert tgBinaryOperator != null : "fx:id=\"tgBinaryOperator\" was not injected: check your FXML file 'Form.fxml'.";
        assert tgClear != null : "fx:id=\"tgClear\" was not injected: check your FXML file 'Form.fxml'.";
        assert tfFirstOperand != null : "fx:id=\"tfFirstOperand\" was not injected: check your FXML file 'Form.fxml'.";
        assert tfSecondOperand != null : "fx:id=\"tfSecondOperand\" was not injected: check your FXML file 'Form.fxml'.";
        assert lbAction != null : "fx:id=\"lbAction\" was not injected: check your FXML file 'Form.fxml'.";
        assert lbResult != null : "fx:id=\"lbResult\" was not injected: check your FXML file 'Form.fxml'.";
        configureControls();
    }

}
