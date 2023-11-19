package br.dev.marcoalmeida.mb.client;

import br.dev.marcoalmeida.mb.dto.omdb.InfoDTO;
import br.dev.marcoalmeida.mb.dto.omdb.ResultsDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name="omdb", url="${omdb.server.url}")

public interface OmdbClient {
    @RequestMapping(method = RequestMethod.GET, value = "/?page=1&apikey=fa212ffa")
    ResponseEntity<ResultsDTO> search(@RequestParam(name = "s") String title);

    @RequestMapping(method = RequestMethod.GET, value = "/?apikey=fa212ffa")
    ResponseEntity<InfoDTO> getInfo(@RequestParam(name = "i") String imdbId);

}
