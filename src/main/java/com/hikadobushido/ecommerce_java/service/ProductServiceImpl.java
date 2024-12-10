package com.hikadobushido.ecommerce_java.service;

import com.hikadobushido.ecommerce_java.common.errors.ResourceNotFoundException;
import com.hikadobushido.ecommerce_java.entity.Category;
import com.hikadobushido.ecommerce_java.entity.Product;
import com.hikadobushido.ecommerce_java.entity.ProductCategory;
import com.hikadobushido.ecommerce_java.entity.ProductCategory.ProductCategoryId;
import com.hikadobushido.ecommerce_java.model.CategoryResponse;
import com.hikadobushido.ecommerce_java.model.PaginatedProductResponse;
import com.hikadobushido.ecommerce_java.model.ProductRequest;
import com.hikadobushido.ecommerce_java.model.ProductResponse;
import com.hikadobushido.ecommerce_java.repository.CategoryRepository;
import com.hikadobushido.ecommerce_java.repository.ProductCategoryRepository;
import com.hikadobushido.ecommerce_java.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductCategoryRepository productCategoryRepository;

    @Override
    public List<ProductResponse> findAll() {
        return productRepository.findAll()
                .stream().map(product -> {
                    List<CategoryResponse> productCategories = getProductCategories(product.getProductId());
                    return ProductResponse.fromProductAndCategories(product, productCategories);
                })
                .toList();
    }

    @Override
    public Page<ProductResponse> findByPage(Pageable pageable) {
        return productRepository.findByPageable(pageable)
                .map(product -> {
                    List<CategoryResponse> productCategories = getProductCategories(product.getProductId());
                    return ProductResponse.fromProductAndCategories(product, productCategories);
                });
    }

    @Override
    public Page<ProductResponse> findByNameAndPageable(String name, Pageable pageable) {
        name = "%" + name + "%";
        name = name.toLowerCase();
        return productRepository.findByNamePageable(name, pageable)
                .map(product -> {
                    List<CategoryResponse> productCategories = getProductCategories(product.getProductId());
                    return ProductResponse.fromProductAndCategories(product, productCategories);
                });
    }

    @Override
    public ProductResponse findById(Long productId) {
        Product existingProduct = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id : " + productId));
        List<CategoryResponse> productCategories = getProductCategories(productId);
        return ProductResponse.fromProductAndCategories(existingProduct, productCategories);
    }

    @Override
    @Transactional
    public ProductResponse create(ProductRequest productRequest) {
        List<Category> categories = getCategoryByIds(productRequest.getCategoryIds());

        Product product = Product.builder()
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .price(productRequest.getPrice())
                .stockQuantity(productRequest.getStockQuantity())
                .userId(productRequest.getUser().getUserId())
                .weight(productRequest.getWeight())
                .build();

        Product createdProduct = productRepository.save(product);
        List<ProductCategory> productCategories = categories.stream()
                .map(category -> {
                    ProductCategory productCategory = ProductCategory.builder().build();
                    ProductCategoryId productCategoryId = new ProductCategoryId();
                    productCategoryId.setCategoryId(category.getCategoryId());
                    productCategoryId.setProductId(createdProduct.getProductId());
                    productCategory.setId(productCategoryId);
                    return productCategory;
                })
                .toList();

        productCategoryRepository.saveAll(productCategories);

        List<CategoryResponse> categoryResponseList = categories.stream().map(
                CategoryResponse::fromCategory)
                .toList();

        return ProductResponse.fromProductAndCategories(createdProduct, categoryResponseList);
    }

    @Override
    @Transactional
    public ProductResponse update(Long productId, ProductRequest productRequest) {
        Product existingProduct = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id : " + productId));

        List<Category> categories = getCategoryByIds(productRequest.getCategoryIds());

        existingProduct.setName(productRequest.getName());
        existingProduct.setDescription(productRequest.getDescription());
        existingProduct.setPrice(productRequest.getPrice());
        existingProduct.setStockQuantity(productRequest.getStockQuantity());
        existingProduct.setWeight(productRequest.getWeight());
        productRepository.save(existingProduct);

        List<ProductCategory> existingProductCategories = productCategoryRepository.findCategoriesByProductId(productId);
        productCategoryRepository.deleteAll(existingProductCategories);

        List<ProductCategory> productCategories = categories.stream()
                .map(category -> {
                    ProductCategory productCategory = ProductCategory.builder().build();
                    ProductCategoryId productCategoryId = new ProductCategoryId();
                    productCategoryId.setCategoryId(category.getCategoryId());
                    productCategoryId.setProductId(productId);
                    productCategory.setId(productCategoryId);
                    return productCategory;
                })
                .toList();

        productCategoryRepository.saveAll(productCategories);

        List<CategoryResponse> categoryResponseList = categories.stream().map(
                CategoryResponse :: fromCategory)
                .toList();

        return ProductResponse.fromProductAndCategories(existingProduct, categoryResponseList);

    }

    @Override
    @Transactional
    public void delete(Long productId) {
        Product existingProduct = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id : " + productId));
        List<ProductCategory> productCategories = productCategoryRepository.findCategoriesByProductId(productId);

        productCategoryRepository.deleteAll(productCategories);
        productRepository.delete(existingProduct);
    }

    @Override
    public PaginatedProductResponse convertProductPage(Page<ProductResponse> productPage) {
        return PaginatedProductResponse.builder()
                .data(productPage.getContent())
                .pageNo(productPage.getNumber())
                .pageSize(productPage.getSize())
                .totalElements(productPage.getTotalElements())
                .totalPages(productPage.getTotalPages())
                .last(productPage.isLast())
                .build();
    }

    private List<Category> getCategoryByIds(List<Long> categoryIds) {
        return categoryIds.stream()
                .map(categoryId -> categoryRepository.findById(categoryId).orElseThrow(() ->
                    new ResourceNotFoundException("Categpry not found for id : "+categoryId)))
                .toList();
    }

    private List<CategoryResponse> getProductCategories(Long productId) {
        List<ProductCategory> productCategories =   productCategoryRepository.findCategoriesByProductId(productId);
        List<Long> catagoryIds = productCategories.stream()
                .map(productCategory -> productCategory.getId().getCategoryId())
                .toList();
        return categoryRepository.findAllById(catagoryIds)
                .stream().map(CategoryResponse::fromCategory)
                .toList();
    }
}
