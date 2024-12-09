# 🛠️ Hamming Code Error Detection & Correction Tool

## 🌟 Overview

The **Hamming Code Error Detection & Correction Tool** is a web-based application designed to encode and decode binary data using Hamming code. It provides a user-friendly interface for encoding and decoding data, both from text input and file uploads. The tool also allows users to introduce errors into encoded data and decode it to check for corrections.

## 🚀 Features

- **📝 Encode Data**: Encode text input or file contents using Hamming code.
- **🔍 Decode Data**: Decode encoded data to check for and correct errors.
- **📂 File Upload**: Upload files for encoding or decoding.
- **✏️ Error Introduction**: Modify encoded data to introduce errors and verify correction capabilities.
- **📊 Data Display**: Display encoded and decoded data with formatting options.

## 🛠️ Installation

To get started with the project, follow these steps:

1. **Clone the Repository**

    ```bash
    git clone https://github.com/your-username/hamming-code-error-detection.git
    cd hamming-code-error-detection
    ```

2. **Set Up the Project**

    Ensure you have [Apache Tomcat](https://tomcat.apache.org/) or a similar servlet container set up to run the web application.

3. **Deploy the Application**

    - Copy the project files to your web application's deployment directory.
    - Ensure that the necessary libraries (e.g., JSTL) are included in your classpath.

4. **Build the Project**

    If you're using Maven or another build tool, build the project to generate the WAR file.

5. **Run the Application**

    Deploy the WAR file to your servlet container and start the server.

## 🧑‍💻 Usage

1. **🔗 Access the Application**

    Open your web browser and navigate to `http://localhost:8080/Web-Based Data-Transmission-Error-Detection-and Correction-Tool/`.

2. **📝 Encode Data**

    - Enter data into the text area or upload a text file.
    - Click the "Encode" button to encode the data.

3. **🔍 Decode Data**

    - Click the "Decode Data" button to decode the previously encoded data.

4. **✏️ Introduce Errors**

    - Use the "Introduce Error" button to make the encoded data editable and introduce errors.

## 📂 Code Structure

- **`/src/main/java/com/servlet/EncoderServiceServlet.java`**: Servlet that handles encoding, decoding, and file uploads.
- **`/src/main/java/com/encoder/HammingCode.java`**: Class for Hamming code encoding and decoding with correction.
- **`/src/main/java/com/encoder/Util.java`**: Utility class for encoding and decoding ASCII data supporting HammingCode class.
- **`/index.jsp`**: Main JSP page with forms and data display. 

## 🤝 Contributing

Contributions are welcome! Please fork the repository and submit pull requests for any improvements or bug fixes.

## 📜 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 🙏 Acknowledgments

- [Tailwind CSS](https://tailwindcss.com/) for styling.
- [JSTL](https://jakarta.ee/specifications/jsp/2.1/jsp-2.1-spec.html) for tag library support.

## 📬 Contact

For any questions or feedback, please contact [here](https://vinayhajare.engineer).

