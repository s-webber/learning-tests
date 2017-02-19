package com.example;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class ExampleTarget {
   private String stringToStringExample;
   private Integer stringToIntegerExample;
   private String doubleToStringExample;
   private Date stringToDateExample;
   private String dateToStringExample;
   private List<BigDecimal> stringListToBigDecimalListExample;
   private String nameNotInSource;
   private ExampleEnum stringToEnum;
   private String defaultValue;
   private String constantValue;

   public String getStringToStringExample() {
      return stringToStringExample;
   }

   public void setStringToStringExample(String stringToStringExample) {
      this.stringToStringExample = stringToStringExample;
   }

   public Integer getStringToIntegerExample() {
      return stringToIntegerExample;
   }

   public void setStringToIntegerExample(Integer stringToIntegerExample) {
      this.stringToIntegerExample = stringToIntegerExample;
   }

   public String getDoubleToStringExample() {
      return doubleToStringExample;
   }

   public void setDoubleToStringExample(String doubleToStringExample) {
      this.doubleToStringExample = doubleToStringExample;
   }

   public Date getStringToDateExample() {
      return stringToDateExample;
   }

   public void setStringToDateExample(Date stringToDateExample) {
      this.stringToDateExample = stringToDateExample;
   }

   public String getDateToStringExample() {
      return dateToStringExample;
   }

   public void setDateToStringExample(String dateToStringExample) {
      this.dateToStringExample = dateToStringExample;
   }

   public List<BigDecimal> getStringListToBigDecimalListExample() {
      return stringListToBigDecimalListExample;
   }

   public void setStringListToBigDecimalListExample(List<BigDecimal> stringListToBigDecimalListExample) {
      this.stringListToBigDecimalListExample = stringListToBigDecimalListExample;
   }

   public String getNameNotInSource() {
      return nameNotInSource;
   }

   public void setNameNotInSource(String nameNotInSource) {
      this.nameNotInSource = nameNotInSource;
   }

   public ExampleEnum getStringToEnum() {
      return stringToEnum;
   }

   public void setStringToEnum(ExampleEnum stringToEnum) {
      this.stringToEnum = stringToEnum;
   }

   public String getDefaultValue() {
      return defaultValue;
   }

   public void setDefaultValue(String defaultValue) {
      this.defaultValue = defaultValue;
   }

   public String getConstantValue() {
      return constantValue;
   }

   public void setConstantValue(String constantValue) {
      this.constantValue = constantValue;
   }
}
