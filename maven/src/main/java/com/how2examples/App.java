package com.how2examples;

/** Used to provide Jacoco, Javadoc, Checkstyle, FindBugs and PMD plug-ins something to work with. */
public class App {
   public String toYesNo(boolean b) {
      if (b) {
         return "yes";
      } else {
         return "false";
      }
   }

   /** Overriding {@code equals(Object)} without also overriding {@code hashCode()} to confirm Checkstyle, FindBugs and PMD will raise it as an issue. */
   @Override
   public boolean equals(Object o) {
      return true;
   }
}
