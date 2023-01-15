package br.dev.marcoalmeida.mb.services;

import br.dev.marcoalmeida.mb.domain.Movie;
import br.dev.marcoalmeida.mb.utils.ReflexivePair;
import lombok.AllArgsConstructor;
import lombok.Setter;
import org.apache.commons.math3.util.ArithmeticUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Service
@AllArgsConstructor
@Setter
public class GameService {

    private static int MAX_PICK_RETRIES;
    public static final String INTERRUPTED_MESSAGE = "could not pick unused MoviePair, max of %d retries reached";

    private MoviePairService moviePairService;

    @Value("${game.maxPickRetries}")
    public void setMaxPickRetries(int maxPickRetries){
        MAX_PICK_RETRIES = maxPickRetries;
    }

    public ReflexivePair<Movie> pick(List<Movie> movieList, Set<ReflexivePair<Movie>> usedPairs) throws InterruptedException{

        assertThat(usedPairs.size())
                .isLessThan((int) ArithmeticUtils.binomialCoefficient(movieList.size(), 2));

        ReflexivePair<Movie> newPick;
        int i = 0;

        do{
            newPick = moviePairService.pickRandom(movieList);
            i++;
        }while((i<MAX_PICK_RETRIES) && (usedPairs.contains(newPick)));

        if (usedPairs.contains(newPick))
            throw new InterruptedException(String.format(INTERRUPTED_MESSAGE, i));

        return newPick;
    }


}
