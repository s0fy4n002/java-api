package programmer.yans.spring.core.model.repository;

import programmer.yans.spring.core.model.entity.Product;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product, Long> {
	List<Product> findByNameContains(String name);
}
