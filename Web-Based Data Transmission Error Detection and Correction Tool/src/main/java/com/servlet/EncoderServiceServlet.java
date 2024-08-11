package com.servlet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import com.encoder.Util;


@WebServlet(urlPatterns = "/EncoderServiceServlet")
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024,
    maxFileSize = 1024 * 1024 * 5,
    maxRequestSize = 1024 * 1024 * 5 * 5
)
public class EncoderServiceServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final String UPLOAD_DIRECTORY = "tmp/uploads";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String action = request.getParameter("action");
        String textData = request.getParameter("text-input");
        String encodedData = request.getParameter("encoded-data");
        Part filePart = request.getPart("file-input");

        try {
            if ("encode".equals(action)) {
                if (textData != null && !textData.trim().isEmpty()) {
                    // Encode text area data
                    StringBuilder encodedContent = new StringBuilder();
                    List<String> lines = Arrays.asList(textData.split("\r?\n"));
                    for (String line : lines) {
                        encodedContent.append(Util.encodeLine(line)).append("\n");
                    }
                    request.setAttribute("encodedData", encodedContent.toString().trim());
                    request.setAttribute("inputData", textData);
                } else if (filePart != null && filePart.getSize() > 0) {
                    // Handle file upload
                    File uploadDir = new File(UPLOAD_DIRECTORY);
                    if (!uploadDir.exists()) {
                        uploadDir.mkdirs();
                    }
                    String fileName = Path.of(filePart.getSubmittedFileName()).getFileName().toString();
                    File file = new File(uploadDir, fileName);
                    filePart.write(file.getAbsolutePath());

                    // Process file
                    List<String> lines = Util.readLines(file);
                    StringBuilder encodedContent = new StringBuilder();
                    for (String line : lines) {
                        encodedContent.append(Util.encodeLine(line)).append('\n');
                    }
                    
                    request.setAttribute("encodedData", encodedContent.toString().trim());
                    String inputData = lines.stream().collect(Collectors.joining("\n"));
                    request.setAttribute("inputData", inputData);
                }
                RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
                dispatcher.forward(request, response);

            } else if ("decode".equals(action)) {
                // Handle decoding
            	if(encodedData != null && !encodedData.trim().isEmpty()) {
            		StringBuilder decodedContent = new StringBuilder();
            		List<String> encodedLines = Arrays.asList(encodedData.split("\r?\n"));
            		List<String> logCollector = new ArrayList<>();
            		for(String line : encodedLines) {
            			decodedContent.append(Util.decodeLine(line, logCollector)).append("\n");
            		}
            		
            		String consoleData = logCollector.stream().collect(Collectors.joining("<br>"));
          
            		request.setAttribute("consoleData", consoleData);
            		request.setAttribute("inputData", textData);
            		request.setAttribute("encodedData", encodedData);
            		request.setAttribute("decodedData", decodedContent.toString().trim());
            	}
            	
            	RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
                dispatcher.forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "An Internal Server Error occurred.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
            dispatcher.forward(request, response);
        }
    }
    
    @Override
    public void destroy() {
        File uploadDir = new File(UPLOAD_DIRECTORY);
        for (File file : uploadDir.listFiles()) {
            file.delete(); // Clean up files
        }
        super.destroy();
    }
}
