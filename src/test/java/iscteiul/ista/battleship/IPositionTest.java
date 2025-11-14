package iscteiul.ista.battleship;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

import static org.junit.jupiter.api.Assertions.assertEquals;


class IPositionTest {

    @Test
    @DisplayName("getRow devolve a linha correta")
    void getRow() {
        Position p = new Position(2, 5);
        assertEquals(2, p.getRow());
    }


    @Test
    @DisplayName("getColumn devolve a coluna correta")
    void getColumn() {
        Position p = new Position(3, 7);
        assertEquals(7, p.getColumn());
    }


    @Test
    @DisplayName("equals identifica corretamente posições iguais")
    void testEquals() {
        Position p1 = new Position(4, 4);
        Position p2 = new Position(4, 4);

        assertTrue(p1.equals(p2));
    }


    @Test
    @DisplayName("isAdjacentTo deteta corretamente posições adjacentes")
    void isAdjacentTo() {
        Position p1 = new Position(3, 3);
        Position p2 = new Position(3, 4); // mesmo row, coluna ao lado

        assertTrue(p1.isAdjacentTo(p2));
    }


    @Test
    @DisplayName("occupy marca a posição como ocupada")
    void occupy() {
        Position p = new Position(2, 3);
        assertFalse(p.isOccupied());  // antes não está ocupada

        p.occupy();                   // ocupar

        assertTrue(p.isOccupied());   // agora está
    }


    @Test
    @DisplayName("shoot marca a posição como atingida")
    void shoot() {
        Position p = new Position(1, 1);
        assertFalse(p.isHit());

        p.shoot();

        assertTrue(p.isHit());
    }


    @Test
    @DisplayName("isOccupied é true depois de occupy()")
    void isOccupiedDepoisDeOcupar() {
        Position p = new Position(2, 3);

        p.occupy(); // ocupamos

        assertTrue(p.isOccupied());
    }


    @Test
    @DisplayName("isHit é true depois de shoot()")
    void isHitDepoisDeSerAtingido() {
        Position p = new Position(3, 3);

        p.shoot(); // atingimos

        assertTrue(p.isHit());
    }

}