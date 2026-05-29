// Modela a un ciclista: datos personales, rutas, amigos y estadísticas acumuladas.
import java.util.ArrayList;

public class Usuario {

    private String nombre;
    private int edad;
    private String tipoCiclismo;
    private String ciudad;
    private ArrayList<Ruta> rutas;
    private ArrayList<String> amigos; // Se guardan nombres para facilitar la persistencia en CSV.
    private Estadistica estadistica;

    public Usuario(String nombre, int edad, String tipoCiclismo, String ciudad) {
        this.nombre = nombre;
        this.edad = edad;
        this.tipoCiclismo = tipoCiclismo;
        this.ciudad = ciudad;
        rutas = new ArrayList<Ruta>();
        amigos = new ArrayList<String>();
        estadistica = new Estadistica();
    }

    // Constructor usado al cargar desde CSV, cuando ya existen estadísticas previas.
    public Usuario(String nombre, int edad, String tipoCiclismo, String ciudad,
                   double kilometros, double ftp, double mejorTiempo) {
        this(nombre, edad, tipoCiclismo, ciudad);
        estadistica = new Estadistica(kilometros, ftp, mejorTiempo);
    }

    public String getNombre() {
        return nombre;
    }

    public int getEdad() {
        return edad;
    }

    public String getTipoCiclismo() {
        return tipoCiclismo;
    }

    public String getCiudad() {
        return ciudad;
    }

    public ArrayList<Ruta> getRutas() {
        return rutas;
    }

    public ArrayList<String> getAmigos() {
        return amigos;
    }

    public Estadistica getEstadistica() {
        return estadistica;
    }

    public void agregarRuta(Ruta ruta) {
        rutas.add(ruta);
        estadistica.actualizarConRuta(ruta);
    }

    // Solo agrega la ruta sin recalcular stats (las estadísticas ya vienen del CSV).
    public void agregarRutaDesdeArchivo(Ruta ruta) {
        rutas.add(ruta);
    }

    public void agregarAmigo(String nombreAmigo) {
        int i = 0;
        boolean yaExiste = false;

        while (i < amigos.size()) {
            if (amigos.get(i).equalsIgnoreCase(nombreAmigo)) {
                yaExiste = true;
            }
            i = i + 1;
        }

        if (!yaExiste) {
            amigos.add(nombreAmigo);
        }
    }

    public void mostrarPerfil() {
        System.out.println("Nombre: " + nombre);
        System.out.println("Edad: " + edad);
        System.out.println("Tipo: " + tipoCiclismo);
        System.out.println("Ciudad: " + ciudad);
        System.out.println("Kilometros: " + estadistica.getKilometros());
        System.out.println("FTP: " + estadistica.getFtp());
        System.out.println("Mejor tiempo: " + estadistica.getMejorTiempo());

        System.out.println("Rutas:");
        int i = 0;
        while (i < rutas.size()) {
            rutas.get(i).mostrarRuta();
            i = i + 1;
        }

        System.out.println("Amigos:");
        if (amigos.size() == 0) {
            System.out.println("Sin amigos conectados");
        } else {
            int j = 0;
            while (j < amigos.size()) {
                System.out.println("- " + amigos.get(j));
                j = j + 1;
            }
        }

        System.out.println("-------------------");
    }
}
