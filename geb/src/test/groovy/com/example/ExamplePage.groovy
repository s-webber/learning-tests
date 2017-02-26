package com.example;

import geb.Page

class ExamplePage extends Page {

   static url = "example-page.html"
   
   static at = { 
      title == "Example Title"
      theButton.value() == "click me" 
   }
   
   static content = {
      theButton { $('input', id: 'btn') }
   }
}
