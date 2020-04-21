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

package Transport4Future.TokenManagement.service;

import Transport4Future.TokenManagement.exception.JsonConstraintsException;
import Transport4Future.TokenManagement.exception.JsonIncorrectRepresentationException;
import Transport4Future.TokenManagement.exception.NullPatternException;
import Transport4Future.TokenManagement.exception.TokenManagementException;
import Transport4Future.TokenManagement.model.skeleton.DeserializationConstraintChecker;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class FileManager {
    public String readFile(String filePath) throws IOException {
        StringBuilder fileContents = new StringBuilder();
        BufferedReader reader;
        reader = new BufferedReader(new FileReader(filePath));

        String line;

        while ((line = reader.readLine()) != null) {
            fileContents.append(line);
        }

        reader.close();
        return fileContents.toString();
    }

    public <T> T readJsonFile(String filePath, Class<T> deserializeClass) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(this.readFile(filePath), deserializeClass);
    }

    public <T extends DeserializationConstraintChecker> T readJsonFileWithConstraints(String filePath, Class<T> deserializeClass) throws IOException, JsonConstraintsException, TokenManagementException, JsonIncorrectRepresentationException, NullPatternException {
        T obj = this.readJsonFile(filePath, deserializeClass);
        obj.areConstraintsPassed();


        return obj;
    }
}
