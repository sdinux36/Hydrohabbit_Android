/*IM-2020-078-P.S.R.LIYANAGE */
package com.example.pro1;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    double totalIntakeInML = 0.0;

    // Define conversion factors
    double litersToMilliliters = 1000.0;
    double glassesToMilliliters = 250.0; // Assuming 1 glass = 250 mL

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) Spinner goalUnitSpinner = findViewById(R.id.goalUnitSpinner);
        Spinner intakeUnitSpinner = findViewById(R.id.intakeUnitSpinner);

        // Set up the unit spinners
        setupUnitSpinner(goalUnitSpinner);
        setupUnitSpinner(intakeUnitSpinner);

        Button addButton = findViewById(R.id.addIntake);
        addButton.setOnClickListener(v -> addWaterIntake());

        Button resetButton = findViewById(R.id.resetIntake);
        resetButton.setOnClickListener(v -> resetIntake());

        EditText goalEditText = findViewById(R.id.goalEditText);
        goalEditText.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {}

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                calculateProgress();
         }
});

    }

    private void setupUnitSpinner(Spinner spinner) {
        String[] units = {"L", "mL", "Glass"};
        ArrayAdapter<String> unitAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, units);
        unitAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(unitAdapter);
    }

    public void resetIntake(){
        totalIntakeInML = 0.0;
        calculateProgress();
    }
    public void addWaterIntake() {
        Spinner intakeUnitSpinner = findViewById(R.id.intakeUnitSpinner);
        EditText intakeEditText = findViewById(R.id.intakeEditText);
        String unit = intakeUnitSpinner.getSelectedItem().toString();
        if (!intakeEditText.getText().toString().isEmpty()) {
            Double userEnteredValue = Double.parseDouble(intakeEditText.getText().toString());
            Double currentIntakeValueML = 0.0;
            if (unit.equals("L")) currentIntakeValueML = userEnteredValue * litersToMilliliters;
            if (unit.equals("mL")) currentIntakeValueML = userEnteredValue;
            if (unit.equals("Glass")) currentIntakeValueML = userEnteredValue * glassesToMilliliters;
            totalIntakeInML += currentIntakeValueML;
        }
        calculateProgress();

    }

    @SuppressLint("DefaultLocale")
    private void calculateProgress() {
        Spinner goalUnitSpinner = findViewById(R.id.goalUnitSpinner);
        EditText goalEditText = findViewById(R.id.goalEditText);
        String unit = goalUnitSpinner.getSelectedItem().toString();
        Double goalValueInML = 0.0;
        if (!goalEditText.getText().toString().isEmpty()) {
            Double userEnteredValue = Double.parseDouble(goalEditText.getText().toString());
            if (unit.equals("L")) goalValueInML = userEnteredValue * litersToMilliliters;
            if (unit.equals("mL")) goalValueInML = userEnteredValue;
            if (unit.equals("Glasses")) goalValueInML = userEnteredValue * glassesToMilliliters;
        }


        // Calculate the progress as a percentage
        double progressPercentage = (totalIntakeInML / goalValueInML) * 100.0;

        //  display the progress percentage in a TextView
        TextView progressTextView = findViewById(R.id.progressTextView);
        progressTextView.setText(String.format("%.2f%%", progressPercentage));



        TextView totalWaterIntakeTextView = findViewById(R.id.totalWaterIntake);
        totalWaterIntakeTextView.setText(String.valueOf(totalIntakeInML/litersToMilliliters));

    }
}

/*IM-2020-078-P.S.R.LIYANAGE*/