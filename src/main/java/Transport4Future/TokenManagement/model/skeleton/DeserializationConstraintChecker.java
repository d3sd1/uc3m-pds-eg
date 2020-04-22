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

package Transport4Future.TokenManagement.model.skeleton;

import Transport4Future.TokenManagement.exception.TokenManagementException;
import com.fasterxml.jackson.databind.JsonMappingException;

public interface DeserializationConstraintChecker {
    boolean areConstraintsPassed() throws TokenManagementException, JsonMappingException;
}
