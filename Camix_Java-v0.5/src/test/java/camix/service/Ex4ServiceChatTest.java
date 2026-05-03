package camix.service;

import camix.communication.ProtocoleChat;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.concurrent.TimeUnit;

@DisplayName("Service Chat Test")
@ExtendWith(MockitoExtension.class)
public class Ex4ServiceChatTest {
    private ServiceChat SUT;
    private final String defaultCanalName = "Default";

    @BeforeEach
    @Timeout(value = 2, unit = TimeUnit.SECONDS, threadMode = Timeout.ThreadMode.SEPARATE_THREAD)
    public void setup() {
       SUT = new ServiceChat(defaultCanalName);
    }

    @Test
    @DisplayName("Doit retourner une erreur en cas de suppression du canal par défaut")
    @Timeout(value = 2, unit = TimeUnit.SECONDS, threadMode = Timeout.ThreadMode.SEPARATE_THREAD)
    public void shouldThrowsWhenDeletingTheDefaultCanal() {
        CanalChat.Exception exception = Assertions.assertThrows(
                CanalChat.Exception.class,
                () -> SUT.supprimeCanal(defaultCanalName),
                "Exception non envoyée"
        );
        Assertions.assertEquals(
            String.format(ProtocoleChat.MESSAGE_SUPPRESSION_CANAL_PAR_DEFAUT, defaultCanalName),
            exception.getMessage(),
            "Message de l'exception incorrect"
        );
    }
}
