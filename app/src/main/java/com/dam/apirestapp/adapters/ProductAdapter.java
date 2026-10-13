package com.dam.apirestapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.dam.apirestapp.R;
import com.dam.apirestapp.models.Product;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private List<Product> productList;

    public ProductAdapter(List<Product> productList) {
        this.productList = productList;
    }

    public void updateList(List<Product> newList) {
        this.productList = newList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = productList.get(position);
        holder.tvCode.setText(product.getCode() != null ? String.valueOf(product.getCode()) : "-");
        holder.tvName.setText(product.getName() != null ? product.getName() : "Sin nombre");

        boolean isActive = product.getStatus() != null && product.getStatus();
        if (isActive) {
            holder.tvStatus.setText("Estado: Activo");
            holder.tvStatus.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.status_green));
        } else {
            holder.tvStatus.setText("Estado: Inactivo");
            holder.tvStatus.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.status_red));
        }
    }

    @Override
    public int getItemCount() {
        return productList != null ? productList.size() : 0;
    }

    static class ProductViewHolder extends RecyclerView.ViewHolder {
        TextView tvCode;
        TextView tvName;
        TextView tvStatus;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCode = itemView.findViewById(R.id.tvProductCode);
            tvName = itemView.findViewById(R.id.tvProductName);
            tvStatus = itemView.findViewById(R.id.tvProductStatus);
        }
    }
}
