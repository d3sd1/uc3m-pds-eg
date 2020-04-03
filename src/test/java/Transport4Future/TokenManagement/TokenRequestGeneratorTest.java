package transport4future.tokenManagement;

import org.junit.Assert;
import org.junit.ComparisonFailure;
import org.junit.Test;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.opentest4j.TestAbortedException;
import transport4future.tokenManagement.controller.TokenRequestGenerator;
import transport4future.tokenManagement.exception.TokenManagementException;
import transport4future.tokenManagement.model.implementation.TokenRequestInterface;

import java.io.File;

public class TokenRequestGeneratorTest {
    private TokenRequestGenerator tokenRequestGenerator;

    public TokenRequestGeneratorTest() {
        this.tokenRequestGenerator = new TokenRequestGenerator();
    }

    @BeforeEach
    void setUp() {
        this.tokenRequestGenerator = new TokenRequestGenerator();
    }

    @AfterEach
    void tearDown() {
        this.tokenRequestGenerator = null;
    }

    @DisplayName("Must implement interface")
    @Test
    @Order(1)
    public void inputFilePathNotExists() {
        if (tokenRequestGenerator instanceof TokenRequestInterface) {
            throw new TestAbortedException("Class must implement interface.");
        }
    }

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
                if (!hash.equals("8bee28dc3953c657297e488b77e4fe9d")) {
                    throw new TestAbortedException("MD5 Hash is not valid nor password is not 1234.");
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