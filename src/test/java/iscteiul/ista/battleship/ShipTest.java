package iscteiul.ista.battleship;

import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ShipTest {

    @Nested
    @DisplayName("Propriedades básicas de um navio genérico")
    class BasicProperties {

        private IShip barge;
        private IShip frigate;
        private IPosition basePos;

        @BeforeEach
        void setUp() {
            basePos = new Position(3, 4);
            barge = new Barge(Compass.EAST, basePos);
            frigate = new Frigate(Compass.EAST, basePos);
        }

        @Test
        @DisplayName("Categoria e tamanho são expostos corretamente")
        void categoryAndSizeAreCorrect() {
            assertAll(
                    () -> {
                        assertEquals("Barca", barge.getCategory());
                        assertEquals(1, barge.getSize());
                    },
                    () -> {
                        assertEquals("Fragata", frigate.getCategory());
                        assertEquals(4, frigate.getSize());
                    }
            );
        }

        @Test
        @DisplayName("Posição e orientação correspondem à construção")
        void positionAndBearingMatchConstruction() {
            assertEquals(basePos.getRow(), barge.getPosition().getRow());
            assertEquals(basePos.getColumn(), barge.getPosition().getColumn());
            assertEquals(Compass.EAST, barge.getBearing());
        }
    }

    @Nested
    @DisplayName("Cálculo de posições e extremos")
    class PositionsAndExtremes {

        private IShip frigateEast;
        private IShip frigateSouth;
        private IPosition basePos;

        @BeforeEach
        void setUp() {
            basePos = new Position(3, 4);
            frigateEast = new Frigate(Compass.EAST, basePos);
            frigateSouth = new Frigate(Compass.SOUTH, basePos);
        }

        @Test
        @DisplayName("Posições são calculadas corretamente para bearings horizontal e vertical")
        void positionsComputedCorrectlyForBearings() {
            List<IPosition> eastPos = frigateEast.getPositions();
            assertAll(
                    () -> assertEquals(3, eastPos.get(0).getRow()),
                    () -> assertEquals(4, eastPos.get(0).getColumn()),
                    () -> assertEquals(3, eastPos.get(3).getRow()),
                    () -> assertEquals(7, eastPos.get(3).getColumn())
            );

            List<IPosition> southPos = frigateSouth.getPositions();
            assertAll(
                    () -> assertEquals(3, southPos.get(0).getRow()),
                    () -> assertEquals(4, southPos.get(0).getColumn()),
                    () -> assertEquals(6, southPos.get(3).getRow()),
                    () -> assertEquals(4, southPos.get(3).getColumn())
            );
        }

        @Test
        @DisplayName("Extremos do navio horizontal são corretos")
        void extremePositionsForHorizontalShip() {
            assertAll(
                    () -> assertEquals(3, frigateEast.getTopMostPos()),
                    () -> assertEquals(3, frigateEast.getBottomMostPos()),
                    () -> assertEquals(4, frigateEast.getLeftMostPos()),
                    () -> assertEquals(7, frigateEast.getRightMostPos())
            );
        }
    }

    @Nested
    @DisplayName("Ocupação e proximidade de posições e navios")
    class OccupancyAndProximity {

        private IShip barge;
        private IShip frigate;

        @BeforeEach
        void setUp() {
            barge = new Barge(Compass.EAST, new Position(3, 3));
            frigate = new Frigate(Compass.EAST, new Position(3, 4));
        }

        @Test
        @DisplayName("occupies devolve true apenas para posições pertencentes ao navio")
        void occupiesWorksForOccupiedAndEmptyPositions() {
            IPosition occupied = frigate.getPositions().get(0);
            IPosition alsoOccupied = frigate.getPositions().get(1);
            IPosition notOccupied = new Position(occupied.getRow(), occupied.getColumn() + 10);

            assertAll(
                    () -> assertTrue(frigate.occupies(occupied)),
                    () -> assertTrue(frigate.occupies(alsoOccupied)),
                    () -> assertFalse(frigate.occupies(notOccupied))
            );
        }

        @Test
        @DisplayName("tooCloseTo(IPosition) deteta posições adjacentes e ignora distantes")
        void tooCloseToPositionDetectsAdjacency() {
            IPosition occupied = barge.getPositions().get(0);
            IPosition adjacent = new Position(occupied.getRow(), occupied.getColumn() + 1);
            IPosition far = new Position(occupied.getRow() + 5, occupied.getColumn() + 5);

            assertAll(
                    () -> assertTrue(barge.tooCloseTo(adjacent)),
                    () -> assertFalse(barge.tooCloseTo(far))
            );
        }

        @Test
        @DisplayName("tooCloseTo(IShip) devolve true para navios próximos e false para afastados")
        void tooCloseToShipDetectsNearbyShips() {
            IShip nearShip = new Frigate(Compass.EAST, new Position(3, 6));
            IShip farShip = new Frigate(Compass.EAST, new Position(10, 10));

            assertAll(
                    () -> assertTrue(frigate.tooCloseTo(nearShip)),
                    () -> assertFalse(frigate.tooCloseTo(farShip))
            );
        }
    }

    @Nested
    @DisplayName("Disparos e estado de flutuação")
    class ShootingAndFloating {

        private IShip frigate;

        @BeforeEach
        void setUp() {
            frigate = new Frigate(Compass.SOUTH, new Position(5, 5));
        }

        @Test
        @DisplayName("shoot só altera o estado quando a posição pertence ao navio")
        void shootHitAndMiss() {
            IPosition hitPos = frigate.getPositions().get(0);
            IPosition missPos = new Position(0, 0);

            assertFalse(hitPos.isHit());

            frigate.shoot(missPos);
            assertFalse(hitPos.isHit());

            frigate.shoot(hitPos);
            assertTrue(hitPos.isHit());
        }

        @Test
        @DisplayName("stillFloating torna-se false quando todas as posições são atingidas")
        void stillFloatingBecomesFalseAfterAllHits() {
            assertTrue(frigate.stillFloating());

            for (IPosition p : frigate.getPositions()) {
                frigate.shoot(p);
            }

            assertFalse(frigate.stillFloating());
        }
    }
    @Nested
    @DisplayName("Método fábrica buildShip")
    class BuildShipFactoryTests {

        @Test
        @DisplayName("buildShip cria o tipo de navio correto para cada código conhecido")
        void buildShipCreatesCorrectShipType() {
            Position pos = new Position(1, 1);

            Ship barge = Ship.buildShip("barca", Compass.EAST, pos);
            Ship caravel = Ship.buildShip("caravela", Compass.NORTH, pos);
            Ship carrack = Ship.buildShip("nau", Compass.SOUTH, pos);
            Ship frigate = Ship.buildShip("fragata", Compass.WEST, pos);
            Ship galleon = Ship.buildShip("galeao", Compass.EAST, pos);

            assertAll(
                    () -> assertTrue(barge instanceof Barge),
                    () -> assertTrue(caravel instanceof Caravel),
                    () -> assertTrue(carrack instanceof Carrack),
                    () -> assertTrue(frigate instanceof Frigate),
                    () -> assertTrue(galleon instanceof Galleon)
            );
        }

        @Test
        @DisplayName("buildShip devolve null para um tipo de navio desconhecido")
        void buildShipReturnsNullForUnknownKind() {
            Position pos = new Position(1, 1);

            Ship unknown = Ship.buildShip("navio-fantasma", Compass.EAST, pos);

            assertNull(unknown);
        }
    }

}
