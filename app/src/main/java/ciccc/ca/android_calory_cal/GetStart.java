package ciccc.ca.android_calory_cal;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class GetStart extends AppCompatActivity {
    EditText tartgetWeight;
    EditText currentWeight;
    TextView calorieTarget;
    TextView calorieCurrent;
    Button calculateDayCalories;
    Button getStarted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_start);

        tartgetWeight = findViewById(R.id.targetWeight);
        currentWeight = findViewById(R.id.currentWeight);
        calorieTarget = findViewById(R.id.calorieTarget);
        calorieCurrent = findViewById(R.id.calorieCurrent);
        calculateDayCalories = findViewById(R.id.calculateDayCal);

    }

    public void calculateDayCal(View view) {
        Double BMICurrent = Integer.valueOf(String.valueOf(currentWeight.getText())) * 9.5 * 24;
        calorieCurrent.setText(String.valueOf(BMICurrent));

        Double BMITarget = Integer.valueOf(String.valueOf(tartgetWeight.getText())) * 9.5 * 24;
        calorieTarget.setText(String.valueOf(BMITarget));

    }

    public void getStarted(View view) {
        //open main screen
    }
}
