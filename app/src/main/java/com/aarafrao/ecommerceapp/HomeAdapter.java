package com.aarafrao.ecommerceapp;

import android.content.Context;

import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder> {
    Context context;
    ArrayList<ProductModel> list;
    String[] item = {"S", "M", "L"};
    SharedPreferences sharedPreferences;
    ArrayAdapter<String> adapteritem;

    String MyPREFERENCES = "MyPrefs";

    public HomeAdapter(Context context, ArrayList<ProductModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.product_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        sharedPreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        ProductModel pd = list.get(position);
        holder.productname.setText(pd.getProduct_name());
        holder.productdes.setText(pd.getProduct_desc());
        holder.productprice.setText(String.valueOf(pd.getPrice()) + " GBP");
        Picasso.get().load(pd.getUrl()).into(holder.productimg);
        holder.addtocard.setId(pd.getProduct_id());
        holder.addtocard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id = view.getId();
                System.out.println(String.valueOf(id));
                ProductModel pd = list.get(id - 1);
                ArrayList<String> store = new ArrayList<>();
                store.add(String.valueOf(pd.getPrice()));
                store.add(String.valueOf(pd.getProduct_desc()));
                store.add(String.valueOf(pd.getUrl()));
                store.add(String.valueOf(pd.getProduct_name()));

                SharedPreferences.Editor editor = sharedPreferences.edit();
                String value = sharedPreferences.getString(String.valueOf(pd.getProduct_id()), "");
//                sharedPreferences.edit().clear().commit();

                //
                System.out.println("value");
                System.out.println(value);

                if (value.isEmpty()) {

//                   // add insert data
                    System.out.println("new added item");
                    store.add(String.valueOf(1));
                    Gson gson = new Gson();
                    String json = gson.toJson(store);
                    editor.putString(String.valueOf(pd.getProduct_id()), json);
                    editor.apply();


                } else {
                    Type type = new TypeToken<List<String>>() {
                    }.getType();
                    System.out.println("Increase item");
                    Gson gson = new Gson();
                    List<String> arrPackageData = gson.fromJson(value, type);
                    store.add(String.valueOf(Integer.parseInt(arrPackageData.get(4)) + 1));
                    String json = gson.toJson(store);
                    editor.putString(String.valueOf(pd.getProduct_id()), json);
                    editor.apply();
                    System.out.println(arrPackageData);
                }

            }
        });


        adapteritem = new ArrayAdapter<>(context.getApplicationContext(), R.layout.item, item);
        holder.autoCompleteTextView.setAdapter(adapteritem);

        holder.autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String item = adapterView.getItemAtPosition(i).toString();

                System.out.println(String.valueOf(item));
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView productname, productdes, productprice, selectbox;
        ImageView productimg, addtocard;
        AutoCompleteTextView autoCompleteTextView;

        public MyViewHolder(@NonNull View itemview) {
            super(itemview);
            productname = itemview.findViewById(R.id.productName);
            productimg = itemview.findViewById(R.id.productImg);
            productdes = itemview.findViewById(R.id.productDes);
            productprice = itemview.findViewById(R.id.productPrice);
            addtocard = itemview.findViewById(R.id.addToCart);
            autoCompleteTextView = itemview.findViewById(R.id.auto_text);
        }
    }
}
