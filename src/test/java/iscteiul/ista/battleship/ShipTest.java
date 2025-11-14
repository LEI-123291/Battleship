package iscteiul.ista.battleship;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ShipTest {

    private IPosition basePos;
    private IShip barge;           // barco de tamanho 1
    private IShip frigateEast;     // navio “genérico” horizontal
    private IShip frigateSouth;    // navio “genérico” vertical

    @BeforeEach
    void setUp() {
        basePos = new Position(3, 4);
        barge = new Barge(Compass.EAST, basePos);
        frigateEast = new Frigate(Compass.EAST, basePos);
        frigateSouth = new Frigate(Compass.SOUTH, basePos);
    }

    @Test
    @DisplayName("Categoria e tamanho são expostos corretamente pelas implementações de IShip")
    void categoryAndSizeAreCorrect() {
        assertAll(
                () -> {
                    assertEquals("Barca", barge.getCategory());
                    assertEquals(1, barge.getSize());
                },
                () -> {
                    assertEquals("Fragata", frigateEast.getCategory());
                    assertEquals(4, frigateEast.getSize());
                }
        );
    }

    @Test
    @DisplayName("getPosition e getBearing devolvem os valores usados na construção do navio")
    void positionAndBearingMatchConstruction() {
        assertEquals(basePos.getRow(), barge.getPosition().getRow());
        assertEquals(basePos.getColumn(), barge.getPosition().getColumn());
        assertEquals(Compass.EAST, barge.getBearing());

        assertEquals(basePos.getRow(), frigateEast.getPosition().getRow());
        assertEquals(basePos.getColumn(), frigateEast.getPosition().getColumn());
        assertEquals(Compass.EAST, frigateEast.getBearing());
    }

    @Test
    @DisplayName("Número de posições coincide com o tamanho do navio")
    void positionsSizeMatchesShipSize() {
        assertAll(
                () -> assertEquals(barge.getSize().intValue(), barge.getPositions().size()),
                () -> assertEquals(frigateEast.getSize().intValue(), frigateEast.getPositions().size())
        );
    }

    @Test
    @DisplayName("Posições são corretamente calculadas para bearings horizontal e vertical")
    void positionsComputedCorrectlyForBearings() {
        // Fragata horizontal (EAST): (3,4),(3,5),(3,6),(3,7)
        List<IPosition> eastPos = frigateEast.getPositions();
        assertAll(
                () -> assertEquals(3, eastPos.get(0).getRow()),
                () -> assertEquals(4, eastPos.get(0).getColumn()),
                () -> assertEquals(3, eastPos.get(3).getRow()),
                () -> assertEquals(7, eastPos.get(3).getColumn())
        );

        // Fragata vertical (SOUTH): (3,4),(4,4),(5,4),(6,4)
        List<IPosition> southPos = frigateSouth.getPositions();
        assertAll(
                () -> assertEquals(3, southPos.get(0).getRow()),
                () -> assertEquals(4, southPos.get(0).getColumn()),
                () -> assertEquals(6, southPos.get(3).getRow()),
                () -> assertEquals(4, southPos.get(3).getColumn())
        );
    }

    @Test
    @DisplayName("stillFloating é true enquanto existir pelo menos uma posição não atingida")
    void stillFloatingUntilAllPositionsHit() {
        assertTrue(frigateEast.stillFloating(), "No início o navio deve estar a flutuar");

        // Acertar em todas as posições
        for (IPosition p : frigateEast.getPositions()) {
            frigateEast.shoot(p);
        }

        assertFalse(frigateEast.stillFloating(), "Depois de todas as posições atingidas o navio não deve flutuar");
    }

    @Test
    @DisplayName("occupies devolve true para posições ocupadas e false para posições livres")
    void occupiesWorksForOccupiedAndEmptyPositions() {
        IPosition occupied = frigateEast.getPositions().get(0);
        IPosition alsoOccupied = frigateEast.getPositions().get(1);
        IPosition notOccupied = new Position(occupied.getRow(), occupied.getColumn() + 10);

        assertAll(
                () -> assertTrue(frigateEast.occupies(occupied)),
                () -> assertTrue(frigateEast.occupies(alsoOccupied)),
                () -> assertFalse(frigateEast.occupies(notOccupied))
        );
    }

    @Test
    @DisplayName("Extremos (top/bottom/left/right) são calculados corretamente para um navio horizontal")
    void extremePositionsForHorizontalShip() {
        // Fragata EAST a partir de (3,4) ocupa colunas 4..7
        assertAll(
                () -> assertEquals(3, frigateEast.getTopMostPos()),
                () -> assertEquals(3, frigateEast.getBottomMostPos()),
                () -> assertEquals(4, frigateEast.getLeftMostPos()),
                () -> assertEquals(7, frigateEast.getRightMostPos())
        );
    }

    @Test
    @DisplayName("tooCloseTo(IPosition) deteta posições adjacentes e ignora posições distantes")
    void tooCloseToPositionDetectsAdjacency() {
        IPosition occupied = barge.getPositions().get(0);
        IPosition adjacent = new Position(occupied.getRow(), occupied.getColumn() + 1); // mesmo linha, col+1
        IPosition far = new Position(occupied.getRow() + 5, occupied.getColumn() + 5);

        assertAll(
                () -> assertTrue(barge.tooCloseTo(adjacent), "Posição adjacente deve ser considerada demasiado próxima"),
                () -> assertFalse(barge.tooCloseTo(far), "Posição distante não deve ser considerada demasiado próxima")
        );
    }

    @Test
    @DisplayName("tooCloseTo(IShip) devolve true para navios próximos e false para navios afastados")
    void tooCloseToShipDetectsNearbyShips() {
        IShip nearShip = new Frigate(Compass.EAST, new Position(3, 6));   // perto da fragata de base (3,4)
        IShip farShip = new Frigate(Compass.EAST, new Position(10, 10));  // longe

        assertAll(
                () -> assertTrue(frigateEast.tooCloseTo(nearShip)),
                () -> assertFalse(frigateEast.tooCloseTo(farShip))
        );
    }

    @Test
    @DisplayName("shoot só marca como atingidas as posições que pertencem ao navio")
    void shootMarksOnlyShipPositionsAsHit() {
        IPosition hitPos = frigateSouth.getPositions().get(0);
        IPosition missPos = new Position(0, 0);

        assertFalse(hitPos.isHit());
        frigateSouth.shoot(missPos);   // tiro falhado, não deve alterar
        assertFalse(hitPos.isHit());

        frigateSouth.shoot(hitPos);    // tiro certeiro
        assertTrue(hitPos.isHit());
    }

}
