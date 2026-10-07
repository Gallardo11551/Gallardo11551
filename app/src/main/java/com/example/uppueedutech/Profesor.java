package com.example.uppueedutech;

public class Profesor {

    private String numeroEmpleado;
    private String nombre;
    private String correo;
    private String telefono;
    private String sexo;
    private String especialidad;
    private String gradoAcademico;
    private String materias;

    // Constructor vacío (obligatorio para Firebase)
    public Profesor() {
    }

    // Constructor con parámetros
    public Profesor(String numeroEmpleado,
                    String nombre,
                    String correo,
                    String telefono,
                    String sexo,
                    String especialidad,
                    String gradoAcademico,
                    String materias) {

        this.numeroEmpleado = numeroEmpleado;
        this.nombre = nombre;
        this.correo = correo;
        this.telefono = telefono;
        this.sexo = sexo;
        this.especialidad = especialidad;
        this.gradoAcademico = gradoAcademico;
        this.materias = materias;
    }

    // Getters y Setters

    public String getNumeroEmpleado() {
        return numeroEmpleado;
    }

    public void setNumeroEmpleado(String numeroEmpleado) {
        this.numeroEmpleado = numeroEmpleado;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public String getGradoAcademico() {
        return gradoAcademico;
    }

    public void setGradoAcademico(String gradoAcademico) {
        this.gradoAcademico = gradoAcademico;
    }

    public String getMaterias() {
        return materias;
    }

    public void setMaterias(String materias) {
        this.materias = materias;
    }
}