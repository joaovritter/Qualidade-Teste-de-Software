public class Nota {
    public char determinarNota(int nota) {

        if (nota < 0 || nota > 100) {
            throw new IllegalArgumentException("Nota inválida: " + nota);
        }
        else if (nota < 60) {
            return 'F';
        } else if (nota < 70) {
            return 'D';
        } else if (nota < 80) {
            return 'C';
        } else if (nota < 90) {
            return 'B';
        } else {
            return 'A';
        }
    }
}
