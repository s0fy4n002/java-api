package programmer.yans.spring.core.mapper;

import org.mapstruct.Mapper;

import programmer.yans.spring.core.dto.CategoryData;
import programmer.yans.spring.core.model.entity.Category;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    CategoryData toDTO(Category category);

    Category toEntity(CategoryData dto, Long id);
}