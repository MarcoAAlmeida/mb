package br.dev.marcoalmeida.mb.controller;

public class PlayerController {

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

}
