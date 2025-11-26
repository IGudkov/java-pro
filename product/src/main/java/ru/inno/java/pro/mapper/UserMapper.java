package ru.inno.java.pro.mapper;

import org.mapstruct.Mapper;
import ru.inno.java.pro.mapper.base.BaseMapper;
import ru.inno.java.pro.model.dto.UserDto;
import ru.inno.java.pro.model.entity.User;

@Mapper
public interface UserMapper extends BaseMapper<UserDto, User> {

  @Override
  UserDto fromEntity(User entity);
}
