package iscteiul.ista.battleship;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class GalleonTest {

    private final IPosition startPos = new Position(5, 5); // Ponto (Row, Col) = (Linha, Coluna)

    @Test
    @DisplayName("1. Galleon com orientação NORTH deve ocupar as 5 posições corretas")
    void testGalleonNorth() {
        Galleon galleon = new Galleon(Compass.NORTH, startPos);
        assertEquals(5, galleon.getSize());

        // Posições esperadas (do fillNorth, com startPos(5, 5)):
        // (5, 5), (5, 6), (5, 7)
        // (6, 6)
        // (7, 6)

        assertTrue(galleon.occupies(new Position(5, 5)));
        assertTrue(galleon.occupies(new Position(5, 7)));
        assertTrue(galleon.occupies(new Position(6, 6)));
        assertTrue(galleon.occupies(new Position(7, 6)));
        assertFalse(galleon.occupies(new Position(5, 4)));

        // Testar os 4 extremos para cobertura (min/max Row e Col)
        assertEquals(5, galleon.getTopMostPos());
        assertEquals(7, galleon.getBottomMostPos());
        assertEquals(5, galleon.getLeftMostPos());
        assertEquals(7, galleon.getRightMostPos());
    }

    @Test
    @DisplayName("2. Galleon com orientação SOUTH deve ocupar as 5 posições corretas")
    void testGalleonSouth() {
        Galleon galleon = new Galleon(Compass.SOUTH, startPos);
        assertEquals(5, galleon.getSize());

        // Posições esperadas (baseadas no fillSouth fornecido):
        // (5, 5), (6, 5)
        // (7, 4), (7, 5), (7, 6)
        assertTrue(galleon.occupies(new Position(5, 5)));
        assertTrue(galleon.occupies(new Position(6, 5)));
        assertTrue(galleon.occupies(new Position(7, 4)));
        assertTrue(galleon.occupies(new Position(7, 6)));
    }

    @Test
    @DisplayName("3. Galleon com orientação EAST deve ocupar as 5 posições corretas")
    void testGalleonEast() {
        Galleon galleon = new Galleon(Compass.EAST, startPos);

        // Posições esperadas (baseadas no fillEast fornecido):
        // (5, 5)
        // (6, 3), (6, 4), (6, 5)
        // (7, 5)
        assertTrue(galleon.occupies(new Position(5, 5)));
        assertTrue(galleon.occupies(new Position(6, 3)));
        assertTrue(galleon.occupies(new Position(6, 4)));
        assertTrue(galleon.occupies(new Position(7, 5)));
    }

    @Test
    @DisplayName("4. Galleon com orientação WEST deve ocupar as 5 posições corretas")
    void testGalleonWest() {
        Galleon galleon = new Galleon(Compass.WEST, startPos);

        // Posições esperadas (baseadas no fillWest fornecido):
        // (5, 5)
        // (6, 5), (6, 6), (6, 7)
        // (7, 5)
        assertTrue(galleon.occupies(new Position(5, 5)));
        assertTrue(galleon.occupies(new Position(6, 5)));
        assertTrue(galleon.occupies(new Position(6, 7)));
        assertTrue(galleon.occupies(new Position(7, 5)));
    }

    @Test
    @DisplayName("5. Galleon deve lançar exceção para bearing null")
    void testGalleonNullBearing() {
        // CORREÇÃO: Alterado para esperar AssertionError, que é lançada pela asserção
        // `assert bearing != null` no construtor da classe pai (Ship), antes que
        // a NullPointerException do Galleon seja alcançada.
        assertThrows(AssertionError.class, () -> {
            new Galleon(null, startPos);
        }, "A asserção na classe base Ship deve lançar AssertionError.");
    }
}
