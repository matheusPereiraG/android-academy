package com.example.android.t05b02concurrenttasks.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Adapter;
import android.widget.ProgressBar;

import com.example.android.t05b02concurrenttasks.R;
import com.example.android.t05b02concurrenttasks.adapter.ProductAdapter;
import com.example.android.t05b02concurrenttasks.model.Product;
import com.example.android.t05b02concurrenttasks.viewmodel.MainActivityViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ProgressBar mProgressBar;
    private RecyclerView mProductView;
    private RecyclerView.Adapter mProductAdapter;

    private MainActivityViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mProgressBar = findViewById(R.id.pb_products);
        mProductView = findViewById(R.id.rv_products);

        mViewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);
        mViewModel.init();

        mViewModel.getProducts().observe(this, products -> {
            //TODO: better to only notify the item that changed
            mProductAdapter.notifyDataSetChanged();
        });

        initRecyclerView();
    }

    private void initRecyclerView() {
        mProductAdapter = new ProductAdapter(this, mViewModel.getProducts().getValue());
        mProductView.setAdapter(mProductAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mProductView.setLayoutManager(layoutManager);
    }


}

