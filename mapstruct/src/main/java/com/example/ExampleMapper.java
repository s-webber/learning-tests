package com.example;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ExampleMapper {
   ExampleMapper INSTANCE = Mappers.getMapper(ExampleMapper.class);

   @Mapping(source = "nameNotInTarget", target = "nameNotInSource")
   @Mapping(target = "stringToDateExample", dateFormat = "yyyyMMdd")
   @Mapping(target = "dateToStringExample", dateFormat = "dd/MM/yyyy")
   @Mapping(target = "doubleToStringExample", numberFormat = "0000,00.00")
   @Mapping(target = "defaultValue", defaultValue = "using default value")
   @Mapping(target = "constantValue", constant = "using constant value")
   ExampleTarget targetToSource(ExampleSource source);
}
