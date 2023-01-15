package br.dev.marcoalmeida.mb.clients;

import br.dev.marcoalmeida.mb.dto.omdb.DetailDTO;
import br.dev.marcoalmeida.mb.dto.omdb.ResultsDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name="omdb", url="https://www.omdbapi.com")
public interface OMDbClient {
    @RequestMapping(method = RequestMethod.GET, value = "/?page=1&apikey=fa212ffa")
    ResponseEntity<ResultsDTO> search(@RequestParam(name = "s") String tag);

    @RequestMapping(method = RequestMethod.GET, value = "/?page=1&apikey=fa212ffa")
    ResponseEntity<DetailDTO> detail(@RequestParam(name = "i") String imdbID);
}
