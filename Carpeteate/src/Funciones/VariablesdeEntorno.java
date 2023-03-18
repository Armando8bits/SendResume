
package Funciones;

public class VariablesdeEntorno {
    //Operaciones operacion = new Operaciones();
    //numero de permiles que admite:
    public final int IntFilas = 20;
    public final int IntColum = 7;
    public static String StrUsuario;
    public static String StrContras;

    public String getStrUsuario() {
        return StrUsuario;
    }

    public void setStrUsuario(String StrUsuario) {
        VariablesdeEntorno.StrUsuario = StrUsuario;
    }

    public String getStrContras() {
        return StrContras;
    }

    public void setStrContras(String StrContras) {
        VariablesdeEntorno.StrContras = StrContras;
    }
    
}
