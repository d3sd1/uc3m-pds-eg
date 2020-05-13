package Transport4Future.TokenManagement;

import Transport4Future.TokenManagement.controller.TokenManager;
import Transport4Future.TokenManagement.exception.TokenManagementException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TokenRequestGenerationTest {
    private final TokenManager myManager;

    public TokenRequestGenerationTest() {
        myManager = TokenManager.getInstance();
    }

    @DisplayName("Invalid Test Cases")
    @ParameterizedTest(name = "{index} -with the input ''{0}'' error expected is ''{1}''")
    @CsvFileSource(resources = "/invalidTestCasesRequestGenerationTest.csv")
    void InvalidTestCases(String InputFilePath, String expectedMessage) {
        TokenManagementException ex = Assertions.assertThrows(TokenManagementException.class, () -> {
            myManager.TokenRequestGeneration(InputFilePath);
        });
		assertEquals (expectedMessage,ex.getMessage());
	}
	
	@DisplayName ("Valid Test Cases")
	@ParameterizedTest(name = "{index} -with the input ''{0}'' output expected is ''{1}''")
	@CsvFileSource(resources = "/validTestCasesRequestGenerationTest.csv")
	void ValidTestCases(String InputFilePath, String Result) throws TokenManagementException {
        String myResult = myManager.TokenRequestGeneration(InputFilePath);
        assertEquals(Result, myResult);
    }
}

