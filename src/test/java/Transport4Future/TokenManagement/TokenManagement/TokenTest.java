package Transport4Future.TokenManagement.TokenManagement;

import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import transport4future.tokenManagement.TokenManagementException;
import transport4future.tokenManagement.TokenManager;
import transport4future.tokenManagement.TokenRequest;


public class TokenTest {
    public TokenManager manager;
    private String JsonFilesFolder;

    public TokenTest() {
        JsonFilesFolder = System.getProperty("g80.t2.eg") + "/JSON/TokenRequest";
        manager = new TokenManager();
    }

    @DisplayName("File is missing")
    @Test
    void FileIsMissingTest() throws TokenManagementException {
        String FilePath = this.JsonFilesFolder + "asdCorrect.json";
        String expectedMessage = "Error: input file not found.";
        TokenManagementException ex = Assertions.assertThrows(TokenManagementException.class, () -> {
            manager.TokenRequestGeneration(FilePath);
        });
    }

    @DisplayName("Correct Token Generation")
    @Test
    void CorrectTokenGenerationTest() throws TokenManagementException {
        String FilePath = this.JsonFilesFolder + "Correct.json";
        String expectedToken = "wdfsd3543"; //Solo hat que modificar esto y ya
        String obtainedToken = manager.TokenRequestGeneration(FilePath);
        assertEquals(expectedToken, obtainedToken);
    }
}