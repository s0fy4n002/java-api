package programmer.yans.spring.core.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import programmer.yans.spring.core.model.entity.Product;
import programmer.yans.spring.core.model.entity.Supplier;
import programmer.yans.spring.core.model.repository.ProductRepository;
import programmer.yans.spring.core.model.repository.SupplierRepository;

@Service
@Transactional
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private SupplierRepository supplierRepository;

    public Product save(Product product) {
        if (product == null) {
            throw new IllegalArgumentException("product tidak boleh kosong");
        }
        return productRepository.save(product);
    }

    public Product getById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("id tidak boleh kosong");
        }
        return productRepository.findById(id).orElse(null);
    }

    public Iterable<Product> getAll() {
        return productRepository.findAll();
    }

    public void deleteById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("id tidak boleh kosong");
        }
        productRepository.deleteById(id);
    }

    public List<Product> findByName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("name tidak boleh kosong");
        }
        return productRepository.findByNameContains(name);
    }

    public void addSupplierToProduct(Supplier supplier, Long productId) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("product tidak ditemukan"));

        product.getSuppliers().add(supplier);
        productRepository.save(product);
    }

    public Product findByProductName(String name){
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("name tidak boleh kosong");
        }
        return productRepository.findProductByName(name);
    }

    public List<Product> findByProductNameLike(String name){
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("name tidak boleh kosong");
        }
        return productRepository.findProductByNameLike(name);
    }

    public List<Product> findByCategory(Long categoryId){
        if (categoryId == null) {
            throw new IllegalArgumentException("categoryId tidak boleh kosong");
        }
        return productRepository.findProductByCategory(categoryId);
    }

}
