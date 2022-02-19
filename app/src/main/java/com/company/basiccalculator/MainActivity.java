package com.company.basiccalculator;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    //variable to handle numbers clicked
    String number = null;

    //variables to handle operations of two numbers
    double firstNumber = 0;
    double lastNumber = 0;

    //operations to note what operations are pressed
    String status = null;
    boolean operator = false;

    //variable formatter to control decimal initially mentioned in double value
    DecimalFormat myformatter = new DecimalFormat("######.######");

    //variable to control history and current result of calculator
    String currentResult, history;

    //to take care of false dot expression or multiple dots
    boolean dot = true;

    //variable to control AC and DEl button and avoid app close
    boolean btnACcontrol = true;

    //btn equal control
    boolean btnEqualsControl = false;

    private Button btn0, btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9,
            btnAC, btnDel, btnPlus, btnMinus, btnDivide, btnEquals, btnMulti, btnDot;
    private TextView textViewResult, textViewHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn0 = findViewById(R.id.btn0);
        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        btn3 = findViewById(R.id.btn3);
        btn4 = findViewById(R.id.btn4);
        btn5 = findViewById(R.id.btn5);
        btn6 = findViewById(R.id.btn6);
        btn7 = findViewById(R.id.btn7);
        btn8 = findViewById(R.id.btn8);
        btn9 = findViewById(R.id.btn9);
        btnAC = findViewById(R.id.btnAC);
        btnDel = findViewById(R.id.btnDel);

        btnPlus = findViewById(R.id.btnPlus);
        btnMinus = findViewById(R.id.btnMinus);
        btnMulti = findViewById(R.id.btnMulti);
        btnDivide = findViewById(R.id.btnDiv);
        btnEquals = findViewById(R.id.btnEquals);
        btnDot = findViewById(R.id.btnDot);

        textViewHistory = findViewById(R.id.textviewHistory);
        textViewResult = findViewById(R.id.textViewResult);


        //all button click events here
        btn0.setOnClickListener(v -> numberClicked("0"));

        btn1.setOnClickListener(v -> numberClicked("1"));
        btn2.setOnClickListener(v -> numberClicked("2"));
        btn3.setOnClickListener(v -> numberClicked("3"));

        btn4.setOnClickListener(v -> numberClicked("4"));

        btn5.setOnClickListener(v -> numberClicked("5"));

        btn6.setOnClickListener(v -> numberClicked("6"));

        btn7.setOnClickListener(v -> numberClicked("7"));
        btn8.setOnClickListener(v -> numberClicked("8"));
        btn9.setOnClickListener(v -> numberClicked("9"));

        btnDot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dot) {
                    if (number == null) {
                        number = "0.";
                    } else {

                        number = number + ".";
                    }


                }


                textViewResult.setText(number);
                dot = false;

            }
        });
        btnAC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                number = null;
                status = null;
                firstNumber = 0;
                lastNumber = 0;
                textViewResult.setText("0");
                textViewHistory.setText("");
                dot = true;
                btnACcontrol = true;


            }
        });
        btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (btnACcontrol) {

                    textViewResult.setText("0");
                } else {
                    number = number.substring(0, number.length() - 1);
                    //here we check if length of result is zero we make button disable
                    if (number.length() == 0) {
                        btnDel.setClickable(false);
                    } else if (number.contains(".")) {
                        dot = false;
                    } else {
                        dot = true;
                    }
                    textViewResult.setText(number);

                }


            }
        });
        btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //building history and showing on calculator
                history = textViewHistory.getText().toString();
                currentResult = textViewResult.getText().toString();
                textViewHistory.setText(history + currentResult + "+");


                if (operator) {

                    if (status == "multiplication") {
                        multiply();
                    } else if (status == "division") {
                        divide();
                    } else if (status == "subtraction") {
                        minus();
                    } else {
                        plus();
                    }

                }
                status = "sum";
                operator = false;
                number = null;
            }
        });
        btnMinus.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //building history with minus sign
                history = textViewHistory.getText().toString();
                currentResult = textViewResult.getText().toString();
                textViewHistory.setText(history + currentResult + "-");

                if (operator) {
                    if (status == "multiplication") {
                        multiply();
                    } else if (status == "division") {
                        divide();
                    } else if (status == "sum") {
                        plus();
                    } else {
                        minus();
                    }


                }
                status = "subtraction";
                operator = false;
                number = null;

            }
        });
        btnMulti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                history = textViewHistory.getText().toString();
                currentResult = textViewResult.getText().toString();
                textViewHistory.setText(history + currentResult + "*");

                if (operator) {
                    if (status == "sum") {
                        plus();
                    } else if (status == "division") {

                    } else if (status == "subtraction") {
                        minus();
                    } else {
                        multiply();
                    }
                }


                status = "multiplication";
                operator = false;
                number = null;
            }

        });
        btnDivide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                history = textViewHistory.getText().toString();
                currentResult = textViewResult.getText().toString();
                textViewHistory.setText(history + currentResult + "/");

                if (operator) {
                    if (status == "sum") {
                        plus();
                    } else if (status == "subtraction") {
                        minus();
                    } else if (status == "multiplication") {
                        multiply();
                    } else {
                        divide();
                    }
                    status = "division";
                    operator = false;
                    number = null;

                }

            }
        });
        btnEquals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (operator) {
                    if (status == "sum") {
                        plus();
                    } else if (status == "multiplication") {
                        multiply();
                    } else if (status == "subtraction") {
                        minus();
                    } else if (status == "division") {
                        divide();

                    } else {
                        firstNumber = Double.parseDouble(textViewResult.getText().toString());
                    }
                }
                operator = false;

                //if button equals is pressed
                btnEqualsControl = true;
            }
        });


    }

    public void numberClicked(String view) {

        if (number == null) {

            number = view;
        } else if (btnEqualsControl) {

            firstNumber = 0;
            lastNumber = 0;
            number = view;
        } else {

            number = number + view;
        }
        textViewResult.setText(number);
        operator = true;
        //here we handle if number is clicked, then AC
        // should be true and delete should be clickable
        btnACcontrol = false;
        btnDel.setClickable(true);
        //btnEqual set false
        btnEqualsControl = false;

    }

    public void plus() {
        lastNumber = Double.parseDouble(textViewResult.getText().toString());
        firstNumber = firstNumber + lastNumber;

        textViewResult.setText(myformatter.format(firstNumber));
        dot = true;

    }

    public void minus() {
        if (firstNumber == 0) {
            firstNumber = Double.parseDouble(textViewResult.getText().toString());
        } else {
            lastNumber = Double.parseDouble(textViewResult.getText().toString());
            firstNumber = firstNumber - lastNumber;

        }
        textViewResult.setText(myformatter.format(firstNumber));
        dot = true;

    }

    public void multiply() {
        if (firstNumber == 0) {
            firstNumber = 1;
        }
        lastNumber = Double.parseDouble(textViewResult.getText().toString());
        firstNumber = firstNumber * lastNumber;
        textViewResult.setText(myformatter.format(firstNumber));
        dot = true;
    }

    public void divide() {
        if (firstNumber == 0) {
            firstNumber = 1;
            lastNumber = Double.parseDouble(textViewResult.getText().toString());
            firstNumber = lastNumber / 1;
        } else {

            lastNumber = Double.parseDouble(textViewResult.getText().toString());
            firstNumber = firstNumber / lastNumber;
        }
        textViewResult.setText(myformatter.format(firstNumber));
        dot = true;

    }

}