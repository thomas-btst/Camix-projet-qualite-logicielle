package camix.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * TODO(2) - meme scenario que TODO(1), structure en GWT via @Nested.
 * TODO(3-bonus) - activation de l'extension de dependance hierarchique.
 * NOTE IA - Ce fichier de test a ete genere par IA (Codex).
 */
@ExtendWith({MockitoExtension.class, SkipOnFailureInEnclosingClassExtension.class})
@DisplayName("TODO2 - CanalChat.ajouteClient en GWT / @Nested")
class CanalChatAjouteClientNestedGwtTest
{
    @Mock
    private ClientChat client;

    private CanalChat canal;

    @Nested
    @DisplayName("Etant donne un CanalChat vide a sa creation")
    class GivenCanalVide
    {
        @BeforeEach
        void arrange()
        {
            // Given
            canal = new CanalChat("general");
            when(client.donneId()).thenReturn("client-1");
        }

        @Nested
        @DisplayName("Quand on ajoute un client")
        class WhenOnAjouteUnClient
        {
            @BeforeEach
            void act()
            {
                // When
                canal.ajouteClient(client);
            }

            @Test
            @DisplayName("Alors celui-ci est seul dans le canal")
            void thenClientEstSeulDansLeCanal()
            {
                // Then
                assertEquals(1, canal.donneNombreClients());
                verify(client, atLeastOnce()).donneId();
            }

            @Nested
            @SkipOnFailureInEnclosingClass
            @DisplayName("Quand on ajoute a nouveau ce client")
            class AndWhenOnAjouteANouveauCeClient
            {
                @BeforeEach
                void actAgain()
                {
                    // When
                    canal.ajouteClient(client);
                }

                @Test
                @DisplayName("Alors celui-ci est toujours seul dans le canal")
                void thenClientEstToujoursSeulDansLeCanal()
                {
                    // Then
                    assertEquals(1, canal.donneNombreClients());
                    verify(client, atLeastOnce()).donneId();
                }
            }
        }
    }
}
