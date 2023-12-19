Feature: Generate CSV file for OMDB movie list
  Background:
		Given the OMDB Api mock
		
		And OMDB search results by title Star are
		| Title                                          | Year | imdbID    | Type  | Poster |
		| Star Wars: Episode IV - A New Hope             | 1977 | tt0076759 | movie | Link1  |
		| Star Wars: Episode V - The Empire Strikes Back | 1980 | tt0080684 | movie | Link2  |
  
  	
  	And OMDB search results by movie id from title search Star are
  	| Year | BoxOffice    | imdbRating | imdbVotes | Plot  |
  	| 1977 | $460,998,507 | 8.6        | 1,420,107 | Plot1 |
  	| 1980 | $292,753,960 | 8.7        | 1,349,018 | Plot2 |
  	
  	
  Scenario:  CSV file is successfully generated when /movie/generateCSVByTitle endpoint is called
    When /movie/generateCSVByTitle endpoint is called
    And statefulBeanToCsv is built
    And generateCSVByTitle is evoked
    Then movieDTOList is write in statefulBeanToCsv
    And CSV file is successfully generated
