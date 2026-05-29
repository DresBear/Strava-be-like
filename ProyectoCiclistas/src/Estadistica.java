// Acumula métricas del ciclista a partir de las rutas registradas.
public class Estadistica {

    private double kilometros;
    private double ftp;
    private double mejorTiempo;

    public Estadistica() {
        kilometros = 0;
        ftp = 0;
        mejorTiempo = 0;
    }

    public Estadistica(double kilometros, double ftp, double mejorTiempo) {
        this.kilometros = kilometros;
        this.ftp = ftp;
        this.mejorTiempo = mejorTiempo;
    }

    public double getKilometros() {
        return kilometros;
    }

    public double getFtp() {
        return ftp;
    }

    public double getMejorTiempo() {
        return mejorTiempo;
    }

    public void setKilometros(double kilometros) {
        this.kilometros = kilometros;
    }

    public void setFtp(double ftp) {
        this.ftp = ftp;
    }

    public void setMejorTiempo(double mejorTiempo) {
        this.mejorTiempo = mejorTiempo;
    }

    // Suma distancia recorrida y conserva el menor tiempo registrado.
    public void actualizarConRuta(Ruta ruta) {
        kilometros = kilometros + ruta.getDistancia();

        if (mejorTiempo == 0 || ruta.getTiempo() < mejorTiempo) {
            mejorTiempo = ruta.getTiempo();
        }
    }
}
