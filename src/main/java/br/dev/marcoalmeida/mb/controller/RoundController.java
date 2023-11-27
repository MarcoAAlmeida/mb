package br.dev.marcoalmeida.mb.controller;

public class RoundController {

    /**
     *  purpose: returns all the info needed to display next round
     *
     *  verb REST : GET
     *
     *  route : round/{roundId}
     *
     *  method : getRound(Integer roundId)
     *
     *  params : Integer roundId (path)
     *
     *  returns: 200 NextRoundDTO
     *
     */

    /**
     *  purpose: evaluate Player choice, and returns nextRoundId, or fails
     *
     *  verb REST : PUT
     *
     *  route : round/{roundId}/answer/{choiceEnum}
     *
     *  e.g round/123/answer/LEFT
     *
     *  method : evaluate(Integer roundId,choice)
     *
     *  params : ChoiceEnum
     *
     *  returns: 200 AnswerRoundDTO
     *
     *  422 - indicates that game is over, client must call /game/over/{gameId}
     *
     */

}
