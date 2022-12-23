package com.aarafrao.ecommerceapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class myadaptercard extends RecyclerView.Adapter<myadaptercard.MyViewHolder> {
    Context context;
    ArrayList<ProductModel> list;

    public myadaptercard(Context context, ArrayList<ProductModel> list) {
        this.context = context;
        this.list = list;
    }


    @NonNull
    @Override
    public myadaptercard.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cart_item_layout, parent, false);
        return new myadaptercard.MyViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull myadaptercard.MyViewHolder holder, int position) {

        ProductModel pd = list.get(position);
        holder.productname.setText(pd.getProduct_name());
        holder.productdes.setText(pd.getProduct_desc());
        holder.productprice.setText(String.valueOf(pd.getPrice()));
        System.out.println(pd.quantity);

        if (holder.selectbox != null) {
            holder.selectbox.setText(String.valueOf(pd.getQuantity()) + " | S");
        }

        Picasso.get().load(pd.getUrl()).into(holder.productimg);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView productname, productdes, productprice, selectbox, productquanty;
        ImageView productimg, addtocard;
        Button btnDelete;
        AutoCompleteTextView autoCompleteTextView;

        public MyViewHolder(@NonNull View itemview) {
            super(itemview);
            productname = itemview.findViewById(R.id.productName);
            btnDelete = itemview.findViewById(R.id.btnDelete);
            productimg = itemview.findViewById(R.id.productImg);
            productdes = itemview.findViewById(R.id.productDes);
            productprice = itemview.findViewById(R.id.productPrice);
            selectbox = itemview.findViewById(R.id.productquanity);

            btnDelete.setOnClickListener(n -> {
                Toast.makeText(itemview.getContext(), "ItemDeleted", Toast.LENGTH_SHORT).show();
            });
        }
    }
}
