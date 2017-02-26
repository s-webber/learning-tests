package com.example;

import javax.swing.plaf.basic.BasicFileChooserUI.DoubleClickListener

import geb.spock.GebReportingSpec

class GebishOrgSpec extends GebReportingSpec {

   def "clicking the button alters its value"() {
      given: "I'm at the example page"
      to ExamplePage
      
      when: "I click the button"
      theButton.click()
    
      then: "the button value is updated"
      waitFor { 
        theButton.value() == "clicked"
      }
   }
}
