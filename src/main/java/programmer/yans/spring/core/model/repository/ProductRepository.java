package programmer.yans.spring.core.model.repository;

import programmer.yans.spring.core.model.entity.Product;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import jakarta.websocket.server.PathParam;

public interface ProductRepository extends CrudRepository<Product, Long> {
	List<Product> findByNameContains(String name);

	@Query("SELECT p FROM Product p WHERE p.name = :name")
	public Product findProductByName(@PathParam("name") String name);


	@Query("SELECT p FROM Product p WHERE p.name LIKE %:name%")
	public List<Product> findProductByNameLike(@PathParam("name") String name);

	@Query("SELECT p FROM Product p WHERE p.category.id = :categoryId")
	public List<Product> findProductByCategory(@PathParam("categoryId") Long categoryId);


}
