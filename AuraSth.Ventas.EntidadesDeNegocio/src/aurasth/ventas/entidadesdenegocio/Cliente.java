
package aurasth.ventas.entidadesdenegocio;
import java.time.LocalDate;
import java.util.ArrayList;

public class Cliente {

  private int Id ;
  private String  Nombre;
  private String Apellido;
  private String Email;
  private String Login;
  private int Contrasenia;
  private LocalDate Fecha;
  private String ConfirmarContrasenia; 
  private int top_aux;
  private ArrayList<Venta>ventas;
  private ArrayList<Carrito>carritos;
  
    public Cliente() {
    }

    public Cliente(int Id, String Nombre, String Apellido, String Email, String Login, int Contrasenia, LocalDate Fecha) {
        this.Id = Id;
        this.Nombre = Nombre;
        this.Apellido = Apellido;
        this.Email = Email;
        this.Login = Login;
        this.Contrasenia = Contrasenia;
        this.Fecha = Fecha;
    }

    
    
    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    public String getApellido() {
        return Apellido;
    }

    public void setApellido(String Apellido) {
        this.Apellido = Apellido;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public String getLogin() {
        return Login;
    }

    public void setLogin(String Login) {
        this.Login = Login;
    }

    public int getContrasenia() {
        return Contrasenia;
    }

    public void setContrasenia(int Contrasenia) {
        this.Contrasenia = Contrasenia;
    }

    public LocalDate getFecha() {
        return Fecha;
    }

    public void setFecha(LocalDate Fecha) {
        this.Fecha = Fecha;
    }

    public String getConfirmarContrasenia() {
        return ConfirmarContrasenia;
    }

    public void setConfirmarContrasenia(String ConfirmarContrasenia) {
        this.ConfirmarContrasenia = ConfirmarContrasenia;
    }

    public int getTop_aux() {
        return top_aux;
    }

    public void setTop_aux(int top_aux) {
        this.top_aux = top_aux;
    }

    public ArrayList<Venta> getVentas() {
        return ventas;
    }

    public void setVentas(ArrayList<Venta> ventas) {
        this.ventas = ventas;
    }

    public ArrayList<Carrito> getCarritos() {
        return carritos;
    }

    public void setCarritos(ArrayList<Carrito> carritos) {
        this.carritos = carritos;
    }

  
    
}
