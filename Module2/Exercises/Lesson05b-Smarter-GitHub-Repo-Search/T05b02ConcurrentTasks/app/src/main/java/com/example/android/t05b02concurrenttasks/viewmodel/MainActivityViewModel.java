package com.example.android.t05b02concurrenttasks.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.android.t05b02concurrenttasks.model.Product;
import com.example.android.t05b02concurrenttasks.repository.ProductsRepo;

import java.util.List;

public class MainActivityViewModel extends ViewModel {
    private MutableLiveData<List<Product>> mProducts;
    private ProductsRepo productRepository;

    public void init(){
        if(mProducts == null) {
            productRepository = ProductsRepo.getInstance();
            mProducts = productRepository.getProducts();
        }

    }

    public LiveData<List<Product>> getProducts(){
        return mProducts;
    }
}
