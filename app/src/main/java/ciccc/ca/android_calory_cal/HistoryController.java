package ciccc.ca.android_calory_cal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;

public class HistoryController extends AppCompatActivity {
    ListView historyListView;
    ArrayAdapter<History> adapter;
    private ArrayList<History> historyArrayList;
    Intent intent;
    private DatabaseReference ref_history;

    Date today = new Date();
    private String TAG= "HistoryController";


    // I need DB to store history

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        historyArrayList = new ArrayList<>();

        //DB
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        ref_history = database.getReference("history");

        //VIEW
        historyListView = findViewById(R.id.historyListView);
        final String date = today.getYear()+1900 + "-" + (1+today.getMonth()) + "-" + today.getDate();
        intent = getIntent();



//        if(!TextUtils.isEmpty(artist_name)) {
//

        // wrire to the DB
            String totalCalories = intent.getStringExtra("totalCalories");
            String item = intent.getStringExtra("food");
            String id = ref_history.push().getKey();


            //set data
            History history = new History(date, item, totalCalories);
            ref_history.child(date).child(id).setValue(history);


        // Read from the database
        ref_history.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                historyArrayList.clear();

                for(DataSnapshot historySnapShot: dataSnapshot.child(date).getChildren()) {
                    History history = historySnapShot.getValue(History.class);
                    historyArrayList.add(history);

                }
//                    adapter.notifyDataSetChanged();
                adapter = new ArrayAdapter<>(HistoryController.this, android.R.layout.simple_list_item_1,historyArrayList);
                historyListView.setAdapter(adapter);



//                Log.d(TAG, "Value is: " + history1);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                if(error != null) {
                    Toast.makeText(HistoryController.this,error.getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        });




    }






    public void goToRecord(View view) {
        intent = new Intent(this, EatActivity.class);
        startActivity(intent);
    }
}
