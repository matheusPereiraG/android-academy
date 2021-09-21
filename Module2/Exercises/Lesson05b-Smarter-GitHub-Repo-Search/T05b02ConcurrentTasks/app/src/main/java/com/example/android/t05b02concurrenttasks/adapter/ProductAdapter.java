package com.example.android.t05b02concurrenttasks.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.t05b02concurrenttasks.R;
import com.example.android.t05b02concurrenttasks.model.Product;
import com.example.android.t05b02concurrenttasks.view.MainActivity;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductViewHolder> {
    private Context context;
    private List<Product> products;

    public ProductAdapter(Context context, List<Product> products) {
        this.context = context;
        this.products = products;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product_item, parent, false);

        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        holder.getProductView().setText(products.get(position).toString());
    }

    @Override
    public int getItemCount() {
        return products.size();
    }
}

//for each item
class ProductViewHolder extends RecyclerView.ViewHolder {
    private TextView productView;
    public ProductViewHolder(@NonNull View itemView) {
        super(itemView);
        productView = (TextView) itemView.findViewById(R.id.tv_product_item);
    }

    public TextView getProductView() {
        return productView;
    }
}
