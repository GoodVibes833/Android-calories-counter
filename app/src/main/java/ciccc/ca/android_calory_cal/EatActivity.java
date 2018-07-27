package ciccc.ca.android_calory_cal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class EatActivity extends AppCompatActivity {
    SearchView searchView;
    ListView listView;
    ArrayAdapter<Eat> adapter;
    ArrayAdapter<Eat> adapter2;
    TextView eat_item;
    private InputStream inputStream;
    private BufferedReader bufferedReader;
    private ArrayList<String> foodArray;
    ArrayList<Eat> list;
    ArrayList<Eat> list2;
    Intent intent;
    private DatabaseReference ref_eat;
    private DatabaseReference ref_basicInfo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eat);

//        searchView = findViewById(R.id.searchView);
        listView = findViewById(R.id.listView);
        list = new ArrayList<>();


        // 1. read text file -> from DB
//
//        inputStream = getResources().openRawResource(R.raw.food_calories);
//        bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
//        foodArray = new ArrayList<>();
//        list2 = new ArrayList<>();
//
//        try {
//            String line;
//            while (true) {
//                line = (bufferedReader.readLine());
//                if (line == null)
//                    break;
//                foodArray.add(line);
//
//            }
//            for (int i = 1; i < foodArray.size()/4; i++) {
//                list.add(new Eat(foodArray.get(4*i), foodArray.get(2+4*i)));
//                list2.add(new Eat(foodArray.get(4*i), foodArray.get(2+4*i)));
//            }
//
//            bufferedReader.close();
//            inputStream.close();
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        //read from DB
        //read from DB


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        ref_eat = database.getReference("DB_CaloriesInfo");
        ref_eat.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                list.clear();
                for(DataSnapshot historySnapShot: dataSnapshot.child("eat").getChildren()) {
                    Eat eat = historySnapShot.getValue(Eat.class);
                    list.add(eat);
                }
                // listView
                adapter = new ArrayAdapter<>(EatActivity.this, android.R.layout.simple_list_item_1,list);
                listView.setAdapter(adapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                if(databaseError != null) {
                    Toast.makeText(EatActivity.this,databaseError.getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        });

        ref_basicInfo = database.getReference("basicInfo");
        ref_basicInfo.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                GetStartModel getStartModel = dataSnapshot.getValue(GetStartModel.class);
                if (getStartModel == null) {
                    Intent intent = new Intent(EatActivity.this, GetStart.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //write to DB
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        ref_eat = database.getReference("DB_CaloriesInfo");
//
//        for (int i =0; i<list.size(); i++){
//            String id = ref_eat.push().getKey();
//            ref_eat.child("eat").child(id).setValue(list.get(i));
//        }


        // listView
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,list);
        listView.setAdapter(adapter);

//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                if(list.contains(query)){
//                    adapter.getFilter().filter(query);
//                }else{
//                    Toast.makeText(EatActivity.this, "No Match found",Toast.LENGTH_LONG).show();
//                }
//                return false;
//            }
//
//        @Override
//        public boolean onQueryTextChange(String newText) {
//                adapter.getFilter().filter(newText);
//            return false;
//        }
//    });


        //short click
        intent = new Intent(this, CalculateEatCalories.class);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // list - everything
                // after filter - refresh list or create another list with searched items.
                System.out.println("position : " + position);
                System.out.println("id : " + id);
                Eat eat = list.get(position);

                intent.putExtra("food", eat.getFood());
                intent.putExtra("calories", eat.getCalories());
                startActivity(intent);


            }
        });

        // tyep eat_gram -> calculate calories
}


    public void goToRecordEatInput(View view) {
        Intent intent = new Intent(this, InputEat.class);
        startActivity(intent);
    }

    public void goToOverview(View view) {
        intent = new Intent(this, OverviewActivity.class);
        startActivity(intent);
    }

    public void goToHistory(View view) {
        intent = new Intent(this, HistoryController.class);
        startActivity(intent);

    }

    public void goToRecordMove(View view) {
        intent = new Intent(this, MoveActivity.class);
        startActivity(intent);
    }


}
