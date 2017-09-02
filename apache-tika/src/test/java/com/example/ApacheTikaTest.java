package com.example;

import static org.junit.Assert.assertEquals;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;

import org.apache.tika.detect.Detector;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.mime.MediaType;
import org.apache.tika.parser.AutoDetectParser;
import org.junit.Test;

/** Unit tests that demonstrate how the Apache Tika toolkit can be used to determine a file's mime-type based on its content. */
public class ApacheTikaTest {
   @Test
   public void gif() {
      assertType("gif", "image/gif");
   }

   @Test
   public void jpg() {
      assertType("jpg", "image/jpeg");
   }

   @Test
   public void json() {
      assertType("json", "application/json", "text/plain");
   }

   @Test
   public void pdf() {
      assertType("pdf", "application/pdf");
   }

   @Test
   public void png() {
      assertType("png", "image/png");
   }

   @Test
   public void tif() {
      assertType("tif", "image/tiff");
   }

   @Test
   public void txt() {
      assertType("txt", "text/plain");
   }

   @Test
   public void xml() {
      assertType("xml", "application/xml");
   }

   private void assertType(String filename, String expected) {
      assertType(filename, expected, expected);
   }

   private void assertType(String filename, String expectedWithFilename, String expectedWithoutFilename) {
      assertEquals(expectedWithFilename, detectMimeType(filename, true));
      assertEquals(expectedWithoutFilename, detectMimeType(filename, false));
   }

   private static String detectMimeType(String extension, boolean useFilename) {
      String filename = "src/test/resources/test." + extension;
      try (InputStream is = new FileInputStream(filename); BufferedInputStream bis = new BufferedInputStream(is);) {
         AutoDetectParser parser = new AutoDetectParser();
         Detector detector = parser.getDetector();
         Metadata md = new Metadata();
         if (useFilename) {
            md.add(Metadata.RESOURCE_NAME_KEY, filename);
         }
         MediaType mediaType = detector.detect(bis, md);
         return mediaType.toString();
      } catch (IOException e) {
         throw new UncheckedIOException(e);
      }
   }
}
