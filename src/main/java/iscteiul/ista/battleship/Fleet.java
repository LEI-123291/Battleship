package iscteiul.ista.battleship;

import java.util.ArrayList;
import java.util.List;

public class Fleet implements IFleet {

    /**
     * Prints all given ships (safe, testable)
     */
    static void printShips(List<IShip> ships) {
        if (ships == null || ships.isEmpty()) {
            System.out.println("(no ships)");
            return;
        }
        for (IShip ship : ships) {
            System.out.println(ship);
        }
    }

    // -----------------------------------------------------

    private final List<IShip> ships;

    public Fleet() {
        this.ships = new ArrayList<>();
    }

    @Override
    public List<IShip> getShips() {
        return ships;
    }

    /**
     * Adds a ship if possible.
     */
    @Override
    public boolean addShip(IShip s) {
        if (s == null) return false;

        // fleet not full
        if (ships.size() >= FLEET_SIZE) return false;

        // inside board
        if (!isInsideBoard(s)) return false;

        // collision check
        if (colisionRisk(s)) return false;

        ships.add(s);
        return true;
    }

    /**
     * Ships with category
     */
    @Override
    public List<IShip> getShipsLike(String category) {
        List<IShip> result = new ArrayList<>();
        if (category == null) return result;

        for (IShip s : ships) {
            if (category.equals(s.getCategory())) {
                result.add(s);
            }
        }
        return result;
    }

    /**
     * Ships still floating
     */
    @Override
    public List<IShip> getFloatingShips() {
        List<IShip> floating = new ArrayList<>();
        for (IShip s : ships) {
            if (s.stillFloating()) {
                floating.add(s);
            }
        }
        return floating;
    }

    /**
     * Returns ship at given position, or null.
     */
    @Override
    public IShip shipAt(IPosition pos) {
        if (pos == null) return null;

        for (IShip ship : ships) {
            if (ship.occupies(pos)) {
                return ship;
            }
        }
        return null;
    }

    /**
     * True if all ship positions inside board.
     */
    private boolean isInsideBoard(IShip s) {
        return s.getLeftMostPos() >= 0 &&
                s.getRightMostPos() < BOARD_SIZE &&
                s.getTopMostPos() >= 0 &&
                s.getBottomMostPos() < BOARD_SIZE;
    }

    /**
     * True if too close to any ship.
     */
    private boolean colisionRisk(IShip s) {
        for (IShip existing : ships) {
            if (existing.tooCloseTo(s)) return true;
        }
        return false;
    }

    /**
     * Prints full fleet status (improved ― loop over categories).
     */
    public void printStatus() {
        printAllShips();
        printFloatingShips();

        String[] categories = {
                "Galeao",
                "Fragata",
                "Nau",
                "Caravela",
                "Barca"
        };

        for (String cat : categories) {
            printShipsByCategory(cat);
        }
    }

    /**
     * Prints ships belonging to a category
     */
    public void printShipsByCategory(String category) {
        if (category == null) {
            System.out.println("(null category)");
            return;
        }

        List<IShip> shipsLike = getShipsLike(category);
        printShips(shipsLike);
    }

    /**
     * Prints floating ships
     */
    public void printFloatingShips() {
        printShips(getFloatingShips());
    }

    /**
     * Prints all ships
     */
    void printAllShips() {
        printShips(ships);
    }
}

