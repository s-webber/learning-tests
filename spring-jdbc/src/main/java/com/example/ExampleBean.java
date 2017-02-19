package com.example;

import java.util.Date;

public class ExampleBean {
   private final int id;
   private final String name;
   private final Date startDate;

   public ExampleBean(int id, String name, Date startDate) {
      this.id = id;
      this.name = name;
      this.startDate = startDate;
   }

   public int getId() {
      return id;
   }

   public String getName() {
      return name;
   }

   public Date getStartDate() {
      return startDate;
   }
}
