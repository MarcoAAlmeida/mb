package br.dev.marcoalmeida.mb.mapper;

import br.dev.marcoalmeida.mb.domain.Movie;
import br.dev.marcoalmeida.mb.dto.csv.MovieDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.WARN, componentModel = "spring")
public interface MovieMapper {
    MovieMapper INSTANCE = Mappers.getMapper(MovieMapper.class);
    MovieDTO convert(Movie movie);
    Movie convert(MovieDTO movieDTO);
}
