package org.example;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Calculator {

    @FXML private Button button_1, button_2, button_3, button_4, button_5, button_6, button_7, button_8, button_9, button_0, button_plus, button_minus, button_times, button_divide, button_mc, button_mplus, button_period, button_equals;
    @FXML TextField textfield;
    private String equation = "";

    public Calculator(){

    }

    public void initialize(){

        button_mc.setOnAction(e -> memory_clear());
        button_plus.setOnAction(e -> plus());
        button_minus.setOnAction(e -> minus());
        button_times.setOnAction(e -> times());
        button_divide.setOnAction(e -> divide());
        button_equals.setOnAction(e -> equals());


        try{
            for (int i = 1; i <= 9; i++) {
                Button btn = (Button) getClass().getDeclaredField("button_" + i).get(this);
                btn.setUserData(i);
                btn.setOnAction(e -> numButton(btn));
            }

        } catch (NoSuchFieldException | IllegalAccessException e){
            System.out.print(e);
        }
    }

    public void memory_clear(){
        equation = "";
    }

    public void numButton(Button buttonClicked){
        equation = equation.concat("" + buttonClicked.getUserData());
        updateTextField();
    }

    public void plus(){
        if(!equation.endsWith("+")){
            if(!(Character.isDigit(equation.charAt(equation.length()-1)))){
                equation = equation.substring(0,equation.length() - 1) + "+";

            } else {
                equation = equation.concat("+");
            }
            updateTextField();
        }
    }

    public void minus(){
        if(!equation.endsWith("-")){
            if(!(Character.isDigit(equation.charAt(equation.length()-1)))){
                equation = equation.substring(0,equation.length() - 1) + "-";

            } else {
                equation = equation.concat("-");
            }
            updateTextField();
        }
    }

    public void times(){
        if(!equation.endsWith("*")){
            if(!(Character.isDigit(equation.charAt(equation.length()-1)))){
                equation = equation.substring(0,equation.length() - 1) + "*";

            } else {
                equation = equation.concat("*");
            }
            updateTextField();
        }
    }

    public void divide(){
        if(!equation.endsWith("/")){
            if(!(Character.isDigit(equation.charAt(equation.length()-1)))){
                equation = equation.substring(0,equation.length() - 1) + "/";

            } else {
                equation = equation.concat("/");
            }
            updateTextField();
        }
    }

    public void equals(){
        evaluate();
        updateTextField();
    }

    public void updateTextField(){

        textfield.setText(equation);
    }

    public void evaluate() {

        ArrayList<Integer> nums = new ArrayList<>();
        ArrayList<Character> operators = new ArrayList<>();

        StringBuilder number = new StringBuilder();

        for (char c : equation.toCharArray()) {
            if (Character.isDigit(c)) {
                number.append(c);
            } else {
                if (number.length() > 0) {
                    nums.add(Integer.parseInt(number.toString()));
                    number.setLength(0);
                }
                operators.add(c);
            }
        }
        if (number.length() > 0) {
            nums.add(Integer.parseInt(number.toString()));
        }

        for (int i = 0; i < operators.size(); ) {
            char op = operators.get(i);
            if (op == '*' || op == '/') {
                int a = nums.get(i);
                int b = nums.get(i + 1);
                int result = (op == '*') ? a * b : a / b;

                nums.set(i, result);
                nums.remove(i + 1);
                operators.remove(i);
            } else {
                i++;
            }
        }

        int result = nums.get(0);
        for (int i = 0; i < operators.size(); i++) {
            char op = operators.get(i);
            int next = nums.get(i + 1);

            if (op == '+') result += next;
            else if (op == '-') result -= next;
        }

        textfield.setText("" + result);
        equation = "" +result;
    }
}
