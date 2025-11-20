package iscteiul.ista.battleship;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

class FleetTest {

    private Fleet fleet;

    @BeforeEach
    void setup() {
        fleet = new Fleet();
    }

    // -------------------------------------------------------------
    // 1. TESTES ADD SHIP (Lógica, Limites e Colisões)
    // -------------------------------------------------------------

    @Test
    @DisplayName("Adicionar barco válido deve retornar true e aumentar a frota")
    void testAddValidShip() {
        IShip ship = new Barge(Compass.NORTH, new Position(0, 0));
        boolean added = fleet.addShip(ship);

        assertTrue(added);
        assertEquals(1, fleet.getShips().size());
    }

    @Test
    @DisplayName("Não deve adicionar barco null")
    void testAddShipNull() {
        boolean result = fleet.addShip(null);
        assertFalse(result, "Adicionar null deve retornar false");
    }

    @Test
    @DisplayName("Não deve adicionar barco totalmente fora do tabuleiro")
    void testAddShipOutsideBoard() {
        // Board size = 10 → índices válidos: 0 a 9
        IShip ship = new Barge(Compass.NORTH, new Position(15, 15));
        boolean added = fleet.addShip(ship);
        assertFalse(added);
    }

    @Test
    @DisplayName("Não deve adicionar barco que começa dentro mas sai dos limites (Straddling)")
    void testAddShipPartiallyOutside() {
        // Fragata (tamanho 4) na posição (0, 8) virada para ESTE.
        // Ocuparia colunas: 8, 9, 10, 11. (10 e 11 são inválidos)
        IShip ship = new Frigate(Compass.EAST, new Position(0, 8));

        boolean added = fleet.addShip(ship);
        assertFalse(added, "Navio que excede os limites laterais não deve ser adicionado");
    }

    @Test
    @DisplayName("Não deve adicionar barco com risco de colisão")
    void testAddShipCollisionRisk() {
        IShip ship1 = new Barge(Compass.NORTH, new Position(5, 5));
        fleet.addShip(ship1);

        // Barco na posição (5,6) é adjacente a (5,5) -> colisão
        IShip ship2 = new Barge(Compass.NORTH, new Position(5, 6));

        boolean added = fleet.addShip(ship2);
        assertFalse(added);
        assertEquals(1, fleet.getShips().size());
    }

    @Test
    @DisplayName("Não deve adicionar barco se a frota já estiver cheia (FLEET_SIZE=10)")
    void testAddShipFleetFull() {
        // CORREÇÃO: Usar espaçamento para evitar colisões ao encher a frota.
        // Se colarmos os barcos (ex: 0,0 e 0,1), o addShip devolve false por colisão e a frota não enche.
        int count = 0;
        for (int r = 0; r < IFleet.BOARD_SIZE && count < IFleet.FLEET_SIZE; r += 2) {
            for (int c = 0; c < IFleet.BOARD_SIZE && count < IFleet.FLEET_SIZE; c += 2) {
                boolean added = fleet.addShip(new Barge(Compass.NORTH, new Position(r, c)));
                assertTrue(added, "Erro ao preparar frota: barco colidiu ou falhou");
                count++;
            }
        }

        // Verificação intermédia: garantir que temos mesmo 10 barcos
        assertEquals(10, fleet.getShips().size(), "A frota devia estar cheia com 10 barcos antes do teste final");

        // Tentar adicionar o 11º barco
        IShip extraShip = new Barge(Compass.NORTH, new Position(9, 9)); // Posição vazia segura
        assertFalse(fleet.addShip(extraShip), "Não deve aceitar mais que 10 navios");
    }

    // -------------------------------------------------------------
    // 2. TESTES DE PESQUISA (GetShipsLike, ShipAt)
    // -------------------------------------------------------------

    @Test
    @DisplayName("Obter barcos de uma categoria específica")
    void testGetShipsLike() {
        fleet.addShip(new Barge(Compass.NORTH, new Position(0, 0)));       // Barca
        fleet.addShip(new Caravel(Compass.EAST, new Position(2, 0)));      // Caravela

        List<IShip> barges = fleet.getShipsLike("Barca");
        assertEquals(1, barges.size());
        assertEquals("Barca", barges.get(0).getCategory());
    }

    @Test
    @DisplayName("getShipsLike deve lidar com categoria null")
    void testGetShipsLikeNull() {
        List<IShip> result = fleet.getShipsLike(null);
        assertNotNull(result);
        assertTrue(result.isEmpty(), "Categoria null deve retornar lista vazia");
    }

    @Test
    @DisplayName("shipAt deve retornar o barco correto na posição indicada")
    void testShipAtValidPos() {
        IShip ship = new Caravel(Compass.EAST, new Position(0, 0)); // ocupa (0,0) e (0,1)
        fleet.addShip(ship);

        IShip result = fleet.shipAt(new Position(0, 1));
        assertEquals(ship, result);
    }

    @Test
    @DisplayName("shipAt deve retornar null para posição vazia ou null")
    void testShipAtEdgeCases() {
        fleet.addShip(new Barge(Compass.NORTH, new Position(0, 0)));

        assertNull(fleet.shipAt(new Position(5, 5)), "Posição vazia deve dar null");
        assertNull(fleet.shipAt(null), "Posição null deve dar null");
    }

    // -------------------------------------------------------------
    // 3. TESTES DE ESTADO (FloatingShips)
    // -------------------------------------------------------------

    @Test
    @DisplayName("Gerir barcos flutuantes vs afundados")
    void testGetFloatingShips() {
        IShip ship = new Barge(Compass.NORTH, new Position(0, 0));
        fleet.addShip(ship);

        // 1. Inicialmente flutua
        assertEquals(1, fleet.getFloatingShips().size());

        // 2. Disparar e afundar
        ship.shoot(new Position(0, 0));

        // 3. Já não deve aparecer na lista de flutuantes
        assertTrue(fleet.getFloatingShips().isEmpty());
        // Mas ainda está na lista total
        assertEquals(1, fleet.getShips().size());
    }

    // -------------------------------------------------------------
    // 4. TESTES DE OUTPUT (Coverage dos métodos Print)
    // -------------------------------------------------------------

    @Test
    @DisplayName("Executar printStatus com frota preenchida")
    void testPrintStatus() {
        fleet.addShip(new Barge(Compass.NORTH, new Position(0, 0)));
        fleet.addShip(new Caravel(Compass.EAST, new Position(2, 2)));

        assertDoesNotThrow(() -> fleet.printStatus());
    }

    @Test
    @DisplayName("Executar printShipsByCategory com categoria null")
    void testPrintShipsByCategoryNull() {
        assertDoesNotThrow(() -> fleet.printShipsByCategory(null));
    }

    @Test
    @DisplayName("Executar método estático printShips com lista null")
    void testStaticPrintShipsNull() {
        assertDoesNotThrow(() -> Fleet.printShips(null));
    }
}