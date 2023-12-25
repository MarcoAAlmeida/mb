package br.dev.marcoalmeida.mb.steps;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;

public class CsvStepdefs {
    @Given("OMDB search results by title {string} are")
    public void omdbSearchResultsByTitleAre(String arg0, DataTable dataTable) {
    }

    @And("OMDB search results by movie id from title search {string} are")
    public void omdbSearchResultsByMovieIdFromTitleSearchAre(String arg0, DataTable dataTable) {
    }

    @When("generateCSVByTitle endpoint is called with {string}")
    public void movieGenerateCSVByTitleEndpointIsCalledWith(String arg0) {
    }

    @And("the response file is equal to {string}")
    public void theResponseFileIsEqualTo(String arg0) {
    }

}
