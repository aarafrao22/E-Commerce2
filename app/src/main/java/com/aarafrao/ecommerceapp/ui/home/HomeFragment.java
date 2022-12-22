package com.aarafrao.ecommerceapp.ui.home;

import android.content.SharedPreferences;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aarafrao.ecommerceapp.R;
import com.aarafrao.ecommerceapp.HomeAdapter;
import com.aarafrao.ecommerceapp.ProductModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HomeFragment extends Fragment {


    RecyclerView recyclerView;
    DatabaseReference databaseReference;
    HomeAdapter HomeAdapter;
    ArrayList<ProductModel> list;
    SharedPreferences sharedPreferences;
    String MyPREFERENCES = "MyPrefs";

    @SuppressLint("MissingInflatedId")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);


        recyclerView = view.findViewById(R.id.productlist);
        databaseReference = FirebaseDatabase.getInstance().getReference("product");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        list = new ArrayList<>();
        HomeAdapter = new HomeAdapter(getContext(), list);
        recyclerView.setAdapter(HomeAdapter);

//        ProductModel p = new ProductModel(9, 100, 2,
//                "NewItem", "", "des");
//
//        list.add(p);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    ProductModel pd = dataSnapshot.getValue(ProductModel.class);
                    list.add(pd);
                }
                HomeAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


//
//        autoCompleteTextView = root.findViewById(R.id.auto_text);
//        adapteritem = new ArrayAdapter<>(getContext(),R.layout.item,item);
//        autoCompleteTextView.setAdapter(adapteritem);
//
//        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                String item = adapterView.getItemAtPosition(i).toString();
//                Toast.makeText(getContext(),"item: "+item,Toast.LENGTH_SHORT);
//            }
//        });
        return view;
    }


}