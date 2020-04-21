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

package Transport4Future.TokenManagement.controller.skeleton;

import Transport4Future.TokenManagement.exception.TokenManagementException;

public interface ITokenManagement {

	String generate(String InputFile) throws TokenManagementException;

	String request(String InputFile) throws TokenManagementException;

	boolean verify(String Token) throws TokenManagementException;


}
