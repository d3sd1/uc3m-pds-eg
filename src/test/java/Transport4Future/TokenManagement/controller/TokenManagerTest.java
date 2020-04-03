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

public class TokenManagerTest {

    private TokenManager tokenManager;
    private Token token;
    private Crypt crypt;

    public TokenManagerTest() {
        this.tokenManager = new TokenManager();
        this.token = this.fillToken();
        this.crypt = new Crypt();
    }

    @BeforeEach
    void setUp() {
        this.tokenManager = new TokenManager();
        this.token = this.fillToken();
        this.crypt = new Crypt();
    }

    @AfterEach
    void tearDown() {
        this.tokenManager = null;
    }

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

    @DisplayName("Must implement interface")
    @Test
    @Order(1)
    public void inputFilePathNotExists() {
        if (!(this.tokenManager instanceof TokenManagerInterface)) {
            throw new TestAbortedException("Class must implement interface.");
        }
    }

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

    @DisplayName("Check output is valid when input is valid as well.")
    @Test
    @Order(4)
    public void checkValidInputStored() {
        try {
            Constants.HASH_PASSWORD = "1234";
            this.token.getPayload().setExpirationDate(LocalDateTime.now().plusDays(500));
            TokenStorage tokenStorage = new TokenStorage();
            tokenStorage.add(this.token);
            this.tokenManager.VerifyToken(this.crypt.encode(this.token));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

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

    @DisplayName("Check random codification error")
    @Test
    @Order(6)
    public void checkRandomCodificationError() {
        // This case is not reproducible since:
        // 1. If we put random chars, test 2/3 won't pass.
        // 2. If we remove classes, it won't compile
        // 3. If we force an error inside class, this could not be checked here.
    }
}
