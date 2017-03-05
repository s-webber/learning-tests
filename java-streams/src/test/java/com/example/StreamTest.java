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
import java.util.stream.IntStream;
import java.util.stream.Stream;

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
   public void collectGroupingByCounting() {
      Map<Integer, Long> grouped = words.stream().collect(Collectors.groupingBy(String::length, Collectors.counting()));
      assertEquals(3, grouped.size());
      assertEquals(2L, grouped.get(3).longValue());
      assertEquals(1L, grouped.get(6).longValue());
      assertEquals(2L, grouped.get(8).longValue());
   }

   @Test
   public void collectGroupingBySummingInt() {
      List<String> words = asList("aardvark", "ant", "alligator", "armadillo", "badger", "cat", "caterpillar");
      Map<Character, IntSummaryStatistics> grouped = words.stream().collect(Collectors.groupingBy(s -> s.charAt(0), Collectors.summarizingInt(String::length)));

      assertEquals(3, grouped.size());

      assertEquals(7.25, grouped.get('a').getAverage(), 0d);
      assertEquals(4, grouped.get('a').getCount());
      assertEquals(9, grouped.get('a').getMax());
      assertEquals(3, grouped.get('a').getMin());
      assertEquals(29, grouped.get('a').getSum());

      assertEquals(1, grouped.get('b').getCount());
      assertEquals(2, grouped.get('c').getCount());
   }

   @Test
   public void collectGroupingByMaxBy() {
      List<String> words = asList("aardvark", "ant", "alligator", "armadillo", "badger", "cat", "caterpillar");
      Map<Character, Optional<String>> grouped = words.stream().collect(Collectors.groupingBy(s -> s.charAt(0), Collectors.maxBy(String::compareTo)));

      assertEquals(3, grouped.size());
      assertEquals("armadillo", grouped.get('a').get());
      assertEquals("badger", grouped.get('b').get());
      assertEquals("caterpillar", grouped.get('c').get());
   }

   @Test
   public void collectGroupingByMapping() {
      List<String> words = asList("aardvark", "ant", "alligator", "armadillo", "badger", "cat", "caterpillar");
      Map<Character, Optional<Integer>> grouped = words.stream().collect(
            Collectors.groupingBy(s -> s.charAt(0), Collectors.mapping(String::length, Collectors.minBy(Integer::compare))));

      assertEquals(3, grouped.size());
      assertEquals(3, grouped.get('a').get().intValue());
      assertEquals(6, grouped.get('b').get().intValue());
      assertEquals(3, grouped.get('c').get().intValue());
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
   public void mapMethodExpression() {
      List<String> upperCased = words.stream().map(String::toUpperCase).collect(Collectors.toList());
      assertEquals(asList("DOG", "AARDVARK", "ELEPHANT", "CAT", "BADGER"), upperCased);
   }

   @Test
   public void mapLambdaExpression() {
      List<Character> firstChars = words.stream().map(s -> s.charAt(0)).collect(Collectors.toList());
      assertEquals(asList('d', 'a', 'e', 'c', 'b'), firstChars);
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

   @Test
   public void streamOf() {
      Stream<String> s = Stream.of("ab,cd,ef".split(","));
      assertEquals(asList("ab", "cd", "ef"), s.collect(Collectors.toList()));
   }

   @Test
   public void streamIterate() {
      Stream<String> s = Stream.iterate("a", x -> x + "a");
      assertEquals(asList("a", "aa", "aaa"), s.limit(3).collect(Collectors.toList()));
   }

   @Test
   public void streamGenerate() {
      Stream<String> s = Stream.generate(() -> "a");
      assertEquals(asList("a", "a", "a"), s.limit(3).collect(Collectors.toList()));
   }

   @Test
   public void intStreamOf() {
      IntStream s = IntStream.of(8, 3, 5, 4);
      assertEquals(8 + 3 + 5 + 4, s.sum());
   }

   @Test
   public void intStreamRange() {
      IntStream s = IntStream.range(2, 7);
      assertEquals(2 + 3 + 4 + 5 + 6, s.sum());
   }

   @Test
   public void intStreamRangeClosed() {
      IntStream s = IntStream.rangeClosed(2, 7);
      assertEquals(2 + 3 + 4 + 5 + 6 + 7, s.sum());
   }
}
