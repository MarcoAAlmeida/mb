package br.dev.marcoalmeida.mb.config;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)

@CucumberOptions(
        features = {"src/test/resources"},
        glue = {"br.dev.marcoalmeida.mb.steps"},
        plugin = { "pretty", "json:build/reports/tests/mb-cucumber.json" })
public class CucumberIntegrationTest {
}