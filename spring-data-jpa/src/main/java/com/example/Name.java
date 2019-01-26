package com.example;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Name {
   @Column(name = "first_name")
   private String firstName;
   @Column(name = "last_name")
   private String lastName;

   public String getFirstName() {
      return firstName;
   }

   public void setFirstName(String firstName) {
      this.firstName = firstName;
   }

   public String getLastName() {
      return lastName;
   }

   public void setLastName(String lastName) {
      this.lastName = lastName;
   }
}
