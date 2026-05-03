package camix.service;

import camix.communication.ProtocoleChat;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@ExtendWith(MockitoExtension.class)
public class Ex5ClientChatTest {
    @Mock
    private CanalChat canal;

    public enum TestData4ChangeSurnom {
        DT1("?", "toto"),
        DT2("truc", "titi"),
        DT3("?", "turlu tutu"),
        DT4("tata", "truc");

        final String defaultSurnom;
        final String dt;
        final String msg;

        TestData4ChangeSurnom(String defaultSurnom, String dt) {
            this.defaultSurnom = defaultSurnom;
            this.dt = dt;
            this.msg = String.format(ProtocoleChat.MESSAGE_CHANGEMENT_SURNOM, defaultSurnom, dt);
        }
    }

    @ParameterizedTest
    @EnumSource
    @DisplayName("Doit changer le surnom correctement")
    public void shouldChangeSurnomCorrectly(TestData4ChangeSurnom param) throws NoSuchMethodException, NoSuchFieldException, IllegalAccessException, InvocationTargetException {
        ClientChat SUT = new ClientChat(null,  null, param.defaultSurnom, canal);
        Mockito.doNothing().when(canal).envoieClients(ArgumentMatchers.anyString());
        Method changeSurnom = ClientChat.class.getDeclaredMethod("changeSurnom", String.class);
        Field surnom = ClientChat.class.getDeclaredField("surnom");
        changeSurnom.setAccessible(true);
        surnom.setAccessible(true);
        changeSurnom.invoke(SUT, param.dt);
        Mockito.verify(canal).envoieClients(param.msg);
        Assertions.assertEquals(param.dt, surnom.get(SUT), "Le nom n'a pas été mis à jour");
    }
}
