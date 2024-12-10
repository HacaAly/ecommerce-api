package com.hikadobushido.ecommerce_java.model;

import com.hikadobushido.ecommerce_java.entity.User;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequest {
    @NotBlank(message = "Nama produk tidak boleh kosong")
    @Size(min = 3, max = 20, message = "Nama produk harus antara 3 dan 20 karakter")
    private String name;

    @NotNull(message = "Harga tidak boleh kosong")
    @Positive(message = "Harga harus lebih besar dari 0")
    @Digits(integer = 10, fraction = 2, message = "Harga harus memiliki maksimal 10 digit dan 2 angka di belakang koma")
    private BigDecimal price;

    @NotNull(message = "Deskripsi produk tidak boleh null")
    @Size(max = 1000, message = "Deskripsi produk tidak boleh lebih dari 1000 karakter")
    private String description;

    @NotNull(message = "Stok tidak boleh kosong")
    @Min(value = 0, message = "Stok tidak boleh kurang atau sama dengan 0")
    private Integer stockQuantity;

    @NotNull(message = "Berat tidak boleh kosong")
    @Min(value = 1000, message = "Berat tidak boleh kurang dari 1000 gram")
    private BigDecimal weight;

    @NotEmpty(message = "Harus ada satu kategori yang dipilih")
    private List<Long> categoryIds;

    private User user;
}
