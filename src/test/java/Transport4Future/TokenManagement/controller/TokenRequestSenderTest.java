package transport4future.tokenManagement.controller;

import org.junit.Assert;
import org.junit.ComparisonFailure;
import org.junit.Test;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.opentest4j.TestAbortedException;
import transport4future.tokenManagement.exception.TokenManagementException;
import transport4future.tokenManagement.exception.TokenStorageException;
import transport4future.tokenManagement.model.Token;
import transport4future.tokenManagement.model.TokenHeader;
import transport4future.tokenManagement.model.TokenPayload;
import transport4future.tokenManagement.model.TokenRequest;
import transport4future.tokenManagement.model.implementation.TokenRequestInterface;
import transport4future.tokenManagement.model.storage.TokenAlgorythm;
import transport4future.tokenManagement.model.storage.TokenType;
import transport4future.tokenManagement.service.TokenStorage;
import transport4future.tokenManagement.utils.Constants;

import java.io.File;

/**
 * The type Token request sender test.
 */
public class TokenRequestSenderTest {

    private TokenRequestSender tokenRequestSender;

    /**
     * Instantiates a new Token request sender test.
     */
    public TokenRequestSenderTest() {
        Constants.HASH_PASSWORD = "1234";
        this.tokenRequestSender = new TokenRequestSender();
    }

    /**
     * Sets up.
     */
    @BeforeEach
    void setUp() {
        this.tokenRequestSender = new TokenRequestSender();
    }

    /**
     * Tear down.
     */
    @AfterEach
    void tearDown() {
        this.tokenRequestSender = null;
    }

    /**
     * Caso de Prueba: mustImplementInterface
     * Clase de Equivalencia o Valor Límite Asociado: N/A
     * Técnica de prueba: Clase de equivalencia
     * Resultado Esperado:
     * Positivo en caso de que implemente la interfaz requerida,
     * en caso negativo el test será inválido.
     */
    @DisplayName("Must implement interface")
    @Test
    @Order(1)
    public void mustImplementInterface() {
        if (!(this.tokenRequestSender instanceof TokenRequestInterface)) {
            throw new TestAbortedException("Class must implement interface.");
        }
    }

    /**
     * Caso de Prueba: constraints
     * Clase de Equivalencia o Valor Límite Asociado: Ambas. Incluye clases de equivalencia y valores límite.
     * Técnica de prueba: Clase de equivalencia y valor límite.
     * Resultado Esperado:
     * Dependiendo del caso, ya que es un test parametrizado. Cada caso contiene el archivo input, el resultado
     * esperado y un hint, el cual se muestra abajo en caso de que falle.
     *
     * @param filePath                 the file path
     * @param expectedExceptionMessage the expected exception message
     * @param hint                     the hint
     */
    @DisplayName("Check input constraints")
    @ParameterizedTest(name = "{index} - with input {0} expects value {1}")
    @CsvFileSource(resources = "/examples/token-request/constraints.csv")
    @Order(2)
    public void constraints(String filePath, String expectedExceptionMessage, String hint) {
        final String relFilePath = String.format(
                "/examples/token-request/invalid/%s.json", filePath
        );
        final File file = new File(this.getClass().getResource(relFilePath).getFile());
        if (file.isFile()) {
            System.out.println(String.format("Test case file absolute path: %s", file.getAbsolutePath()));
            TokenManagementException tokenManagementException = Assertions.assertThrows(TokenManagementException.class, () -> {
                this.tokenRequestSender.RequestToken(file.getAbsolutePath());
            });
            try {
                Assert.assertEquals(expectedExceptionMessage, tokenManagementException.getMessage());
            } catch (ComparisonFailure e) {
                /**
                 * Wrap exception (if thrown), by putting hint around it and rethrowing it.
                 */
                System.out.println("-----------------------------");
                System.out.println(
                        String.format(
                                "There was an error doing current test. However, there's your hint: %s %s",
                                System.lineSeparator(),
                                hint
                        )
                );
                System.out.println("-----------------------------");
                throw new ComparisonFailure(e.getMessage(), e.getExpected(), e.getActual());
            }

        } else {
            throw new TestAbortedException(
                    String.format(
                            "Input from CSV case file does not exist (testing error): %s",
                            filePath));
        }
    }

    /**
     * Caso de Prueba: checkValidOutput
     * Clase de Equivalencia o Valor Límite Asociado: Clase de equivalencia.
     * Técnica de prueba: Clase de equivalencia.
     * Resultado Esperado:
     * Revisa que el programa sí funciona correctamente y devuelve el tokenRequest codificado.
     */
    @DisplayName("Check output is valid urlbase64encoded string")
    @Test
    @Order(3)
    public void checkValidOutput() {
        final String filePath = "/examples/token-request/valid-token-request.json";
        final File file = new File(this.getClass().getResource(filePath).getFile());
        if (file.isFile()) {
            System.out.println(String.format("Test case file absolute path: %s", file.getAbsolutePath()));
            try {
                Constants.HASH_PASSWORD = "1234";
                String hash = this.tokenRequestSender.RequestToken(file.getAbsolutePath());
                if (!hash.equals("eyJoZWFkZXIiOnsiYWxnIjoiSFMyNTYiLCJ0eXAiOiJQRFMifSwicGF5bG9hZCI6eyJ0b2tlblJlcXVlc3QiOnsidG9rZW5SZXF1ZXN0IjoiOGJlZTI4ZGMzOTUzYzY1NzI5N2U0ODhiNzdlNGZlOWQiLCJub3RpZmljYXRpb25FbWFpbCI6InZlZ2V0YUBwbGFuZXRhLm5hbWVrIiwicmVxdWVzdERhdGUiOnsiaG91ciI6NiwibWludXRlIjo1MCwic2Vjb25kIjoxMiwieWVhciI6MTk5NywibW9udGgiOiJOT1ZFTUJFUiIsImRheU9mTW9udGgiOjIxLCJkYXlPZldlZWsiOiJGUklEQVkiLCJuYW5vIjowLCJtb250aFZhbHVlIjoxMSwiZGF5T2ZZZWFyIjozMjUsImNocm9ub2xvZ3kiOnsiaWQiOiJJU08iLCJjYWxlbmRhclR5cGUiOiJpc284NjAxIn19fSwiaXNzdWVkQXQiOnsiaG91ciI6NiwibWludXRlIjo1MCwic2Vjb25kIjoxMiwieWVhciI6MTk5NywibW9udGgiOiJOT1ZFTUJFUiIsImRheU9mTW9udGgiOjIxLCJkYXlPZldlZWsiOiJGUklEQVkiLCJuYW5vIjowLCJtb250aFZhbHVlIjoxMSwiZGF5T2ZZZWFyIjozMjUsImNocm9ub2xvZ3kiOnsiaWQiOiJJU08iLCJjYWxlbmRhclR5cGUiOiJpc284NjAxIn19LCJleHBpcmF0aW9uRGF0ZSI6bnVsbH0sInNpZ25hdHVyZSI6IkhTMjU2In0=")) {
                    throw new TestAbortedException("Custom encoding is not valid nor password is not 1234.");
                }
            } catch (TokenManagementException e) {
                e.printStackTrace();
            }
        } else {
            throw new TestAbortedException(
                    String.format(
                            "Input case file does not exist (testing error): %s",
                            filePath));
        }
    }
}
