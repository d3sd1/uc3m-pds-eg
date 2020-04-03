package transport4future.tokenManagement.generics;

import org.junit.jupiter.api.*;
import org.opentest4j.TestAbortedException;
import transport4future.tokenManagement.exception.TokenManagementException;
import transport4future.tokenManagement.controller.TokenRequestSender;
import transport4future.tokenManagement.controller.TokenRequestGenerator;
import transport4future.tokenManagement.utils.TestConstants;

import java.io.File;

public class GenericInputFileTest {
    private TokenRequestGenerator tokenRequestGenerator;
    private TokenRequestSender tokenRequestSender;
    private final String fileNotFoundMessage = "No se encuentra el fichero con los datos de entrada.";
    private final String inputFileNotValid = "El fichero de entrada no contiene los datos o el formato esperado.";

    @BeforeEach
    void setUp() {
        this.tokenRequestGenerator = new TokenRequestGenerator();
        this.tokenRequestSender = new TokenRequestSender();
    }

    @AfterEach
    void tearDown() {
        this.tokenRequestGenerator = null;
        this.tokenRequestSender = null;
    }

    /**
     * LM-RF-01-E1
     * This case cover the case that the path does not exists nor the path is not valid at all.
     */
    @DisplayName("Non-existent file paths")
    @Test
    @Order(1)
    void inputFilePathNotExists() {

        final TokenManagementException e = Assertions.assertThrows(TokenManagementException.class, () -> {
            this.tokenRequestSender.RequestToken("/dev/null/this-file-does-not-exists.json");
            this.tokenRequestGenerator.TokenRequestGeneration("/dev/null/this-file-does-not-exists.json");
        });
        this.checkCorrectExceptionMessage(e, this.fileNotFoundMessage);
    }

    /**
     * This case cover if the path is null.
     */
    @Test
    @DisplayName("Null file paths")
    @Order(1)
    void inputFilePathIsNull() {
        final TokenManagementException e = Assertions.assertThrows(
                TokenManagementException.class, () -> {
                    this.tokenRequestGenerator.TokenRequestGeneration(null);
                    this.tokenRequestSender.RequestToken(null);
                });
        this.checkCorrectExceptionMessage(e, this.fileNotFoundMessage);
    }

    /**
     * This case cover if the path is null.
     */
    @Test
    @DisplayName("File exists (but generic content)")
    @Order(1)
    void inputFileExists() {
        final String filePath = TestConstants.GENERIC_FILE_PATH;
        final File file = new File(this.getClass().getResource(filePath).getFile());
        if (file.isFile()) {
            System.out.println(String.format("File absolute path: %s", file.getAbsolutePath()));
            Exception e = Assertions.assertThrows(TokenManagementException.class, () -> {
                this.tokenRequestSender.RequestToken(file.getAbsolutePath());
                this.tokenRequestGenerator.TokenRequestGeneration(file.getAbsolutePath());
            });

            this.checkCorrectExceptionMessage(e, this.inputFileNotValid);
        } else {
            throw new TestAbortedException(
                    String.format(
                            "Input case file does not exist (testing error): %s",
                            filePath));
        }
    }

    private void checkCorrectExceptionMessage(final Exception e, final String msg) {
        if (!e.getMessage().equalsIgnoreCase(msg)) {
            System.out.println("Exception message: "
                    + e.getMessage());
            throw new TestAbortedException(
                    String.format(
                            "Exception message did not match the requirements. Required message: %s",
                            msg));
        }
    }
}