public class ReservaHotel {

    private int numeroDiarias;
    private double valorDiaria;
    private boolean cafeDaManha;
    private boolean cancelada;

    public ReservaHotel(int numeroDiarias, double valorDiaria) {

        if (numeroDiarias <= 0) {
            throw new IllegalArgumentException(
                    "Número de diárias deve ser maior que zero");
        }

        if (valorDiaria <= 0) {
            throw new IllegalArgumentException(
                    "Valor da diária deve ser maior que zero");
        }

        this.numeroDiarias = numeroDiarias;
        this.valorDiaria = valorDiaria;
        this.cancelada = false;
    }

    public void adicionarCafeDaManha() {

        if (cancelada) {
            throw new IllegalStateException(
                    "Não é possível alterar uma reserva cancelada");
        }

        cafeDaManha = true;
    }

    public double calcularValorTotal() {

        if (cancelada) {
            return 0;
        }

        double total = numeroDiarias * valorDiaria;

        if (cafeDaManha) {
            total += numeroDiarias * 30;
        }

        if (numeroDiarias >= 7) {
            total = total * 0.90;
        }

        return total;
    }

    public void cancelar() {

        if (cancelada) {
            throw new IllegalStateException(
                    "Reserva já está cancelada");
        }

        cancelada = true;
    }

    public boolean isCancelada() {
        return cancelada;
    }
}
