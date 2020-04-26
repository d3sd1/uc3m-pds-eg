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

import java.io.*;
import java.lang.reflect.Type;

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
     * Read file string.
     *
     * @param filePath the file path
     * @return the string
     * @throws IOException the io exception
     */
    public void writeFile(String filePath, String content) throws IOException {
        FileWriter myWriter = new FileWriter(filePath);
        myWriter.write(content);
        myWriter.close();
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
    public <T> T readJsonFile(String filePath, Type typeReference) throws IOException {
        JsonManager jsonManager = new JsonManager();
        return jsonManager.decode(this.readFile(filePath), typeReference);
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
        JsonManager jsonManager = new JsonManager();
        return jsonManager.decode(this.readFile(filePath), deserializeClass);
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
        JsonManager jsonManager = new JsonManager();
        this.writeFile(filePath, jsonManager.encode(content));
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
        if (!(new File(filePath).exists())) {
            this.writeObjectToJsonFile(filePath, content);
        }
    }
}
