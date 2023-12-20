package com.connect.web.interceptor;

import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class CustomHttpRequestWrapper extends HttpServletRequestWrapper {
    private String requestBody;

    public CustomHttpRequestWrapper(HttpServletRequest request) {
        super(request);

        try {
            requestBody = readRequestBody(request);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(getInputStream()));
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(requestBody.getBytes());

        return new ServletInputStream() {

            @Override
            public int read() throws IOException {
                return byteArrayInputStream.read();
            }

            @Override
            public void setReadListener(ReadListener listener) {
            }

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public boolean isFinished() {
                return false;
            }
        };
    }

    private String readRequestBody(HttpServletRequest request) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader bufferedReader = request.getReader();
        char[] charBuffer = new char[8192];
        int bytesRead;
        while ((bytesRead = bufferedReader.read(charBuffer)) != -1) {
            stringBuilder.append(charBuffer, 0, bytesRead);
        }

        return stringBuilder.toString();
    }
}

