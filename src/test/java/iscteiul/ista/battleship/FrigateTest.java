package iscteiul.ista.battleship;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class FrigateTest {

    private final IPosition startPos = new Position(5, 5);

    @Test
    @DisplayName("1. Frigate com orientação NORTH deve ocupar 4 posições verticais")
    void testFrigateNorth() {
        Frigate frigate = new Frigate(Compass.NORTH, startPos);

        assertEquals(4, frigate.getSize());
        // Deve ocupar: (5,5), (6,5), (7,5), (8,5)
        assertTrue(frigate.occupies(new Position(5, 5)));
        assertTrue(frigate.occupies(new Position(8, 5)));
    }

    @Test
    @DisplayName("2. Frigate com orientação SOUTH deve ocupar 4 posições verticais (cobre o primeiro bloco do switch)")
    void testFrigateSouth() {
        Frigate frigate = new Frigate(Compass.SOUTH, startPos);

        assertEquals(4, frigate.getSize());
        // Deve ocupar: (5,5), (6,5), (7,5), (8,5)
        assertTrue(frigate.occupies(new Position(5, 5)));
        assertTrue(frigate.occupies(new Position(8, 5)));
    }

    @Test
    @DisplayName("3. Frigate com orientação EAST deve ocupar 4 posições horizontais")
    void testFrigateEast() {
        Frigate frigate = new Frigate(Compass.EAST, startPos);

        assertEquals(4, frigate.getSize());
        // Deve ocupar: (5,5), (5,6), (5,7), (5,8)
        assertTrue(frigate.occupies(new Position(5, 5)));
        assertTrue(frigate.occupies(new Position(5, 8)));
    }

    @Test
    @DisplayName("4. Frigate com orientação WEST deve ocupar 4 posições horizontais (cobre o segundo bloco do switch)")
    void testFrigateWest() {
        Frigate frigate = new Frigate(Compass.WEST, startPos);

        assertEquals(4, frigate.getSize());
        // Deve ocupar: (5,5), (5,6), (5,7), (5,8)
        assertTrue(frigate.occupies(new Position(5, 5)));
        assertTrue(frigate.occupies(new Position(5, 8)));
    }

    @Test
    @DisplayName("5. Frigate deve lançar exceção se Position for nula")
    void testFrigatePositionNull() {
        // Testa a asserção ou validação no construtor do Ship (o pai) ou na Frigate
        // Assumindo que a asserção no Ship está activa (assert pos != null)
        // ou se o construtor Ship for mais robusto (throws NPE)
        assertThrows(AssertionError.class, () -> {
            new Frigate(Compass.NORTH, null);
        });
    }
}