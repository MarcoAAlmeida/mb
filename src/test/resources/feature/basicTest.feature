Feature: Playing games game

Scenario: stop the game before 3 mistakes are reached

  Given the database has default startup data

  When Player 1 checks for unfinished game
  Then response is 422 with an error message

  When Player 1 start a new game
  Then response is 201 and content is
    | gameId | roundId |
    | 1      | 1       |

  When Player 1 checks for unfinished game
  Then response is 201 and content is
    | gameId | roundId |
    | 1      | 1       |

  When Player 1 gets nextRound 1
  Then response is 200 and content is
    | playerId | gameId | roundId | startedAt           | roundCount | errorCount | score | left        | right       |
    | 1        | 1      | 1       | {someLocalDateTime} | 1          | 0          | 0     | {someMovie} | {someMovie} |

  When Player 1 chooses correctly for roundId 1
  Then response is 200 and content is
    | answerId | isCorrect | nextRoundId |
    | 1        | true      | 2           |

  When Player 1 gets nextRound 2
  Then response is 200 and content is
    | playerId | gameId | roundId | startedAt           | roundCount | errorCount | score | left        | right       |
    | 1        | 1      | 2       | {someLocalDateTime} | 1          | 0          | 1     | {someMovie} | {someMovie} |

  When Player 1 stops a new game
  Then response is 200 and content is
    | gameId | startedAt           | finishedAt          | roundCount | errorCount | score |
    | 1      | {someLocalDateTime} | {someLocalDateTime} | 1          | 1          | 1     |

  When Player 1 when checks for unfinished game
  Then response is 422 with an error message

