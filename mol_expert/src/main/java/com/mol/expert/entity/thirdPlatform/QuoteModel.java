package com.mol.expert.entity.thirdPlatform;

import java.util.List;


public class QuoteModel {

    public QuoteModel(){
        super();
    }
    public QuoteModel(List<FyQuote> quotes){
        super();
        this.quotes=quotes;
    }
    private List<FyQuote> quotes;

    public List<FyQuote> getQuotes() {
        return quotes;
    }

    public void setQuotes(List<FyQuote> quotes) {
        this.quotes = quotes;
    }
}
