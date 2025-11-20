package iscteiul.ista.battleship;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BargeTest {

    private final IPosition startPos = new Position(5, 5);

    @Test
    @DisplayName("1. Barge deve ser construída corretamente (Tamanho 1, Categoria Barca)")
    void testBargeCreation() {
        Barge barge = new Barge(Compass.NORTH, startPos);

        assertEquals(1, barge.getSize(), "O tamanho da Barca deve ser 1");
        assertEquals("Barca", barge.getCategory(), "A categoria deve ser 'Barca'");
        assertEquals(startPos, barge.getPosition(), "A posição inicial deve ser (5,5)");

        // O Barge é o único navio que ocupa apenas a posição inicial
        assertEquals(1, barge.getPositions().size(), "A Barca deve ter apenas uma posição no array de posições");
    }

    @Test
    @DisplayName("2. Barge deve ocupar a posição de início e retornar falso para adjacentes")
    void testBargeOccupiesCorrectPosition() {
        Barge barge = new Barge(Compass.EAST, startPos);

        // Deve ocupar a posição inicial
        assertTrue(barge.occupies(startPos));

        // Não deve ocupar posições adjacentes
        assertFalse(barge.occupies(new Position(5, 6))); // direita
        assertFalse(barge.occupies(new Position(6, 5))); // baixo
        assertFalse(barge.occupies(new Position(4, 4))); // diagonal
    }

    @Test
    @DisplayName("3. Barge deve estar 'stillFloating' até ser atingida")
    void testBargeStillFloating() {
        Barge barge = new Barge(Compass.NORTH, startPos);

        // Estado inicial: deve flutuar
        assertTrue(barge.stillFloating(), "A Barca deve estar a flutuar ao ser criada");

        // Simular um tiro na posição que ocupa
        barge.shoot(startPos);

        // Após ser atingida na sua única posição: não deve flutuar
        assertFalse(barge.stillFloating(), "A Barca deve afundar após ser atingida na sua única posição");
    }

    @Test
    @DisplayName("4. Os 'MostPos' devem retornar a linha e coluna da posição inicial")
    void testMostPositions() {
        Barge barge = new Barge(Compass.WEST, startPos); // (5, 5)

        assertEquals(5, barge.getTopMostPos(), "TopMostPos deve ser 5");
        assertEquals(5, barge.getBottomMostPos(), "BottomMostPos deve ser 5");
        assertEquals(5, barge.getLeftMostPos(), "LeftMostPos deve ser 5");
        assertEquals(5, barge.getRightMostPos(), "RightMostPos deve ser 5");
    }

    @Test
    @DisplayName("5. Deve lançar AssertionError se a posição inicial for nula")
    void testBargeNullPosition() {
        // Devido à asserção 'assert pos != null' na classe Ship,
        // esperamos que uma AssertionError seja lançada antes.
        assertThrows(AssertionError.class, () -> {
            new Barge(Compass.NORTH, null);
        }, "A asserção na classe base Ship deve lançar AssertionError para posição nula.");
    }

    @Test
    @DisplayName("6. Deve lançar AssertionError se o bearing for nulo")
    void testBargeNullBearing() {
        // Devido à asserção 'assert bearing != null' na classe Ship,
        // esperamos que uma AssertionError seja lançada.
        assertThrows(AssertionError.class, () -> {
            new Barge(null, startPos);
        }, "A asserção na classe base Ship deve lançar AssertionError para bearing nulo.");
    }
}
