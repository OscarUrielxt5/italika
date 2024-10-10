package com.italika.product_crud.service;

import com.italika.product_crud.model.Product;
import com.italika.product_crud.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    public Product getProduct(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    /*
    public String updateProduct(Long id, Product product) {
        Product existingProduct = productRepository.findById(id).orElse(null);
        if (existingProduct == null) return "Producto no encontrado.";
        if (existingProduct.isLocked() || existingProduct.getModifiedAt().isAfter(LocalDateTime.now().minusHours(12))) {
            return "No se puede actualizar el producto: está bloqueado o fue modificado recientemente.";
        }
        product.setId(id);
        productRepository.save(product);
        return "Producto actualizado con éxito.";
    }
     */

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}
