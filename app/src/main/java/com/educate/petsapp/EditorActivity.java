package com.educate.petsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.educate.petsapp.data.PetContract;
import com.educate.petsapp.data.PetDBHelper;

import static com.educate.petsapp.data.PetContract.PetEntry.COLUMN_PET_BREED;
import static com.educate.petsapp.data.PetContract.PetEntry.COLUMN_PET_GENDER;
import static com.educate.petsapp.data.PetContract.PetEntry.COLUMN_PET_NAME;
import static com.educate.petsapp.data.PetContract.PetEntry.COLUMN_PET_WEIGHT;
import static com.educate.petsapp.data.PetContract.PetEntry.GENDER_MALE;
import static com.educate.petsapp.data.PetContract.PetEntry.TABLE_NAME;

public class EditorActivity extends AppCompatActivity {

    EditText nameEditText, breedEditText, weightEditText;
    Spinner mGenderSpinner;

    int gender = PetContract.PetEntry.GENDER_UNKNOWN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        nameEditText = findViewById(R.id.edit_pet_name);
        breedEditText = findViewById(R.id.edit_pet_breed);
        weightEditText = findViewById(R.id.edit_pet_weight);
        mGenderSpinner = findViewById(R.id.spinner_gender);
        setupSpinner();
    }

    private void setupSpinner() {
        ArrayAdapter genderSpinnerAdapter = ArrayAdapter.createFromResource(
                this, R.array.array_gender_options, android.R.layout.simple_spinner_item
        );

        genderSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        mGenderSpinner.setAdapter(genderSpinnerAdapter);
        mGenderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);

                if (selection.equals("Male")) {
                    gender = PetContract.PetEntry.GENDER_MALE;
                }
                else if (selection.equals("Female")) {
                    gender = PetContract.PetEntry.GENDER_FEMALE;
                }
                else {
                    gender = PetContract.PetEntry.GENDER_UNKNOWN;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                gender = PetContract.PetEntry.GENDER_UNKNOWN;
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_editor, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_save){
            insertPet();
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    void insertPet() {
        PetDBHelper dbHelper = new PetDBHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String name = nameEditText.getText().toString();
        String breed = breedEditText.getText().toString();
        String weight = weightEditText.getText().toString();
        int weightInt = Integer.parseInt(weight);

        ContentValues values = new ContentValues();
        values.put(COLUMN_PET_NAME, name);
        values.put(COLUMN_PET_BREED, breed);
        values.put(COLUMN_PET_GENDER, gender);
        values.put(COLUMN_PET_WEIGHT, weightInt);

        long newId = db.insert(TABLE_NAME, null, values);
        Toast.makeText(this, String.valueOf(newId), Toast.LENGTH_SHORT).show();
    }
}
