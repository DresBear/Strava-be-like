// Administra la colección de ciclistas: búsquedas, ordenamientos y conexiones.
import java.util.ArrayList;

public class RedSocial {

    private ArrayList<Usuario> usuarios;

    public RedSocial() {
        usuarios = new ArrayList<Usuario>();
    }

    public void limpiarUsuarios() {
        usuarios.clear();
    }

    public void registrarUsuario(Usuario usuario) {
        usuarios.add(usuario);
    }

    public ArrayList<Usuario> getUsuarios() {
        return usuarios;
    }

    // Búsqueda lineal por nombre (recorre la lista hasta encontrar coincidencia).
    public Usuario buscarUsuario(String nombre) {
        int i = 0;

        while (i < usuarios.size()) {
            if (usuarios.get(i).getNombre().equalsIgnoreCase(nombre)) {
                return usuarios.get(i);
            }
            i = i + 1;
        }

        return null;
    }

    public void listarUsuarios() {
        if (usuarios.size() == 0) {
            System.out.println("No hay ciclistas registrados.");
            return;
        }

        int i = 0;
        while (i < usuarios.size()) {
            usuarios.get(i).mostrarPerfil();
            i = i + 1;
        }
    }

    public void listarUsuariosPorTipo(String tipo) {
        int i = 0;
        boolean encontro = false;

        while (i < usuarios.size()) {
            if (usuarios.get(i).getTipoCiclismo().equalsIgnoreCase(tipo)) {
                usuarios.get(i).mostrarPerfil();
                encontro = true;
            }
            i = i + 1;
        }

        if (!encontro) {
            System.out.println("No hay ciclistas de tipo " + tipo);
        }
    }

    public void buscarPorCiudad(String ciudad) {
        int i = 0;
        boolean encontro = false;

        while (i < usuarios.size()) {
            if (usuarios.get(i).getCiudad().equalsIgnoreCase(ciudad)) {
                usuarios.get(i).mostrarPerfil();
                encontro = true;
            }
            i = i + 1;
        }

        if (!encontro) {
            System.out.println("No se encontraron ciclistas en " + ciudad);
        }
    }

    public void buscarPorTipoCiclismo(String tipo) {
        listarUsuariosPorTipo(tipo);
    }

    public void mostrarAmigos(String nombre) {
        Usuario usuario = buscarUsuario(nombre);

        if (usuario == null) {
            System.out.println("Usuario no encontrado");
            return;
        }

        System.out.println("Amigos de " + usuario.getNombre() + ":");
        ArrayList<String> amigos = usuario.getAmigos();

        if (amigos.size() == 0) {
            System.out.println("Sin amigos conectados");
            return;
        }

        int i = 0;
        while (i < amigos.size()) {
            Usuario amigo = buscarUsuario(amigos.get(i));
            if (amigo != null) {
                amigo.mostrarPerfil();
            } else {
                System.out.println("- " + amigos.get(i));
            }
            i = i + 1;
        }
    }

    public void listarTodasLasRutas() {
        int i = 0;
        boolean hayRutas = false;

        while (i < usuarios.size()) {
            ArrayList<Ruta> rutas = usuarios.get(i).getRutas();
            int j = 0;

            while (j < rutas.size()) {
                rutas.get(j).mostrarRuta();
                hayRutas = true;
                j = j + 1;
            }

            i = i + 1;
        }

        if (!hayRutas) {
            System.out.println("No hay rutas registradas.");
        }
    }

    // La amistad es bidireccional: ambos ciclistas se agregan mutuamente.
    public void conectarUsuarios(Usuario u1, Usuario u2) {
        u1.agregarAmigo(u2.getNombre());
        u2.agregarAmigo(u1.getNombre());
    }

    // Pasa de ArrayList a arreglo fijo para aplicar algoritmos de ordenamiento clásicos.
    public Usuario[] convertirAArreglo() {
        Usuario[] arreglo = new Usuario[usuarios.size()];
        int i = 0;

        while (i < usuarios.size()) {
            arreglo[i] = usuarios.get(i);
            i = i + 1;
        }

        return arreglo;
    }

    public void cargarDesdeArreglo(Usuario[] arreglo) {
        usuarios.clear();
        int i = 0;

        while (i < arreglo.length) {
            usuarios.add(arreglo[i]);
            i = i + 1;
        }
    }

    public void ordenarPorEdad() {
        Usuario[] arreglo = convertirAArreglo();
        ordenarBurbujaPorEdad(arreglo);
        cargarDesdeArreglo(arreglo);
    }

    public void ordenarPorNombre() {
        Usuario[] arreglo = convertirAArreglo();
        ordenarSeleccionPorNombre(arreglo);
        cargarDesdeArreglo(arreglo);
    }

    public void ordenarPorTipoCiclismo() {
        Usuario[] arreglo = convertirAArreglo();
        ordenarBurbujaPorTipo(arreglo);
        cargarDesdeArreglo(arreglo);
    }

    // Bubble sort: compara edades adyacentes e intercambia si están desordenadas.
    private void ordenarBurbujaPorEdad(Usuario[] arreglo) {
        int i = 0;

        while (i < arreglo.length - 1) {
            int j = 0;

            while (j < arreglo.length - 1 - i) {
                if (arreglo[j].getEdad() > arreglo[j + 1].getEdad()) {
                    Usuario temp = arreglo[j];
                    arreglo[j] = arreglo[j + 1];
                    arreglo[j + 1] = temp;
                }
                j = j + 1;
            }

            i = i + 1;
        }
    }

    private void ordenarBurbujaPorTipo(Usuario[] arreglo) {
        int i = 0;

        while (i < arreglo.length - 1) {
            int j = 0;

            while (j < arreglo.length - 1 - i) {
                String tipoActual = arreglo[j].getTipoCiclismo();
                String tipoSiguiente = arreglo[j + 1].getTipoCiclismo();

                if (tipoActual.compareToIgnoreCase(tipoSiguiente) > 0) {
                    Usuario temp = arreglo[j];
                    arreglo[j] = arreglo[j + 1];
                    arreglo[j + 1] = temp;
                }
                j = j + 1;
            }

            i = i + 1;
        }
    }

    // Selection sort: en cada pasada ubica el nombre alfabéticamente menor al frente.
    private void ordenarSeleccionPorNombre(Usuario[] arreglo) {
        int i = 0;

        while (i < arreglo.length - 1) {
            int indiceMenor = i;
            int j = i + 1;

            while (j < arreglo.length) {
                String nombreActual = arreglo[j].getNombre();
                String nombreMenor = arreglo[indiceMenor].getNombre();

                if (nombreActual.compareToIgnoreCase(nombreMenor) < 0) {
                    indiceMenor = j;
                }
                j = j + 1;
            }

            if (indiceMenor != i) {
                Usuario temp = arreglo[i];
                arreglo[i] = arreglo[indiceMenor];
                arreglo[indiceMenor] = temp;
            }

            i = i + 1;
        }
    }

    // Recolecta tiempos de una ruta específica y los ordena de menor a mayor.
    public void mostrarLeaderboard(String nombreRuta) {
        String[] nombres = new String[usuarios.size()];
        double[] tiempos = new double[usuarios.size()];
        int cantidad = 0;

        int i = 0;
        while (i < usuarios.size()) {
            ArrayList<Ruta> rutas = usuarios.get(i).getRutas();
            int j = 0;

            while (j < rutas.size()) {
                if (rutas.get(j).getNombreRuta().equalsIgnoreCase(nombreRuta)) {
                    nombres[cantidad] = usuarios.get(i).getNombre();
                    tiempos[cantidad] = rutas.get(j).getTiempo();
                    cantidad = cantidad + 1;
                }
                j = j + 1;
            }

            i = i + 1;
        }

        if (cantidad == 0) {
            System.out.println("Nadie ha completado la ruta " + nombreRuta);
            return;
        }

        ordenarLeaderboard(nombres, tiempos, cantidad);

        System.out.println("Leaderboard - " + nombreRuta);
        int k = 0;
        while (k < cantidad) {
            System.out.println((k + 1) + ". " + nombres[k] + " - " + tiempos[k] + " min");
            k = k + 1;
        }
    }

    private void ordenarLeaderboard(String[] nombres, double[] tiempos, int cantidad) {
        int i = 0;

        while (i < cantidad - 1) {
            int j = 0;

            while (j < cantidad - 1 - i) {
                if (tiempos[j] > tiempos[j + 1]) {
                    double tempTiempo = tiempos[j];
                    tiempos[j] = tiempos[j + 1];
                    tiempos[j + 1] = tempTiempo;

                    String tempNombre = nombres[j];
                    nombres[j] = nombres[j + 1];
                    nombres[j + 1] = tempNombre;
                }
                j = j + 1;
            }

            i = i + 1;
        }
    }
}
