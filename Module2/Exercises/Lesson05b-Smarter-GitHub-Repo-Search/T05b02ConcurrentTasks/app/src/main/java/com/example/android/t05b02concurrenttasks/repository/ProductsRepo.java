package com.example.android.t05b02concurrenttasks.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.android.t05b02concurrenttasks.model.Product;

import java.util.ArrayList;
import java.util.List;

/**
 * This is where we do the async task to provide the viewModel with data, therefore
 * the view model provides the activity or fragment with data
 */
public class ProductsRepo {
    private static ProductsRepo instance;
    private ArrayList<Product> products = new ArrayList<>();

    public static ProductsRepo getInstance(){
        if(instance == null)
            instance = new ProductsRepo();

        return instance;
    }

    /**
     * Pretend we are getting items from a webserver of some sort
     */
    public MutableLiveData<List<Product>> getProducts(){
        initProducts();
        MutableLiveData<List<Product>> data = new MutableLiveData<>();
        data.setValue(products);
        return data;
    }

    private void initProducts(){
        products.add(new Product("Apple", 12.3));
        products.add(new Product("Peach", 63));
        products.add(new Product("Orange", 45));
        products.add(new Product("asdasdasd", 12.3));
        products.add(new Product("produtofixe", 2));
        products.add(new Product("produtomenosfixe", 30));
        products.add(new Product("aqueleproduto", 69));
        products.add(new Product("nowwetalkin", 12));
    }
}
