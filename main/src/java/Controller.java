import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import org.jetbrains.annotations.NotNull;
import rx.Observable;
import rx.Subscriber;

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
        taCalc.setText(" \n \n ");
        tgSymbol.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue!=null)
                secondOperand.append(((ToggleButton)newValue).getText());
            else
                secondOperand.append(((ToggleButton)oldValue).getText());
            changeText();
        });
        tgBinaryOperator.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            Toggle value;
            if (newValue!=null)value=newValue;
            else value=oldValue;
            if (operator.toString().equals(" ")||operator.toString().length()==0) {
                operator=new StringBuilder(((ToggleButton)value).getText());
                firstOperand=new StringBuilder(secondOperand.toString());
                secondOperand=new StringBuilder("");
                changeText();
            }else {
                System.out.println("else");
                applyPreviousFunction();
                operator=new StringBuilder("");
                if (value!=null)
                operator.append(((ToggleButton)value).getText());
                changeText();
            }
        });
        tgUnaryOperator.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (operator.toString().equals(" ")||operator.toString().length()==0) {
                operator= new StringBuilder((((ToggleButton)newValue).getText()));
                firstOperand=new StringBuilder(secondOperand.toString());;
                secondOperand=new StringBuilder("");;
                applyPreviousFunction();
                changeText();
            }else {
                applyPreviousFunction();
                operator=new StringBuilder(((ToggleButton)newValue).getText());
                changeText();
            }
            tgUnaryOperator.getSelectedToggle().setSelected(false);
        });
    }

    private void changeText() {
        String[] array=new String[]{" "," "," "};
        StringBuilder textToSet = new StringBuilder();
        array[0]=selectNumberType(firstOperand);
        array[1]=operator.toString();
        array[2]=selectNumberType(secondOperand);
        for (String anArray : array) {
            textToSet.append(anArray).append("\n");
        }
        taCalc.setText(textToSet.toString());
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
        System.out.println("|"+operator+"|");
        Double result=0.;
        switch (operator.toString().replaceAll(" ","")){
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
        firstOperand=new StringBuilder(result.toString());
        secondOperand=new StringBuilder(" ");
        operator=new StringBuilder(" ");
        changeText();

    }

    @NotNull
    private Double calcPower() {
        return Math.pow(convertToNumber(firstOperand),convertToNumber(secondOperand));
    }

    @NotNull
    private Double calcRoot() {
        return Math.sqrt(convertToNumber(firstOperand));
    }

    @NotNull
    private Double calcPercent() {
        return convertToNumber(firstOperand)/100*convertToNumber(secondOperand);
    }

    @NotNull
    private Double calcFactorial() {
        try {
            Integer integer=Integer.getInteger(firstOperand.toString());
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
        return 1/convertToNumber(firstOperand);
    }

    @NotNull
    private Double calcDiv() {
        return (convertToNumber(firstOperand)/convertToNumber(secondOperand));
    }

    @NotNull
    private Double calcMul() {
        return (convertToNumber(firstOperand)*convertToNumber(secondOperand));
    }

    @NotNull
    private Double calcSub() {
        return (convertToNumber(firstOperand)-convertToNumber(secondOperand));
    }

    @NotNull
    private Double calcSum() {
        return (convertToNumber(firstOperand)+convertToNumber(secondOperand));
    }

    @NotNull
    private Double convertToNumber(StringBuilder operand) {
        try {
            return Double.valueOf(operand.toString());
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
        assert taCalc != null : "fx:id=\"taCalc\" was not injected: check your FXML file 'Form.fxml'.";
        configureControls();
    }

}
