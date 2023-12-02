package br.dev.marcoalmeida.mb.controller;

import br.dev.marcoalmeida.mb.MbAbstractTest;
import br.dev.marcoalmeida.mb.service.RoundService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class PlayerControllerTests extends MbAbstractTest {

    @Mock
    private RoundService roundService;

    @InjectMocks
    private PlayerController playerController;

    @Test
    public void WhenUnfinishedGameExists_UnfinishedGameByPlayerDTOReturned() {
        // TODO
    }

}
