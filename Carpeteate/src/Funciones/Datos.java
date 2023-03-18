package Funciones;

import com.google.gson.Gson;
//import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 * @author ROQUEARMANDORAMIREZP
 */
public class Datos {

    VariablesdeEntorno Variable = new VariablesdeEntorno();
    Operaciones operacion = new Operaciones();
    private String[][] MatrixPerfiles = new String[Variable.IntFilas][Variable.IntColum];

    public Datos() {  //constructor
        String[][] MatrixTempo = operacion.LeeJSon();
        /*String[][] MatrixTempo = operacion.LeeJSon();//cargar datos

        for (int i = 0; i < MatrixTempo.length; i++) {
            if (MatrixTempo[i][0] != null) { //imprime solo los validos
                for (int j = 0; j < MatrixTempo[0].length; j++) {
                    MatrixPerfiles[i][j]=MatrixTempo[i][j];
                }
            }
        } */

        if (MatrixTempo != null) {
            MatrixPerfiles = MatrixTempo;//cargar datos
        }
        //RellenaMatrizWithTrash(); //solo para rellenar con datos basura y hacer pruebas
    }

    public String[][] getMatrixPerfiles() {
        return MatrixPerfiles;
    }

    public void setNewPerfil(String[] Perfil) {
        //if (MatrixNOEsNula()) {
        //JOptionPane.showMessageDialog(null, "Inicio", "Notificación de setNewPerfil", JOptionPane.ERROR_MESSAGE);
        int IntPosicion = getMatrixPerfilesTamano(); // para saber en que indice hay que agregar el registro
        for (int i = 0; i < Variable.IntColum; i++) {
            MatrixPerfiles[IntPosicion][i] = Perfil[i];
        }
        //JOptionPane.showMessageDialog(null, "fin", "Notificación de setNewPerfil", JOptionPane.ERROR_MESSAGE);
        GuardaMatrizJSON();
        //JOptionPane.showMessageDialog(null, "si llamó a guardamatriz", "Notificación de setNewPerfil", JOptionPane.ERROR_MESSAGE);
        //}
    }

    public void RellenaMatrizWithTrash() {
        int IntPosicion = getMatrixPerfilesTamano();
        for (int i = IntPosicion; i < Variable.IntFilas; i++) {
            for (int j = 0; j < Variable.IntColum; j++) {
                MatrixPerfiles[i][j] = i + " - " + j;
            }
        }
        GuardaMatrizJSON();
    }

    public void GuardaMatrizJSON() {
        //JOptionPane.showMessageDialog(null, "Inicio", "Notificación de GuardaMatrizJSON", JOptionPane.ERROR_MESSAGE);
        Gson gson = new Gson();
        boolean resultado;
        //JOptionPane.showMessageDialog(null, "mitad", "Notificación de GuardaMatrizJSON", JOptionPane.ERROR_MESSAGE);
        String json = gson.toJson(MatrixPerfiles);
        //JOptionPane.showMessageDialog(null, "fin", "Notificación de GuardaMatrizJSON", JOptionPane.ERROR_MESSAGE);
        //ImprimirPerfiles();
        resultado = operacion.GeneraArchivo(json, "source.json");
        //JOptionPane.showMessageDialog(null, "si llamó a generaArchivo", "Notificación de GuardaMatrizJSON", JOptionPane.ERROR_MESSAGE);
    }

    public void ImprimirPerfiles() {
        System.out.println("\n NUEVA IMPRESIÓN:\n");
        for (int i = 0; i < Variable.IntFilas; i++) {
            if (MatrixPerfiles[i][0] != null) { //imprime solo los validos
                System.out.println("\n Vuelta #" + i + "\n");
                for (int j = 0; j < Variable.IntColum; j++) {
                    System.out.println("\t " + MatrixPerfiles[i][j]);
                }
            }
        }
    }

    public int getMatrixPerfilesTamano() {
        //if (MatrixNOEsNula()) {//si no es nula, cuenta...
        //devuelve el numero de elementos que no son NULL
        int IntContador = 0;
        for (int i = 0; i < Variable.IntFilas; i++) {
            if (MatrixPerfiles[i][0] != null) { //si no funciona prueba con .length()
                IntContador++;
            }
        }
        return IntContador;
        /*} else {//si es nula, devuelve 0 para q se agregue un primer registro
            return 0;
        }*/
    }

    public boolean MatrixNOEsNula() {
        boolean rpta = false;
        if (MatrixPerfiles != null) {
            rpta = true;
        }
        return rpta;
    }

    public boolean HayEspacio() {
        if (getMatrixPerfilesTamano() < Variable.IntFilas) { //si hay hasta 20 registros, ya no hay más espacio
            return true;
        } else {
            return false;
        }
    }

    public void setModifPerfil(String[] Datos) {
        //if (MatrixNOEsNula()) {
        for (int i = 0; i < Variable.IntFilas; i++) {
            if (MatrixPerfiles[i][0].equals(Datos[0])) { //si es el cod a modificar...
                for (int j = 1; j < Variable.IntColum; j++) { // se procede a actualizar...
                    MatrixPerfiles[i][j] = Datos[j];
                }
                break; //termina bucle anterior si ya modificó
            }
        }
        GuardaMatrizJSON(); //guatda los cambios
        //}
    }

    public void DeletePerfil(String StrCodFila) {
        int Ultimo = getMatrixPerfilesTamano() - 1;
        for (int i = 0; i < Variable.IntFilas; i++) {
            if (MatrixPerfiles[i][0].equals(StrCodFila)) { //si es el cod a eliminar...
                if (Ultimo != i) {
                    for (int I = i; I < Variable.IntFilas - 1; I++) {
                        for (int j = 0; j < Variable.IntColum; j++) {
                            MatrixPerfiles[I][j] = MatrixPerfiles[I + 1][j];
                        }
                    }
                }
                for (int j = 0; j < Variable.IntColum; j++) {
                    MatrixPerfiles[Ultimo][j] = null;
                }
                /*if (Ultimo != i) {
                    for (int j = 0; j < Variable.IntColum; j++) {
                        MatrixPerfiles[i][j] = MatrixPerfiles[Ultimo][j];
                        MatrixPerfiles[Ultimo][j] = null;
                    }
                } else {
                    for (int j = 0; j < Variable.IntColum; j++) {
                        MatrixPerfiles[i][j] = null;
                    }
                } */
                break; //termina bucle anterior si ya modificó
            }
        }
        GuardaMatrizJSON(); //guatda los cambios
    }

    public String[][] CreateListCodNom() { //devuelve un array con los codigos y nombres de perfiles
        String[][] MatrixTemp = new String[getMatrixPerfilesTamano()][2];
        for (int i = 0; i < getMatrixPerfilesTamano(); i++) {
            //for (int j = 0; j < 2; j++) {
            MatrixTemp[i][0] = MatrixPerfiles[i][0];
            MatrixTemp[i][1] = MatrixPerfiles[i][0] + " > " + MatrixPerfiles[i][1] + " - " + MatrixPerfiles[i][2];
            //}
        }
        return MatrixTemp;
    }

    public int GetFilaOf(String StrCod) {//devuelve la fila de donde está un item a buscar
        int i;
        for (i = 0; i < Variable.IntFilas; i++) {
            if (StrCod.equals(MatrixPerfiles[i][0])) { //si es el cod a buscar
                break; //termina bucle anterior si ya modificó
            }
        }
        return i;
    }

    public String[][] EmpaquetarDatos(DefaultTableModel modelo) {
        int Fil = modelo.getRowCount();
        int Col = modelo.getColumnCount();
        int FilaOrig;
        String[][] MatrixDestino = new String[Fil][4];//4 col ya que solo me interesa cv, correo, asunto, mensaje
        String[][] MatrixTemp = new String[Fil][Col];

        for (int i = 0; i < Fil; i++) {//copia a una matriz tempotal
            for (int j = 0; j < Col; j++) {
                MatrixTemp[i][j] = (String) modelo.getValueAt(i, j);
            }
        }

        for (int i = 0; i < Fil; i++) {
            FilaOrig = GetFilaOf(MatrixTemp[i][1]);// obtengo cargo, CV, asunto y labia de la matriz principal
            for (int j = 2; j < Col; j++) {
                //String StrCadena = MatrixTemp[i][j];
                if (MatrixTemp[i][j].length() < 1 && j > 1) {
                    /*recorre las columnas en busca de espacios en blanco
                            tiene que ser >1 ya que evalua desde 2 que es donde están
                            los datos que pueden ser por defecto o modificados*/
                    switch (j) {//reemplaza los datos no ingresdos por los del perfil
                        case 2:
                        case 3:
                        case 4:
                            //modelo.setValueAt(MatrixPerfiles[FilaOrig][j + 1], i, j);
                            MatrixTemp[i][j] = MatrixPerfiles[FilaOrig][j + 1];
                            break;
                        case 7:
                            String StrLabia = MatrixPerfiles[FilaOrig][6];
                            StrLabia = StrLabia.replaceAll("<<CARGO>>", MatrixPerfiles[FilaOrig][3]);
                            StrLabia = StrLabia.replaceAll("<<DÍA>>", operacion.GetDia());
                            StrLabia = StrLabia.replaceAll("<<MES>>", operacion.GetMes());
                            StrLabia = StrLabia.replaceAll("<<AÑO>>", operacion.GetAnio());
                            StrLabia = StrLabia.replaceAll("<<EMPLEADOR>>", MatrixTemp[i][6]);
                            StrLabia = StrLabia.replaceAll("<<EMPRESA>>", MatrixTemp[i][5]);
                            //modelo.setValueAt(StrLabia, i, j);
                            MatrixTemp[i][j] = StrLabia;
                            break;
                    }
                }
            }
        }

        for (int i = 0; i < Fil; i++) {//copia a una matriz sin el codigo de peril, q ya no es necesario
            MatrixDestino[i][0] = MatrixTemp[i][0];//coreo
            MatrixDestino[i][1] = MatrixTemp[i][3];//cv
            MatrixDestino[i][2] = MatrixTemp[i][4];//Asunto
            MatrixDestino[i][3] = MatrixTemp[i][7];//mensaje
        }
        /*
        System.out.println("\n Impresion de Método EmpaquetarDatos:\n");
        for (int i = 0; i < Fil; i++) {
            System.out.println("\n Vuelta #" + i + "\n");
            for (int j = 0; j < 4; j++) {
                System.out.print("\t -" + MatrixDestino[i][j]);
            }
        } */
        return MatrixDestino;
    }
}
