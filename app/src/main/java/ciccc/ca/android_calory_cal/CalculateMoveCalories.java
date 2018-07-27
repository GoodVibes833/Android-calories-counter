package ciccc.ca.android_calory_cal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CalculateMoveCalories extends AppCompatActivity {
    TextView clickedItem;
    TextView gram;
    TextView tatalcalories;
    Intent intent;
    String clickedItemCalories;
    String clickedItemExercise;
    private DatabaseReference ref_history;
    List<History> historyArrayList;
    ListView historyListView;
    Date today = new Date();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculate_move_calories);

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
        clickedItemExercise = intent.getStringExtra("exercise");
        clickedItem.setText("("+ clickedItemExercise + ")"+ clickedItemCalories);
        System.out.println("ex : " + clickedItemExercise);
        System.out.println("cal : " + clickedItemCalories);
    }


    public void calculateTotal(View view) {
        String total = String.valueOf(Integer.valueOf(String.valueOf(gram.getText())) * Integer.valueOf(clickedItemCalories));
        tatalcalories.setText(total);
    }


    public void addToHistory(View view) {
        historyArrayList = new ArrayList<>();
        //DB
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        ref_history = database.getReference("history");

        //VIEW
        historyListView = findViewById(R.id.historyListView);
        final String date = today.getYear()+1900 + "-" + (1+today.getMonth()) + "-" + today.getDate();
        intent = getIntent();


        //from exercise
        String totalCalories = intent.getStringExtra("calories");
        String total = String.valueOf(Integer.valueOf(String.valueOf(gram.getText())) * Integer.valueOf(clickedItemCalories));
        String move = intent.getStringExtra("exercise");
        String id = ref_history.push().getKey();

        //set data
        History history = new History(id, date, "MOVE : " + move, total);
        ref_history.child(date).child(id).setValue(history);



    Intent intent = new Intent(this, HistoryController.class);
//        String total = String.valueOf(Integer.valueOf(String.valueOf(gram.getText())) * Integer.valueOf(clickedItemCalories));
//        intent.putExtra("totalCalories", total);
//        intent.putExtra("exercise", clickedItemExercise);
        startActivity(intent);

    }

    public void goToHistory(View view) {
        intent = new Intent(this, HistoryController.class);
        startActivity(intent);
    }

    public void goToOverview(View view) {
        intent = new Intent(this, OverviewActivity.class);
        startActivity(intent);
    }

    public void goToRecord(View view) {
        intent = new Intent(this, EatActivity.class);
        startActivity(intent);
    }
}
