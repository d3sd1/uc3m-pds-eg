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

import Transport4Future.TokenManagement.exception.TokenManagementException;
import Transport4Future.TokenManagement.model.skeleton.DeserializationConstraintChecker;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * The type File manager.
 */
public class FileManager {
    /**
     * Read file string.
     *
     * @param filePath the file path
     * @return the string
     * @throws IOException the io exception
     */
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

    /**
     * Read json file t.
     *
     * @param <T>           the type parameter
     * @param filePath      the file path
     * @param typeReference the type reference
     * @return the t
     * @throws IOException the io exception
     */
    public <T> T readJsonFile(String filePath, TypeReference<?> typeReference) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return (T) objectMapper.readValue(
                this.readFile(filePath),
                typeReference
        );
    }

    /**
     * Read json file t.
     *
     * @param <T>              the type parameter
     * @param filePath         the file path
     * @param deserializeClass the deserialize class
     * @return the t
     * @throws IOException the io exception
     */
    public <T> T readJsonFile(String filePath, Class<T> deserializeClass) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(this.readFile(filePath), deserializeClass);
    }

    /**
     * Write object to json file.
     *
     * @param <T>      the type parameter
     * @param filePath the file path
     * @param content  the content
     * @throws IOException the io exception
     */
    public <T> void writeObjectToJsonFile(String filePath, T content) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectWriter writer = objectMapper.writer(new DefaultPrettyPrinter());
        writer.writeValue(new File(filePath), content);
    }

    /**
     * Read json file with constraints t.
     *
     * @param <T>              the type parameter
     * @param filePath         the file path
     * @param deserializeClass the deserialize class
     * @return the t
     * @throws IOException              the io exception
     * @throws TokenManagementException the token management exception
     */
    public <T extends DeserializationConstraintChecker> T readJsonFileWithConstraints(String filePath, Class<T> deserializeClass) throws IOException, TokenManagementException {
        T obj = this.readJsonFile(filePath, deserializeClass);
        obj.areConstraintsPassed();
        return obj;
    }

    /**
     * Create path recursive boolean.
     *
     * @param path the path
     * @return the boolean
     */
    public boolean createPathRecursive(String path) {
        return new File(path).mkdirs();
    }

    /**
     * Create json file if not exists.
     *
     * @param <T>      the type parameter
     * @param filePath the file path
     * @param content  the content
     * @throws IOException the io exception
     */
    public <T> void createJsonFileIfNotExists(String filePath, T content) throws IOException {
        this.writeObjectToJsonFile(filePath, content);
    }
}
