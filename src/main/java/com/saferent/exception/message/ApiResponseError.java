package com.saferent.exception.message;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public class ApiResponseError {

    // AMACIM : customer error mesajlarinin ana sablonu olusturmak
    private HttpStatus status;

    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern = "dd-MM-yyy HH:mm:ss")
    private LocalDateTime timestamp;

    private String message;

    private String requestURI;

    // Const

    private ApiResponseError(){
        timestamp = LocalDateTime.now();
    }

    public ApiResponseError(HttpStatus status){
        this(); // yukardaki parametresiz private const. cagriliyor
        this.message="Unexpected Error";
        this.status = status;
    }

    public ApiResponseError(HttpStatus status, String message, String requestURI){
        this(status); // yukardaki 1 parametreli, public const. cagriliyor
        this.message = message;
        this.requestURI = requestURI;
    }



    // Getter - Setter


    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getRequestURI() {
        return requestURI;
    }

    public void setRequestURI(String requestURI) {
        this.requestURI = requestURI;
    }

}
