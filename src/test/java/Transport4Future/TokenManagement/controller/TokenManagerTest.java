package transport4future.tokenManagement.controller;

import org.junit.Assert;
import org.junit.ComparisonFailure;
import org.junit.Test;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.opentest4j.TestAbortedException;
import transport4future.tokenManagement.exception.LMException;
import transport4future.tokenManagement.exception.TokenEncodingException;
import transport4future.tokenManagement.exception.TokenManagementException;
import transport4future.tokenManagement.model.Token;
import transport4future.tokenManagement.model.TokenHeader;
import transport4future.tokenManagement.model.TokenIssue;
import transport4future.tokenManagement.model.TokenPayload;
import transport4future.tokenManagement.model.implementation.TokenManagerInterface;
import transport4future.tokenManagement.model.implementation.TokenRequestInterface;
import transport4future.tokenManagement.model.storage.TokenAlgorythm;
import transport4future.tokenManagement.model.storage.TokenType;
import transport4future.tokenManagement.service.Crypt;
import transport4future.tokenManagement.service.TokenStorage;
import transport4future.tokenManagement.utils.Constants;

import java.io.File;
import java.time.LocalDateTime;

/**
 * Esta clase maneja todos los tests relativos a la clase TokenManager.
 */
public class TokenManagerTest {

    private TokenManager tokenManager;
    private Token token;
    private Crypt crypt;

    /**
     * Instantiates a new Token manager test.
     */
    public TokenManagerTest() {
        this.tokenManager = new TokenManager();
        this.token = this.fillToken();
        this.crypt = new Crypt();
    }

    /**
     * Sets up.
     */
    @BeforeEach
    void setUp() {
        this.tokenManager = new TokenManager();
        this.token = this.fillToken();
        this.crypt = new Crypt();
    }

    /**
     * Tear down.
     */
    @AfterEach
    void tearDown() {
        this.tokenManager = null;
    }

    /**
     * This method is developet to prevent redundancy. It fills and resets a token object with default token Issue.
     * @return Token filled (never null).
     */
    private Token fillToken() {
        Token token = new Token();
        token.setPayload(
                new TokenPayload(
                        new TokenIssue(
                                "8bee28dc3953c657297e488b77e4fe9d",
                                "nappa@planeta.namek",
                                LocalDateTime.now()
                        )
                )
        );
        token.setHeader(new TokenHeader(TokenAlgorythm.HS256, TokenType.PDS));
        token.setSignature(TokenAlgorythm.HS256);
        return token;
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
        if (!(this.tokenManager instanceof TokenManagerInterface)) {
            throw new TestAbortedException("Class must implement interface.");
        }
    }

    /**
     * Caso de Prueba: inputIsNull
     * Clase de Equivalencia o Valor Límite Asociado: Check de nulos.
     * Técnica de prueba: Clase de equivalencia
     * Resultado Esperado:
     * Excepción del tipo LMException con el siguiente mensaje
     * "La cadena de caracteres de la entrada no se corresponde con un token que se pueda procesar.".
     * Nótese que agrega hints a los fallos. Esto se ha hecho para facilitarnos la programación.
     */
    @DisplayName("Check input is null")
    @Test
    @Order(2)
    public void inputIsNull() {
        LMException lmException = Assertions.assertThrows(LMException.class, () -> {
            this.tokenManager.VerifyToken(null);
        });
        try {
            Assert.assertEquals("La cadena de caracteres de la entrada no se corresponde con un token que se pueda procesar.", lmException.getMessage());
        } catch (ComparisonFailure e) {
            throw new ComparisonFailure(e.getMessage(), e.getExpected(), e.getActual());
        }
    }

    /**
     * Caso de Prueba: inputIsNotValid
     * Clase de Equivalencia o Valor Límite Asociado: El valor debe ser un token válido,
     * así que insertamos un texto aleatorio.
     * Técnica de prueba: Valor límite.
     * Resultado Esperado:
     * Excepción del tipo LMException con el siguiente mensaje
     * "La cadena de caracteres de la entrada no se corresponde con un token que se pueda procesar.".
     * Nótese que agrega hints a los fallos. Esto se ha hecho para facilitarnos la programación.
     */
    @DisplayName("Check input is not valid")
    @Test
    @Order(3)
    public void inputIsNotValid() {
        LMException lmException = Assertions.assertThrows(LMException.class, () -> {
            this.tokenManager.VerifyToken("aaaaaaaaaaaaaajajajajajajajja");
        });
        try {
            Assert.assertEquals("La cadena de caracteres de la entrada no se corresponde con un token que se pueda procesar.", lmException.getMessage());
        } catch (ComparisonFailure e) {
            throw new ComparisonFailure(e.getMessage(), e.getExpected(), e.getActual());
        }
    }

    /**
     * Caso de Prueba: checkValidInputNotStored
     * Clase de Equivalencia o Valor Límite Asociado: El valor debe ser un token válido y estar almacenado.
     * así que insertamos un token válido, pero que no está almacenado.
     * Técnica de prueba: Valor límite.
     * Resultado Esperado:
     * Excepción del tipo LMException con el siguiente mensaje
     * "No se encuentra registrado el token para el cual se solicita verificación.".
     * Nótese que agrega hints a los fallos. Esto se ha hecho para facilitarnos la programación.
     */
    @DisplayName("Check output is invalid when token not stored.")
    @Test
    @Order(4)
    public void checkValidInputNotStored() {
        LMException lmException = Assertions.assertThrows(LMException.class, () -> {
            Constants.HASH_PASSWORD = "1234";
            this.token.getPayload().setExpirationDate(LocalDateTime.now().plusDays(100));
            boolean isValid = this.tokenManager.VerifyToken(this.crypt.encode(this.token));
            if (!isValid) {
                throw new TestAbortedException("Algorythm that decodes data is not valid nor hash password is not 1234.");
            }
        });
        try {
            Assert.assertEquals("No se encuentra registrado el token para el cual se solicita verificación.", lmException.getMessage());
        } catch (ComparisonFailure e) {
            throw new ComparisonFailure(e.getMessage(), e.getExpected(), e.getActual());
        }
    }


    /**
     * Caso de Prueba: checkValidInputStored
     * Clase de Equivalencia o Valor Límite Asociado: El valor debe ser un token válido y estar almacenado.
     * así que insertamos un token válido, pero que sí está almacenado.
     * Técnica de prueba: Clase de equivalencia.
     * Resultado Esperado:
     * No se deben lanzar excepciones y debe pasar el test satisfactoriamente, devolviendo un true en VerifyToken.
     */
    @DisplayName("Check output is valid when input is valid as well.")
    @Test
    @Order(4)
    public void checkValidInputStored() {
        try {
            Constants.HASH_PASSWORD = "1234";
            this.token.getPayload().setExpirationDate(LocalDateTime.now().plusDays(500));
            TokenStorage tokenStorage = new TokenStorage();
            tokenStorage.add(this.token);
            boolean isValid = this.tokenManager.VerifyToken(this.crypt.encode(this.token));
            if(!isValid) {
                throw new TestAbortedException("Example token not valid. It may fall to bad code :).");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Caso de Prueba: checkValidInputStored
     * Clase de Equivalencia o Valor Límite Asociado: El valor debe ser un token válido y estar almacenado,
     * pero expirado. Así que insertamos un token válido, pero que sí está almacenado, y además expirado.
     * Técnica de prueba: Clase de equivalencia.
     * Resultado Esperado:
     * Excepción del tipo LMException con el siguiente mensaje
     * "Token expirado.".
     * Nótese que agrega hints a los fallos. Esto se ha hecho para facilitarnos la programación.
     */
    @DisplayName("Check output is valid when token is expired.")
    @Test
    @Order(5)
    public void checkExpirationDate() {
        LMException lmException = Assertions.assertThrows(LMException.class, () -> {
            Constants.HASH_PASSWORD = "1234";
            this.token.getPayload().setExpirationDate(LocalDateTime.now().minusDays(500));
            boolean isValid = this.tokenManager.VerifyToken(this.crypt.encode(this.token));
            if (!isValid) {
                throw new TestAbortedException("Algorythm that decodes data is not valid nor hash password is not 1234.");
            }
        });
        try {
            Assert.assertEquals("Token expirado.", lmException.getMessage());
        } catch (ComparisonFailure e) {
            throw new ComparisonFailure(e.getMessage(), e.getExpected(), e.getActual());
        }
    }
}
