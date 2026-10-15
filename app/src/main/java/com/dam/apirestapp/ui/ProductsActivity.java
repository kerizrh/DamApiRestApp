package com.dam.apirestapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dam.apirestapp.R;
import com.dam.apirestapp.adapters.ProductAdapter;
import com.dam.apirestapp.models.Product;
import com.dam.apirestapp.network.ApiClient;
import com.dam.apirestapp.utils.SessionManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.switchmaterial.SwitchMaterial;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductsActivity extends AppCompatActivity implements ProductAdapter.OnProductActionListener {

    private RecyclerView rvProducts;
    private ProgressBar progressBar;
    private TextView tvEmpty;
    private TextView tvUserEmail;
    private Button btnLogout;
    private FloatingActionButton fabAddProduct;

    private ProductAdapter adapter;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);

        sessionManager = new SessionManager(this);

        if (!sessionManager.isLoggedIn()) {
            goToLogin();
            return;
        }

        rvProducts = findViewById(R.id.rvProducts);
        progressBar = findViewById(R.id.progressBar);
        tvEmpty = findViewById(R.id.tvEmpty);
        tvUserEmail = findViewById(R.id.tvUserEmail);
        btnLogout = findViewById(R.id.btnLogout);
        fabAddProduct = findViewById(R.id.fabAddProduct);

        tvUserEmail.setText(sessionManager.getEmail());

        rvProducts.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ProductAdapter(new ArrayList<>(), this);
        rvProducts.setAdapter(adapter);

        btnLogout.setOnClickListener(v -> logout());
        fabAddProduct.setOnClickListener(v -> showProductDialog(null));

        loadProducts();
    }

    private void loadProducts() {
        progressBar.setVisibility(View.VISIBLE);
        tvEmpty.setVisibility(View.GONE);

        String authHeader = sessionManager.getAuthHeader();
        ApiClient.getApiService().getProducts(authHeader).enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                progressBar.setVisibility(View.GONE);

                if (response.isSuccessful() && response.body() != null) {
                    List<Product> products = response.body();
                    if (products.isEmpty()) {
                        tvEmpty.setVisibility(View.VISIBLE);
                        adapter.updateList(new ArrayList<>());
                    } else {
                        adapter.updateList(products);
                    }
                } else if (response.code() == 401) {
                    Toast.makeText(ProductsActivity.this, "Sesión expirada. Por favor inicie sesión nuevamente.", Toast.LENGTH_LONG).show();
                    logout();
                } else {
                    Toast.makeText(ProductsActivity.this, getString(R.string.error_load_products), Toast.LENGTH_SHORT).show();
                    tvEmpty.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(ProductsActivity.this, "Error de red: " + t.getMessage(), Toast.LENGTH_LONG).show();
                tvEmpty.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void onEdit(Product product) {
        showProductDialog(product);
    }

    @Override
    public void onDelete(Product product) {
        confirmDeleteProduct(product);
    }

    private void showProductDialog(Product productToEdit) {
        boolean isEditing = productToEdit != null;
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_product, null);
        EditText etName = dialogView.findViewById(R.id.etDialogProductName);
        SwitchMaterial switchStatus = dialogView.findViewById(R.id.switchDialogProductStatus);

        if (isEditing) {
            etName.setText(productToEdit.getName());
            switchStatus.setChecked(productToEdit.getStatus() != null && productToEdit.getStatus());
        }

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle(isEditing ? R.string.dialog_title_edit_product : R.string.dialog_title_create_product)
                .setView(dialogView)
                .setPositiveButton(R.string.btn_save, null) // Listener asignado abajo para evitar cierre si hay error
                .setNegativeButton(R.string.btn_cancel, (d, which) -> d.dismiss())
                .create();

        dialog.show();

        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(v -> {
            String name = etName.getText().toString().trim();
            if (name.isEmpty()) {
                Toast.makeText(ProductsActivity.this, getString(R.string.error_empty_fields), Toast.LENGTH_SHORT).show();
                return;
            }

            boolean status = switchStatus.isChecked();
            String authHeader = sessionManager.getAuthHeader();

            if (isEditing) {
                Product updatedProduct = new Product(name, status);
                ApiClient.getApiService().updateProduct(authHeader, productToEdit.getCode(), updatedProduct)
                        .enqueue(new Callback<Product>() {
                            @Override
                            public void onResponse(Call<Product> call, Response<Product> response) {
                                if (response.isSuccessful()) {
                                    Toast.makeText(ProductsActivity.this, getString(R.string.success_product_updated), Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                    loadProducts();
                                } else {
                                    Toast.makeText(ProductsActivity.this, "Error al actualizar producto", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<Product> call, Throwable t) {
                                Toast.makeText(ProductsActivity.this, "Error de red: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            } else {
                Product newProduct = new Product(name, status);
                ApiClient.getApiService().createProduct(authHeader, newProduct)
                        .enqueue(new Callback<Product>() {
                            @Override
                            public void onResponse(Call<Product> call, Response<Product> response) {
                                if (response.isSuccessful()) {
                                    Toast.makeText(ProductsActivity.this, getString(R.string.success_product_created), Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                    loadProducts();
                                } else {
                                    Toast.makeText(ProductsActivity.this, "Error al crear producto", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<Product> call, Throwable t) {
                                Toast.makeText(ProductsActivity.this, "Error de red: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
    }

    private void confirmDeleteProduct(Product product) {
        String message = String.format(getString(R.string.confirm_delete_message), product.getName());
        new AlertDialog.Builder(this)
                .setTitle(R.string.confirm_delete_title)
                .setMessage(message)
                .setPositiveButton(R.string.btn_delete, (dialog, which) -> {
                    String authHeader = sessionManager.getAuthHeader();
                    ApiClient.getApiService().deleteProduct(authHeader, product.getCode())
                            .enqueue(new Callback<Map<String, String>>() {
                                @Override
                                public void onResponse(Call<Map<String, String>> call, Response<Map<String, String>> response) {
                                    if (response.isSuccessful()) {
                                        Toast.makeText(ProductsActivity.this, getString(R.string.success_product_deleted), Toast.LENGTH_SHORT).show();
                                        loadProducts();
                                    } else {
                                        Toast.makeText(ProductsActivity.this, "Error al eliminar producto", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<Map<String, String>> call, Throwable t) {
                                    Toast.makeText(ProductsActivity.this, "Error de red: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                })
                .setNegativeButton(R.string.btn_cancel, (dialog, which) -> dialog.dismiss())
                .show();
    }

    private void logout() {
        String authHeader = sessionManager.getAuthHeader();
        // Llamada de cortesía al backend para logout
        if (authHeader != null) {
            ApiClient.getApiService().logout(authHeader).enqueue(new Callback<Map<String, String>>() {
                @Override
                public void onResponse(Call<Map<String, String>> call, Response<Map<String, String>> response) {
                    // No se requiere acción adicional
                }

                @Override
                public void onFailure(Call<Map<String, String>> call, Throwable t) {
                    // Ignorar error de red en logout
                }
            });
        }

        sessionManager.clearSession();
        Toast.makeText(this, "Sesión cerrada correctamente", Toast.LENGTH_SHORT).show();
        goToLogin();
    }

    private void goToLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
