package br.dev.marcoalmeida.mb.controller;

import br.dev.marcoalmeida.mb.dto.UnfinishedGameByPlayerDTO;
import br.dev.marcoalmeida.mb.service.PlayerService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/player")
@SecurityRequirement(name = "Bearer Authentication")
@AllArgsConstructor
public class PlayerController {

    PlayerService playerService;
    @GetMapping(value = "/{playerId}/unfinishedGame", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UnfinishedGameByPlayerDTO> getUnfinishedGameByPlayer(@PathVariable(required = true) Integer playerId) {
        return new ResponseEntity<>(playerService.getUnfinishedGameByPlayer(playerId), HttpStatus.OK);

    }



}
