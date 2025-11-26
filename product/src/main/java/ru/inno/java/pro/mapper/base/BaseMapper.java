package ru.inno.java.pro.mapper.base;

public interface BaseMapper<DtoT, EntityT> {

  DtoT fromEntity(final EntityT entity);
}
