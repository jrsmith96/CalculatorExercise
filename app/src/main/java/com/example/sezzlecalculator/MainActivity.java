package com.example.sezzlecalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private final static String TAG = "TAG";

    private enum mType {
        NUMBER,
        OPERAND,
        OPEN_PARENTHESIS,
        CLOSED_PARENTHESIS,
        DECIMAL,
        FAILURE
    }

    private int mOpenParenthesis = 0;
    private boolean mEqualClicked = false;
    private String mLastExpression = "";

    private Button mButtonDecimal;

    private TextView mTextViewInputNumbers;
    private ScriptEngine mScriptEngine;

//    private ArrayList<String> mHistory;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDatabase = FirebaseDatabase.getInstance();
        mRef = mDatabase.getReference("history");
        mRef.limitToLast(3);

        // Read from the database
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
//                String value = dataSnapshot.getValue(String.class);
//                Log.d(TAG, "Value is: " + value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        mScriptEngine = new ScriptEngineManager().getEngineByName("rhino");
//        mHistory = new ArrayList<>();

        initializeButtons();
    }

    private void initializeButtons() {

        Button mButton0 = (Button) findViewById(R.id.button_zero);
        mButton0.setOnClickListener(this);

        Button mButton1 = (Button) findViewById(R.id.button_one);
        mButton1.setOnClickListener(this);

        Button mButton2 = (Button) findViewById(R.id.button_two);
        mButton2.setOnClickListener(this);

        Button mButton3 = (Button) findViewById(R.id.button_three);
        mButton3.setOnClickListener(this);

        Button mButton4 = (Button) findViewById(R.id.button_four);
        mButton4.setOnClickListener(this);

        Button mButton5 = (Button) findViewById(R.id.button_five);
        mButton5.setOnClickListener(this);

        Button mButton6 = (Button) findViewById(R.id.button_six);
        mButton6.setOnClickListener(this);

        Button mButton7 = (Button) findViewById(R.id.button_seven);
        mButton7.setOnClickListener(this);

        Button mButton8 = (Button) findViewById(R.id.button_eight);
        mButton8.setOnClickListener(this);

        Button mButton9 = (Button) findViewById(R.id.button_nine);
        mButton9.setOnClickListener(this);

        Button mButtonClear = (Button) findViewById(R.id.button_clear);
        mButtonClear.setOnClickListener(this);

        Button mButtonParentheses = (Button) findViewById(R.id.button_parentheses);
        mButtonParentheses.setOnClickListener(this);

        Button mButtonPercent = (Button) findViewById(R.id.button_percent);
        mButtonPercent.setOnClickListener(this);

        Button mButtonDivision = (Button) findViewById(R.id.button_division);
        mButtonDivision.setOnClickListener(this);

        Button mButtonMultiplication = (Button) findViewById(R.id.button_multiplication);
        mButtonMultiplication.setOnClickListener(this);

        Button mButtonSubtraction = (Button) findViewById(R.id.button_subtraction);
        mButtonSubtraction.setOnClickListener(this);

        Button mButtonAddition = (Button) findViewById(R.id.button_addition);
        mButtonAddition.setOnClickListener(this);

        Button mButtonEquals = (Button) findViewById(R.id.button_equal);
        mButtonEquals.setOnClickListener(this);

        mButtonDecimal = (Button) findViewById(R.id.button_decimal);
        mButtonDecimal.setOnClickListener(this);

        mTextViewInputNumbers = (TextView) findViewById(R.id.textView_input_numbers);
        mTextViewInputNumbers.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_zero:
                if (addNumber("0")) {
                    mEqualClicked = false;
                }
                break;
            case R.id.button_one:
                if (addNumber("1")) {
                    mEqualClicked = false;
                }
                break;
            case R.id.button_two:
                if (addNumber("2")) {
                    mEqualClicked = false;
                }
                break;
            case R.id.button_three:
                if (addNumber("3")) {
                    mEqualClicked = false;
                }
                break;
            case R.id.button_four:
                if (addNumber("4")) {
                    mEqualClicked = false;
                }
                break;
            case R.id.button_five:
                if (addNumber("5")) {
                    mEqualClicked = false;
                }
                break;
            case R.id.button_six:
                if (addNumber("6")) {
                    mEqualClicked = false;
                }
                break;
            case R.id.button_seven:
                if (addNumber("7")) {
                    mEqualClicked = false;
                }
                break;
            case R.id.button_eight:
                if (addNumber("8")) {
                    mEqualClicked = false;
                }
                break;
            case R.id.button_nine:
                if (addNumber("9")) {
                    mEqualClicked = false;
                }
                break;
            case R.id.button_addition:
                if (addOperand("+")) {
                    mEqualClicked = false;
                }
                break;
            case R.id.button_subtraction:
                if (addOperand("-")) {
                    mEqualClicked = false;
                }
                break;
            case R.id.button_multiplication:
                if (addOperand("x")) {
                    mEqualClicked = false;
                }
                break;
            case R.id.button_division:
                if (addOperand("\u00F7")) {
                    mEqualClicked = false;
                }
                break;
            case R.id.button_percent:
                if (addOperand("%")) {
                    mEqualClicked = false;
                }
                break;
            case R.id.button_decimal:
                if (addDecimal()) {
                    mEqualClicked = false;
                }
                break;
            case R.id.button_parentheses:
                if (addParenthesis()) {
                    mEqualClicked = false;
                }
                break;
            case R.id.button_clear:
                mTextViewInputNumbers.setText("");
                mOpenParenthesis = 0;
                mEqualClicked = false;
                break;
            case R.id.button_equal:
                if (!mTextViewInputNumbers.getText().toString().equals(""))
                    calculate(mTextViewInputNumbers.getText().toString());
                break;

        }
    }

    private boolean addDecimal() {
        boolean done = false;
        CharSequence input = mTextViewInputNumbers.getText();

        if (input.length() == 0) {
            mTextViewInputNumbers.setText("0.");
            done = true;
        } else if (defineLastCharacter(input.charAt(input.length() - 1) + "") == mType.OPERAND) {
            mTextViewInputNumbers.setText(getString(R.string.zero_decimal_string, input));
            done = true;
        } else if (defineLastCharacter(input.charAt(input.length() - 1) + "") == mType.NUMBER) {
            mTextViewInputNumbers.setText(getString(R.string.decimal_string, input));
            done = true;
        }
        return done;
    }

    private boolean addParenthesis() {
        boolean done = false;
        int operationLength = mTextViewInputNumbers.getText().length();
        CharSequence input = mTextViewInputNumbers.getText();

        if (operationLength == 0) {
            mTextViewInputNumbers.setText(getString(R.string.open_parenthesis_string, input));
            mOpenParenthesis++;
            done = true;
        } else if (mOpenParenthesis > 0 && operationLength > 0) {
            String lastInput = mTextViewInputNumbers.getText().charAt(operationLength - 1) + "";
            switch (defineLastCharacter(lastInput)) {
                case NUMBER:
                case CLOSED_PARENTHESIS:
                    mTextViewInputNumbers.setText(getString(R.string.close_parenthesis_string, input));
                    done = true;
                    mOpenParenthesis--;
                    break;
                case OPERAND:
                case OPEN_PARENTHESIS:
                    mTextViewInputNumbers.setText(getString(R.string.open_parenthesis_string, input));
                    done = true;
                    mOpenParenthesis++;
                    break;
            }
        } else if (mOpenParenthesis == 0 && operationLength > 0) {
            String lastInput = mTextViewInputNumbers.getText().charAt(operationLength - 1) + "";
            mTextViewInputNumbers.setText(defineLastCharacter(lastInput) == mType.OPERAND
                    ? getString(R.string.open_parenthesis_string, input)
                    : getString(R.string.openx_parenthesis_string, input));
            done = true;
            mOpenParenthesis++;
        }
        return done;
    }

    private boolean addOperand(String operand) {
        boolean done = false;
        CharSequence input = mTextViewInputNumbers.getText();
        int operationLength = input.length();
        if (operationLength > 0) {
            String lastInput = input.charAt(operationLength - 1) + "";

            if ((lastInput.equals("+") || lastInput.equals("-") || lastInput.equals("*") || lastInput.equals("\u00F7") || lastInput.equals("%"))) {
                Toast.makeText(getApplicationContext(), "Wrong format", Toast.LENGTH_LONG).show();
            } else if (!operand.equals("%") || defineLastCharacter(lastInput) == mType.NUMBER) {
                mTextViewInputNumbers.setText(getString(R.string.generic_concatenated_string, input, operand));
                mEqualClicked = false;
                mLastExpression = "";
                done = true;
            }
        } else {
            Toast.makeText(getApplicationContext(), "Wrong Format. Operand Without any numbers?", Toast.LENGTH_LONG).show();
        }
        return done;
    }

    private boolean addNumber(String number) {
        boolean done = false;
        int operationLength = mTextViewInputNumbers.getText().length();
        CharSequence input = mTextViewInputNumbers.getText();

        if (operationLength > 0) {
            String lastCharacter = input.charAt(operationLength - 1) + "";
            mType lastCharacterState = defineLastCharacter(lastCharacter);

            if (operationLength == 1 && lastCharacterState == mType.NUMBER && lastCharacter.equals("0")) {
                mTextViewInputNumbers.setText(number);
                done = true;
            } else if (lastCharacterState == mType.OPEN_PARENTHESIS) {
                mTextViewInputNumbers.setText(getString(R.string.generic_concatenated_string, input, number));
                done = true;
            } else if (lastCharacterState == mType.CLOSED_PARENTHESIS || lastCharacter.equals("%")) {
                mTextViewInputNumbers.setText(getString(R.string.generic_concatenated_string_2, input, number));
                done = true;
            } else if (lastCharacterState == mType.NUMBER || lastCharacterState == mType.OPERAND || lastCharacterState == mType.DECIMAL) {
                mTextViewInputNumbers.setText(getString(R.string.generic_concatenated_string, input, number));
                done = true;
            }
        } else {
            mTextViewInputNumbers.setText(getString(R.string.generic_concatenated_string, input, number));
            done = true;
        }
        return done;
    }

    private void calculate(String input) {
        String result;
        String temp;
        try {
            temp = input;
            if (mEqualClicked) {
                temp = input + mLastExpression;
            } else {
                saveLastExpression(input);
            }
            result = mScriptEngine.eval(temp.replaceAll("%", "/100").replaceAll("x", "*").replaceAll("[^\\x00-\\x7F]", "/")).toString();
            BigDecimal decimal = new BigDecimal(result);
            result = decimal.setScale(8, BigDecimal.ROUND_HALF_UP).toPlainString();
            mEqualClicked = true;

        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Wrong Format", Toast.LENGTH_SHORT).show();
            return;
        }

        if (result.equals("Infinity")) {
            Toast.makeText(getApplicationContext(), "Division by zero is not allowed", Toast.LENGTH_SHORT).show();
            mTextViewInputNumbers.setText(input);

        } else if (result.contains(".")) {
            result = result.replaceAll("\\.?0*$", "");
            mTextViewInputNumbers.setText(result);
            Input finalResult = new Input(temp + "=" + result);
            mRef.child(getFormattedTimestamp(new Timestamp(System.currentTimeMillis()))).setValue(finalResult);
        }
    }

    private void saveLastExpression(String input) {
        String lastOfExpression = input.charAt(input.length() - 1) + "";
        if (input.length() > 1) {
            if (lastOfExpression.equals(")")) {
                mLastExpression = ")";
                int numberOfCloseParenthesis = 1;

                for (int i = input.length() - 2; i >= 0; i--) {
                    if (numberOfCloseParenthesis > 0) {
                        String last = input.charAt(i) + "";
                        if (last.equals(")")) {
                            numberOfCloseParenthesis++;
                        } else if (last.equals("(")) {
                            numberOfCloseParenthesis--;
                        }
                        mLastExpression = getString(R.string.generic_concatenated_string, last, mLastExpression);
                    } else if (defineLastCharacter(input.charAt(i) + "") == mType.OPERAND) {
                        mLastExpression = input.charAt(i) + mLastExpression;
                        break;
                    } else {
                        mLastExpression = "";
                    }
                }
            } else if (defineLastCharacter(lastOfExpression + "") == mType.NUMBER) {
                mLastExpression = lastOfExpression;
                for (int i = input.length() - 2; i >= 0; i--) {
                    String last = input.charAt(i) + "";
                    if (defineLastCharacter(last) == mType.NUMBER || defineLastCharacter(last) == mType.DECIMAL) {
                        mLastExpression = getString(R.string.generic_concatenated_string, last, mLastExpression);
                    } else if (defineLastCharacter(last) == mType.OPERAND) {
                        mLastExpression = getString(R.string.generic_concatenated_string, last, mLastExpression);
                        break;
                    }
                    if (i == 0) {
                        mLastExpression = "";
                    }
                }
            }
        }
    }

    private mType defineLastCharacter(String lastCharacter) {
        try {
            Integer.parseInt(lastCharacter);
            return mType.NUMBER;
        } catch (NumberFormatException ignored) {
        }

        if ((lastCharacter.equals("+") || lastCharacter.equals("-") || lastCharacter.equals("x")
                || lastCharacter.equals("\u00F7")
                || lastCharacter.equals("%"))) {
            return mType.OPERAND;
        }

        if (lastCharacter.equals("(")) {
            return mType.OPEN_PARENTHESIS;
        }

        if (lastCharacter.equals(")")) {
            return mType.CLOSED_PARENTHESIS;
        }

        if (lastCharacter.equals(".")) {
            return mType.DECIMAL;
        }

        return mType.FAILURE;
    }

    private String getFormattedTimestamp(Timestamp timestamp) {
        return String.valueOf(timestamp).replaceAll("\\.", ":");
    }

    private void appendToHistory(String result) {
        // Removes last item from the list
//        if (mHistory.size() == 10) {
//            mHistory.remove(mHistory.size() - 1);
//        }
//        mHistory.add(0, result);
    }
}