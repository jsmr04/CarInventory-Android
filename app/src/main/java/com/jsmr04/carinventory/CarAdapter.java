package com.jsmr04.carinventory;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.jsmr04.carinventory.model.Car;

import java.text.DecimalFormat;
import java.util.ArrayList;


public class CarAdapter extends RecyclerView.Adapter<CarAdapter.ViewHolder> {
    private ArrayList<Car> dataset;
    private Context context;

    public CarAdapter(ArrayList<Car> dataset){
        this.dataset =  dataset;
    }

    public void setDataset(ArrayList<Car> dataset) {
        this.dataset = dataset;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView nameTextView;
        TextView yearTextView;
        TextView modelTextView;
        ImageView imageView;
        TextView priceTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            nameTextView = itemView.findViewById(R.id.main_name_textView);
            yearTextView = itemView.findViewById(R.id.main_year_textView);
            modelTextView = itemView.findViewById(R.id.main_model_textView);
            imageView = itemView.findViewById(R.id.main_image_imageView);
            priceTextView = itemView.findViewById(R.id.main_price_textView);
        }
    }

    @NonNull
    @Override
    public CarAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.car_row, parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CarAdapter.ViewHolder holder, int position) {
        holder.nameTextView.setText(dataset.get(position).getName());
        holder.yearTextView.setText(String.valueOf(dataset.get(position).getYear()));
        holder.modelTextView.setText(dataset.get(position).getModel());
        DecimalFormat decimalFormat = new DecimalFormat("$#,##0.00");
        holder.priceTextView.setText(decimalFormat.format(dataset.get(position).getPrice()));

        if(dataset.get(position).getImage().trim().length() > 0){
           holder.imageView.setImageBitmap(Common.stringToBitMap(dataset.get(position).getImage()));
        }
    }

    @Override
    public int getItemCount() {
        return this.dataset.size();
    }
}
