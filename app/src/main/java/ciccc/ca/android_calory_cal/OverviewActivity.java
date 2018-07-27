package ciccc.ca.android_calory_cal;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import ciccc.ca.android_calory_cal.R;
import im.dacer.androidcharts.PieHelper;
import im.dacer.androidcharts.PieView;



public class OverviewActivity extends AppCompatActivity {
    TextView basicCalories_tv;
    TextView gotCalories;
    TextView lostCalories;

    TextView charGotCalories_tv;
    Intent intent;
    DatabaseReference ref_overview;
    DatabaseReference ref_getStarted;

     int sumOfEatCal = 0;
     int sumofMoveCal = 0;
     int currentCalories = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);
        final PieView pieView = findViewById(R.id.pie_view);
        final ArrayList<PieHelper> pieHelperArrayList = new ArrayList<>();

        FirebaseDatabase database = FirebaseDatabase.getInstance();

        ref_getStarted = database.getReference("basicInfo");
        ref_getStarted.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                GetStartModel getStartModel = dataSnapshot.getValue(GetStartModel.class);
                if (getStartModel != null) {
                    currentCalories = getStartModel.getCurrentCalories();

                }
                basicCalories_tv = findViewById(R.id.basicCalories);
                basicCalories_tv.setText(String.valueOf(currentCalories));


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        ref_overview = database.getReference("overview");
        ref_overview.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Overview overview = dataSnapshot.getValue(Overview.class);
                sumOfEatCal = overview.getSumOfEatCal();
                sumofMoveCal = overview.getSumOfMoveCal() * -1;

                if (sumOfEatCal !=0 && sumofMoveCal !=0 &&currentCalories != 0) {
                    System.out.println("current : " +currentCalories);
                    System.out.println("eat cal : " + sumOfEatCal);
                    System.out.println("move cal : " + sumofMoveCal);

                    gotCalories = findViewById(R.id.gotCalories);
                    lostCalories = findViewById(R.id.lostCalories);
                    charGotCalories_tv = findViewById(R.id.chartGotCalories_tv);

                    gotCalories.setText(String.valueOf(sumOfEatCal));
                    lostCalories.setText(String.valueOf(sumofMoveCal));
                    charGotCalories_tv.setText(String.valueOf((currentCalories + sumofMoveCal - sumOfEatCal)));

                    //gragh

                    //to percentage
                    int percentageOfBurn = 100 * (sumOfEatCal - sumofMoveCal)/currentCalories ;
                    int percentageOfLeft = 100 * (sumofMoveCal) / currentCalories;
//                    int percentageOfLeft = 100 * (currentCalories- sumOfEatCal) / currentCalories;
                    System.out.println(percentageOfBurn);

                    pieHelperArrayList.add(new PieHelper(percentageOfBurn, Color.rgb(78, 186, 106)));
                    pieHelperArrayList.add(new PieHelper(100-percentageOfBurn, Color.rgb(74, 141, 181)));
//                    pieHelperArrayList.add(new PieHelper(percentageOfLeft, Color.BLUE));

                    pieView.setDate(pieHelperArrayList);
                    pieView.showPercentLabel(false); //optional

                }else {
                    Toast.makeText(OverviewActivity.this,"Please record today's activity",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                if(databaseError != null) {
                    Toast.makeText(OverviewActivity.this,databaseError.getMessage(),Toast.LENGTH_LONG).show();
                }
            }


        });






//        Intent intent = getIntent();
//        if(intent.getStringExtra("sumOfCalories") != null) {
//            String sumOfCalories = intent.getStringExtra("sumOfCalories");
//            String sumOfMoveCal = intent.getStringExtra("sumOfMoveCal");
//            String sumOfEatCal = intent.getStringExtra("sumOfEatCal");
//
//            System.out.println("sumOfCalories :" + sumOfCalories);
//            System.out.println("sumOfMoveCal :" + sumOfMoveCal);
//            System.out.println("sumOfEatCal :" + sumOfEatCal);
//
//            //to percentage
//            int percentageOfEat = 100 * Integer.valueOf(sumOfEatCal) /
//                    (Integer.valueOf(sumOfEatCal) + Integer.valueOf(sumOfMoveCal));
//            int percentageOfMove = 100 * Integer.valueOf(sumOfMoveCal) /
//                    (Integer.valueOf(sumOfEatCal) + Integer.valueOf(sumOfMoveCal));
//
//            System.out.println(percentageOfEat);
//            System.out.println(percentageOfMove);
//
//            pieHelperArrayList.add(new PieHelper(percentageOfEat, Color.RED));
//            pieHelperArrayList.add(new PieHelper(100 - percentageOfEat, Color.BLUE));
//
//
//            pieView.setDate(pieHelperArrayList);
////        pieView.selectedPie(2); //optional
////        pieView.setOnPieClickListener(listener) //optional
//            pieView.showPercentLabel(true); //optional
//
//            gotCalories = findViewById(R.id.gotCalories);
//            lostCalories = findViewById(R.id.lostCalories);
//
//            gotCalories.setText(sumOfEatCal);
//            lostCalories.setText(sumOfMoveCal);
//
//        }
    }

    public void goToRecord(View view) {
        intent = new Intent(this, EatActivity.class);
        startActivity(intent);
    }

    public void goToHistory(View view) {
        intent = new Intent(this, HistoryController.class);
        startActivity(intent);
    }
}
