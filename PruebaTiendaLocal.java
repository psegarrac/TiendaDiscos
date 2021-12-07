package Solucion;

import java.util.Scanner;

public class PruebaTiendaLocal {

    public enum OpcionesMenu {
        SALIR("Salir del programa"),
        LISTAR_AUTORES("Listar los autores o grupos del catalogo"),
        BUSCAR_AUTOR("Buscar autor o grupo"),
        COMPRAR_DISCO("Comprar Disco"),
        REVENDER_DISCO("Revender disco");

        private String descripcion;

        private OpcionesMenu(String descripcion)
        {
            this.setDescripcion(descripcion);
        }

        public String getDescripcion() {
            return descripcion;
        }

        public void setDescripcion(String descripcion) {
            this.descripcion = descripcion;
        }

        public static OpcionesMenu getOpcion(int posicion)
        {
            return values()[posicion];
        }
    }

    public static String getMenu() {
        StringBuilder sb = new StringBuilder();
        for(OpcionesMenu opcion: OpcionesMenu.values()) {
            sb.append(opcion.ordinal());
            sb.append(".- ");
            sb.append(opcion.getDescripcion());
            sb.append("\n");
        }
        return sb.toString();
    }

    public static void main(String[] args) throws ClassNotFoundException {
        GestorConsultas gestor = new GestorConsultas();
        byte opcion;
        while(true)
        {
            System.out.println(getMenu());
            Scanner scanner = new Scanner(System.in);
            System.out.print("Elige una opcion: ");

            opcion=scanner.nextByte();
            if (opcion<=4 && opcion >=-0){


                //opcion = scanner.nextByte();
                OpcionesMenu opcionesMenu = OpcionesMenu.getOpcion(opcion);
                switch(opcionesMenu)
                {
                    case SALIR:
                        cerrarAplicacion(gestor);
                        break;
                    case LISTAR_AUTORES:
                        listarAutores(gestor);
                        System.out.println("");
                        break;
                    case BUSCAR_AUTOR:
                        buscarAutor(gestor, scanner);
                        System.out.println("");
                        break;
                    case COMPRAR_DISCO:
                        comprarDisco(gestor, scanner);
                        System.out.println("");
                        break;
                    case REVENDER_DISCO:
                        revenderDisco(gestor, scanner);
                        System.out.println("");
                        break;
                }
            }

            else{
                System.out.println ("Opcion No valida");
                System.out.println ("");
            }

        }

    }




    private static void cerrarAplicacion(GestorConsultas gestor) {
        gestor.cierraGestor();
        System.exit(0);
    }

    private static void revenderDisco(GestorConsultas gestor, Scanner scanner) {
        System.out.print("Codigo: ");
        int codigo_alta = scanner.nextInt();
        String resultadoVenta = gestor.altaDisco(codigo_alta);
        if (resultadoVenta.isEmpty())
        {
            System.out.println("No se encuentra la referencia en el catalogo");
        }
        else
        {
            System.out.println(resultadoVenta);
        }
    }

    private static void listarAutores(GestorConsultas gestor) {
        String[] autores = gestor.listaAutores();
        for(String autor: autores) {
            System.out.println(autor);
        }
    }

    private static void buscarAutor(GestorConsultas gestor, Scanner scanner) {
        System.out.print("Autor: ");
        String autorBuscado = scanner.nextLine();
        autorBuscado = scanner.nextLine();
        String[] comics = gestor.buscaAutor(autorBuscado);
        if (comics.length==0)
            System.out.println("No existe ningún disco de ese autor.");
        else{
            for(String comic: comics) {
                System.out.println(comic);
            }
        }
    }

    private static void comprarDisco(GestorConsultas gestor, Scanner scanner) {
        System.out.print("Código: ");
        int codigo_compra = scanner.nextInt();
        String resultadoCompra = gestor.bajaDisco(codigo_compra);
        if (resultadoCompra.isEmpty())
        {
            System.out.println("No se encuentra la referencia en el catálogo");
        }
        else
        {
            System.out.println(resultadoCompra);
        }
    }

}
