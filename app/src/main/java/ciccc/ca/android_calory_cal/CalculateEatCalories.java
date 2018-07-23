package ciccc.ca.android_calory_cal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.TextView;

public class CalculateEatCalories extends AppCompatActivity {
    TextView clickedItem;
    TextView gram;
    TextView tatalcalories;
    Intent intent;
    String clickedItemCalories;
    String clickedItemfood;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculate_eat_calories);

        gram = findViewById(R.id.gram);
        tatalcalories = findViewById(R.id.tatalcalories);
        clickedItem = findViewById(R.id.clickedItem);

        gram.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        intent = getIntent();
        clickedItemCalories = intent.getStringExtra("calories");
        clickedItemfood = intent.getStringExtra("food");
        clickedItem.setText("("+ clickedItemfood + ")"+ clickedItemCalories);
    }


    public void calculateTotal(View view) {
        String total = String.valueOf(Integer.valueOf(String.valueOf(gram.getText())) * Integer.valueOf(clickedItemCalories));
        tatalcalories.setText(total);
    }


    public void addToHistory(View view) {
        Intent intent = new Intent(this, HistoryController.class);
        String total = String.valueOf(Integer.valueOf(String.valueOf(gram.getText())) * Integer.valueOf(clickedItemCalories));
        intent.putExtra("totalCalories", total);
        intent.putExtra("food",clickedItemfood);
        startActivity(intent);

    }

}
