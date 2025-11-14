package iscteiul.ista.battleship;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Parte D - Cobertura global para Position e Compass (Miguel)")
class GlobalPositionCompassTest {

    @Nested
    @DisplayName("Position - ramos, condições e caminhos extra")
    class PositionExtraTests {

        @Test
        @DisplayName("equals devolve false para null e para objetos de outro tipo")
        void equalsNullAndOtherType() {
            Position p = new Position(2, 3);

            assertFalse(p.equals(null));             // other == null
            assertFalse(p.equals("não é posição"));  // other não é Position
        }

        @Test
        @DisplayName("equals devolve false para posições com mesma linha mas coluna diferente")
        void equalsSameRowDifferentColumn() {
            Position p1 = new Position(2, 3);
            Position p2 = new Position(2, 4);

            assertFalse(p1.equals(p2));
        }

        @Test
        @DisplayName("isAdjacentTo considera a própria posição como adjacente")
        void isAdjacentToSamePosition() {
            Position p = new Position(5, 5);

            assertTrue(p.isAdjacentTo(p));
        }


        @Test
        @DisplayName("isAdjacentTo devolve true para adjacência diagonal imediata")
        void isAdjacentToDiagonal() {
            Position p1 = new Position(5, 5);
            Position p2 = new Position(6, 6);   // diferença de 1 em linha e coluna

            assertTrue(p1.isAdjacentTo(p2));
        }

        @Test
        @DisplayName("isAdjacentTo devolve false para posições distantes (diferença > 1)")
        void isAdjacentToFarAway() {
            Position p1 = new Position(1, 1);
            Position p2 = new Position(3, 3);   // diferença de 2

            assertFalse(p1.isAdjacentTo(p2));
        }
    }

    @Nested
    @DisplayName("Compass - ramos e condições extra")
    class CompassExtraTests {

        @Test
        @DisplayName("charToCompass devolve UNKNOWN para várias letras inválidas")
        void charToCompassInvalidos() {
            assertEquals(Compass.UNKNOWN, Compass.charToCompass('x'));
            assertEquals(Compass.UNKNOWN, Compass.charToCompass(' '));
            assertEquals(Compass.UNKNOWN, Compass.charToCompass('1'));
        }

        @Test
        @DisplayName("charToCompass é sensível a maiúsculas (não aceita 'N', 'S', 'E', 'O')")
        void charToCompassMaiusculas() {
            assertEquals(Compass.UNKNOWN, Compass.charToCompass('N'));
            assertEquals(Compass.UNKNOWN, Compass.charToCompass('S'));
            assertEquals(Compass.UNKNOWN, Compass.charToCompass('E'));
            assertEquals(Compass.UNKNOWN, Compass.charToCompass('O'));
        }
    }
}
