package com.jorgestor.backend.dto;

public class MenuOptionDTO {
    private String title;
    private String path;
    private String icon;

    public MenuOptionDTO() {}

    public MenuOptionDTO(String title, String path, String icon) {
        this.title = title;
        this.path = path;
        this.icon = icon;
    }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getPath() { return path; }
    public void setPath(String path) { this.path = path; }
    public String getIcon() { return icon; }
    public void setIcon(String icon) { this.icon = icon; }
}
