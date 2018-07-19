package ciccc.ca.android_calory_cal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

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
    private ArrayList<History> historyArrayList = new ArrayList<>();
    Intent intent;
    private DatabaseReference ref_history;

    Date today = new Date();
    private String TAG= "HistoryController";


    // I need DB to store history

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        //DB
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        ref_history = database.getReference("history");

        //VIEW
        historyListView = findViewById(R.id.historyListView);
        String date = today.getYear()+1900 + "-" + (1+today.getMonth()) + "-" + today.getDate();

        intent = getIntent();



//        if(!TextUtils.isEmpty(artist_name)) {
//
            // 1. generate unique id key.
            String totalCalories = intent.getStringExtra("totalCalories");
            String item = intent.getStringExtra("food");
            String id = ref_history.push().getKey();

            History history = new History(date, item, totalCalories);


            ref_history.child(date).child("EAT" + id).setValue(history);





        // Read from the database
        ref_history.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
//                History history1 = dataSnapshot.getValue(History.class);
//                String string = dataSnapshot.getValue(String.class);
//                historyArrayList.add(history1);
//                System.out.println("reading : " + string);
//            adapter.notifyDataSetChanged();
//                Log.d(TAG, "Value is: " + history1);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,historyArrayList);
        historyListView.setAdapter(adapter);

//        }else{
//            Toast.makeText(this,"Please enter Artist name",Toast.LENGTH_LONG).show();
//        }




    }


    public void goToRecord(View view) {
        intent = new Intent(this, EatActivity.class);
        startActivity(intent);
    }
}
