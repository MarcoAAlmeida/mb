package br.dev.marcoalmeida.mb.mapper;

import br.dev.marcoalmeida.mb.domain.Player;
import br.dev.marcoalmeida.mb.dto.PlayerDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.WARN, componentModel = "spring")
public interface PlayerMapper {
    PlayerMapper INSTANCE = Mappers.getMapper(PlayerMapper.class);
    PlayerDTO convert(Player player);
}
