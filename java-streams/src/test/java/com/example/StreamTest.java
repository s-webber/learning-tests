package com.example;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.junit.Test;

/** Unit tests that demonstrate working with java.util.stream.Stream */
public class StreamTest {
   private final List<String> words = asList("dog", "aardvark", "elephant", "cat", "badger");

   @Test
   public void allMatch() {
      assertTrue(words.stream().allMatch(s -> s.length() > 2));
      assertFalse(words.stream().allMatch(s -> s.length() < 8));
   }

   @Test
   public void anyMatch() {
      assertTrue(words.stream().anyMatch(s -> "cat".equals(s)));
      assertFalse(words.stream().anyMatch(s -> "caterpillar".equals(s)));
   }

   @Test
   public void collectToList() {
      assertEquals(words, words.stream().collect(Collectors.toList()));
   }

   @Test
   public void collectGroupingBy() {
      Map<Integer, List<String>> grouped = words.stream().collect(Collectors.groupingBy(String::length));
      assertEquals(3, grouped.size());
      assertEquals(asList("dog", "cat"), grouped.get(3));
      assertEquals(asList("badger"), grouped.get(6));
      assertEquals(asList("aardvark", "elephant"), grouped.get(8));
   }

   @Test
   public void collectJoining() {
      assertEquals("dog-aardvark-elephant-cat-badger", words.stream().collect(Collectors.joining("-")));
   }

   @Test
   public void collectPartitioningBy() {
      Map<Boolean, List<String>> partitioned = words.stream().collect(Collectors.partitioningBy(s -> s.length() == 3));
      assertEquals(asList("dog", "cat"), partitioned.get(true));
      assertEquals(asList("aardvark", "elephant", "badger"), partitioned.get(false));
   }

   @Test
   public void collectToCollection() {
      TreeSet<String> collected = words.stream().collect(Collectors.toCollection(TreeSet::new));
      assertEquals(asList("aardvark", "badger", "cat", "dog", "elephant"), new ArrayList<>(collected));
   }

   @Test
   public void count() {
      assertEquals(5L, words.stream().count());
   }

   @Test
   public void distinct() {
      List<String> duplicates = asList("apple", "orange", "apple", "lemon", "lemon", "banana");
      List<String> distinct = duplicates.stream().distinct().collect(Collectors.toList());
      assertEquals(asList("apple", "orange", "lemon", "banana"), distinct);
   }

   @Test
   public void filter() {
      List<String> filtered = words.stream().filter(s -> s.length() == 3).collect(Collectors.toList());
      assertEquals(asList("dog", "cat"), filtered);
   }

   @Test
   public void findFirst() {
      Optional<String> o = words.stream().findFirst();
      assertEquals("dog", o.get());
   }

   @Test
   public void flatMap() {
      List<List<String>> input = asList(asList("a", "b"), asList("c", "d"), asList("e", "f"));
      List<Object> flatMapped = input.stream().flatMap(l -> l.stream()).collect(Collectors.toList());
      assertEquals(asList("a", "b", "c", "d", "e", "f"), flatMapped);
   }

   @Test
   public void isParallel() {
      assertTrue(words.parallelStream().isParallel());
      assertFalse(words.stream().isParallel());
   }

   @Test
   public void limit() {
      List<String> limited = words.stream().limit(3).collect(Collectors.toList());
      assertEquals(asList("dog", "aardvark", "elephant"), limited);
   }

   @Test
   public void map() {
      List<String> upperCased = words.stream().map(String::toUpperCase).collect(Collectors.toList());
      assertEquals(asList("DOG", "AARDVARK", "ELEPHANT", "CAT", "BADGER"), upperCased);
   }

   @Test
   public void mapToInt() {
      IntSummaryStatistics statistics = words.stream().mapToInt(String::length).summaryStatistics();
      assertEquals(5.6, statistics.getAverage(), 0);
      assertEquals(5, statistics.getCount());
      assertEquals(8, statistics.getMax());
      assertEquals(3, statistics.getMin());
      assertEquals(28, statistics.getSum());
   }

   @Test
   public void max() {
      Optional<String> max = words.stream().max(String::compareTo);
      assertEquals("elephant", max.get());
   }

   @Test
   public void min() {
      Optional<String> min = words.stream().min(String::compareTo);
      assertEquals("aardvark", min.get());
   }

   @Test
   public void noneMatch() {
      assertTrue(words.stream().noneMatch(s -> s.length() < 3));
      assertFalse(words.stream().noneMatch(s -> s.length() > 3));
   }

   @Test
   public void skip() {
      List<String> limited = words.stream().skip(3).collect(Collectors.toList());
      assertEquals(asList("cat", "badger"), limited);
   }

   @Test
   public void sorted() {
      List<String> limited = words.stream().sorted().collect(Collectors.toList());
      assertEquals(asList("aardvark", "badger", "cat", "dog", "elephant"), limited);
   }
}
