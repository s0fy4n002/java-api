package programmer.yans.spring.core.mapper;

import org.mapstruct.Mapper;
import programmer.yans.spring.core.dto.ProductData;
import programmer.yans.spring.core.model.entity.Product;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    // @Mapping(source = "price", target = "productPrice")
    ProductData toDTO(Product product);

    Product toEntity(ProductData dto);
}