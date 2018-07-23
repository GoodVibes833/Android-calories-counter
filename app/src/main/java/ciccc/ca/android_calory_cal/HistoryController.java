package ciccc.ca.android_calory_cal;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.DatePicker;
import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.builders.DatePickerBuilder;
import com.applandeo.materialcalendarview.exceptions.OutOfDateRangeException;
import com.applandeo.materialcalendarview.listeners.OnDayClickListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class HistoryController extends AppCompatActivity {
    ListView historyListView;
    ArrayAdapter<History> adapter;
    private ArrayList<History> historyArrayList;
    Intent intent;
    private DatabaseReference ref_history;

    Date today = new Date();
    private String TAG = "HistoryController";


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
                adapter = new ArrayAdapter<>(HistoryController.this, android.R.layout.simple_list_item_1,historyArrayList);
                historyListView.setAdapter(adapter);

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


//      https://github.com/Applandeo/Material-Calendar-View
//      https://github.com/snollidea/peppy-calendarview
    public void chooseDate(View view) throws OutOfDateRangeException {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.activity_history_calendar,null);
        builder.setView(dialogView);

        //adding images
        List<EventDay> events = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        events.add(new EventDay(calendar, R.drawable.popup_green));


        final CalendarView calendarView = dialogView.findViewById(R.id.calendarView);
        calendarView.setEvents(events);

        //setting current date
        calendar.set(2018, 6, 20);
        calendarView.setDate(calendar);

         final AlertDialog alertDialog = builder.create();
        alertDialog.show();





        calendarView.setOnDayClickListener(new OnDayClickListener() {
            @Override
            public void onDayClick(EventDay eventDay) {



                Calendar clickedDayCalendar = eventDay.getCalendar();
                TextView textView= findViewById(R.id.selectedDate);
                final String selectedDate = String.valueOf(clickedDayCalendar.get(Calendar.YEAR))
                        +'-'+   String.valueOf(clickedDayCalendar.get(Calendar.MONTH)+1)
                        +'-'+   String.valueOf(clickedDayCalendar.get(Calendar.DATE));
                textView.setText(selectedDate);

                alertDialog.dismiss();

                // Read from the database
                ref_history.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        historyArrayList.clear();
                        for(DataSnapshot historySnapShot: dataSnapshot.child(selectedDate).getChildren()) {
                            History history = historySnapShot.getValue(History.class);
                            historyArrayList.add(history);

                        }
                        adapter = new ArrayAdapter<>(HistoryController.this, android.R.layout.simple_list_item_1,historyArrayList);
                        historyListView.setAdapter(adapter);

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
        });








    }
}
