package programmer.yans.spring.core.mapper;

import org.mapstruct.Mapper;
import programmer.yans.spring.core.dto.SupplierData;
import programmer.yans.spring.core.model.entity.Supplier;

@Mapper(componentModel = "spring")
public interface SupplierMapper {

    // @Mapping(source = "price", target = "productPrice")
    SupplierData toDTO(Supplier supplier);

    Supplier toEntity(SupplierData dto, Long id);
}