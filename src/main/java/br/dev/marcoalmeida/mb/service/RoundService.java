package br.dev.marcoalmeida.mb.service;

public class RoundService {

/**
 *  purpose: returns info need to display next Round
 *
 *  method : getRound(Integer roundId)
 *
 *  returns : NextRoundDTO
 *
**/

/**
 *  purpose: evaluates if receivedAnswer is correct
 *
 *  method : AnswerRoundDTO evaluate(Integer roundId, ChoiceEnum choice)
 *
 *  - retrieve Round by roundId
 *  - calculate Ranking for left and right movies
 *  - checks if user choice is correct
 *  - marks answeredAt and correct attributes for that Round
 *  - calculates the number of errors
 *  - if equal to 3
 *      - populate Game.finishedAt
 *      - return 422
 *  - if less than 3:
 *      - calls RoundService.createNewRound(Integer gameId)
 *      - returns 200 with AnswerRoundDTO.nextRoundId
 *
 *  returns : 200 - AnswerRoundDTO
 *
 *
 *
 **/

/**
 *  purpose: creates a new Round for a giver Game (gameId)
 *
 *  method : Round createNewRound(Integer gameId)
 *
 *  - retrieve Game by gameId
 *  - retrieve used Set<ReflexivePair<Movie>> usedPairs from the List<Round>
 *  - calls ReflexivePair<Movie> MovieService.findNewPair(Set<ReflexivePair<Movie>> usedPairs)
 *  - create new Round based on newly found Pair
 *
 *  returns : Round
 *
 *
 *
 **/


}
