/*
 * Copyright (c) 2020.
 * Content created by:
 * - Andrei García Cuadra
 * - Miguel Hernández Cassel
 *
 * For the module PDS, on university Carlos III de Madrid.
 * Do not share, review nor edit any content without implicitly asking permission to it's owners, as you can contact by this email:
 * andreigarciacuadra@gmail.com
 *
 * All rights reserved.
 */

package Transport4Future.TokenManagement;

import Transport4Future.TokenManagement.controller.TokenManager;
import Transport4Future.TokenManagement.exception.TokenManagementException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TokenRequestTest {

    private final TokenManager myManager;

    public TokenRequestTest() {
        myManager = new TokenManager();
    }

    @DisplayName("Caso de prueba - Eliminación de Llave Inicial")
    @Test
    void inicio() {
        String InputFilePath = "./TestData/TokenRequestTest/WithoutInitialBrace.json";
        String expectedMessage = "Error: JSON object cannot be created due to incorrect representation";
        TokenManagementException ex = Assertions.assertThrows(TokenManagementException.class, () -> {
            myManager.request(InputFilePath);
        });
        assertEquals(expectedMessage, ex.getMessage());

    }

    @DisplayName("Invalid Test Cases")
    @ParameterizedTest(name = "{index} -with the input ''{0}'' error expected is ''{1}''")
    @CsvFileSource(resources = "/invalidTestCasesRequestTokenTestReduced.csv")
    void InvalidTestCases(String InputFilePath, String expectedMessage) {
        TokenManagementException ex = Assertions.assertThrows(TokenManagementException.class, () -> {
            myManager.request(InputFilePath);
        });
        assertEquals(expectedMessage, ex.getMessage());
    }

    @DisplayName("Valid Test Cases")
    @ParameterizedTest(name = "{index} -with the input ''{0}'' output expected is ''{1}''")
    @CsvFileSource(resources = "/validTestCasesRequestTokenTest.csv")
    void ValidTestCases(String InputFilePath, String Result) throws TokenManagementException {
        String myResult = myManager.request(InputFilePath);
        assertEquals(Result, myResult);
    }
}
