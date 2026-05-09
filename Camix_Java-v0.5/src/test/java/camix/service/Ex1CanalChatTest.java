package camix.service;

import camix.service.CanalChat;
import camix.service.ClientChat;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@DisplayName("Etant donné un CanalChat vide à sa création")
@ExtendWith(MockitoExtension.class)
public class Ex1CanalChatTest {
    @Mock
    private ClientChat clientChat;

    private CanalChat SUT;

    @BeforeEach
    void setup() {
        SUT = new CanalChat("SUT");
        Assumptions.assumeTrue(SUT.donneNombreClients() == 0, "Le canal ne doit contenir aucun client");
    }

    @Test
    @DisplayName("Le client est ajouté correctement")
    public void shouldAddClientCorrectly() {
        String clientId = "1";

        when(clientChat.donneId()).thenReturn(clientId);
        Assumptions.assumeFalse(SUT.estPresent(clientChat), "Le client ne doit pas déjà être présent dans le canal");

        SUT.ajouteClient(clientChat);

        Assertions.assertTrue(SUT.estPresent(clientChat), "Le client doit être présent dans le canal");
        Assertions.assertEquals(1, SUT.donneNombreClients(), "Le canal doit contenir un seul client");

        verify(clientChat, atLeastOnce()).donneId();
    }

    @Test
    @DisplayName("Le client ne doit pas être ajouté s'il est déjà présent")
    public void shouldNotAddClientWhenAlreadyPresent() {
        String id = "1";

        when(clientChat.donneId()).thenReturn(id);

        SUT.ajouteClient(clientChat);
        Assumptions.assumeTrue(SUT.estPresent(clientChat), "Le canal doit contenir le client");
        Assumptions.assumeTrue(SUT.donneNombreClients() == 1, "Le canal doit contenir un seul client");

        SUT.ajouteClient(clientChat);

        Assertions.assertEquals(1, SUT.donneNombreClients(), "Le canal doit contenir un seul client");
        Assertions.assertTrue(SUT.estPresent(clientChat), "Le canal doit toujours contenir le client");

        verify(clientChat, atLeastOnce()).donneId();
    }
}