package ciccc.ca.android_calory_cal;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
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
    private DatabaseReference ref_overview;
    private DatabaseReference ref_basicInfo;
    Date today = new Date();
    private String TAG = "HistoryController";
    static int sumOfCalories;
    static int sumOfMoveCal;
    static int sumOfEatCal;
    TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
//
        textView = findViewById(R.id.selectedDate);


        historyArrayList = new ArrayList<>();
//        //DB
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        ref_history = database.getReference("history");
        ref_overview = database.getReference("overview");
        ref_basicInfo = database.getReference("basicInfo");
//
//        //VIEW
        historyListView = findViewById(R.id.historyListView);

        final String date = today.getYear()+1900 + "-" + (1+today.getMonth()) + "-" + today.getDate();

        // Read from the database
        readFromTheDatabase(date);




    }


    //      https://github.com/Applandeo/Material-Calendar-View
    public void chooseDate(View view) throws OutOfDateRangeException {



        // calender library
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.activity_history_calendar,null);
        builder.setView(dialogView);

        //adding images
        List<EventDay> events = new ArrayList<>();
        final Calendar calendar = Calendar.getInstance();
        events.add(new EventDay(calendar, R.drawable.img_success));

        final CalendarView calendarView = dialogView.findViewById(R.id.calendarView);
        calendarView.setEvents(events);


        //calendar icon
        ref_basicInfo.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                GetStartModel getStartModel = dataSnapshot.getValue(GetStartModel.class);
                if (getStartModel != null) {
                    int currentWeight = getStartModel.getCurrentWeight();
                    int targetWeight = getStartModel.getTargetWeight();

                    if(currentWeight>targetWeight && sumOfCalories >=0){
                        //setting current date
                        calendar.set(2018, 6, 26);
                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        //setting current date
        calendar.set(2018, 6, 20);

        calendarView.setDate(calendar);

         final AlertDialog alertDialog = builder.create();
         alertDialog.show();
        calendarView.setOnDayClickListener(new OnDayClickListener() {
            @Override
            public void onDayClick(EventDay eventDay) {



                Calendar clickedDayCalendar = eventDay.getCalendar();
                String selectedDate = String.valueOf(clickedDayCalendar.get(Calendar.YEAR))
                        +'-'+   String.valueOf(clickedDayCalendar.get(Calendar.MONTH)+1)
                        +'-'+   String.valueOf(clickedDayCalendar.get(Calendar.DATE));
                textView.setText(selectedDate);
                alertDialog.dismiss();

                // Read from the database
                readFromTheDatabase(selectedDate);
            }
        });



    }

    private void readFromTheDatabase(final String date) {

        ref_history.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot dataSnapshot) {
                historyArrayList.clear();
                for(DataSnapshot historySnapShot: dataSnapshot.child(date).getChildren()) {
                    History history = historySnapShot.getValue(History.class);
                    historyArrayList.add(history);
                }
                adapter = new ArrayAdapter<>(HistoryController.this, android.R.layout.simple_list_item_1,historyArrayList);
                historyListView.setAdapter(adapter);

                //short onclick
                historyListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long idd) {
                        //dialog -> delete

                        History history = historyArrayList.get(position);
                        final String id = history.getId();

                        //1. build dialog with custome layout
                        // inflate custom view -> set it on builder
                        AlertDialog.Builder builder = new AlertDialog.Builder(HistoryController.this);

                        LayoutInflater inflater = getLayoutInflater();
                        View dialogView = inflater.inflate(R.layout.dialog_history_delete,null);
                        builder.setView(dialogView);


                        //                        builder.setTitle("delete?");
                        final AlertDialog alertDialog = builder.create();
                        alertDialog.show();


                        Button delete_btn = dialogView.findViewById(R.id.dialog_delete);
                        delete_btn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                ref_history.child(date).child(id).removeValue();
                                alertDialog.dismiss();
                            }
                        });

                        Button cancel_btn = dialogView.findViewById(R.id.dialog_cancel);
                        cancel_btn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                alertDialog.dismiss();
                            }
                        });



                    }
                });


                //find the amount of calories and set text and send to overview
                 sumOfCalories = 0;
                 sumOfMoveCal = 0;
                 sumOfEatCal = 0;

                for (int i =0; i < historyArrayList.size(); i++){
                    sumOfCalories += Integer.valueOf(historyArrayList.get(i).getTotalCalories());
                    if(Integer.valueOf(historyArrayList.get(i).getTotalCalories())>0){
                        sumOfEatCal += Integer.valueOf(historyArrayList.get(i).getTotalCalories());
                    }else{
                        sumOfMoveCal += Integer.valueOf(historyArrayList.get(i).getTotalCalories());
                    }
                }
                // calculate the sum of calories
//                TextView sum = findViewById(R.id.sumOfCalories);
//                sum.setText(String.valueOf(sumOfCalories));

                System.out.println(sumOfCalories);
                System.out.println(sumOfEatCal);
                System.out.println(sumOfMoveCal);

                Overview overview = new Overview(sumOfMoveCal,sumOfEatCal);
                ref_overview.setValue(overview);
                textView.setText(date);

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


    public void goToOverview(View view) {
        intent = new Intent(this, OverviewActivity.class);
//        intent.putExtra("sumOfCalories",String.valueOf(sumOfCalories));
//        intent.putExtra("sumOfMoveCal",String.valueOf(sumOfMoveCal * -1));
//        intent.putExtra("sumOfEatCal",String.valueOf(sumOfEatCal));

        startActivity(intent);
    }
}
