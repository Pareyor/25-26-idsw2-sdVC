package com.jorgestor.backend.dto;

public class DocenteDTO {
    private Long id;
    private String username;
    private String email;
    private String nombre;
    private String apellidos;
    private String password;

    public DocenteDTO() {}

    public DocenteDTO(Long id, String username, String email, String nombre, String apellidos, String password) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.password = password;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getApellidos() { return apellidos; }
    public void setApellidos(String apellidos) { this.apellidos = apellidos; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}
