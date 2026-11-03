import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

/*
 * Exercício 3 - ReservaHotel
 *
 * Regras que a gente tirou do código:
 *   R1 - diárias > 0 e valor da diária > 0
 *   R2 - café custa 30 por diária
 *   R3 - 7 diárias ou mais tem 10% de desconto (em cima do total COM café)
 *   R4 - reserva cancelada vale 0
 *   R5 - reserva cancelada não aceita café nem pode ser cancelada de novo
 *
 * Estados: ATIVA (com ou sem café) -> CANCELADA. Não tem volta.
 *
 * Os caminhos 1 a 5 são do GFC do calcularValorTotal() no RELATORIO.md.
 */
class ReservaHotelTest {

    private static final double DELTA = 0.0001;
    private ReservaHotel reserva;

    // usada nos testes de estado: 3 diárias de 100, total 300
    @BeforeEach
    void preparar() {
        reserva = new ReservaHotel(3, 100);
    }

    // ---------- construtor (R1) ----------

    // Caso 1 - Partição + valor-limite: 0 é o limite (inválido), -1 representa os negativos
    // Tratamento de erros / ramo verdadeiro do primeiro if
    @ParameterizedTest
    @ValueSource(ints = {0, -1})
    void deveRejeitarDiariasZeroOuNegativas(int diarias) {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> new ReservaHotel(diarias, 100));
        assertEquals("Número de diárias deve ser maior que zero", e.getMessage());
    }

    // Caso 2 - Mesmo raciocínio pro valor da diária (é double, então usamos -0.01 também)
    // Tratamento de erros / ramo verdadeiro do segundo if
    @ParameterizedTest
    @ValueSource(doubles = {0, -0.01, -100})
    void deveRejeitarValorDaDiariaZeroOuNegativo(double valor) {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> new ReservaHotel(2, valor));
        assertEquals("Valor da diária deve ser maior que zero", e.getMessage());
    }

    // Caso 3 - Valor-limite: menores valores válidos (1 diária, diária de 0.01)
    // Os dois ifs dão falso e a reserva começa não cancelada
    @Test
    void deveCriarReservaAtivaNosLimitesMinimos() {
        ReservaHotel r = new ReservaHotel(1, 0.01);
        assertFalse(r.isCancelada());
        assertEquals(0.01, r.calcularValorTotal(), DELTA);
    }

    // ---------- valor total (R2, R3, R4) ----------

    // Caso 4 - Caminhos independentes do GFC + valor-limite 6/7 do desconto
    //   caminho 2: sem café, < 7 diárias
    //   caminho 3: com café, < 7 diárias
    //   caminho 4: sem café, >= 7 diárias
    //   caminho 5: com café, >= 7 diárias
    // Conta pra conferir o caminho 5: 7 * (100 + 30) = 910, menos 10% = 819
    @ParameterizedTest
    @CsvSource({
            "6, false, 600",   // Caminho 2 - logo abaixo do limite do desconto
            "6, true,  780",   // Caminho 3
            "7, false, 630",   // Caminho 4 - exatamente no limite, já ganha desconto
            "7, true,  819",   // Caminho 5 - desconto em cima do café também
            "1, true,  130",   // Caminho 3 - mínimo de diárias com café
            "10, false, 900"   // Caminho 4 - partição "muitas diárias"
    })
    void deveCalcularValorTotal(int diarias, boolean cafe, double esperado) {
        ReservaHotel r = new ReservaHotel(diarias, 100);
        if (cafe) {
            r.adicionarCafeDaManha();
        }
        assertEquals(esperado, r.calcularValorTotal(), DELTA);
    }

    // Caso 5 - Caminho 1 (cancelada sai no primeiro return) / estado CANCELADA
    @Test
    void deveRetornarZeroParaReservaCancelada() {
        reserva.cancelar();
        assertEquals(0.0, reserva.calcularValorTotal(), DELTA);
    }

    // Caso 6 - Caminho 1 de novo, mas com café e desconto antes de cancelar.
    // Queríamos garantir que o café adicionado antes não "sobra" no valor.
    @Test
    void deveZerarReservaCanceladaMesmoComCafeEDesconto() {
        ReservaHotel r = new ReservaHotel(8, 100);
        r.adicionarCafeDaManha();
        r.cancelar();
        assertEquals(0.0, r.calcularValorTotal(), DELTA);
    }

    // Caso 7 - Regra de negócio: adicionar café 2x não cobra 2x (é boolean, não contador)
    @Test
    void naoDeveCobrarCafeEmDobro() {
        reserva.adicionarCafeDaManha();
        reserva.adicionarCafeDaManha();
        assertEquals(390.0, reserva.calcularValorTotal(), DELTA);
    }

    // ---------- estados (R5) ----------

    // Caso 8 - Estado ATIVA -> CANCELADA / ramo falso do if do cancelar()
    @Test
    void deveCancelarReservaAtiva() {
        assertFalse(reserva.isCancelada());
        reserva.cancelar();
        assertTrue(reserva.isCancelada());
    }

    // Caso 9 - Estado: cancelar de novo não pode
    // Tratamento de erros / ramo verdadeiro do if do cancelar()
    @Test
    void naoDeveCancelarDuasVezes() {
        reserva.cancelar();
        IllegalStateException e = assertThrows(IllegalStateException.class,
                () -> reserva.cancelar());
        assertEquals("Reserva já está cancelada", e.getMessage());
        assertTrue(reserva.isCancelada());
    }

    // Caso 10 - Estado: café em reserva cancelada não pode
    // Tratamento de erros / ramo verdadeiro do if do adicionarCafeDaManha().
    // Depois do erro o total continua 0, ou seja, nada mudou.
    @Test
    void naoDeveAdicionarCafeEmReservaCancelada() {
        reserva.cancelar();
        IllegalStateException e = assertThrows(IllegalStateException.class,
                () -> reserva.adicionarCafeDaManha());
        assertEquals("Não é possível alterar uma reserva cancelada", e.getMessage());
        assertEquals(0.0, reserva.calcularValorTotal(), DELTA);
    }

    // Caso 11 - Estado: ATIVA sem café -> ATIVA com café, o total tem que mudar
    // Ramo falso do if do adicionarCafeDaManha()
    @Test
    void deveAumentarTotalAoAdicionarCafe() {
        assertEquals(300.0, reserva.calcularValorTotal(), DELTA);
        reserva.adicionarCafeDaManha();
        assertEquals(390.0, reserva.calcularValorTotal(), DELTA);
    }
}
