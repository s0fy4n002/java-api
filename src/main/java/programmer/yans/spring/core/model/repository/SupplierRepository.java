package programmer.yans.spring.core.model.repository;

import org.springframework.data.repository.CrudRepository;
import programmer.yans.spring.core.model.entity.Supplier;

public interface SupplierRepository extends CrudRepository<Supplier, Long>{
    
    Supplier findByEmail(String email);

}
