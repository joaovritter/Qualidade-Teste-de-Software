import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

/*
 * Exercício 2 - ContaBancaria
 *
 * Uma coisa que a gente decidiu conferir em todos os casos de erro: o saldo
 * não pode mudar quando a operação falha. Se o teste olhar só a exceção,
 * passa um bug tipo "subtraiu e depois lançou o erro".
 */
class ContaBancariaTest {

    private static final double DELTA = 0.0001;
    private ContaBancaria conta;

    // quase todos os testes começam com uma conta de 100 pra facilitar as contas
    @BeforeEach
    void preparar() {
        conta = new ContaBancaria(100);
    }

    // ---------- construtor ----------

    // Caso 1 - Partição de equivalência (saldo inicial positivo)
    @Test
    void deveCriarContaComSaldoPositivo() {
        assertEquals(150.0, new ContaBancaria(150).getSaldo(), DELTA);
    }

    // Caso 2 - Valor-limite: 0 tem que ser aceito (o if é < 0, não <= 0)
    // Ramo falso do if do construtor
    @Test
    void deveAceitarSaldoInicialZero() {
        assertEquals(0.0, new ContaBancaria(0).getSaldo(), DELTA);
    }

    // Caso 3 - Partição (inválida) + valor-limite (-0.01 é logo abaixo do 0)
    // Tratamento de erros / ramo verdadeiro
    @ParameterizedTest
    @ValueSource(doubles = {-0.01, -100})
    void deveRejeitarSaldoInicialNegativo(double saldo) {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> new ContaBancaria(saldo));
        assertEquals("Saldo inicial não pode ser negativo", e.getMessage());
    }

    // ---------- depósito ----------

    // Caso 4 - Partição (depósito válido) / ramo falso do if
    // Estado: o saldo aumenta exatamente o valor depositado
    @Test
    void deveSomarDepositoNoSaldo() {
        conta.depositar(50);
        assertEquals(150.0, conta.getSaldo(), DELTA);
    }

    // Caso 5 - Valor-limite: menor depósito positivo que testamos
    @Test
    void deveAceitarDepositoDeUmCentavo() {
        conta.depositar(0.01);
        assertEquals(100.01, conta.getSaldo(), DELTA);
    }

    // Caso 6 - Valor-limite (0 é inválido aqui, o if usa <=) + partição (negativos)
    // Tratamento de erros / ramo verdadeiro. Saldo tem que continuar 100.
    @ParameterizedTest
    @ValueSource(doubles = {0, -0.01, -50})
    void deveRejeitarDepositoZeroOuNegativo(double valor) {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> conta.depositar(valor));
        assertEquals("Valor do depósito deve ser positivo", e.getMessage());
        assertEquals(100.0, conta.getSaldo(), DELTA);
    }

    // Caso 7 - Estado: vários depósitos vão acumulando
    @Test
    void deveAcumularDepositosSeguidos() {
        conta.depositar(10);
        conta.depositar(20);
        conta.depositar(30);
        assertEquals(160.0, conta.getSaldo(), DELTA);
    }

    // ---------- saque ----------

    // Caso 8 - Partição (saque menor que o saldo)
    // Os dois ifs dão falso e chega no saldo -= valor
    @Test
    void deveDescontarSaqueDoSaldo() {
        conta.sacar(30);
        assertEquals(70.0, conta.getSaldo(), DELTA);
    }

    // Caso 9 - Valor-limite: sacar exatamente o saldo.
    // Achamos esse o mais importante da classe. O if é "valor > saldo"; se alguém
    // mudar pra >= a pessoa não consegue mais zerar a conta e só esse teste pega.
    @Test
    void devePermitirSacarTodoOSaldo() {
        conta.sacar(100);
        assertEquals(0.0, conta.getSaldo(), DELTA);
    }

    // Caso 10 - Valor-limite: menor saque positivo
    @Test
    void deveAceitarSaqueDeUmCentavo() {
        conta.sacar(0.01);
        assertEquals(99.99, conta.getSaldo(), DELTA);
    }

    // Caso 11 - Valor-limite + partição (zero e negativos)
    // Tratamento de erros / ramo verdadeiro do primeiro if do sacar
    @ParameterizedTest
    @ValueSource(doubles = {0, -0.01, -20})
    void deveRejeitarSaqueZeroOuNegativo(double valor) {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> conta.sacar(valor));
        assertEquals("Valor do saque deve ser positivo", e.getMessage());
        assertEquals(100.0, conta.getSaldo(), DELTA);
    }

    // Caso 12 - Valor-limite (100.01 é logo acima do saldo) + partição (500, bem acima)
    // Tratamento de erros / ramo verdadeiro do segundo if. Saldo não pode mudar.
    @ParameterizedTest
    @ValueSource(doubles = {100.01, 500})
    void deveRejeitarSaqueMaiorQueOSaldo(double valor) {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> conta.sacar(valor));
        assertEquals("Saldo insuficiente", e.getMessage());
        assertEquals(100.0, conta.getSaldo(), DELTA);
    }

    // Caso 13 - Valor-limite: conta com saldo zero não deixa sacar nem 1 centavo
    @Test
    void naoDevePermitirSaqueEmContaZerada() {
        ContaBancaria vazia = new ContaBancaria(0);
        assertThrows(IllegalArgumentException.class, () -> vazia.sacar(0.01));
        assertEquals(0.0, vazia.getSaldo(), DELTA);
    }

    // Caso 14 - Estado: sequência de operações, mais parecido com o uso real.
    // Serve pra ver se o "Saldo insuficiente" usa o saldo atual e não o inicial.
    @Test
    void deveUsarSempreOSaldoAtualizado() {
        ContaBancaria c = new ContaBancaria(50);
        c.depositar(50);      // 100
        c.sacar(80);          // 20
        assertThrows(IllegalArgumentException.class, () -> c.sacar(20.01));
        c.sacar(20);          // 0
        assertEquals(0.0, c.getSaldo(), DELTA);
    }
}
