package transport4future.tokenManagement.model.implementation;

import transport4future.tokenManagement.exception.LMException;

public interface TokenManagerInterface {
    boolean VerifyToken(String token) throws LMException;
}
