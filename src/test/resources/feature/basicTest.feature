Feature: Single player common scenarios
  Background:
    Given the database has default startup data

    # pre-populate database with some known entities
    And that Players are
      | id | name    |
      | 1  | player1 |
    And that Movies are
      | id | title         | rating | votes |
      | m1 | a bad movie   | 1.0    | 10    |
      | m2 | a worse movie | 1.0    | 5     |
      | m3 | a movie       | 2.0    | 10    |
      | m4 | a movie       | 3.0    | 10    |
      | m5 | a movie       | 4.0    | 10    |

    And Player 1 checks for unfinished game with error "could not find unfinished Game for playerId [1]"

    When Player 1 start a new game
      | gameId | roundId |
      | 1      | 1       |

  Scenario: Player makes 3 mistakes, and Game is over
    When Player 1 chooses incorrectly for round 1
      | answerRoundId | correctAnswer | nextRoundId |
      | 1             | false         | 2           |
    When Player 1 chooses incorrectly for round 2
      | answerRoundId | correctAnswer | nextRoundId |
      | 2             | false         | 3           |
    When Player 1 makes last mistake for round 3 with error message "could not compute next round for roundId [3], is the game over?"

  Scenario: Player stops the game before 3 mistakes are reached
    Then Player 1 checks for unfinished game:
      | gameId | roundId |
      | 1      | 1       |
    And Player 1 gets round 1
      | playerId | gameId | roundId | startedAt           | roundCount | errorCount | score | left        | right       |
      | 1        | 1      | 1       | {someLocalDateTime} | 1          | 0          | 0     | {someMovie} | {someMovie} |

    When Player 1 chooses correctly for round 1
      | answerRoundId | correctAnswer | nextRoundId |
      | 1             | true          | 2           |
    Then Player 1 gets round 2
      | playerId | gameId | roundId | startedAt           | roundCount | errorCount | score | left        | right       |
      | 1        | 1      | 2       | {someLocalDateTime} | 2          | 0          | 1     | {someMovie} | {someMovie} |

    When Player 1 stops current game
      | gameId | startedAt           | finishedAt          | roundCount | errorCount | score |
      | 1      | {someLocalDateTime} | {someLocalDateTime} | 2          | 1          | 1     |

    Then Player 1 checks for unfinished game with error "could not find unfinished Game for playerId [1]"


