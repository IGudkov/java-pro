package ru.inno.java.pro.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.inno.java.pro.mapper.base.BaseMapper;
import ru.inno.java.pro.model.dto.ProductDto;
import ru.inno.java.pro.model.entity.Product;

@Mapper
public interface ProductMapper extends BaseMapper<ProductDto, Product> {

  @Override
  @Mapping(source = "user.id", target = "userId")
  ProductDto fromEntity(Product entity);
}
