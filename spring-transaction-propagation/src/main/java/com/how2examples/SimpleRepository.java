package com.how2examples;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class SimpleRepository {
   private static final SingleColumnRowMapper<String> ROW_MAPPER = new SingleColumnRowMapper<String>();

   @Autowired
   private JdbcTemplate jdbcTemplate;

   public List<String> findAll() {
      return jdbcTemplate.query("SELECT name FROM simple_table", ROW_MAPPER);
   }

   public void insert(String name) {
      jdbcTemplate.update("INSERT INTO simple_table (name) values (?)", name);
   }
}
