package programmer.yans.spring.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.TransactionScoped;
import programmer.yans.spring.core.model.entity.Category;
import programmer.yans.spring.core.model.repository.CategoryRepository;

@Service
@TransactionScoped
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public Category save(Category category) {
        if (category == null) {
            throw new IllegalArgumentException("category tidak boleh kosong");
        }
        return categoryRepository.save(category);
    }

    public Category getById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("id tidak boleh kosong");
        }
        return categoryRepository.findById(id).orElse(null);
    }

    public Iterable<Category> getAll() {
        return categoryRepository.findAll();
    }

    public void deleteById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("id tidak boleh kosong");
        }
        categoryRepository.deleteById(id);
    }
    
}
