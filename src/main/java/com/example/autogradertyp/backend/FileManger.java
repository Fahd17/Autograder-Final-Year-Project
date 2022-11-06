/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.autogradertyp.backend;

import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 *
 * @author fahds
 */
public class FileManger {

    public void SaveUploadedFille(MemoryBuffer memoryBuffer) {
        InputStream inputStream = memoryBuffer.getInputStream();

        //making a directory for the submission
        boolean f = new File("C:\\Users\\fahds\\OneDrive\\Documents\\TryingFileUpload\\directory").mkdir();

        try {
            FileOutputStream fileOutputStream = new FileOutputStream("C:\\Users\\fahds\\OneDrive\\Documents\\TryingFileUpload\\FahdGoodProject111.java");
            byte dataBuffer[] = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(dataBuffer, 0, 1024)) != -1) {
                fileOutputStream.write(dataBuffer, 0, bytesRead);
            }
            fileOutputStream.close();

        } catch (IOException IO) {
            System.out.print("Invalid Path");

        }

    }

    public void createAFile() {
        Path path
                = Paths.get("C:\\Users\\fahds\\OneDrive\\Documents\\TryingFileUpload\\FahdIsGreat.txt");

        String str
                = "Fahd is great";
        try {

            Files.writeString(path, str, StandardCharsets.UTF_8);
        } // Catch block to handle the exception
        catch (IOException ex) {

            System.out.print("Invalid Path");
        }
    }

}
