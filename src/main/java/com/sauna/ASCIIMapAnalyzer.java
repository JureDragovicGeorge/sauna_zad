package com.sauna;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;


public class ASCIIMapAnalyzer {

    private List<List<Character>> matrix;

    private final Character START = '@';
    private final Character FINISH = 'x';
    private final Character DIRECTION_CHANGE = '+';
    private final Character MOVE_VERTICAL = '|';
    private final Character MOVE_HORIZONTAL = '-';
    private static final Character BLANK = ' ';
    private List<Character> ALLOWED_CHARACTERS =
            Arrays.asList(START, FINISH, DIRECTION_CHANGE, MOVE_HORIZONTAL, MOVE_VERTICAL);
    private Direction direction;

    private String filePath;
    private StringBuilder letters = new StringBuilder("");
    private StringBuilder fullPath = new StringBuilder("");
    private Position currentPosition;
    private Map<Position, Boolean> usedPositions = new HashMap<>();
asdasdasd
    public ASCIIMapAnalyzer(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Retrieves full path and letters from ASCII map.
     *
     * @return
     * @throws Exception
     */
    public Solution getSolution() throws Exception {
        this.matrix = getASCIIMapAsMatrix(filePath);
        while (true) {
            findNextPosition();
            Character currentValue = getChar(this.currentPosition.getRowIndex(), this.currentPosition.getColumnIndex());

            if (Character.isLetter(currentValue) && currentValue != FINISH && !this.usedPositions.containsKey(this.currentPosition)) {
                letters.append(currentValue);
            }
            usedPositions.put(this.currentPosition, true);
            fullPath.append(currentValue);

            if (currentValue == FINISH) {
                return new Solution(fullPath.toString(), letters.toString());
            }
        }
    }

    /**
     * Retrieves Character from the 2D matrix. If provided row or column are invalid it returns a blank space: ' '.
     *
     * @param row
     * @param column
     * @return
     */
    private Character getChar(int row, int column) {
        if (row < 0 || column < 0)
            return ' ';
        return matrix.get(row).get(column);
    }

    private void findNextPosition() throws Exception {
        //locate starting position. It only runs 1 time
        if (this.currentPosition == null) {
            setStartingPositionAndStartingDirection();
            return;
        }
        this.currentPosition = getNewPosition();
        findNewDirection();
    }

    /**
     * Updates direction.
     */
    private void findNewDirection() {
        Character currentCharacter = getChar(this.currentPosition.getRowIndex(), this.currentPosition.getColumnIndex());
        //direction is changed only if currentPosition is '+' or letter
        if (currentCharacter != DIRECTION_CHANGE && !Character.isLetter(currentCharacter)) {
            return;
        }
        int row = this.currentPosition.getRowIndex();
        int column = this.currentPosition.getColumnIndex();

        //possible new position
        Position newPosition = getNewPosition();

        //if current direction is left or right, new direction should be opposite to that: either UP or DOWN and vice versa
        //change of direction occurs if current character is '+' or new position is already used or row or column is invalid (exceeding matrix limits)
        if ((direction == Direction.RIGHT || direction == Direction.LEFT)
                && (currentCharacter == DIRECTION_CHANGE || usedPositions.containsKey(newPosition) || getChar(newPosition.getRowIndex(), newPosition.getColumnIndex()) == BLANK)) {
            if (row > 0 && ALLOWED_CHARACTERS.contains(matrix.get(row - 1).get(column))) {
                direction = Direction.UP;
            } else {
                direction = Direction.DOWN;
            }
        } else if ((direction == Direction.UP || direction == Direction.DOWN)
                && (currentCharacter == DIRECTION_CHANGE || usedPositions.containsKey(newPosition) || getChar(newPosition.getRowIndex(), newPosition.getColumnIndex()) == BLANK)) {
            if (column < matrix.get(row).size() - 1 && ALLOWED_CHARACTERS.contains(matrix.get(row).get(column + 1))) {
                direction = Direction.RIGHT;
            } else {
                direction = Direction.LEFT;
            }
        }
    }

    /**
     * Returns new Position with regards to current direction.
     *
     * @return Position
     */
    private Position getNewPosition() {
        if (direction == Direction.DOWN) {
            return new Position(this.currentPosition.getRowIndex() + 1, this.currentPosition.getColumnIndex());
        } else if (direction == Direction.UP) {
            return new Position(this.currentPosition.getRowIndex() + -1, this.currentPosition.getColumnIndex());
        } else if (direction == Direction.LEFT) {
            return new Position(this.currentPosition.getRowIndex(), this.currentPosition.getColumnIndex() - 1);
        } else {
            return new Position(this.currentPosition.getRowIndex(), this.currentPosition.getColumnIndex() + 1);
        }
    }

    /**
     * Sets starting position and starting direction.
     *
     * @throws Exception
     */
    private void setStartingPositionAndStartingDirection() throws Exception {
        for (int row = 0; row < this.matrix.size(); row++) {
            for (int column = 0; column < this.matrix.get(row).size(); column++) {
                if (this.matrix.get(row).get(column).equals(START)) {
                    currentPosition = new Position(row, column);
                    usedPositions.put(currentPosition, true);
                    if (row > 0 && ALLOWED_CHARACTERS.contains(matrix.get(row - 1).get(column)))
                        direction = Direction.UP;
                    else if (column > 0 && ALLOWED_CHARACTERS.contains(matrix.get(row).get(column - 1)))
                        direction = Direction.LEFT;
                    else if (column < matrix.get(row).size() - 1 && ALLOWED_CHARACTERS.contains(matrix.get(row).get(column + 1)))
                        direction = Direction.RIGHT;
                    else if (row < matrix.size() - 1 && ALLOWED_CHARACTERS.contains(matrix.get(row + 1).get(column)))
                        direction = Direction.DOWN;
                    return;
                }
            }
        }
        throw new Exception("Invalid ASCII map: No START position");
    }

    /**
     * Returns 2D matrix representing imported file.
     *
     * @param filePath
     * @return List<List < Character>>
     * @throws FileNotFoundException
     */
    private List<List<Character>> getASCIIMapAsMatrix(String filePath) throws FileNotFoundException {
        List<List<Character>> matrix = new ArrayList<List<Character>>();

        File file = new File(filePath);
        Scanner scanner = new Scanner(file);
        for (int row = 0; scanner.hasNextLine(); row++) {
            char[] chars = scanner.nextLine().toCharArray();
            for (int i = 0; i < chars.length; i++) {
                if (matrix.size() <= row) {
                    matrix.add(new ArrayList<Character>());
                }
                List<Character> listRow = matrix.get(matrix.size() - 1);
                listRow.add(chars[i]);
            }
        }

        //adding additional fields to rows which are smaller in size when compared to others
        int max = matrix.stream().map(row -> row.size()).max(Integer::compare).get();
        matrix.stream().forEach(x -> {
            if (x.size() < max) {
                int size = x.size();
                for (int i = 0; i < max - size; i++)
                    x.add(' ');
            }
        });

        return matrix;
    }


}
