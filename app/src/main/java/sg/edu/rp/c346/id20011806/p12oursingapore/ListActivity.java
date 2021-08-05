package sg.edu.rp.c346.id20011806.p12oursingapore;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class ListActivity extends AppCompatActivity {

    Button btnFilter, btnInsertDialog;
    ListView lv;
    //Spinner spinnerFilter;
    ArrayList<Song> al;
    //ArrayAdapter<Song> aa;
    ArrayList<Song> alFilter;
    ArrayAdapter<Song> aaFilter;
    CustomAdapter ca;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        btnInsertDialog = findViewById(R.id.buttonInsertDialog);
        btnFilter = findViewById(R.id.btnFilter);
        lv = findViewById(R.id.lv);
        //spinnerFilter = findViewById(R.id.spinner);
        //Song data = al.get(position);
        DBHelper dbh = new DBHelper(ListActivity.this);

        alFilter = new ArrayList<Song>();




        al = new ArrayList<Song>();
        al = dbh.getAllSongs();
        ca = new CustomAdapter(this, R.layout.row, al);
        lv.setAdapter(ca);

        btnInsertDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View viewDialog = inflater.inflate(R.layout.activity_main, null);

                final EditText etInputName = viewDialog.findViewById(R.id.etTitle);
                final EditText etInputDesc = viewDialog.findViewById(R.id.etSingers);
                final EditText etInputArea = viewDialog.findViewById(R.id.etYear);
                final RatingBar inputStars = viewDialog.findViewById(R.id.ratingBarMain);

                AlertDialog.Builder myBuilder = new AlertDialog.Builder(ListActivity.this);
                myBuilder.setView(viewDialog);  // Set the view of the dialog
                myBuilder.setTitle("Insert a New Island");
                myBuilder.setNegativeButton("Insert", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String dataTitle = etInputName.getText().toString();
                        String dataSingers = etInputDesc.getText().toString();
                        int dataYear = Integer.parseInt(etInputArea.getText().toString());
                        int dataStar = Math.round(inputStars.getRating());

                        DBHelper dbh = new DBHelper(ListActivity.this);
                        long inserted_id = dbh.insertSong(dataTitle, dataSingers, dataYear, dataStar);

                        if (inserted_id != -1) {
                            Toast.makeText(ListActivity.this, "Insert successful",
                                    Toast.LENGTH_SHORT).show();
                            al.clear();
                            al.addAll(dbh.getAllSongs());
                            ca.notifyDataSetChanged();
                        }
                    }
                });

                myBuilder.setPositiveButton("Cancel", null);
                AlertDialog myDialog = myBuilder.create();
                myDialog.show();
            }
        });
        //  aa = new ArrayAdapter<Song>(this,
        //         android.R.layout.simple_list_item_1, al);
        //  lv.setAdapter(aa);

        /*//filter enhancement
        ArrayList<String> spinneral = new ArrayList<String>();
        for (int i = 0; i < al.size(); i++) {
            String addyear = String.valueOf(al.get(i).getYear());
            spinneral.add(addyear);
        }
        Set<String> spinnerset = new HashSet<>(spinneral);
        spinneral.clear();
        spinneral.addAll(spinnerset);
        spinneral.add("No Filter");
        Collections.sort(spinneral);
        Collections.reverse(spinneral);
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, spinneral);
        spinnerFilter.setAdapter(spinnerAdapter);

        spinnerFilter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                for (int i = 1; i<=al.size(); i++) {
                    if (position == 0) {
                        lv.setAdapter(ca);
                    } else if (position == i) {
                        alFilter.clear();
                        alFilter = dbh.getAllSongs(Integer.parseInt(spinnerFilter.getSelectedItem().toString()));
                        aaFilter = new CustomAdapter(getApplicationContext(), R.layout.row, alFilter);
                        lv.setAdapter(aaFilter);
                    }
                }}

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });*/

        btnFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper dbh = new DBHelper(ListActivity.this);
                al.clear();
                al.addAll(dbh.getAll5StarSongs());
                ca.notifyDataSetChanged();
            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Song data = al.get(position);
                Intent i = new Intent(ListActivity.this, EditActivity.class);
                i.putExtra("data", data);
                startActivity(i);
            }
        });

    }

    @Override
    protected void onResume() {
        Log.d("MainActivity", "onResume() called.");
        DBHelper dbh = new DBHelper(ListActivity.this);
        al.clear();
        al.addAll(dbh.getAllSongs());
        ca.notifyDataSetChanged();
        super.onResume();

    }
}