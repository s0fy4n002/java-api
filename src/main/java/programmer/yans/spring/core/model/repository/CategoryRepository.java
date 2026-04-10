package programmer.yans.spring.core.model.repository;

import org.springframework.data.repository.CrudRepository;
import programmer.yans.spring.core.model.entity.Category;

public interface CategoryRepository extends CrudRepository<Category, Long> {
    
}
