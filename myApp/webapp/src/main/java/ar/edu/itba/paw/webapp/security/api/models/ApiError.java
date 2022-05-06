package ar.edu.itba.paw.webapp.security.api.models;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiError implements Serializable {
    private String property;
    private String description;

    public ApiError() {
        // For MessageBodyWriter
    }

    public ApiError(String property, String description) {
        this.property = property;
        this.description = description;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

