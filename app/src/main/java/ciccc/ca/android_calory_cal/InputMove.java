package ciccc.ca.android_calory_cal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class InputMove extends AppCompatActivity {

    private Intent intent;
    private EditText exerciseName;
    private EditText caloriesLost;
    private DatabaseReference ref_move;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_move);
        exerciseName = findViewById(R.id.exerciseName_et);
        caloriesLost = findViewById(R.id.exerciseCaloriesLost_et);
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

    public void addToList(View view) {
        if(exerciseName != null && caloriesLost != null){
           String newExercise = String.valueOf(exerciseName.getText());
           String newCaloriesLost = '-'+String.valueOf(caloriesLost.getText());


//            write to DB
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        ref_move = database.getReference("DB_CaloriesInfo");
            String id = ref_move.push().getKey();
            Move move = new Move(newExercise,newCaloriesLost);
            ref_move.child("move").child(id).setValue(move);
        }else {
            Toast.makeText(this,"Please input exercise and calories lost",Toast.LENGTH_LONG).show();
        }


    }
}
