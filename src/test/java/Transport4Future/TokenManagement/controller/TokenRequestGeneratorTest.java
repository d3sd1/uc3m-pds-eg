package transport4future.tokenManagement.controller;

import org.junit.Assert;
import org.junit.ComparisonFailure;
import org.junit.Test;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.opentest4j.TestAbortedException;
import transport4future.tokenManagement.controller.TokenRequestGenerator;
import transport4future.tokenManagement.exception.TokenManagementException;
import transport4future.tokenManagement.model.implementation.TokenRequestGeneratorInterface;
import transport4future.tokenManagement.model.implementation.TokenRequestInterface;
import transport4future.tokenManagement.utils.Constants;

import java.io.File;

/**
 * The type Token request generator test.
 */
public class TokenRequestGeneratorTest {
    private TokenRequestGenerator tokenRequestGenerator;

    /**
     * Instantiates a new Token request generator test.
     */
    public TokenRequestGeneratorTest() {
        Constants.HASH_PASSWORD = "1234";
        this.tokenRequestGenerator = new TokenRequestGenerator();
    }

    /**
     * Sets up.
     */
    @BeforeEach
    void setUp() {
        this.tokenRequestGenerator = new TokenRequestGenerator();
    }

    /**
     * Tear down.
     */
    @AfterEach
    void tearDown() {
        this.tokenRequestGenerator = null;
    }

    /**
     * Caso de Prueba: mustImplementInterfaces
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
        if (!(tokenRequestGenerator instanceof TokenRequestGeneratorInterface)) {
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
    @CsvFileSource(resources = "/examples/token-request-generator/constraints.csv")
    @Order(2)
    public void constraints(String filePath, String expectedExceptionMessage, String hint) {
        final String relFilePath = String.format(
                "/examples/token-request-generator/invalid-token-request/%s.json", filePath
        );
        final File file = new File(this.getClass().getResource(relFilePath).getFile());
        if (file.isFile()) {
            System.out.println(String.format("Test case file absolute path: %s", file.getAbsolutePath()));
            TokenManagementException tokenManagementException = Assertions.assertThrows(TokenManagementException.class, () -> {
                this.tokenRequestGenerator.TokenRequestGeneration(file.getAbsolutePath());
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
     * Revisa que el programa sí funciona correctamente y devuelve el tokenRequestGeneration codigocado.
     */
    @DisplayName("Check output is valid MD5 rand-password hashed string")
    @Test
    @Order(3)
    public void checkValidOutput() {
        final String filePath = "/examples/token-request-generator/valid-token.json";
        final File file = new File(this.getClass().getResource(filePath).getFile());
        if (file.isFile()) {
            System.out.println(String.format("Test case file absolute path: %s", file.getAbsolutePath()));
            try {
                String hash = this.tokenRequestGenerator.TokenRequestGeneration(file.getAbsolutePath());

                if (!hash.equals("de236411f0c8654103fe9198b92c9632")) {
                    throw new TestAbortedException("MD5 Hash is not valid nor password is not 1234.");
                }
            } catch (TokenManagementException e) {
                throw new TestAbortedException("MD5 Hash is not valid nor password is not 1234.");
            }
        } else {
            throw new TestAbortedException(
                    String.format(
                            "Input case file does not exist (testing error): %s",
                            filePath));
        }
    }
}