<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
    // Check if there's an error attribute in the request
    if(request.getAttribute("error") != null) {
        String errorMessage = (String) request.getAttribute("error");
%>
        <!-- Display the error message -->
        <div style="color: red;">
            <strong>Error:</strong> <%= errorMessage %>
        </div>
<%
    }
%>


<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Hamming Code Error Detection &amp Correction</title>
    <link href="https://cdn.jsdelivr.net/npm/tailwindcss@2.2.16/dist/tailwind.min.css" rel="stylesheet" />
    <style>
        body {
            margin: 2rem;
            background-color: #f5ffef;
        }

        .container {
            max-width: 1400px;
            margin: 0 auto;
            padding: 20px;
            border: 1px solid #4e4c4c;
            background-color: #313430;
            color: #000000;
            border-radius: 10px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }

        .console {
            border: 1px solid #333;
            border-radius: 5px;
            padding: 10px;
            margin-top: 10px;
            background-color: #f0f0f0;
        }

        .console-content {
            font-family: "Courier New", monospace;
            padding: 10px;
            background-color: #000000;
            color: #fff;
            border-radius: 5px;
        }

        .separator {
            font-weight: bold;
            font-size: 18px;
            color: #ffffff;
            margin: 10px;
        }

        .file-upload {
            display: inline-block;
            margin: 10px;
        }

        .file-upload input[type="file"] {
            display: none;
        }

        .file-upload label {
            background-color: #10b981;
            color: #fff;
            padding: 10px 20px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
        }

        .file-upload label:hover {
            background-color: #3e8e41;
        }

        .header {
            color: #ff8001;
        }
        
        .parity-bit {
            background-color: yellow; /* Highlight color for parity bits */
            font-weight: bold;
        }

        /* Add media query for mobile devices */
        @media (max-width: 768px) {
            .grid-cols-2 {
                grid-template-columns: 1fr;
            }

            .col-span-1 {
                grid-column: 1 / -1;
            }
        }
    </style>
</head>

<body>
    <div class="font-mono container mx-auto p-4 md:w-4/5 lg:w-3/4 xl:w-2/3">
        <h1
            class="header m-8 text-center text-2xl font-bold leading-7 text-gray-900 sm:truncate sm:text-3xl sm:tracking-tight">
            Hamming Code Error Detection &amp Correction Web Tool
        </h1>
        <form action="EncoderServiceServlet" method="post" enctype="multipart/form-data">
            <div class="grid grid-cols-2 gap-4">
                <div class="col-span-1 border-gray-300 border rounded-md p-4">
                    <h2 class="header text-xl text-center font-bold mb-2">Data</h2>
                    <textarea class="whitespace-pre-wrap text-base font-serif w-full max-w-full h-32 border-gray-300 border rounded-md resize-none p-2 overflow-x-auto" name="text-input" placeholder="Enter your data here">${inputData != null ? inputData : ""}</textarea>
                    <span class="font-mono separator">OR</span>
                    <div class="file-upload">
                        <label for="file-input"> Upload File </label>
                        <input type="file" accept=".txt" class="hidden" id="file-input" name="file-input" />
                    </div>
                    <button class="px-4 py-2 bg-blue-500 text-white rounded-md hover:bg-blue-700 ml-2 mt-2"
                        type="submit" name="action" value="encode" title="Encode data">
                        Encode
                    </button>
                </div>
                <div class="col-span-1 border-gray-300 border rounded-md p-4">
                    <h2 class="header text-xl text-center font-bold mb-2">Encoded Data</h2>
                    <textarea class="whitespace-pre-wrap text-base font-serif w-full max-w-full h-32 border-gray-300 border rounded-md resize-none p-2 overflow-x-auto" placeholder="Encoded data will appear here" name="encoded-data" readonly>${encodedData != null ? encodedData : ""}</textarea>
                    <div class="flex justify-center mt-2">
                        <button class="px-4 py-2 bg-red-500 text-white rounded-md hover:bg-red-700 mr-2"
                            value="introduce-error" title="Make editable to introduce error">
                            Introduce Error
                        </button>
                        <button class="px-4 py-2 bg-green-500 text-white rounded-md hover:bg-green-700"
                            name="action" value="decode" title="Decode data">
                            Decode Data
                        </button>
                    </div>
                </div>
            </div>
        </form>
        <div class="grid grid-cols-2 gap-4 mt-4">
            <div class="col-span-1 border-gray-300 border rounded-md p-4">
                <h2 class="header text-xl text-center font-bold mb-2">Decoded Data</h2>
                <textarea class="whitespace-pre-wrap text-base font-serif w-full h-64 border-gray-300 border rounded-md resize-none p-2" placeholder="Decoded data will appear here" readonly>${decodedData != null ? decodedData : ""}</textarea>
            </div>
            <div class="col-span-1 border-gray-300 border rounded-md p-4">
                <h2 class="header text-xl text-center font-bold mb-2">Console</h2>
                <div class="console w-full h-64">
                    <div class="console-content h-60 overflow-y-scroll">
                        ${consoleData != null ? consoleData : ""}
                    </div>
                </div>
            </div>
        </div>
    </div>
    <script>

        const fileInput = document.getElementById('file-input');
        
        fileInput.addEventListener('change', (e) => {
            const file = fileInput.files[0];
            alert(`File uploaded successfully: ${file.name}`);
        });

        document.querySelector(".bg-red-500").addEventListener("click", function (event) {
                event.preventDefault();
        		var encodedTextArea = document.querySelector(
                    'textarea[placeholder="Encoded data will appear here"]'
                );
                var isEditable = encodedTextArea.getAttribute("readonly") === null;

                if (isEditable) {
                    encodedTextArea.setAttribute("readonly", "readonly");
                    this.title = "Make editable to introduce error";
                    this.classList.toggle("bg-red-500");
                    this.classList.toggle("bg-green-500");
                } else {
                    encodedTextArea.removeAttribute("readonly");
                    encodedTextArea.focus();
                    this.title = "Make readonly";
                    this.classList.toggle("bg-red-500");
                    this.classList.toggle("bg-green-500");
                }
            });
    </script>
</body>

</html>