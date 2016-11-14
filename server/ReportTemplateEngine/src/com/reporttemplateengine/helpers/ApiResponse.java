package com.reporttemplateengine.helpers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public class ApiResponse {

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Object data;

	@JsonSerialize(using = HttpStatusSerializer.class)
    private HttpStatus status;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String error;

    public ApiResponse() {
        this(null);
    }

    public ApiResponse(Object data) {
        this.data = data;
        this.error = null;
    }

    public ResponseEntity<ApiResponse> send(HttpStatus status) {
        this.status = status;
        return new ResponseEntity<ApiResponse>(this, status);
    }

    public ResponseEntity<ApiResponse> send(HttpStatus status, String error) {
        this.status = status;
        this.error = error;
        return new ResponseEntity<ApiResponse>(this, status);
    }

    public Object getData() {
        return data;
    }

    public String getError() {
        return error;
    }

    public HttpStatus getStatus() {
        return this.status;
    }
}