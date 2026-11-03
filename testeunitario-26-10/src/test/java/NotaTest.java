import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;

class NotaTest {
    private Nota nota;

    @BeforeEach
    void setUp() {
        nota = new Nota();
    }

    @Test
    void menosUmRetornaExcecao() {
        assertThrows(IllegalArgumentException.class,
                () -> nota.determinarNota(-67));
    }

    @Test
    void maisDe100RetornaExcecao() {
        assertThrows(IllegalArgumentException.class,
                () -> nota.determinarNota(167));
    }

    @Test
    void nota59RetornaF() {
        assertEquals('F', nota.determinarNota(37));
    }

    @Test
    void nota67retornaD() {
        assertEquals('D', nota.determinarNota(67));
    }

    @Test
    void nota75RetornaC() {
        assertEquals('C', nota.determinarNota(77));
    }

    @Test
    void nota85RetornaB() {
        assertEquals('B', nota.determinarNota(87));
    }

    @Test
    void nota95RetornaA() {
        assertEquals('A', nota.determinarNota(97));
    }
}
