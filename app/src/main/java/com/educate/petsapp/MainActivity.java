package com.educate.petsapp;

import androidx.annotation.ContentView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.educate.petsapp.data.PetContract;
import com.educate.petsapp.data.PetDBHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import static com.educate.petsapp.data.PetContract.PetEntry.COLUMN_PET_BREED;
import static com.educate.petsapp.data.PetContract.PetEntry.COLUMN_PET_GENDER;
import static com.educate.petsapp.data.PetContract.PetEntry.COLUMN_PET_NAME;
import static com.educate.petsapp.data.PetContract.PetEntry.COLUMN_PET_WEIGHT;
import static com.educate.petsapp.data.PetContract.PetEntry.GENDER_MALE;
import static com.educate.petsapp.data.PetContract.PetEntry.TABLE_NAME;
import static com.educate.petsapp.data.PetContract.PetEntry._ID;

public class MainActivity extends AppCompatActivity {

    private PetDBHelper dbHelper;
    ListView listPets;
    ArrayList<Pets> data = new ArrayList<>();
    PetsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new PetDBHelper(this);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, EditorActivity.class);
                startActivity(intent);
            }
        });

        displayData();
    }

    @Override
    protected void onStart() {
        super.onStart();
        displayData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_insert) {
            insertPet();
            displayData();
        }
        else if (id == R.id.action_delete_all){
            deleteAllPets();
            displayData();
        }
        return super.onOptionsItemSelected(item);
    }

    // Function atau Method
    void insertPet() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_PET_NAME, "Dede");
        values.put(COLUMN_PET_BREED, "Dog");
        values.put(COLUMN_PET_GENDER, GENDER_MALE);
        values.put(COLUMN_PET_WEIGHT, 7);

        long newId = db.insert(TABLE_NAME, null, values);
        Toast.makeText(this, String.valueOf(newId), Toast.LENGTH_SHORT).show();
    }

    void displayData() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] projection = {
                PetContract.PetEntry._ID,
                COLUMN_PET_NAME,
                COLUMN_PET_BREED,
                COLUMN_PET_GENDER,
                COLUMN_PET_WEIGHT
        };

        Cursor cursor = db.query(
                TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
        );

        int idColumnIndex = cursor.getColumnIndex(_ID);
        int nameColumnIndex = cursor.getColumnIndex(COLUMN_PET_NAME);
        int breedColumnIndex = cursor.getColumnIndex(COLUMN_PET_BREED);
        int genderColumnIndex = cursor.getColumnIndex(COLUMN_PET_GENDER);
        int weightColumnIndex = cursor.getColumnIndex(COLUMN_PET_WEIGHT);

//        textPets.setText("");
        // Ini untuk mengambil data dari kursor
        while (cursor.moveToNext()) {
            int currentId = cursor.getInt(idColumnIndex);
            String currentName = cursor.getString(nameColumnIndex);
            String currentBreed = cursor.getString(breedColumnIndex);
            int currentGender = cursor.getInt(genderColumnIndex);
            int currentWeight = cursor.getInt(weightColumnIndex);

            String currentpet = "" + currentId + " - " + currentName + " - " + currentBreed + " - " + currentGender + " - " + currentWeight + "\n";
//            textPets.setText(textPets.getText().toString() + currentpet);
        }
    }

    void deleteAllPets() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        db.delete(TABLE_NAME, null, null);
        Toast.makeText(this, "All data has been deleted", Toast.LENGTH_SHORT).show();
    }
}
