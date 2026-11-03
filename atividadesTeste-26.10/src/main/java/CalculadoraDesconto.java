public class CalculadoraDesconto {

    public double calcular(double valorCompra, double percentual) {

        if (valorCompra < 0) {
            throw new IllegalArgumentException(
                    "Valor da compra não pode ser negativo");
        }

        if (percentual < 0 || percentual > 100) {
            throw new IllegalArgumentException(
                    "Percentual deve estar entre 0 e 100");
        }

        return valorCompra - (valorCompra * percentual / 100);
    }
}
