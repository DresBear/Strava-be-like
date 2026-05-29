// Persistencia en CSV: lectura al iniciar y escritura al salir o bajo demanda.
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class ArchivoCSV {

    public static void guardarDatos(RedSocial red) {
        guardarUsuarios(red);
        guardarRutas(red);
        guardarAmistades(red);
        System.out.println("Datos guardados correctamente.");
    }

    // Carga silenciosa usada al arrancar el programa.
    public static void cargarDatosInicio(RedSocial red) {
        red.limpiarUsuarios();
        cargarUsuarios(red);
        cargarRutas(red);
        cargarAmistades(red);
    }

    // Recarga completa desde disco (opción manual del menú).
    public static void cargarDatos(RedSocial red) {
        cargarDatosInicio(red);
        System.out.println("Datos cargados correctamente.");
    }

    // Formato: nombre,edad,tipo,ciudad,km,ftp,mejorTiempo
    private static void guardarUsuarios(RedSocial red) {
        try {
            FileWriter archivo = new FileWriter("usuarios.csv");
            PrintWriter escritor = new PrintWriter(archivo);
            ArrayList<Usuario> usuarios = red.getUsuarios();
            int i = 0;

            while (i < usuarios.size()) {
                Usuario usuario = usuarios.get(i);
                escritor.println(
                        usuario.getNombre() + "," +
                        usuario.getEdad() + "," +
                        usuario.getTipoCiclismo() + "," +
                        usuario.getCiudad() + "," +
                        usuario.getEstadistica().getKilometros() + "," +
                        usuario.getEstadistica().getFtp() + "," +
                        usuario.getEstadistica().getMejorTiempo()
                );
                i = i + 1;
            }

            escritor.close();
        } catch (IOException e) {
            System.out.println("Error guardando usuarios.csv");
        }
    }

    // Formato: nombreUsuario,nombreRuta,terreno,distancia,dificultad,tiempo
    private static void guardarRutas(RedSocial red) {
        try {
            FileWriter archivo = new FileWriter("rutas.csv");
            PrintWriter escritor = new PrintWriter(archivo);
            ArrayList<Usuario> usuarios = red.getUsuarios();
            int i = 0;

            while (i < usuarios.size()) {
                Usuario usuario = usuarios.get(i);
                ArrayList<Ruta> rutas = usuario.getRutas();
                int j = 0;

                while (j < rutas.size()) {
                    Ruta ruta = rutas.get(j);
                    escritor.println(
                            usuario.getNombre() + "," +
                            ruta.getNombreRuta() + "," +
                            ruta.getTipoTerreno() + "," +
                            ruta.getDistancia() + "," +
                            ruta.getDificultad() + "," +
                            ruta.getTiempo()
                    );
                    j = j + 1;
                }

                i = i + 1;
            }

            escritor.close();
        } catch (IOException e) {
            System.out.println("Error guardando rutas.csv");
        }
    }

    // Formato: usuario,amigo
    private static void guardarAmistades(RedSocial red) {
        try {
            FileWriter archivo = new FileWriter("amistades.csv");
            PrintWriter escritor = new PrintWriter(archivo);
            ArrayList<Usuario> usuarios = red.getUsuarios();
            int i = 0;

            while (i < usuarios.size()) {
                Usuario usuario = usuarios.get(i);
                ArrayList<String> amigos = usuario.getAmigos();
                int j = 0;

                while (j < amigos.size()) {
                    escritor.println(usuario.getNombre() + "," + amigos.get(j));
                    j = j + 1;
                }

                i = i + 1;
            }

            escritor.close();
        } catch (IOException e) {
            System.out.println("Error guardando amistades.csv");
        }
    }

    private static void cargarUsuarios(RedSocial red) {
        File archivo = new File("usuarios.csv");

        if (!archivo.exists()) {
            return;
        }

        try {
            BufferedReader lector = new BufferedReader(new FileReader("usuarios.csv"));
            String linea = lector.readLine();

            while (linea != null) {
                String[] partes = linea.split(",");

                if (partes.length >= 4) {
                    String nombre = partes[0];
                    int edad = Integer.parseInt(partes[1]);
                    String tipo = partes[2];
                    String ciudad = partes[3];
                    double kilometros = 0;
                    double ftp = 0;
                    double mejorTiempo = 0;

                    if (partes.length >= 7) {
                        kilometros = Double.parseDouble(partes[4]);
                        ftp = Double.parseDouble(partes[5]);
                        mejorTiempo = Double.parseDouble(partes[6]);
                    }

                    Usuario usuario = new Usuario(nombre, edad, tipo, ciudad,
                            kilometros, ftp, mejorTiempo);
                    red.registrarUsuario(usuario);
                }

                linea = lector.readLine();
            }

            lector.close();
        } catch (IOException e) {
            System.out.println("Error cargando usuarios.csv");
        } catch (NumberFormatException e) {
            System.out.println("Formato invalido en usuarios.csv");
        }
    }

    private static void cargarRutas(RedSocial red) {
        File archivo = new File("rutas.csv");

        if (!archivo.exists()) {
            return;
        }

        try {
            BufferedReader lector = new BufferedReader(new FileReader("rutas.csv"));
            String linea = lector.readLine();

            while (linea != null) {
                String[] partes = linea.split(",");

                if (partes.length >= 6) {
                    Usuario usuario = red.buscarUsuario(partes[0]);

                    if (usuario != null) {
                        Ruta ruta = new Ruta(
                                partes[1],
                                partes[2],
                                Double.parseDouble(partes[3]),
                                partes[4],
                                Double.parseDouble(partes[5]),
                                partes[0]
                        );
                        usuario.agregarRutaDesdeArchivo(ruta);
                    }
                }

                linea = lector.readLine();
            }

            lector.close();
        } catch (IOException e) {
            System.out.println("Error cargando rutas.csv");
        } catch (NumberFormatException e) {
            System.out.println("Formato invalido en rutas.csv");
        }
    }

    private static void cargarAmistades(RedSocial red) {
        File archivo = new File("amistades.csv");

        if (!archivo.exists()) {
            return;
        }

        try {
            BufferedReader lector = new BufferedReader(new FileReader("amistades.csv"));
            String linea = lector.readLine();

            while (linea != null) {
                String[] partes = linea.split(",");

                if (partes.length >= 2) {
                    Usuario usuario = red.buscarUsuario(partes[0]);
                    Usuario amigo = red.buscarUsuario(partes[1]);

                    if (usuario != null && amigo != null) {
                        usuario.agregarAmigo(amigo.getNombre());
                    }
                }

                linea = lector.readLine();
            }

            lector.close();
        } catch (IOException e) {
            System.out.println("Error cargando amistades.csv");
        }
    }
}
