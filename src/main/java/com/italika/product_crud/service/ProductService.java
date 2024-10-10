package com.italika.product_crud.service;

import com.italika.product_crud.model.Product;
import com.italika.product_crud.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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


    public String updateProduct(Long id, Product product) {
        // Busca el producto existente por ID
        Product existingProduct = productRepository.findById(id).orElse(null);
        if (existingProduct == null) {
            return "Producto no encontrado.";
        }

        // Verifica si se puede actualizar el producto
        if (existingProduct.getFechaModificacion() != null &&
                existingProduct.getFechaModificacion().isAfter(LocalDateTime.now().minusHours(12))) {
                                                                                    //minusMinutes(2)
            return "No se puede actualizar el producto: debe pasar 12 horas desde su fecha de creacion.";
        }

        // Actualiza los campos del producto existente
        existingProduct.setNombre(product.getNombre());
        existingProduct.setDescripcion(product.getDescripcion());
        existingProduct.setExistencias(product.getExistencias());
        existingProduct.setPrecio(product.getPrecio());

        // La fecha de modificación se actualiza automáticamente gracias a @PreUpdate

        productRepository.save(existingProduct); // Guarda los cambios
        return "Producto actualizado con éxito.";
    }


    public String deleteProduct(Long id) {
        Product existingProduct = productRepository.findById(id).orElse(null);
        if (existingProduct == null) {
            return "Producto no encontrado."; // Mensaje si el producto no existe
        }

        // Cambia el estado a no activo y actualiza la fecha de modificación
        existingProduct.setActivo(false);
        existingProduct.setFechaModificacion(LocalDateTime.now());

        // Guarda los cambios en la base de datos
        productRepository.save(existingProduct);

        return "Producto desactivado con éxito.";
    }

}
