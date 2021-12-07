package Solucion;

import java.io.EOFException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.HashSet;


public class GestorConsultas {

    RandomAccessFile stream;

    /**
     * Constructor del gestor de consultas de la tienda.
     * Abre el fichero con los datos de prueba.
     * Si no existe, lo crea
     */
    public GestorConsultas() {
        creaFichero("discos2.dat");
    }

    /**
     * Crea / abre el fichero en disco
     * @param nombreFichero nombre del fichero en disco
     */
    private void creaFichero(String nombreFichero) {
        try {
            boolean exists = (new File(nombreFichero)).exists();
            stream = new RandomAccessFile(nombreFichero, "rw");
            if ( ! exists) {
                creaDiscosPorDefecto();
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error al abrir el fichero: " + nombreFichero);
            System.exit(0);
        }
    }

    private void creaDiscosPorDefecto() {
        Disco disco1 = new Disco(1, "Que voy a hacer", "Los Planetas", 20.0, 5);
        disco1.escribeEnFichero(stream);
        Disco disco2 = new Disco(2, "La voz del presidente", "Viva Suecia", 35.0, 1);
        disco2.escribeEnFichero(stream);
        Disco disco3 = new Disco(3, "La revolución sexual", "La casa azul", 20.0, 10);
        disco3.escribeEnFichero(stream);
        Disco disco4 = new Disco(4, "Finisterre", "Vetusta Morla", 40.0, 5);
        disco4.escribeEnFichero(stream);
        Disco disco5 = new Disco(5, "Paradise","Coldplay", 35.0, 2);
        disco5.escribeEnFichero(stream);
    }

    /**
     * Cierra el flujo/stream asociado al fichero de discos.
     */
    public void cierraGestor() {
        try {
            stream.close();
        } catch (IOException e) {
            System.out.println("No se ha podido cerrar el fichero");
        }
    }

    /**
     * Metodo auxiliar privado que busca un disco con un codigo dado y devuelve su posicion en el fichero
     * Si no lo encuentra, devuelve -1
     * @param	codigoBuscado	codigo del disco buscado
     * @return					byte de inicio del registro en el fichero
     */
    private long buscaCodigo(int codigoBuscado) {
        Disco Disco = new Disco();
        long posicion = -1;
        posicionaFichero(0);
        do
        {
            try {
                posicion = stream.getFilePointer();
                Disco.leeDeFichero(stream);
            } catch (EOFException e) {
                return -1;
            } catch (IOException e) {
                System.out.println("Error leyendo del fichero");
                System.exit(0);
            }
        } while (Disco.getCodigo() != codigoBuscado);
        return posicion;
    }

    private void posicionaFichero(long posicion) {
        try {
            stream.seek(posicion);
        } catch (IOException e) {
            System.out.println("Error posicionando el puntero al inicio del fichero");
            System.exit(0);
        }
    }

    /**
     * Devuelve un vector con los autores de discos en el catalog de la tienda
     * @return	Vector de cadenas con los autores
     */
    public String[] listaAutores() {
        Disco Disco = new Disco();
        HashSet<String> autores = new HashSet<String>();
        posicionaFichero(0);
        while(true)
        {
            try {
                Disco.leeDeFichero(stream);
                autores.add(Disco.getAutor());
            } catch (EOFException e) {
                break;
            } catch (IOException e) {
                System.out.println("Error leyendo del fichero.");
                System.exit(0);
            }
        }
        return hashArray(autores);
    }

    private String[] hashArray(HashSet<String> autores) {
        String[] lista = new String[autores.size()];
        int i = 0;
        for (String val : autores) lista[i++] = val;
        return lista;
    }

    /**
     * Busca los discos de un determinado autor en el fichero y los devuelve como un vector de cadenas
     * Si no hay ninguno, devuelve el vector vacio
     * @param	autorBuscado	autor del disco buscado
     * @return					Vector de cadenas asociadas a los discos del autor
     */
    public String[] buscaAutor(String autorBuscado) {
        Disco Disco = new Disco();
        HashSet<String> libros = new HashSet<String>();
        posicionaFichero(0);
        while(true)
        {
            try {
                Disco.leeDeFichero(stream);
                if (autorBuscado.equals(Disco.getAutor()))
                {
                    libros.add(Disco.toString());
                }
            } catch (EOFException e) {
                break;
            } catch (IOException e) {
                System.out.println("Error leyendo del fichero");
                System.exit(0);
            }
        }
        return hashArray(libros);
    }

    /**
     * Da de alta un ejemplar del disco con un codigo dado y devuelve una cadena con sus datos
     * Si el disco no estaba en el fichero, devuelve la cadena vacía
     * @param	codigoBuscado	codigo del disco buscado
     * @return					cadena con informacion del disco
     */
    public String altaDisco(int codigoBuscado) {
        long posicion = buscaCodigo(codigoBuscado);
        if (posicion != -1) {
            posicionaFichero(posicion);
            Disco Disco = new Disco();
            try {
                Disco.leeDeFichero(stream);
                Disco.setCantidad(Disco.getCantidad() + 1);
                posicionaFichero(posicion);
                Disco.escribeEnFichero(stream);
                return Disco.toString();
            } catch (EOFException e) {
                e.printStackTrace();
            } catch (IOException e) {
                System.out.println("Error escribiendo el fichero");
                System.exit(0);
            }
        }
        return "";
    }

    /**
     * Da de baja un ejemplar del disco con un codigo dado y devuelve una cadena con sus datos
     * Si no hay ninguno, devuelve una cadena vacía
     * @param	codigo	codigo del disco buscado
     * @return			cadena con informacion del disco
     */
    public String bajaDisco(int codigoBuscado) {
        long posicion = buscaCodigo(codigoBuscado);
        if (posicion != -1) {
            posicionaFichero(posicion);
            Disco Disco = new Disco();
            try {
                Disco.leeDeFichero(stream);
                if (Disco.getCantidad() > 0)
                {
                    Disco.setCantidad(Disco.getCantidad() - 1);
                    posicionaFichero(posicion);
                    Disco.escribeEnFichero(stream);
                    if (Disco.getCantidad() == 0)
                    {
                        return ("Se han agotado las unidades de este disco.\n" + Disco.toString());
                    }
                    else
                    {
                        return Disco.toString();
                    }
                }
                else
                {
                    return ("No quedan ejemplares del disco.\n" + Disco.toString());
                }
            } catch (EOFException e) {
                e.printStackTrace();
            } catch (IOException e) {
                System.out.println("Error escribiendo el fichero");
                System.exit(0);
            }
        }
        return "";
    }

}
