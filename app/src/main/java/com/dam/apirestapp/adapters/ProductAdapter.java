package com.dam.apirestapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.dam.apirestapp.R;
import com.dam.apirestapp.models.Product;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    public interface OnProductActionListener {
        void onEdit(Product product);
        void onDelete(Product product);
    }

    private List<Product> productList;
    private OnProductActionListener actionListener;

    public ProductAdapter(List<Product> productList) {
        this.productList = productList;
    }

    public ProductAdapter(List<Product> productList, OnProductActionListener actionListener) {
        this.productList = productList;
        this.actionListener = actionListener;
    }

    public void setOnProductActionListener(OnProductActionListener listener) {
        this.actionListener = listener;
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

        holder.btnEdit.setOnClickListener(v -> {
            if (actionListener != null) {
                actionListener.onEdit(product);
            }
        });

        holder.btnDelete.setOnClickListener(v -> {
            if (actionListener != null) {
                actionListener.onDelete(product);
            }
        });
    }

    @Override
    public int getItemCount() {
        return productList != null ? productList.size() : 0;
    }

    static class ProductViewHolder extends RecyclerView.ViewHolder {
        TextView tvCode;
        TextView tvName;
        TextView tvStatus;
        ImageButton btnEdit;
        ImageButton btnDelete;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCode = itemView.findViewById(R.id.tvProductCode);
            tvName = itemView.findViewById(R.id.tvProductName);
            tvStatus = itemView.findViewById(R.id.tvProductStatus);
            btnEdit = itemView.findViewById(R.id.btnEditProduct);
            btnDelete = itemView.findViewById(R.id.btnDeleteProduct);
        }
    }
}
