package com.paxos.problemone;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * This servlet is used for parsing and persisting the JSON text from POST request.
 * @author Xiang Li
 *
 */
@WebServlet("/messages")
public class SimpleServlet extends HttpServlet {
    
    private static final long serialVersionUID = 1L;
    private static final String DIGEST_KEY = "digest";
    private static final String MESSAGES_PREFIX = "messages";
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @Override
    @SuppressWarnings("unchecked")
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        BufferedReader reader = request.getReader();
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            // Read the message JSON
            StringBuffer stringBuffer = new StringBuffer();
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuffer.append(line);
            }

            // Create map object for easier JSON formatting
            // The map that contains original value
            Map<String, String> originalValueMap = gson.fromJson(stringBuffer.toString(), HashMap.class);
            // The map that contains SHA256 encrypted value
            Map<String, String> encryptedValueMap = new HashMap<>();

            for (Map.Entry<String, String> entry : originalValueMap.entrySet()) {
                String value = entry.getValue();
                StringBuffer valueBuffer = new StringBuffer();
                byte[] encryptedValue = digest.digest(value.getBytes(StandardCharsets.UTF_8));
                // Encryption
                for (int i = 0; i < encryptedValue.length; ++i) {
                    valueBuffer.append(Integer.toHexString(0xff & encryptedValue[i]));
                }
                encryptedValueMap.put(DIGEST_KEY, valueBuffer.toString());
                writeOriginalJsonToFile(valueBuffer.toString(), gson.toJson(originalValueMap));
            }
            response.setContentType("application/json");
            out.println(gson.toJson(encryptedValueMap));
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Failed to process the message:"+ e.getMessage());
        }
    }

    /**
     * Write the JSON text to the file system of server.
     * As required by the problem, the JSON should include one key and one value as a map with a single entry.
     * For example:
     * {
     *   "message": "foo"
     * }
     * 
     * @param encryptedValue the SHA256 encrypted value of the original value, which will be used as the filename
     * @param originalJson the JSON text to be written to the file
     * @throws IOException
     */
    private void writeOriginalJsonToFile(String encryptedValue, String originalJson) throws IOException {
        BufferedWriter bufferedWriter = null;
        try {
            String fileDir = this.getServletContext().getRealPath(MESSAGES_PREFIX + "/" + encryptedValue);
            Path path = Paths.get(fileDir);
            Files.createDirectories(path.getParent());
            Files.deleteIfExists(path);
            Files.createFile(path);
            bufferedWriter = new BufferedWriter(new FileWriter(fileDir));
            bufferedWriter.write(originalJson+'\n');
            bufferedWriter.flush();
        } finally {
            if (bufferedWriter != null) {
                bufferedWriter.close();
            }
        }
    }
}