
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

public class CalculadoraSimplesTest {
    //cada teste deve testar somente uma unica coisa, em um unico cenario
    //o nome do teste deve descrever o caso de teste e resultado

    private CalculadoraSimples calculadora;

    @BeforeEach
    void setUp() {
        //criar uman nova calculadora antes de cada teste:
        var calculadora = new CalculadoraSimples();
    }

    @Test
    @DisplayName ("2 + 2 = 4") 
    void doisMaisDoisIgualQuatro() {
        var calculadora = new CalculadoraSimples();
        assertEquals(4, calculadora.somar(2, 2));

    }

    @Test 
    @DisplayName ("2 + 3 = 5")
    void doisMaisTresIgualCinco() {
        var calculadora = new CalculadoraSimples();
        assertEquals(5, calculadora.somar(2, 3));
    }


}
