package com.microService.stock.dbservice.repository;

import com.microService.stock.dbservice.model.Quote;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuotesRepository extends JpaRepository<Quote, Integer> {
  List<Quote> findByUsername(String username);
}
