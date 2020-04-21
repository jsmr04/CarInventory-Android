package com.jsmr04.carinventory;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.jsmr04.carinventory.model.Car;
import com.jsmr04.carinventory.model.DatabaseHelper;
import com.jsmr04.carinventory.model.SampleData;
import com.jsmr04.carinventory.model.User;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    String TAG = "CarInventory";
    Toolbar toolbar;
    RecyclerView recyclerView;
    FloatingActionButton fab;
    RecyclerView.LayoutManager layoutManager;
    CarAdapter adapter;
    ArrayList<Car> cars;
    DatabaseHelper databaseHelper;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Setting up toolbar
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(getString(R.string.main_title));

        //Getting list of cars from SQLite
        databaseHelper = new DatabaseHelper(this);
        cars = databaseHelper.getCarList();

        //Setting up Recycler View
        recyclerView = findViewById(R.id.cars_recyclerView);
        layoutManager = new LinearLayoutManager(this);
        adapter = new CarAdapter(cars);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        new ItemTouchHelper(simpleCallback).attachToRecyclerView(recyclerView);

        int resId = R.anim.layout_animation_fall_down;
        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(this, resId);
        recyclerView.setLayoutAnimation(animation);

        //Button
        fab = findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addCar();
            }
        });

        Bundle bundle = getIntent().getExtras();

        if (bundle != null){
            user = (User) bundle.getSerializable("USER");
            Log.d(TAG, "onCreate: " + user.toString());
            if (user != null){
                if (!user.getType().equals(User.USER_TYPE_MANAGER)){
                    fab.setVisibility(View.INVISIBLE);
                    fab.setEnabled(false);
                }
            }

        }

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(this, recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                showDetail(position);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        cars = databaseHelper.getCarList();
        adapter.setDataset(cars);
        adapter.notifyDataSetChanged();
    }

    private void showDetail(int position) {
        Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
        intent.putExtra("CAR", cars.get(position).getName());
        startActivity(intent);
    }

    private void addCar() {
        Intent intent = new Intent(getApplicationContext(), AddCarActivity.class);
        startActivity(intent);
    }

    private void removeCar(final int index){

        DialogInterface.OnClickListener yesClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                databaseHelper.deleteCarByPrimaryKey(cars.get(index));
                cars.remove(index);
                adapter.notifyDataSetChanged();
                Toast.makeText(getApplicationContext(), "Car removed.", Toast.LENGTH_SHORT).show();
            }
        };

        DialogInterface.OnClickListener noClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                adapter.notifyDataSetChanged();
            }
        };

        Common.showMessage(this, "Car", "Do you want to remove this car?", yesClickListener, noClickListener);
    }

    private void runLayoutAnimation() {
        final Context context = recyclerView.getContext();
        final LayoutAnimationController controller =
                AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation_fall_down);

        recyclerView.setLayoutAnimation(controller);
        recyclerView.getAdapter().notifyDataSetChanged();
        recyclerView.scheduleLayoutAnimation();
    }

     ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
         @Override
         public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
             return false;
         }

         @Override
         public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
             if (user.getType().equals(User.USER_TYPE_MANAGER)){
                 removeCar(viewHolder.getAdapterPosition());
             }
             adapter.notifyDataSetChanged();
         }
     };

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
