
package aurasth.ventas.entidadesdenegocio;

import java.time.LocalDate;

public class Usuario {
    
    private int id;
    private int idRol;
    private String Nombre;
    private String apellido;
    private String email;
    private String login;
    private String contrasenia;
    private byte estado;
    private LocalDate fecha;
    private int top_aux;
    private String confirmar_password;
    private Rol roles;

    public Usuario() {
    }

    public Usuario(int id, int idRol, String Nombre, String apellido, String email,
            String login, String contrasenia, byte estado, LocalDate fecha) {
        this.id = id;
        this.idRol = idRol;
        this.Nombre = Nombre;
        this.apellido = apellido;
        this.email = email;
        this.login = login;
        this.contrasenia = contrasenia;
        this.estado = estado;
        this.fecha = fecha;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdRol() {
        return idRol;
    }

    public void setIdRol(int idRol) {
        this.idRol = idRol;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public byte getEstado() {
        return estado;
    }

    public void setEstado(byte estado) {
        this.estado = estado;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public int getTop_aux() {
        return top_aux;
    }

    public void setTop_aux(int top_aux) {
        this.top_aux = top_aux;
    }

    public String getConfirmar_password() {
        return confirmar_password;
    }

    public void setConfirmar_password(String confirmar_password) {
        this.confirmar_password = confirmar_password;
    }

    public Rol getRoles() {
        return roles;
    }

    public void setRoles(Rol roles) {
        this.roles = roles;
    }
    
    
    
    
}
