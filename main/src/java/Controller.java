import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;

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
    private TextArea taCalc;
    private StringBuilder firstOperand=new StringBuilder();
    private StringBuilder secondOperand=new StringBuilder();
    private StringBuilder operator=new StringBuilder();

    private void configureControls(){
        tgSymbol.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            secondOperand.append(((ToggleButton)newValue).getText());
        });
        tgBinaryOperator.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (operator.toString().equals("")) {
                operator.append(((ToggleButton)newValue).getText());
                firstOperand.setLength(0);
                firstOperand.append(secondOperand);
                secondOperand.setLength(0);
            }else {
                applyPreviousFunction();
                operator.setLength(0);
                operator.append(((ToggleButton)newValue).getText());
            }
        });
        tgUnaryOperator.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (operator.toString().equals("")) {
                operator.append(((ToggleButton)newValue).getText());
                firstOperand.setLength(0);
                firstOperand.append(secondOperand);
                secondOperand.setLength(0);
                applyPreviousFunction();
            }else {
                applyPreviousFunction();
                operator.setLength(0);
                operator.append(((ToggleButton)newValue).getText());
            }
        });
    }

    private void applyPreviousFunction() {
        Double result;
        switch (operator.toString()){
            case "+":{
                result=calcSum();
                break;
            }
            case "-":{
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
    }

    private Double calcPower() {
        return Math.pow(convertToNumber(firstOperand),convertToNumber(secondOperand));
    }

    private Double calcRoot() {
        return Math.sqrt(convertToNumber(firstOperand));
    }

    private Double calcPercent() {
        return convertToNumber(firstOperand)/100*convertToNumber(secondOperand);
    }

    private Double calcFactorial() {
        try {
            Integer integer=Integer.getInteger(firstOperand.toString());
            Integer res=1;
            for (int i = 0; i < integer; i++) {
                res=res*i;
            }
            return Double.valueOf(res);
        } catch (Exception e) {
            e.printStackTrace();
            return 0.;
        }
    }

    private Double calcReverse() {
        return 1/convertToNumber(firstOperand);
    }

    private Double calcDiv() {
        return (convertToNumber(firstOperand)/convertToNumber(secondOperand));
    }

    private Double calcMul() {
        return (convertToNumber(firstOperand)*convertToNumber(secondOperand));
    }

    private Double calcSub() {
        return (convertToNumber(firstOperand)-convertToNumber(secondOperand));
    }

    private Double calcSum() {
        return (convertToNumber(firstOperand)+convertToNumber(secondOperand));
    }

    private Double convertToNumber(StringBuilder operand) {
        return Double.valueOf(operand.toString());
    }

    @FXML
    void initialize() {
        assert tgSymbol != null : "fx:id=\"tgSymbol\" was not injected: check your FXML file 'Form.fxml'.";
        assert tgUnaryOperator != null : "fx:id=\"tgUnaryOperator\" was not injected: check your FXML file 'Form.fxml'.";
        assert tgBinaryOperator != null : "fx:id=\"tgBinaryOperator\" was not injected: check your FXML file 'Form.fxml'.";
        assert tgClear != null : "fx:id=\"tgClear\" was not injected: check your FXML file 'Form.fxml'.";
        assert taCalc != null : "fx:id=\"taCalc\" was not injected: check your FXML file 'Form.fxml'.";
        configureControls();
    }

}
