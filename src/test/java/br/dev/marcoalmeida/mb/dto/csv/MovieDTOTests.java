package br.dev.marcoalmeida.mb.dto.csv;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;

import static br.dev.marcoalmeida.mb.dto.csv.MovieDTO.NEEDS_TITLE;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class MovieDTOTests {

    private static final String NON_EMPTY_STRING = "smth";
    private static final Long SOME_LONG = 1984L;

    private static final Double SOME_RATING = 1.1;

    @Test
    public void WhenTitleIsEmpty_MovieDTOIsInvalid(){
        MovieDTO movieDTO = MovieDTO.builder()
                .releaseYear(SOME_LONG)
                .votes(SOME_LONG)
                .posterUrl(NON_EMPTY_STRING)
                .rating(SOME_RATING)
                .build();

        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()){
            Validator validator = factory.getValidator();

            Set<ConstraintViolation<MovieDTO>> constraintViolations = validator.validate(movieDTO);

            assertThat(constraintViolations).isNotEmpty();
            assertThat(constraintViolations).hasSize(1);

            ConstraintViolation<MovieDTO> violation = constraintViolations.stream().findFirst().get();

            assertThat(violation.getMessage()).isEqualTo(NEEDS_TITLE);

        }



    }

}
