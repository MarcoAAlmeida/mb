package br.dev.marcoalmeida.mb.service;

import br.dev.marcoalmeida.mb.MbAbstractTest;
import br.dev.marcoalmeida.mb.repository.GameRepository;
import br.dev.marcoalmeida.mb.repository.RoundRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
public class PlayerServiceTest extends MbAbstractTest {
    @Mock
    private GameRepository gameRepository;

    @Mock
    private RoundRepository roundRepository;

    @InjectMocks
    private PlayerService playerService;

    @Test
    public void WhenUnfinishedGameExists_UnfinishedGameByPlayerDTOReturned(){
        // TODO
    }

    @Test
    public void WhenUnfinishedGameDoesNotExist_MbExceptionIsThrown(){
        // TODO
    }

    public void WhenPlayerIdDoesNotExist_MbExceptionIsThrown(){
        // TODO
    }


}
