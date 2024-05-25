package com.hg.service.dto.mydto;

import java.time.LocalDate;

public class UploadImageDTO {

    private String objectURL;
    private LocalDate lastModified;
    private LocalDate lastModifiedDate;
    private String name;
    private int number;
    private String type;
    private String webkitRelativePath;

    public String getObjectURL() {
        return objectURL;
    }

    public void setObjectURL(String objectURL) {
        this.objectURL = objectURL;
    }

    public LocalDate getLastModified() {
        return lastModified;
    }

    public void setLastModified(LocalDate lastModified) {
        this.lastModified = lastModified;
    }

    public LocalDate getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(LocalDate lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getWebkitRelativePath() {
        return webkitRelativePath;
    }

    public void setWebkitRelativePath(String webkitRelativePath) {
        this.webkitRelativePath = webkitRelativePath;
    }
}
