package iscteiul.ista.battleship;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CaravelTest {

    private final IPosition startPos = new Position(5, 5);

    @Test
    @DisplayName("1. Caravel deve ser construída corretamente (Tamanho 2, Categoria Caravela)")
    void testCaravelCreation() {
        Caravel caravel = new Caravel(Compass.NORTH, startPos);

        assertEquals(2, caravel.getSize(), "O tamanho da Caravela deve ser 2");
        assertEquals("Caravela", caravel.getCategory(), "A categoria deve ser 'Caravela'");
    }

    @Test
    @DisplayName("2. Caravel com orientação NORTH/SOUTH deve ocupar 2 posições verticais")
    void testCaravelVerticalPositioning() {
        // Testa a orientação NORTH
        Caravel caravelNorth = new Caravel(Compass.NORTH, startPos);

        // Ocupa: (5, 5) e (6, 5)
        assertTrue(caravelNorth.occupies(new Position(5, 5)));
        assertTrue(caravelNorth.occupies(new Position(6, 5)));
        assertFalse(caravelNorth.occupies(new Position(7, 5))); // Fora do limite

        // Testa a orientação SOUTH (para garantir cobertura do bloco NORTH/SOUTH)
        Caravel caravelSouth = new Caravel(Compass.SOUTH, startPos);
        assertTrue(caravelSouth.occupies(new Position(6, 5)));
    }

    @Test
    @DisplayName("3. Caravel com orientação EAST/WEST deve ocupar 2 posições horizontais")
    void testCaravelHorizontalPositioning() {
        // Testa a orientação EAST
        Caravel caravelEast = new Caravel(Compass.EAST, startPos);

        // Ocupa: (5, 5) e (5, 6)
        assertTrue(caravelEast.occupies(new Position(5, 5)));
        assertTrue(caravelEast.occupies(new Position(5, 6)));
        assertFalse(caravelEast.occupies(new Position(5, 7))); // Fora do limite

        // Testa a orientação WEST (para garantir cobertura do bloco EAST/WEST)
        Caravel caravelWest = new Caravel(Compass.WEST, startPos);
        assertTrue(caravelWest.occupies(new Position(5, 6)));
    }

    @Test
    @DisplayName("4. Os 'MostPos' devem retornar os limites do navio")
    void testMostPositions() {
        Caravel caravel = new Caravel(Compass.NORTH, startPos); // Ocupa (5,5) a (6,5)

        assertEquals(5, caravel.getTopMostPos());
        assertEquals(6, caravel.getBottomMostPos());
        assertEquals(5, caravel.getLeftMostPos());
        assertEquals(5, caravel.getRightMostPos());
    }

    @Test
    @DisplayName("5. Deve lançar AssertionError para bearing nulo ou posição nula")
    void testCaravelNullInputs() {
        // Esperamos AssertionError, pois o 'assert bearing != null' na classe Ship
        // lança o erro antes da exceção da Caravela.
        assertThrows(AssertionError.class, () -> {
            new Caravel(null, startPos);
        }, "A asserção na classe Ship deve lançar AssertionError para bearing nulo.");

        // Também testamos o caso de Position ser nula
        assertThrows(AssertionError.class, () -> {
            new Caravel(Compass.NORTH, null);
        }, "A asserção na classe Ship deve lançar AssertionError para posição nula.");
    }
}