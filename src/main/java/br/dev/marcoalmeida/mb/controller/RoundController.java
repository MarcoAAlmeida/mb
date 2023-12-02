package br.dev.marcoalmeida.mb.controller;

import br.dev.marcoalmeida.mb.dto.AnswerDTO;
import br.dev.marcoalmeida.mb.dto.ChoiceEnum;
import br.dev.marcoalmeida.mb.dto.NextRoundDTO;
import br.dev.marcoalmeida.mb.service.RoundService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("round")
@AllArgsConstructor
public class RoundController {
    private final RoundService roundService;

    @GetMapping(value = "/next/{nextRoundId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<NextRoundDTO> getNextRound(@PathVariable(required = true) Integer nextRoundId) {
        return new ResponseEntity<>(roundService.getNextRound(nextRoundId), HttpStatus.OK);

    }

    @PutMapping(value = "/{roundId}/answer/{choice}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AnswerDTO> getAnswer(@PathVariable(required = true) Integer roundId, ChoiceEnum choice) {
        return new ResponseEntity<>(roundService.getAnswer(roundId, choice), HttpStatus.OK);

    }
}
