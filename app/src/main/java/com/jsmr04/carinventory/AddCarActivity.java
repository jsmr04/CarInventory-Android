package com.jsmr04.carinventory;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;


import com.jsmr04.carinventory.model.Car;
import com.jsmr04.carinventory.model.DatabaseHelper;

import java.util.ArrayList;

public class AddCarActivity extends AppCompatActivity {
    int REQUEST_IMAGE_CAPTURE = 1234;
    ImageView imageView;
    Spinner modelSpinner;
    Spinner yearSpinner;
    EditText nameEditText;
    EditText priceEditText;
    EditText colorEditText;
    EditText vinEditText;
    Button button;
    Toolbar toolbar;
    String selectedImage = "";

    String[] models = {"Honda Accord", "Honda Civic", "Honda Fit", "Toyota Corolla", "Toyota Camry"};
    ArrayList<String> years = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_car);

        imageView = findViewById(R.id.add_imageView);
        modelSpinner = findViewById(R.id.add_model_spinner);
        yearSpinner = findViewById(R.id.add_year_spinner);
        nameEditText = findViewById(R.id.add_name_editText);
        priceEditText = findViewById(R.id.add_price_textInput_editText);
        colorEditText = findViewById(R.id.add_color_editText);
        vinEditText = findViewById(R.id.add_vin_editText);
        button = findViewById(R.id.add_save_button);
        toolbar = findViewById(R.id.add_toolbar);

        //Setting up Action Bar
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Add New Car");

        //Setting up Spinners
        fillModels();
        fillYears();

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkFields()) {
                    saveCar();
                }
            }
        });

    }

    private void saveCar() {
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        Car car = new Car();

        car.setName(nameEditText.getText().toString().trim());
        car.setPrice(Float.parseFloat(priceEditText.getText().toString().trim()));
        car.setColor(colorEditText.getText().toString().trim());
        car.setVin(vinEditText.getText().toString().trim());
        car.setModel(modelSpinner.getSelectedItem().toString());
        car.setYear(Integer.parseInt(yearSpinner.getSelectedItem().toString()));
        car.setImage(selectedImage);

        databaseHelper.insertCar(car);

        DialogInterface.OnClickListener onClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        };

        Common.showMessage(this, "Usuario", "Data saved.", onClickListener);
    }

    private void selectImage() {
        Intent takeImageIntent = ImagePicker.getPickImageIntent(this);
        if (takeImageIntent.resolveActivity(this.getPackageManager()) != null) {
            startActivityForResult(takeImageIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    private void fillModels(){
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, models);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        modelSpinner.setAdapter(adapter);
    }

    private boolean checkFields(){

        if (nameEditText.getText().toString().trim().length() == 0
        || colorEditText.getText().toString().trim().length() == 0
        || vinEditText.getText().toString().trim().length() == 0
        || priceEditText.getText().toString().trim().length() == 0){
            Common.showMessage(this, "Add Car", "Complete required fields.", null );
            return false;
        }

        if (!Common.isFloat(priceEditText.getText().toString())){
            Common.showMessage(this, "Add Car", "Price does not have a valid format.", null );
            return false;
        }

        return true;

    }

    private void fillYears(){
        for (int i = 2000; i <= 2021; i++){
            years.add(String.valueOf(i));
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, years);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        yearSpinner.setAdapter(adapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap bitmap = ImagePicker.getBitmapFromResult(this, resultCode, data);
        if (null != bitmap && resultCode == RESULT_OK) {
            imageView.setImageBitmap(bitmap);
            selectedImage = Common.bitMapToString(bitmap);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
