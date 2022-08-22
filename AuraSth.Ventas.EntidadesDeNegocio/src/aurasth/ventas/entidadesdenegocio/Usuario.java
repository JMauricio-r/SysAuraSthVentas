
package aurasth.ventas.entidadesdenegocio;

import java.time.LocalDate;


public class Usuario {
    
    private int id;
    private int idRol;
    private String nombre;
    private String Apellido;
    private String email;
    private String login;
    private String contrasenia;
    private byte estado;
    private LocalDate fecha;
    private String confirmar_contrasenia;
    private int top_aux;   
    private Rol rol;

    public Usuario() {
    }

    public Usuario(int id, int idRol, String nombre, String Apellido, String email, String login, String contrasenia, byte estado, LocalDate fecha) {
        this.id = id;
        this.idRol = idRol;
        this.nombre = nombre;
        this.Apellido = Apellido;
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
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return Apellido;
    }

    public void setApellido(String Apellido) {
        this.Apellido = Apellido;
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

    public String getConfirmar_contrasenia() {
        return confirmar_contrasenia;
    }

    public void setConfirmar_contrasenia(String confirmar_contrasenia) {
        this.confirmar_contrasenia = confirmar_contrasenia;
    }

    public int getTop_aux() {
        return top_aux;
    }

    public void setTop_aux(int top_aux) {
        this.top_aux = top_aux;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }
    
    
    
}
