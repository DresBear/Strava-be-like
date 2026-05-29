// Representa una ruta completada por un ciclista.
public class Ruta {

    private String nombreRuta;
    private String tipoTerreno;
    private double distancia;
    private String dificultad;
    private double tiempo;
    private String nombreUsuario; // Vincula la ruta con quien la registró.

    public Ruta(String nombreRuta, String tipoTerreno, double distancia,
                  String dificultad, double tiempo, String nombreUsuario) {
        this.nombreRuta = nombreRuta;
        this.tipoTerreno = tipoTerreno;
        this.distancia = distancia;
        this.dificultad = dificultad;
        this.tiempo = tiempo;
        this.nombreUsuario = nombreUsuario;
    }

    public String getNombreRuta() {
        return nombreRuta;
    }

    public String getTipoTerreno() {
        return tipoTerreno;
    }

    public double getDistancia() {
        return distancia;
    }

    public String getDificultad() {
        return dificultad;
    }

    public double getTiempo() {
        return tiempo;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void mostrarRuta() {
        System.out.println("Ruta: " + nombreRuta);
        System.out.println("Ciclista: " + nombreUsuario);
        System.out.println("Terreno: " + tipoTerreno);
        System.out.println("Distancia: " + distancia + " km");
        System.out.println("Dificultad: " + dificultad);
        System.out.println("Tiempo: " + tiempo + " min");
        System.out.println("----------------");
    }
}
