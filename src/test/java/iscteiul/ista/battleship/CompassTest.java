package iscteiul.ista.battleship;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testes para a enum Compass")
class CompassTest {

    @Test
    @DisplayName("getDirection devolve o char correto para cada direção")
    void getDirection() {
        assertEquals('n', Compass.NORTH.getDirection());
        assertEquals('s', Compass.SOUTH.getDirection());
        assertEquals('e', Compass.EAST.getDirection());
        assertEquals('o', Compass.WEST.getDirection());
        assertEquals('u', Compass.UNKNOWN.getDirection());
    }

    @Test
    @DisplayName("toString devolve a string correta para cada direção")
    void testToString() {
        assertEquals("n", Compass.NORTH.toString());
        assertEquals("s", Compass.SOUTH.toString());
        assertEquals("e", Compass.EAST.toString());
        assertEquals("o", Compass.WEST.toString());
        assertEquals("u", Compass.UNKNOWN.toString());
    }

    @Test
    @DisplayName("charToCompass converte corretamente chars válidos")
    void charToCompassValidos() {
        assertEquals(Compass.NORTH, Compass.charToCompass('n'));
        assertEquals(Compass.SOUTH, Compass.charToCompass('s'));
        assertEquals(Compass.EAST, Compass.charToCompass('e'));
        assertEquals(Compass.WEST, Compass.charToCompass('o'));
    }

    @Test
    @DisplayName("charToCompass devolve UNKNOWN para chars inválidos ou maiúsculos")
    void charToCompassInvalidos() {
        assertEquals(Compass.UNKNOWN, Compass.charToCompass('x'));
        assertEquals(Compass.UNKNOWN, Compass.charToCompass('N')); // maiúsculas também são inválidas
        assertEquals(Compass.UNKNOWN, Compass.charToCompass(' '));
        assertEquals(Compass.UNKNOWN, Compass.charToCompass('1'));
    }

    @Test
    @DisplayName("values devolve todas as direções na ordem correta")
    void valuesTest() {
        Compass[] vals = Compass.values();

        assertEquals(5, vals.length);
        assertArrayEquals(
                new Compass[]{
                        Compass.NORTH,
                        Compass.SOUTH,
                        Compass.EAST,
                        Compass.WEST,
                        Compass.UNKNOWN
                },
                vals
        );
    }

    @Test
    @DisplayName("valueOf devolve a direção correta quando o nome é válido")
    void valueOfTest() {
        assertEquals(Compass.NORTH, Compass.valueOf("NORTH"));
        assertEquals(Compass.SOUTH, Compass.valueOf("SOUTH"));
        assertEquals(Compass.EAST, Compass.valueOf("EAST"));
        assertEquals(Compass.WEST, Compass.valueOf("WEST"));
        assertEquals(Compass.UNKNOWN, Compass.valueOf("UNKNOWN"));
    }

    @Test
    @DisplayName("valueOf lança exceção quando o nome é inválido")
    void valueOfInvalido() {
        assertThrows(IllegalArgumentException.class, () -> Compass.valueOf("XPTO"));
    }
}
