package br.dev.marcoalmeida.mb.utils;

import br.dev.marcoalmeida.mb.AbstractTests;
import br.dev.marcoalmeida.mb.domain.Movie;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.opentest4j.AssertionFailedError;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
class ReflexivePairTests extends AbstractTests {

	@Test
	void WhenStaticallyConstructed_ExpectValues() {
		assertThat(PAIR_1_2.getFirst()).isEqualTo(MOVIE_1);
		assertThat(PAIR_1_2.getSecond()).isEqualTo(MOVIE_2);
	}


	@Test
	void WhenReflexivePairSameObject_ExpectAssertionFailure() {
		assertThatThrownBy(()-> ReflexivePair.of(MOVIE_1, MOVIE_1))
				.isInstanceOf(AssertionFailedError.class);

		assertThatThrownBy(()-> ReflexivePair.of(MOVIE_1, MOVIE_1_OTHER_INSTANCE))
				.isInstanceOf(AssertionFailedError.class);
	}

	@Test
	void WhenReflexivePairEquals_PairOrderDoesNotMatter() {
		assertThat(PAIR_1_2.equals(new Object())).isFalse();
		assertThat(PAIR_1_2.equals(PAIR_1_2)).isTrue();
		assertThat(PAIR_1_2.equals(PAIR_1_2_REVERSED)).isTrue();
		assertThat(PAIR_1_2_REVERSED.equals(PAIR_1_2)).isTrue();
		assertThat(PAIR_1_2.equals(PAIR_1_3)).isFalse();
	}

	@Test
	void WhenReflexiveInSet_NoDuplicates() {
		Set<ReflexivePair<Movie>> testSet = new HashSet<>();

		testSet.add(PAIR_1_2);
		assertThat(testSet).hasSize(1);

		testSet.add(PAIR_1_3);
		assertThat(testSet).hasSize(2);

		testSet.add(PAIR_1_2_REVERSED);
		assertThat(testSet).hasSize(2);
	}


}
