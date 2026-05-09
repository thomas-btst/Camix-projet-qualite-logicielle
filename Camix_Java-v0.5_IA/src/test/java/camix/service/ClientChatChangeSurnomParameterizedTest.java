package camix.service;

import camix.communication.ProtocoleChat;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * TODO(5) - ClientChat.changeSurnom :
 * test parametre avec @EnumSource et @MethodSource.
 * NOTE IA - Ce fichier de test a ete genere par IA (Codex).
 */
@DisplayName("TODO5 - ClientChat.changeSurnom (parameterized)")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ClientChatChangeSurnomParameterizedTest
{
    private static final String SURNOM_INITIAL = "ancien";

    private static Method changeSurnomMethod;
    private static Field surnomField;

    private CanalChatTrace canal;
    private ClientChat client;

    private enum SurnomDepuisEnum
    {
        ALPHA("Alpha"),
        BRAVO("Bravo_42"),
        CHARLIE("Charlie");

        private final String valeur;

        SurnomDepuisEnum(String valeur)
        {
            this.valeur = valeur;
        }
    }

    @BeforeAll
    static void prepareReflection() throws NoSuchMethodException, NoSuchFieldException
    {
        changeSurnomMethod = ClientChat.class.getDeclaredMethod("changeSurnom", String.class);
        changeSurnomMethod.setAccessible(true);

        surnomField = ClientChat.class.getDeclaredField("surnom");
        surnomField.setAccessible(true);
    }

    @BeforeEach
    void setUp()
    {
        // Arrange
        this.canal = new CanalChatTrace();
        this.client = new ClientChat(null, "client-1", SURNOM_INITIAL, this.canal);
    }

    @ParameterizedTest(name = "EnumSource -> nouveau surnom: {0}")
    @EnumSource(SurnomDepuisEnum.class)
    @DisplayName("EnumSource: changement de surnom et message de canal")
    void changeSurnom_EnumSource_EnvoieLeMessageAttendu(SurnomDepuisEnum donnee)
            throws IllegalAccessException, InvocationTargetException
    {
        // Act
        invokeChangeSurnom(donnee.valeur);

        // Assert
        assertEquals(donnee.valeur, surnomField.get(this.client));
        assertEquals(
                String.format(ProtocoleChat.MESSAGE_CHANGEMENT_SURNOM, SURNOM_INITIAL, donnee.valeur),
                this.canal.derniereDiffusion
        );
        assertEquals(1, this.canal.nombreDiffusions);
    }

    @ParameterizedTest(name = "MethodSource -> nouveau surnom: {0}")
    @MethodSource("methodSourceSurnoms")
    @DisplayName("MethodSource: changement de surnom et message de canal")
    void changeSurnom_MethodSource_EnvoieLeMessageAttendu(String nouveauSurnom)
            throws IllegalAccessException, InvocationTargetException
    {
        // Act
        invokeChangeSurnom(nouveauSurnom);

        // Assert
        assertEquals(nouveauSurnom, surnomField.get(this.client));
        assertEquals(
                String.format(ProtocoleChat.MESSAGE_CHANGEMENT_SURNOM, SURNOM_INITIAL, nouveauSurnom),
                this.canal.derniereDiffusion
        );
        assertEquals(1, this.canal.nombreDiffusions);
    }

    private static Stream<String> methodSourceSurnoms()
    {
        return Stream.of("neo", "Test_User", "X");
    }

    private void invokeChangeSurnom(String surnom)
            throws InvocationTargetException, IllegalAccessException
    {
        changeSurnomMethod.invoke(this.client, surnom);
    }

    private static class CanalChatTrace extends CanalChat
    {
        private String derniereDiffusion;
        private int nombreDiffusions;

        CanalChatTrace()
        {
            super("trace");
        }

        @Override
        public void envoieClients(String message)
        {
            this.derniereDiffusion = message;
            this.nombreDiffusions++;
        }
    }
}
