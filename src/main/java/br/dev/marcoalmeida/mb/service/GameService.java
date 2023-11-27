package br.dev.marcoalmeida.mb.service;

public class GameService {

/**
 *  purpose: creates a new game, and returns newly-created gameId
 *
 *  method : createGame(Integer playerId)
 *
 *  returns : NewGameDTO { private Integer gameId }
 *
 *  implmentation : checks if Player has no unfinished games, (later also checks if Player is the same as the one loggedIn)
 *
 *  before returning calls RoundService.createNewRound(Integer gameId)
 *
**/

/**
 *  purpose: finishes a game, and returns updated gameId
 *
 *  method : finishGame(Integer gameId)
 *
 *  returns : void
 *
 *  implmentation : set finishedAt to LocalDateTime.now()
 *
 **/

/**
 *  purpose: gets info from finished Game
 *
 *  method : getFinishedGame(Integer gameId)(Integer gameId)
 *
 *  returns : GameOverDTO
 *
 *
 **/

}
