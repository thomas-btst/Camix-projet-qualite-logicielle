package camix.service;

import camix.communication.ProtocoleChat;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * TODO(4) - ServiceChat.supprimeCanal : levee d'exception sur canal par defaut.
 * NOTE IA - Ce fichier de test a ete genere par IA (Codex).
 */
@DisplayName("TODO4 - ServiceChat.supprimeCanal")
class ServiceChatSupprimeCanalTest
{
    @Test
    @DisplayName("AAA: tentative de suppression du canal par defaut -> exception")
    void supprimeCanal_CanalParDefaut_LeveException() throws CanalChat.Exception
    {
        // Arrange
        final String nomCanalDefaut = "general";
        final ServiceChat service = new ServiceChat(nomCanalDefaut);

        // Act
        final CanalChat.Exception exception = assertThrows(
                CanalChat.Exception.class,
                () -> service.supprimeCanal(nomCanalDefaut)
        );

        // Assert
        assertEquals(
                String.format(ProtocoleChat.MESSAGE_SUPPRESSION_CANAL_PAR_DEFAUT, nomCanalDefaut),
                exception.getMessage()
        );
        assertTrue(service.donneInformationsCanaux().contains(nomCanalDefaut));
    }
}
