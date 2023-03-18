package Funciones;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.GregorianCalendar;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
//import javax.swing.JOptionPane;

/**
 * @author ROQUEARMANDORAMIREZP
 */
public class Operaciones {

    VariablesdeEntorno Variable = new VariablesdeEntorno();
    Calendar c1 = GregorianCalendar.getInstance();

    public String[][] LeeJSon() {
        String[][] MatrixTemp = new String[Variable.IntFilas][Variable.IntColum];
        try {
            Path filePath = Paths.get("source.json");
            if (!Files.exists(filePath)) {//si no existe lo crea pero vacio
                GeneraArchivo("", "source.json");
            } else if (Files.size(filePath) > 0) {//si el archivo NO está vacio
                String jsonString = new String(Files.readAllBytes(filePath));
                Gson gson = new Gson();
                MatrixTemp = gson.fromJson(jsonString, String[][].class);//deposita el contenido en la matriz
            }
        } catch (IOException ex) {
            Logger.getLogger(Operaciones.class.getName()).log(Level.SEVERE, null, ex);
        }
        return MatrixTemp;
    }

    public String GeneraCodigo() {
        //genera un codigo aleatorio usando el fecha y hora para construirlo
        return String.valueOf(c1.get(Calendar.YEAR)) + String.valueOf(c1.get(Calendar.MONTH)) + String.valueOf(c1.get(Calendar.DAY_OF_MONTH)) + String.valueOf(c1.get(Calendar.HOUR_OF_DAY)) + String.valueOf(c1.get(Calendar.MINUTE)) + String.valueOf(c1.get(Calendar.SECOND));
    }

    public boolean GeneraArchivo(String json, String StrNombre) {
        boolean estado;
        try ( FileWriter file = new FileWriter(StrNombre)) {
            file.write(json);
            file.flush();
            estado = true;
            //JOptionPane.showMessageDialog(null, json, "Notificación de GeneraArchivo", JOptionPane.ERROR_MESSAGE);
        } catch (IOException e) {
            //JOptionPane.showMessageDialog(null, e, "Notificación de GeneraArchivo", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            estado = false;
        }
        //JOptionPane.showMessageDialog(null, estado, "Notificación de GeneraArchivo", JOptionPane.ERROR_MESSAGE);
        return estado;
    }

    public void VerficaArchivoInicio(String StrNombre) {
        //se que si no existe al guardar lo genera, pero igual queiro evitar falos por las moscas
        File archivo = new File(StrNombre);//"source.json"
        if (!archivo.exists()) { //si no existe un archivo
            GeneraArchivo("", StrNombre);//genera uno en blanco
        }
    }

    /*public void VerficaArchivoInicio(String StrNombre, String StrCadena) {
        //se que si no existe al guardar lo genera, pero igual queiro evitar falos por las moscas
        File archivo = new File(StrNombre);
        if (!archivo.exists()) { //si no existe un archivo
            GeneraArchivo(StrCadena, StrNombre);//genera uno con contenido
        }
    } */
    public boolean EsCorreo(String Texto) {
        boolean Rpta = true;
        Pattern pattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" //comfigura el patrón para validar email
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        Matcher mather = pattern.matcher(Texto);
        if (Texto.length() < 1 || Texto.length() > 245 || mather.find() == false) {
            Rpta = false;   //si la cadenaa es menor a 1 O no cuadra con el formato de email, nada ok
        }
        return Rpta;
    }

    public String GetDia() {
        return String.valueOf(c1.get(Calendar.DAY_OF_MONTH));
    }

    public String GetMes() {
        String Mes = "";
        int mes = Integer.parseInt(String.valueOf(c1.get(Calendar.MONTH)));//si no hago conversiones redundantes, no funciona
        switch (mes + 1) {
            case 1:
                Mes = "Enero";
                break;
            case 2:
                Mes = "Febrero";
                break;
            case 3:
                Mes = "Marzo";
                break;
            case 4:
                Mes = "Abril";
                break;
            case 5:
                Mes = "Mayo";
                break;
            case 6:
                Mes = "Junio";
                break;
            case 7:
                Mes = "Julio";
                break;
            case 8:
                Mes = "Agosto";
                break;
            case 9:
                Mes = "Septiembre";
                break;
            case 10:
                Mes = "Octubre";
                break;
            case 11:
                Mes = "Noviembre";
                break;
            case 12:
                Mes = "Diciembre";
                break;
        }
        return Mes;
    }

    public String GetAnio() {
        return String.valueOf(c1.get(Calendar.YEAR));
    }

    public String GetNombreArchivo(String StrDireccion) {
        String NomArchivo = "";
        char signo = (char) 92; //graba la barra invertida

        for (int n = StrDireccion.length() - 1; n >= 0; n--) {
            char c = StrDireccion.charAt(n);
            if (c == signo) {
                NomArchivo = StrDireccion.substring(n + 1);
                break;
            }
        }
        return NomArchivo;
    }

    public void SetUsuario(String Usuario, String Contrasena) { //establece usuario y contraseña
        String StrCadena = Usuario + " " + Contrasena;
        StrCadena = Encriptar(StrCadena);
        GeneraArchivo(StrCadena, "main.dat");
        Variable.setStrUsuario(Usuario);
        Variable.setStrContras(Contrasena);
    }

    private String GetLeeUsuario() {//saca la cadena desde el archivo de texto
        String fichero = "";
        String StrCredenciales = "";
        try ( BufferedReader br = new BufferedReader(new FileReader("main.dat"))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                fichero += linea;
            }
        } catch (FileNotFoundException ex) {
            //System.out.println(ex.getMessage());
        } catch (IOException ex) {
            //System.out.println(ex.getMessage());
        }
        StrCredenciales = Desencriptar(fichero);
        return StrCredenciales;
    }

    public String GetUsuar() {
        String Usu = "";
        String StrCredenciales = GetLeeUsuario();
        if (StrCredenciales.length() > 0) {
            for (int n = 0; n < StrCredenciales.length() - 1; n++) {
                char c = StrCredenciales.charAt(n); //obtengo el caracter
                if (c == ' ') {
                    Usu = StrCredenciales.substring(0, n);
                    //Variable.setStrContras(StrCredenciales.substring(n + 1));
                    //System.out.println("El usuario Es: " + StrCredenciales.substring(0, n) + "<");
                    //System.out.println("La contraseña Es: >" + StrCredenciales.substring(n + 1) + "<");
                    break;
                }
            }
            Variable.setStrUsuario(Usu);
        }

        return Usu;
    }

    public String GetContra() {
        String contra = "";
        String StrCredenciales = GetLeeUsuario();
        if (StrCredenciales.length() > 0) {
            for (int n = 0; n < StrCredenciales.length() - 1; n++) {
                char c = StrCredenciales.charAt(n); //obtengo el caracter
                if (c == ' ') {
                    //Variable.setStrUsuario(StrCredenciales.substring(0, n));
                    contra = StrCredenciales.substring(n + 1);
                    //System.out.println("El usuario Es: " + StrCredenciales.substring(0, n) + "<");
                    //System.out.println("La contraseña Es: >" + StrCredenciales.substring(n + 1) + "<");
                    break;
                }
            }
            //System.out.println("Metodo GetContra():Contra:>"+contra+"<");
            Variable.setStrContras(contra);
        }
        return contra;
    }

    public String Encriptar(String StrCadena) {
        String CadenaTotal = "";
        for (int n = StrCadena.length() - 1; n >= 0; n--) {
            int c = StrCadena.charAt(n); //obtengo el ascci del caracter
            String hex = Integer.toHexString(c * 12);//lo convierto a Hexadecimal
            //CadenaTotal+=hex; //concatena todos los caracteres encontrados
            CadenaTotal = CadenaTotal + hex + "g";
        }
        return CadenaTotal;
    }

    public String Desencriptar(String StrCadena) {
        String CadenaTotal = "";
        //char ge = (char) 103;
        String CaracterTemp = "";
        for (int n = 0; n < StrCadena.length(); n++) { //
            char c = StrCadena.charAt(n); //obtengo el caracter
            if (c != 'g') {
                //System.out.println(StrCadena.charAt(n));
                CaracterTemp = CaracterTemp + StrCadena.charAt(n) + "";//para que mantenga el orden del numero
                //System.out.println(CaracterTemp);
            } else {
                //System.out.println(CaracterTemp + " voy a convertir");
                int decimal;
                decimal = Integer.parseInt(CaracterTemp, 16);//convierte de hexa a base decimal
                decimal = decimal / 12; //divide el numero para12
                char ca = (char) decimal;//obtiene el caracter del numero
                CadenaTotal = ca + CadenaTotal; //restaura la cadena a su orden original
                CaracterTemp = "";
            }
        }
        return CadenaTotal;
    }
}
