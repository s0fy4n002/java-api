package programmer.yans.spring.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import programmer.yans.spring.core.model.entity.Product;
import programmer.yans.spring.core.model.repository.ProductRepository;

@Service
@Transactional
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public Product save(Product product) {
        if (product == null) {
            throw new IllegalArgumentException("product tidak boleh null");
        }
        return productRepository.save(product);
    }

    public Product getById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("id tidak boleh null");
        }
        return productRepository.findById(id).orElse(null);
    }

    public Iterable<Product> getAll() {
        return productRepository.findAll();
    }

    public void deleteById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("id tidak boleh null");
        }
        productRepository.deleteById(id);
    }

}
