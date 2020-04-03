package transport4future.tokenManagement.model.implementation;

import transport4future.tokenManagement.exception.TokenManagementException;

public interface TokenRequestInterface {
    String RequestToken(String inputFile) throws TokenManagementException;
}
