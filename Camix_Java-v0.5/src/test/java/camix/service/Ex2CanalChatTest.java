package camix.service;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@DisplayName("Etant donné un CanalChat vide à sa création")
@ExtendWith(MockitoExtension.class)
public class Ex2CanalChatTest {
    @Mock
    private ClientChat clientChat;

    private CanalChat SUT;

    @BeforeEach
    void setup() {
        SUT = new CanalChat("SUT");
        Assumptions.assumeTrue(SUT.donneNombreClients() == 0, "Le canal ne doit contenir aucun client");
    }

    @Nested
    @DisplayName("Quand on ajoute un client")
    class AddClient {
        @BeforeEach
        public void addClient() {
            when(clientChat.donneId()).thenReturn("1");
            SUT.ajouteClient(clientChat);
        }

        @Test
        @DisplayName("Le client est seul dans le canal")
        public void isClientChatAlone() {
            Assertions.assertTrue(SUT.estPresent(clientChat), "Le canal doit contenir le client");
            Assertions.assertEquals(1, SUT.donneNombreClients(), "Le canal doit contenir un seul client");
        }

        @Nested
        @DisplayName("Quand on ajoute à nouveau ce client")
        class AddSameClient {
            @BeforeEach
            public void addClient() {
                SUT.ajouteClient(clientChat);
            }

            @Test
            @DisplayName("Le client est toujours seul dans le canal")
            public void isClientStillAlone() {
                Assertions.assertEquals(1, SUT.donneNombreClients(), "Le canal doit contenir le client");
                Assertions.assertTrue(SUT.estPresent(clientChat), "Le canal doit contenir le client");
            }
        }
    }
}