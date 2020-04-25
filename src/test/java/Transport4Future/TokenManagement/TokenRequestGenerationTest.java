package Transport4Future.TokenManagement;

import static org.junit.jupiter.api.Assertions.*;

import Transport4Future.TokenManagement.controller.TokenController;
import Transport4Future.TokenManagement.exception.TokenManagementException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

public class TokenRequestGenerationTest {
	private TokenController myManager;
	
	public TokenRequestGenerationTest () {
		 myManager = TokenController.getInstance ();
	}
	
	@DisplayName ("Invalid Test Cases")
	@ParameterizedTest(name = "{index} -with the input ''{0}'' error expected is ''{1}''")
	@CsvFileSource(resources = "/invalidTestCasesRequestGenerationTest.csv")
	void InvalidTestCases(String InputFilePath, String expectedMessage) {
		TokenManagementException ex = Assertions.assertThrows(TokenManagementException.class, ()-> {
			myManager.generate(InputFilePath);
		});
		assertEquals (expectedMessage,ex.getMessage());
	}
	
	@DisplayName ("Valid Test Cases")
	@ParameterizedTest(name = "{index} -with the input ''{0}'' output expected is ''{1}''")
	@CsvFileSource(resources = "/validTestCasesRequestGenerationTest.csv")
	void ValidTestCases(String InputFilePath, String Result) throws TokenManagementException {
		String myResult = myManager.generate(InputFilePath);
		assertEquals (Result,myResult);
	}
}

