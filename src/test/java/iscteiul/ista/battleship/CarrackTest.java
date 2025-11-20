package iscteiul.ista.battleship;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CarrackTest {

    private final IPosition startPos = new Position(5, 5);

    @Test
    @DisplayName("1. Carrack deve ser construída corretamente (Tamanho 3, Categoria Nau)")
    void testCarrackCreation() {
        Carrack carrack = new Carrack(Compass.NORTH, startPos);

        assertEquals(3, carrack.getSize(), "O tamanho do Nau deve ser 3");
        assertEquals("Nau", carrack.getCategory(), "A categoria deve ser 'Nau'");
    }

    @Test
    @DisplayName("2. Carrack com orientação NORTH/SOUTH deve ocupar 3 posições verticais (cobre o 1º bloco do switch)")
    void testCarrackVerticalPositioning() {
        // Testa a orientação NORTH
        Carrack carrackNorth = new Carrack(Compass.NORTH, startPos);

        // Ocupa: (5, 5), (6, 5), (7, 5)
        assertTrue(carrackNorth.occupies(new Position(5, 5)));
        assertTrue(carrackNorth.occupies(new Position(7, 5)));
        assertFalse(carrackNorth.occupies(new Position(8, 5))); // Fora do limite

        // Testa a orientação SOUTH (para garantir cobertura do bloco NORTH/SOUTH)
        Carrack carrackSouth = new Carrack(Compass.SOUTH, startPos);
        assertTrue(carrackSouth.occupies(new Position(7, 5)));
    }

    @Test
    @DisplayName("3. Carrack com orientação EAST/WEST deve ocupar 3 posições horizontais (cobre o 2º bloco do switch)")
    void testCarrackHorizontalPositioning() {
        // Testa a orientação EAST
        Carrack carrackEast = new Carrack(Compass.EAST, startPos);

        // Ocupa: (5, 5), (5, 6), (5, 7)
        assertTrue(carrackEast.occupies(new Position(5, 5)));
        assertTrue(carrackEast.occupies(new Position(5, 7)));
        assertFalse(carrackEast.occupies(new Position(5, 8))); // Fora do limite

        // Testa a orientação WEST (para garantir cobertura do bloco EAST/WEST)
        Carrack carrackWest = new Carrack(Compass.WEST, startPos);
        assertTrue(carrackWest.occupies(new Position(5, 7)));
    }

    @Test
    @DisplayName("4. Os 'MostPos' devem retornar os limites do navio")
    void testMostPositions() {
        Carrack carrack = new Carrack(Compass.NORTH, startPos); // Ocupa (5,5) a (7,5)

        assertEquals(5, carrack.getTopMostPos());
        assertEquals(7, carrack.getBottomMostPos());
        assertEquals(5, carrack.getLeftMostPos());
        assertEquals(5, carrack.getRightMostPos());
    }

    @Test
    @DisplayName("5. Deve lançar AssertionError para bearing nulo ou posição nula")
    void testCarrackNullInputs() {
        // Esperamos AssertionError, pois o 'assert bearing != null' na classe Ship
        // lança o erro antes da exceção do Carrack.
        assertThrows(AssertionError.class, () -> {
            new Carrack(null, startPos);
        }, "A asserção na classe Ship deve lançar AssertionError para bearing nulo.");

        // Também testamos o caso de Position ser nula
        assertThrows(AssertionError.class, () -> {
            new Carrack(Compass.NORTH, null);
        }, "A asserção na classe Ship deve lançar AssertionError para posição nula.");
    }
}