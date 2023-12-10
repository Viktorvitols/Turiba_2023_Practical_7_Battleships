import java.util.Scanner;

public class Battlefield {

    public Battlefield() {
        createGrid();
    }

    final String[] SHIP_ARRAY =
            {"SSSS", "SSS", "SSS", "SS", "SS", "SS", "S", "S", "S", "S",};
    public String[][] grid;

    public void createGrid() {
        grid = new String[10][10];
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                grid[i][j] = " ";
            }
        }
    }

    public void drawBattlefield() {
        System.out.println("   A|B|C|D|E|F|G|H|I|J| ");
        for (int i = 0; i < grid.length; i++) {
            if (i < 9) {
                System.out.print(" " + (i + 1) + "[");
            } else {
                System.out.print((i + 1) + "[");
            }

            for (int j = 0; j < grid[i].length; j++) {
                System.out.print(grid[i][j] + "|");
            }
            System.out.println("]");
        }
    }

    public Integer charMapping(String letter) {
        switch (letter) {
            case "A":
                return 1;
            case "B":
                return 2;
            case "C":
                return 3;
            case "D":
                return 4;
            case "E":
                return 5;
            case "F":
                return 6;
            case "G":
                return 7;
            case "H":
                return 8;
            case "I":
                return 9;
            case "J":
                return 10;
            case "V":
                return 0;
        }
        return null;
    }

    public Integer[] inputParsing() {
        String input = null;
        Integer[] coords = new Integer[3];
        Scanner scanner = new Scanner(System.in);
        if (scanner.hasNext("[VHvh][ABCDEFGHIJabcdefghij]([1-9])(0?)")) {
            input = scanner.next();
            String shipDir = input.toUpperCase().substring(0, 1);
            String coordV = input.toUpperCase().substring(1, 2);
            String coordH = input.toUpperCase().substring(2);
            coords[0] = Integer.valueOf(coordH) - 1;
            coords[1] = charMapping(coordV) - 1;
            coords[2] = charMapping(shipDir);
        } else {
            System.out.println("Enter a letter V or H to place a ship vertically or horizontally, " +
                    "letter A to J and a number 1 - 10 " +
                    "(e.g. HD5)");
            inputParsing();
        }
        return coords;
    }

    public void setUpFleet() {
        Integer[] coords = null;
        for (int shipNo = 0; shipNo < SHIP_ARRAY.length; ) {
            System.out.println("\nEnter coordinates for " + SHIP_ARRAY[shipNo].length() +
                    "-square(s) ship № " + (shipNo + 1) + ":");
            coords = inputParsing();
            if (setShip(coords, shipNo)) {
                drawBattlefield();
                shipNo++;
            }
        }
    }

    public Boolean setShip(Integer[] coords, int shipIndex) {
        int row = coords[0];
        int col = coords[1];
        int shipDir = coords[2];
        String ship = SHIP_ARRAY[shipIndex];
        String[][] tempGrid = grid.clone();

        if (canSet(coords)) {
            try {
                if (shipDir == 0) { //Vertical
                    for (int i = 0; i < ship.length(); i++) {
                        tempGrid[row][col] = ship.substring(i, i + 1);
                        row++;
                    }
                } else { //Horizontal
                    for (int i = 0; i < ship.length(); i++) {
                        tempGrid[row][col] = ship.substring(i, i + 1);
                        col++;
                    }
                }
                grid = tempGrid;
                return true;
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("You can't place this ship here");
            }
        }
        return false;
    }

    public Boolean canSet(Integer[] coords) {
        int row = coords[0];
        int col = coords[1];

        Integer[] rowsToCheck = new Integer[3];
        Integer[] colsToCheck = new Integer[3];
        rowsToCheck[0] = row;
        rowsToCheck[1] = row - 1;
        rowsToCheck[2] = row + 1;
        colsToCheck[0] = col;
        colsToCheck[1] = col - 1;
        colsToCheck[2] = col + 1;

        for (int i = 0; i < rowsToCheck.length; i++) {
            if (rowsToCheck[i] < 0) {
                rowsToCheck[i] = 0;
            } else if (rowsToCheck[i] > 9) {
                rowsToCheck[i] = 9;
            }
        }

        for (int i = 0; i < colsToCheck.length; i++) {
            if (colsToCheck[i] < 0) {
                colsToCheck[i] = 0;
            } else if (colsToCheck[i] > 9) {
                colsToCheck[i] = 9;
            }
        }

        for (int i = 0; i < rowsToCheck.length; i++) {
            for (int j = 0; j < colsToCheck.length; j++) {
                if ((grid[rowsToCheck[i]][colsToCheck[j]]).equals("S")) {
                    System.out.println("Another ship is too close.");
                    return false;
                }
            }
        }
        return true;
    }
}
