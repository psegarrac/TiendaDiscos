package Solucion;

import java.io.EOFException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.Serializable;
import java.util.Scanner;


public class Disco implements Serializable {
    /**
     * Clase que permite escribir y leer un Solucion.Disco de teclado y en un fichero de acceso directo
     */
    private static final long serialVersionUID = 1L;

    private int codigo;
    private String titulo;
    private String autor;
    private double precio;
    private int cantidad;

    /**
     * Constructor de un Solucion.Disco sin argumentos
     */
    public Disco() {
    } // fin de constructor sin argumentos

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    /**
     * Constructor de un Solucion.Disco con argumentos
     * @param	codigo		codigo del disco
     * @param	titulo		titulo del disco
     * @param	autor		autor del disco
     * @param	precio		precio del disco
     * @param	cantidad	ejemplares disponibles del disco
     */
    public Disco(int codigo, String titulo, String autor, double precio, int cantidad) {
        this.codigo = codigo;
        this.titulo = titulo;
        this.autor = autor;
        this.precio = precio;
        this.cantidad = cantidad;
    } // fin de constructor con argumentos

    /**
     * Devuelve el codigo del disco
     * @return
     */
    public int getCodigo() {
        return codigo;
    }

    /**
     * Escribe los datos de un disco en una cadena y la devuelve
     * @return	cadena con los datos del disco
     */


    @Override
    public String toString() {
        String cadena="";
        cadena+="TITULO: " + this.titulo + ". CODIGO: " + this.codigo + "\n";
        cadena+="AUTOR: " + this.autor + "\nPRECIO: " + this.precio + ". CANTIDAD: " + this.cantidad;
        return cadena;
    } // fin de toString
    /**
     * Lee los datos de un disco de un stream de entrada
     */
    public void leeDeTeclado(Scanner teclado) {
        System.out.println("INTRODUCE LOS DATOS DEL DISCO");
        System.out.println("Codigo: ");
        codigo = teclado.nextInt();
        System.out.println("Titulo: ");
        titulo = teclado.next();
        System.out.println("Autor: ");
        autor = teclado.next();
        System.out.println("Precio:");
        precio = teclado.nextDouble();
        System.out.println("Cantidad: ");
        cantidad = teclado.nextInt();
    } // fin leeDeTeclado

    /**
     * Escribe los datos de un disco en la posicion actual de un fichero
     * @param	stream	stream asociado al fichero
     */
    public void escribeEnFichero(RandomAccessFile stream) {
        try {
            stream.writeInt(codigo);
            stream.writeUTF(titulo);
            stream.writeUTF(autor);
            stream.writeDouble(precio);
            stream.writeInt(cantidad);
        } catch (IOException e) {
            System.out.println("Error al escribir en el fichero.");
            System.exit(0);
        }
    } // fin escribeEnFichero

    /**
     * Lee los datos de un disco de la posicion actual de un fichero
     * @param	stream	stream asociado al fichero
     * @throws	EOFException, IOException
     */
    public void leeDeFichero(RandomAccessFile stream) throws EOFException, IOException {
        codigo = stream.readInt();
        titulo = stream.readUTF();
        autor = stream.readUTF();
        precio = stream.readDouble();
        cantidad = stream.readInt();
    }

}
