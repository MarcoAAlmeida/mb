package br.dev.marcoalmeida.mb.controller;

public class GameController {

    /**
     *  purpose: creates a new game, and returns newly-created gameId
     *
     *  verb REST : POST
     *
     *  route : game/create/{playerId}
     *
     *  method : createGame(Integer playerId)
     *
     *  params : Integer playerId (path)
     *
     *  returns: NewGameDTO { gameId: 12213213 }
     *
     *  201 in case of successful
     *  { gameId: 12321321 }
     *
     */

    /**
     *  purpose: creates a new game, and returns newly-created gameId
     *
     *  verb REST : POST
     *
     *  route : game/finish/{gameId}
     *
     *  method : finishGame(Integer gameId)
     *
     *  params : Integer playerId (path)
     *
     *  returns:
     *
     *  void
     *
     *  200 in case of successful
     *
     *
     */

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
