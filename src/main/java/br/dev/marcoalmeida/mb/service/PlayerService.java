package br.dev.marcoalmeida.mb.service;

public class PlayerService {

/**
 *  purpose: returns a gameId of the unfinished game associated with a given playerId
 *
 *  method : hasUnfinishedGame(Integer playerId)
 *
 *  returns : UnfinishedGameByPlayerDTO { private Integer gameId }
 *
 *  implmentation : calls GameRepository.findByPlayerIdAndFinishedAtIsNull(Integer playerId)
 *
**/
}
