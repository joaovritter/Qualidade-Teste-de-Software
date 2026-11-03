import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

/*
 * Exercício 1 - CalculadoraDesconto
 *
 * Particionamento de equivalência e valores limite:
 *   valorCompra: < 0 (inválido) | >= 0 (válido)
 *   percentual:  < 0 (inválido) | 0 a 100 (válido) | > 100 (inválido)
 *
 */
class CalculadoraDescontoTest {

    private static final double DELTA = 0.0001;
    private CalculadoraDesconto calc;

    @BeforeEach
    void preparar() {
        calc = new CalculadoraDesconto();
    }

    // Caso 1 - um cenário válido (partição de equivalência)
    // passa pelos dois ifs e vai direto para o return
    @Test
    void deveAplicarDescontoDe10PorCento() {
        assertEquals(180.0, calc.calcular(200, 10), DELTA);
    }

    // Caso 2 - Valor limite (limites válidos)
    // 0 e 100 deve aceitar porque o if usa < e >.
    @ParameterizedTest
    @CsvSource({//valor, percentual, esperado 
            "100, 0, 100", // percentual no menor limite, sem desconto
            "100, 0.01, 99.99", // pouco acima do 0
            "100, 99.99, 0.01", // pouco abaixo do 100
            "100, 100, 0", // percentual no maior limite, sai de graça
            "0, 50, 0", // comprar por zero é válido
            "0.01, 0, 0.01"
    })
    void deveCalcularNosLimitesValidos(double valor, double percentual, double esperado) {
        assertEquals(esperado, calc.calcular(valor, percentual), DELTA);
    }

    // Caso 3 - Partição + valor limite (-0.01 é logo abaixo do 0)
    // Tratamento de erros / ramo verdadeiro do primeiro if 
    @ParameterizedTest
    @ValueSource(doubles = { -0.01, -500 })
    void deveRejeitarValorDaCompraNegativo(double valor) {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> calc.calcular(valor, 10));
        assertEquals("Valor da compra não pode ser negativo", e.getMessage());
    }

    // Caso 4 - Valor limite (logo abaixo do 0)
    // Condição: percentual < 0 é verdadeira
    @Test
    void deveRejeitarPercentualNegativo() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> calc.calcular(100, -0.01));
        assertEquals("Percentual deve estar entre 0 e 100", e.getMessage());
    }

    // Caso 5 - Valor limite (logo acima do 100)
    // Condição: percentual < 0 é falsa e percentual > 100 é verdadeira
    @Test
    void deveRejeitarPercentualAcimaDe100() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> calc.calcular(100, 100.01));
        assertEquals("Percentual deve estar entre 0 e 100", e.getMessage());
    }

    // Caso 6 - Partição, os dois parâmetros inválidos juntos.
    @Test
    void deveValidarValorAntesDoPercentual() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> calc.calcular(-1, 150));
        assertEquals("Valor da compra não pode ser negativo", e.getMessage());
    }
}
