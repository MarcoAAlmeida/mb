package br.dev.marcoalmeida.mb.controller;

import br.dev.marcoalmeida.mb.dto.GameOverDTO;
import br.dev.marcoalmeida.mb.dto.NewGameDTO;
import br.dev.marcoalmeida.mb.service.GameService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/game")
@SecurityRequirement(name = "Bearer Authentication")
@AllArgsConstructor
public class GameController {

    private final GameService gameService;

    @PostMapping(value = "/start/{playerId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<NewGameDTO> start(@PathVariable(required = true) Integer playerId) {
        return new ResponseEntity<>(gameService.start(playerId), HttpStatus.CREATED);

    }

    @PutMapping(value = "/stop/{gameId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GameOverDTO> stop(@PathVariable(required = true) Integer gameId) {
        return new ResponseEntity<>(gameService.stop(gameId), HttpStatus.OK);

    }

    @GetMapping(value = "/over/{gameId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GameOverDTO> getGameOver(@PathVariable(required = true) Integer gameId) {
        return new ResponseEntity<>(gameService.getGameOver(gameId), HttpStatus.OK);

    }




    /**
     *  purpose: returns info about a finished Game
     *
     *  verb REST : GET
     *
     *  route : game/over/{gameId}
     *
     *  method : getFinishedGame(Integer gameId)
     *
     *  params : Integer gameId (path)
     *
     *  returns: 200 and GameOverDTO
     *
     */

}
