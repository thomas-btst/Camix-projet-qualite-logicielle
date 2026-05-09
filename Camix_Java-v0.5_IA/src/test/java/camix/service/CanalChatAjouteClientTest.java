package camix.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

/**
 * TODO(1) - Test JUnit 5 / Mockito sur CanalChat.ajouteClient.
 * NOTE IA - Ce fichier de test a ete genere par IA (Codex).
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("TODO1 - CanalChat.ajouteClient")
class CanalChatAjouteClientTest
{
    private CanalChat canal;

    @Mock
    private ClientChat client;

    @BeforeEach
    void setUp()
    {
        // Arrange
        this.canal = new CanalChat("general");
        when(this.client.donneId()).thenReturn("client-1");
    }

    @Test
    @DisplayName("AAA: client absent -> ajoute et donneId sollicite")
    void ajouteClient_ClientAbsent_AjouteLeClient()
    {
        // Act
        this.canal.ajouteClient(this.client);

        // Assert
        assertEquals(1, this.canal.donneNombreClients());
        verify(this.client, atLeastOnce()).donneId();
        verifyNoMoreInteractions(this.client);
    }

    @Test
    @DisplayName("AAA: client deja present -> pas de doublon et donneId sollicite")
    void ajouteClient_ClientDejaPresent_NeDupliquePasLeClient()
    {
        // Act
        this.canal.ajouteClient(this.client);
        this.canal.ajouteClient(this.client);

        // Assert
        assertEquals(1, this.canal.donneNombreClients());
        verify(this.client, atLeastOnce()).donneId();
        verifyNoMoreInteractions(this.client);
    }
}
