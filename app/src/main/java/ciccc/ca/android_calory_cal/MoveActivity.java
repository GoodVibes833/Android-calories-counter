package ciccc.ca.android_calory_cal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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

public class MoveActivity extends AppCompatActivity {
    SearchView searchView;
    ListView listView;
    ArrayAdapter<Move> adapter;

    ArrayList<Move> list;
    Intent intent;
    private DatabaseReference ref_move;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_move);

//        searchView = findViewById(R.id.searchView);
        listView = findViewById(R.id.listView);
        list = new ArrayList<>();


        // 1. read text file
//        inputStream = getResources().openRawResource(R.raw.exercise_calories);
//        bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
//        MoveArray = new ArrayList<>();
//
//        try {
//            String line;
//            while (true) {
//                line = (bufferedReader.readLine());
//                if (line == null)
//                    break;
//                MoveArray.add(line);
//
//            }
//            for (int i = 1; i < MoveArray.size()/5-2; i++) {
//                list.add(new Move(MoveArray.get(5*i), '-' + MoveArray.get(5*i+1)));
//            }
//
//            bufferedReader.close();
//            inputStream.close();
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }


        //read from DB
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        ref_move = database.getReference("DB_CaloriesInfo");

        ref_move.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                list.clear();
                for(DataSnapshot historySnapShot: dataSnapshot.child("move").getChildren()) {
                    Move move = historySnapShot.getValue(Move.class);
                    list.add(move);
                }
                // listView
                adapter = new ArrayAdapter<>(MoveActivity.this, android.R.layout.simple_list_item_1,list);
                listView.setAdapter(adapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                if(databaseError != null) {
                    Toast.makeText(MoveActivity.this,databaseError.getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        });

        //write to DB
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        ref_move = database.getReference("DB_CaloriesInfo");
//        for (int i =0; i<list.size(); i++){
//            String id = ref_move.push().getKey();
//
//            ref_move.child("move").child(id).setValue(list.get(i));
//        }




        //searchView
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                if(list.contains(query)){
//                    adapter.getFilter().filter(query);
//
//                }else{
//                    Toast.makeText(MoveActivity.this, "No Match found",Toast.LENGTH_LONG).show();
//                }
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                adapter.getFilter().filter(newText);
//                return false;
//            }
//        });


        //short click
        intent = new Intent(this, CalculateMoveCalories.class);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // list - everything
                // after filter - refresh list or create another list with searched items.
                System.out.println("position : " + position);
                System.out.println("id : " + id);
                Move move = list.get(position);

                intent.putExtra("exercise", move.getExercises());
                intent.putExtra("calories", move.getCalories());
                startActivity(intent);


            }
        });

        // tyep eat_gram -> calculate calories
}


    public void goToRecordEat(View view) {
        intent = new Intent(this, EatActivity.class);
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


    public void MoveInput(View view) {
        intent = new Intent(this, InputMove.class);
        startActivity(intent);
    }
}
