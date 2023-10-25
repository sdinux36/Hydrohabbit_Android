package com.example.calculator;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class WaterIntakeCalculatorActivity extends AppCompatActivity {

    private EditText ageEditText;
    private RadioButton maleRadioButton, femaleRadioButton;
    private EditText feetEditText, inchesEditText;
    private EditText weightEditText;
    private Spinner weightUnitSpinner, activityLevelSpinner;

    private Spinner climateSpinner;  // Declare the climateSpinner

    private Button calculateButton;
    private TextView recommendedIntakeValue;
    private Button homeButton, nextButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ageEditText = findViewById(R.id.ageEditText);
        maleRadioButton = findViewById(R.id.maleRadioButton);
        femaleRadioButton = findViewById(R.id.femaleRadioButton);
        feetEditText = findViewById(R.id.feetEditText);
        inchesEditText = findViewById(R.id.inchesEditText);
        weightEditText = findViewById(R.id.weightEditText);
        weightUnitSpinner = findViewById(R.id.weightUnitSpinner);
        activityLevelSpinner = findViewById(R.id.activityLevelSpinner);
        climateSpinner = findViewById(R.id.climateSpinner);  // Initialize the climateSpinner
        calculateButton = findViewById(R.id.calculateButton);
        recommendedIntakeValue = findViewById(R.id.recommendedIntakeValue);

        homeButton = findViewById(R.id.homeButton); // Assign the home button
        nextButton = findViewById(R.id.nextButton);

        // Populate weight unit spinner
        ArrayAdapter<CharSequence> weightUnitAdapter = ArrayAdapter.createFromResource(
                this, R.array.weight_units, android.R.layout.simple_spinner_item
        );
        weightUnitAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        weightUnitSpinner.setAdapter(weightUnitAdapter);

        // Populate activity level spinner
        ArrayAdapter<CharSequence> activityLevelAdapter = ArrayAdapter.createFromResource(
                this, R.array.activity_levels, android.R.layout.simple_spinner_item
        );
        activityLevelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        activityLevelSpinner.setAdapter(activityLevelAdapter);

        // Populate climate spinner
        ArrayAdapter<CharSequence> climateAdapter = ArrayAdapter.createFromResource(
                this, R.array.climate_factors, android.R.layout.simple_spinner_item
        );
        climateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        climateSpinner.setAdapter(climateAdapter);



        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Calculate recommended water intake here
                calculateRecommendedWaterIntake();
            }
        });

homeButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(WaterIntakeCalculatorActivity.this, DummyActivity.class);
            startActivity(intent);
        }
    });

        nextButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(WaterIntakeCalculatorActivity.this, DummyActivity.class);
            startActivity(intent);
        }
    });
}


    private void calculateRecommendedWaterIntake() {
        // Retrieve user inputs
        int age = Integer.parseInt(ageEditText.getText().toString());
        int heightFeet = Integer.parseInt(feetEditText.getText().toString());
        int heightInches = Integer.parseInt(inchesEditText.getText().toString());
        double weight = Double.parseDouble(weightEditText.getText().toString());
        String weightUnit = weightUnitSpinner.getSelectedItem().toString();
        double activityFactor = getActivityFactor();
        String climateFactor = climateSpinner.getSelectedItem().toString();


        // Calculate TDEE
        double tdee = calculateTDEE(age, heightFeet, heightInches, weight, weightUnit, activityFactor);

        // Adjust TDEE based on climate factor
        double climateFactorMultiplier = getClimateFactorMultiplier(climateFactor);
        tdee *= climateFactorMultiplier;

        // Convert TDEE to recommended water intake (ml)
        double recommendedWaterIntake = tdee;

        // Display the recommended water intake
        recommendedIntakeValue.setText(String.format("%.2f ml", recommendedWaterIntake));
    }

    private double getActivityFactor() {
        String selectedActivity = activityLevelSpinner.getSelectedItem().toString();
        switch (selectedActivity) {
            case "Sedentary (little to no exercise)":
                return 1.2;
            case "Lightly Active (light exercise 1-3 days / week)":
                return 1.375;
            case "Moderately Active (exercise 3-5 days / week)":
                return 1.55;
            case "Very Active (exercise 6-7 days / week)":
                return 1.725;
            case "Extremely Active (very heavy exercise / day)":
                return 1.9;
            default:
                return 1.0; // Default to sedentary
        }
    }

    private double calculateTDEE(int age, int heightFeet, int heightInches, double weight, String weightUnit, double activityFactor) {
        double heightInCm = ((heightFeet * 12) + heightInches) * 2.54;
        double weightInKg = (weightUnit.equals("Kg")) ? weight : weight * 0.453592;

        if (maleRadioButton.isChecked()) {
            return ((10 * weightInKg) + (6.25 * heightInCm) - (5 * age) + 5) * activityFactor;
        } else if (femaleRadioButton.isChecked()) {
            return ((10 * weightInKg) + (6.25 * heightInCm) - (5 * age) - 161) * activityFactor;
        } else {
            // Handle this case gracefully, e.g., by displaying an error message.
            return 0;
        }
    }
    private double getClimateFactorMultiplier(String climateFactor) {
        // Add logic to determine the multiplier based on climate
        switch (climateFactor) {
            case "Hot and Dry":
                return 1.2;
            case "Moderate":
                return 1.0; // No adjustment for moderate climate
            // Add more cases for other climate factors as needed
            default:
                return 1.0; // Default to no adjustment
        }
    }}
