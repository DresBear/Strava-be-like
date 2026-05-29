// Punto de entrada: menú de consola y coordinación de las demás clases.
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        RedSocial red = new RedSocial();

        // Restaura datos guardados en sesiones anteriores (si existen los CSV).
        ArchivoCSV.cargarDatosInicio(red);

        int opcion = 0;

        while (opcion != 8) {
            mostrarMenuPrincipal();
            System.out.print("Seleccione una opcion: ");

            // Evita que el programa falle si el usuario escribe texto en lugar de un número.
            if (sc.hasNextInt()) {
                opcion = sc.nextInt();
                sc.nextLine();
            } else {
                sc.nextLine();
                System.out.println("Opcion invalida. Ingrese un numero del 1 al 8.");
                continue;
            }

            if (opcion == 1) {
                registrarUsuario(sc, red);
            } else if (opcion == 2) {
                anadirRuta(sc, red);
            } else if (opcion == 3) {
                conectarCiclistas(sc, red);
            } else if (opcion == 4) {
                visualizarDatos(sc, red);
            } else if (opcion == 5) {
                buscarCiclista(sc, red);
            } else if (opcion == 6) {
                listarCiclistas(sc, red);
            } else if (opcion == 7) {
                listarRutas(red, sc);
            } else if (opcion == 8) {
                ArchivoCSV.guardarDatos(red);
                System.out.println("Saliendo...");
            } else {
                System.out.println("Opcion invalida");
            }
        }

        sc.close();
    }

    private static void mostrarMenuPrincipal() {
        System.out.println("\n===== RED SOCIAL CICLISTAS =====");
        System.out.println("1. Registrar nuevo usuario");
        System.out.println("2. Añadir ruta a usuario");
        System.out.println("3. Conectar con otro ciclista");
        System.out.println("4. Visualizar datos");
        System.out.println("5. Buscar ciclista");
        System.out.println("6. Listar ciclistas");
        System.out.println("7. Lista rutas");
        System.out.println("8. Salir");
    }

    private static void registrarUsuario(Scanner sc, RedSocial red) {
        System.out.print("Nombre: ");
        String nombre = sc.nextLine();

        if (red.buscarUsuario(nombre) != null) {
            System.out.println("Ya existe un ciclista con ese nombre.");
            return;
        }

        System.out.print("Edad: ");
        int edad = leerEntero(sc);

        System.out.print("Tipo ciclismo (Ruta/MTB/Gravel/Urbano): ");
        String tipo = sc.nextLine();

        System.out.print("Ciudad: ");
        String ciudad = sc.nextLine();

        System.out.print("FTP: ");
        double ftp = leerDouble(sc);

        Usuario nuevo = new Usuario(nombre, edad, tipo, ciudad);
        nuevo.getEstadistica().setFtp(ftp);
        red.registrarUsuario(nuevo);

        System.out.println("Usuario registrado");
    }

    private static void anadirRuta(Scanner sc, RedSocial red) {
        System.out.print("Nombre del usuario: ");
        String nombreUsuario = sc.nextLine();
        Usuario usuario = red.buscarUsuario(nombreUsuario);

        if (usuario == null) {
            System.out.println("Usuario no encontrado");
            return;
        }

        System.out.print("Nombre ruta: ");
        String rutaNombre = sc.nextLine();

        System.out.print("Terreno: ");
        String terreno = sc.nextLine();

        System.out.print("Distancia (km): ");
        double distancia = leerDouble(sc);

        System.out.print("Dificultad: ");
        String dificultad = sc.nextLine();

        System.out.print("Tiempo (min): ");
        double tiempo = leerDouble(sc);

        Ruta ruta = new Ruta(rutaNombre, terreno, distancia, dificultad, tiempo,
                usuario.getNombre());
        // Al agregar la ruta también se recalculan km y mejor tiempo del ciclista.
        usuario.agregarRuta(ruta);

        System.out.println("Ruta agregada");
    }

    private static void conectarCiclistas(Scanner sc, RedSocial red) {
        System.out.print("Primer usuario: ");
        String u1 = sc.nextLine();

        System.out.print("Segundo usuario: ");
        String u2 = sc.nextLine();

        Usuario usuario1 = red.buscarUsuario(u1);
        Usuario usuario2 = red.buscarUsuario(u2);

        if (usuario1 != null && usuario2 != null) {
            red.conectarUsuarios(usuario1, usuario2);
            System.out.println("Usuarios conectados");
        } else {
            System.out.println("Usuarios no encontrados");
        }
    }

    // Submenú de consultas y persistencia manual (requisito del proyecto).
    private static void visualizarDatos(Scanner sc, RedSocial red) {
        int subOpcion = 0;

        while (subOpcion != 6) {
            System.out.println("\n--- Visualizar datos ---");
            System.out.println("1. Todos los usuarios");
            System.out.println("2. Amigos de un ciclista");
            System.out.println("3. Filtrar por tipo de ciclismo");
            System.out.println("4. Leaderboard de una ruta");
            System.out.println("5. Guardar datos manualmente");
            System.out.println("6. Volver");
            System.out.print("Seleccione una opcion: ");

            subOpcion = leerEntero(sc);

            if (subOpcion == 1) {
                red.listarUsuarios();
            } else if (subOpcion == 2) {
                System.out.print("Nombre del ciclista: ");
                red.mostrarAmigos(sc.nextLine());
            } else if (subOpcion == 3) {
                System.out.print("Tipo de ciclismo: ");
                red.listarUsuariosPorTipo(sc.nextLine());
            } else if (subOpcion == 4) {
                System.out.print("Nombre de la ruta: ");
                red.mostrarLeaderboard(sc.nextLine());
            } else if (subOpcion == 5) {
                ArchivoCSV.guardarDatos(red);
            } else if (subOpcion != 6) {
                System.out.println("Opcion invalida");
            }
        }
    }

    private static void buscarCiclista(Scanner sc, RedSocial red) {
        System.out.println("\n--- Buscar ciclista ---");
        System.out.println("1. Por nombre");
        System.out.println("2. Por ciudad");
        System.out.println("3. Por tipo de ciclismo");
        System.out.print("Seleccione una opcion: ");

        int subOpcion = leerEntero(sc);

        if (subOpcion == 1) {
            System.out.print("Nombre: ");
            Usuario encontrado = red.buscarUsuario(sc.nextLine());

            if (encontrado != null) {
                encontrado.mostrarPerfil();
            } else {
                System.out.println("No encontrado");
            }
        } else if (subOpcion == 2) {
            System.out.print("Ciudad: ");
            red.buscarPorCiudad(sc.nextLine());
        } else if (subOpcion == 3) {
            System.out.print("Tipo de ciclismo: ");
            red.buscarPorTipoCiclismo(sc.nextLine());
        } else {
            System.out.println("Opcion invalida");
        }
    }

    // Primero ordena según el criterio elegido y luego muestra la lista.
    private static void listarCiclistas(Scanner sc, RedSocial red) {
        System.out.println("\n--- Ordenar ciclistas ---");
        System.out.println("1. Por nombre");
        System.out.println("2. Por edad");
        System.out.println("3. Por tipo de ciclismo");
        System.out.print("Seleccione criterio: ");

        int subOpcion = leerEntero(sc);

        if (subOpcion == 1) {
            red.ordenarPorNombre();
        } else if (subOpcion == 2) {
            red.ordenarPorEdad();
        } else if (subOpcion == 3) {
            red.ordenarPorTipoCiclismo();
        } else {
            System.out.println("Opcion invalida");
            return;
        }

        red.listarUsuarios();
    }

    private static void listarRutas(RedSocial red, Scanner sc) {
        System.out.println("\n--- Lista rutas ---");
        System.out.println("1. Todas las rutas");
        System.out.println("2. Cargar datos manualmente");
        System.out.print("Seleccione una opcion: ");

        int subOpcion = leerEntero(sc);

        if (subOpcion == 1) {
            red.listarTodasLasRutas();
        } else if (subOpcion == 2) {
            ArchivoCSV.cargarDatos(red);
        } else {
            System.out.println("Opcion invalida");
        }
    }

    // Repite la lectura hasta recibir un entero válido.
    private static int leerEntero(Scanner sc) {
        while (!sc.hasNextInt()) {
            sc.nextLine();
            System.out.print("Ingrese un numero valido: ");
        }

        int valor = sc.nextInt();
        sc.nextLine();
        return valor;
    }

    private static double leerDouble(Scanner sc) {
        while (!sc.hasNextDouble()) {
            sc.nextLine();
            System.out.print("Ingrese un numero valido: ");
        }

        double valor = sc.nextDouble();
        sc.nextLine();
        return valor;
    }
}
