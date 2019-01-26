package com.example;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Customer {
   @Id
   private int id;
   @Embedded
   private Name name;

   public int getId() {
      return id;
   }

   public void setId(int id) {
      this.id = id;
   }

   public Name getName() {
      return name;
   }

   public void setName(Name name) {
      this.name = name;
   }
}
