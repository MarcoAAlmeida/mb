package br.dev.marcoalmeida.mb.controller;

import br.dev.marcoalmeida.mb.dto.UnfinishedGameByPlayerDTO;
import br.dev.marcoalmeida.mb.service.PlayerService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("player")
@AllArgsConstructor
public class PlayerController {

    PlayerService playerService;
    /**
     *  purpose: returns a gameId of the unfinished game associated with a given playerId (1 Player -> 1 unfinished Game)
     *
     *  verb REST : GET
     *
     *  route : player/{playerId}/unfinishedGame
     *
     *  method : hasUnfinishedGame(Integer playerId) (PlayerService)
     *
     *  params : Integer playerId (path)
     *
     *  returns:
     *  200 in case of unfinished game is found
     *  { gameId: 12321321 }
     *
     *  422 (unprocessable entity) in case of no game
     *
     */
    @GetMapping(value = "/{playerId}/unfinishedGame", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UnfinishedGameByPlayerDTO> getUnfinishedGameByPlayer(@PathVariable(required = true) Integer playerId) {
        return new ResponseEntity<>(playerService.getUnfinishedGameByPlayer(playerId), HttpStatus.OK);

    }



}
