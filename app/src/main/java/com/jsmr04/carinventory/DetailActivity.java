package com.jsmr04.carinventory;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.jsmr04.carinventory.model.Car;
import com.jsmr04.carinventory.model.DatabaseHelper;

import java.text.DecimalFormat;

public class DetailActivity extends AppCompatActivity {
    Toolbar toolbar;
    TextView nameTextView;
    TextView priceTextView;
    TextView modelTextView;
    TextView yearTextView;
    TextView colorTextView;
    TextView vinTextView;
    ImageView imageView;

    DatabaseHelper databaseHelper;
    Car car;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        //Setting up toolbar
        toolbar = (Toolbar)findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("");

        nameTextView = findViewById(R.id.detail_name_textView);
        priceTextView = findViewById(R.id.detail_price_textView);
        modelTextView = findViewById(R.id.detail_model_textView);
        yearTextView = findViewById(R.id.detail_year_textView);
        colorTextView = findViewById(R.id.detail_color_textView);
        vinTextView = findViewById(R.id.detail_vin_textView);
        imageView = findViewById(R.id.detail_image_imageView);

        databaseHelper = new DatabaseHelper(this);

        //Getting extras
        Bundle bundle = getIntent().getExtras();

        if (bundle != null){
            String carName = bundle.getString("CAR");

            car = databaseHelper.getCarByPrimaryKey(carName);

            DecimalFormat decimalFormat = new DecimalFormat("$#,##0.00");

            nameTextView.setText(car.getName());
            priceTextView.setText(decimalFormat.format(car.getPrice()));
            modelTextView.setText(car.getModel());
            yearTextView.setText(String.valueOf(car.getYear()));
            colorTextView.setText(car.getColor());
            vinTextView.setText(car.getVin());

            if (car.getImage().length() > 0){
                imageView.setImageBitmap(Common.stringToBitMap(car.getImage()));
            }
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
