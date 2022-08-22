
package aurasth.ventas.entidadesdenegocio;

import java.time.LocalDate;

public class Empleado {
    
    private int id;
    private int idRol;
    private String nombre;
    private String Apellido;
    private String contacto;
    private String numeroDocumento;
    private byte estado;
    private LocalDate fecha;
    private int top_aux;
    private Rol rol;

    public Empleado() {
    }

    public Empleado(int id, int idRol, String nombre, String Apellido, String contacto, String numeroDocumento, byte estado, LocalDate fecha) {
        this.id = id;
        this.idRol = idRol;
        this.nombre = nombre;
        this.Apellido = Apellido;
        this.contacto = contacto;
        this.numeroDocumento = numeroDocumento;
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

    public String getContacto() {
        return contacto;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }

    public String getNumeroDocumento() {
        return numeroDocumento;
    }

    public void setNumeroDocumento(String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
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

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }
    
    
}
