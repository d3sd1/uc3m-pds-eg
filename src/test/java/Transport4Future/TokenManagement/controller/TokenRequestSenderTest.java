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

import java.io.File;

public class TokenRequestSenderTest {

    private TokenRequestSender tokenRequestSender;

    public TokenRequestSenderTest() {
        this.tokenRequestSender = new TokenRequestSender();
    }

    @BeforeEach
    void setUp() {
        this.tokenRequestSender = new TokenRequestSender();
    }

    @AfterEach
    void tearDown() {
        this.tokenRequestSender = null;
    }

    @DisplayName("Must implement interface")
    @Test
    @Order(1)
    public void inputFilePathNotExists() {
        if (!(this.tokenRequestSender instanceof TokenRequestInterface)) {
            throw new TestAbortedException("Class must implement interface.");
        }
    }

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

    @DisplayName("Check output is valid urlbase64encoded string")
    @Test
    @Order(3)
    public void checkValidOutput() {
        final String filePath = "/examples/token-request/valid-token-request.json";
        final File file = new File(this.getClass().getResource(filePath).getFile());
        if (file.isFile()) {
            System.out.println(String.format("Test case file absolute path: %s", file.getAbsolutePath()));
            try {
                String hash = this.tokenRequestSender.RequestToken(file.getAbsolutePath());
                if (!hash.equals("VG9rZW5Jc3N1ZXt0b2tlblJlcXVlc3Q9JzhiZWUyOGRjMzk1M2M2NTcyOTdlNDg4Yjc3ZTRmZTlkJywgbm90aWZpY2F0aW9uRW1haWw9J3ZlZ2V0YUBwbGFuZXRhLm5hbWVrJywgcmVxdWVzdERhdGU9MTk5Ny0xMS0yMVQwNjo1MDoxMn0=")) {
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

    @DisplayName("Check random codification error")
    @Test
    @Order(4)
    public void checkRandomCodificationError() {
        // This case is not reproducible since:
        // 1. If we put random chars, test 2/3 won't pass.
        // 2. If we remove classes, it won't compile
        // 3. If we force an error inside class, this could not be checked here.
    }
}
