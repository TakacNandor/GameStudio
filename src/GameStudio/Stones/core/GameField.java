package GameStudio.Stones.core;

import java.sql.SQLException;
import java.util.Random;
import java.util.Scanner;

import GameStudio.MineSweeperGame.Minesweeper.core.GameState;
import GameStudio.MineSweeperGame.Minesweeper.core.Tile;
import GameStudio.score.HallOfFame;

public class GameField {

	private final int size;
	private final int[][] tiles;
	boolean playing = true;
	private long startMillis;
	private HallOfFame hall;
	private int[] positionOfNull = new int[2];

	public GameField(int size) {
		this.size = size;

		tiles = new int[size][size];

		startMillis = System.currentTimeMillis();

		hall = new HallOfFame();
	}

	public void play() throws SQLException {
		generateRandom();
		// findNullPosition();
		// System.out.println(getPositionOfNull()[0]+" "+ getPositionOfNull()[1]);
		while (playing) {
			showGameField();
			makeMove();
			isCorrect();
		}
	}

	private void findNullPosition() {
		for (int column = 0; column < size; column++) {
			for (int row = 0; row < size; row++) {
				if (tiles[column][row] == 0) {
					setPositionOfNull(column, row);
				}
			}
		}
	}

	private void generateRandom() {
		generate();
		findNullPosition();
		Random random = new Random();
		for (int i = 0; i < 500; i++) {
			switch (random.nextInt(4)) {
			case 0:
				if (!(getPositionOfNull()[0] == size - 1)) {
					moveLeft();
				}
				break;
			case 1:
				if (!(getPositionOfNull()[0] == 0)) {
					moveRight();
				}
				break;
			case 2:
				if (!(getPositionOfNull()[1] == size - 1)) {
					moveUp();
				}
				break;
			case 3:
				if (!(getPositionOfNull()[1] == 0)) {
					moveDown();
				}
				break;
			}
		}
	}

	private void generate() {
		int counter = 1;
		for (int column = 0; column < size; column++) {
			for (int row = 0; row < size; row++) {
				tiles[row][column] = counter;
				counter++;
			}

		}
		tiles[size - 1][size - 1] = 0;
	}

	private void isCorrect() throws SQLException {

		int counter = 1;
		for (int column = 0; column < size; column++) {
			for (int row = 0; row < size; row++) {
				if (tiles[row][column] == counter && tiles[size - 1][size - 1] == 0) {
					counter++;
				}
			}
		}
		if (counter == size * size) {
			playing = false;
			showGameField();
			System.out.println("You win!");
			System.out.println("Your reward is: ( . Y . )");
			System.out.println("Name: "+System.getProperty("user.name"));
			System.out.println("Time: "+getPlayingSeconds());

			hall.saveScore(System.getProperty("user.name"), getPlayingSeconds(), "Stones");
		}
	}

	private void showGameField() {
		for (int column = 0; column < size; column++) {
			for (int row = 0; row < size; row++) {
				if (tiles[row][column] > 9) {
					System.out.print("[" + tiles[row][column] + "] ");
				} else {
					System.out.print("[" + tiles[row][column] + "]  ");
				}
			}
			System.out.println();
		}
	}

	private void makeMove() {

		System.out.println("A= LEFT    D= RIGHT    W= UP    S= DOWN    X= exit");
		System.out.println("Enter your option: ");
		String direction = new Scanner(System.in).next().toUpperCase();
		switch (direction) {

		case "A":
			moveLeft();
			break;
		case "D":
			moveRight();
			break;
		case "W":
			moveUp();
			break;
		case "S":
			moveDown();
			break;
		case "X":
			System.out.println("End game.");
			playing = false;
			break;

		default:
			System.out.println("Wrong key. Try again!");
			makeMove();
			break;
		}
	}

	private void moveDown() {
		moveUpDown(-1);
	}

	private void moveUp() {
		moveUpDown(1);
	}

	private void moveRight() {
		moveLeftRight(-1);
	}

	private void moveLeft() {
		moveLeftRight(1);
	}

	private void moveUpDown(int direction) {
		if (direction == -1 && getPositionOfNull()[1] == 0 || direction == 1 && getPositionOfNull()[1] == size - 1) {
			System.err.println("Invalid move! Try again.");
			makeMove();
		} else {
			tiles[getPositionOfNull()[0]][getPositionOfNull()[1]] = tiles[getPositionOfNull()[0]][getPositionOfNull()[1] + direction];
			tiles[getPositionOfNull()[0]][getPositionOfNull()[1] + direction] = 0;
			setPositionOfNull(getPositionOfNull()[0], getPositionOfNull()[1] + direction);

		}
	}

	private void moveLeftRight(int direction) {
		if (direction == -1 && getPositionOfNull()[0] == 0 || direction == 1 && getPositionOfNull()[0] == size - 1) {
			System.err.println("Invalid move! Try again.");
			makeMove();
		} else {
			tiles[getPositionOfNull()[0]][getPositionOfNull()[1]] = tiles[getPositionOfNull()[0] + direction][getPositionOfNull()[1]];
			tiles[getPositionOfNull()[0] + direction][getPositionOfNull()[1]] = 0;
			setPositionOfNull(getPositionOfNull()[0] + direction, getPositionOfNull()[1]);

		}

	}

	public int getPlayingSeconds() {
		return (int) ((System.currentTimeMillis() - startMillis) / 1000);
	}

	public int[] getPositionOfNull() {
		return positionOfNull;
	}

	public void setPositionOfNull(int column, int row) {
		this.positionOfNull[0] = column;
		this.positionOfNull[1] = row;
	}
}
