package com.microService.stock.dbservice.resource;

import com.microService.stock.dbservice.model.Quote;
import com.microService.stock.dbservice.model.Quotes;
import com.microService.stock.dbservice.repository.QuotesRepository;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/rest/db")
public class DBServiceResource {
  private QuotesRepository quotesRepository;

  public DBServiceResource(QuotesRepository quotesRepository) {
    this.quotesRepository = quotesRepository;
  }

  @GetMapping("/{username}")
  public List<String> getQuotes(@PathVariable("username") final String username) {

    // Query repository for particular username and collecting only quotes  then sending back to
    //REST end point
    return getQuotesByUserName(username);
  }

  private List<String> getQuotesByUserName(@PathVariable("username") String username) {
    return quotesRepository.findByUsername(username)
            .stream()
            .map(Quote::getQuote)
            .collect(Collectors.toList());
  }

  //Iterate through list of quotes and insert for same username
  @PostMapping("/add")
  public List<String> add(@RequestBody final Quotes quotes){
    quotes.getQuotes()
            .stream()
            .map(quote -> new Quote(quotes.getUserName(), quote))// creating new Quote object
            // which is saved in the repository
            .forEach(quote -> quotesRepository.save(quote));
    return getQuotesByUserName(quotes.getUserName());
  }

  @PostMapping("/delete/{username}")
  public List<String> delete(@PathVariable("username") final String username) {

//    List<Quote> quotes = quotesRepository.findByUsername(username);
    quotesRepository.deleteAll();

    return getQuotesByUserName(username);
  }

}
