package GameStudio.MineSweeperGame.Minesweeper.core;

public class Clue extends Tile {
	private final int value;
	
	public Clue(int value) {
		if(value < 0 && value > 8) {
			throw new IllegalArgumentException("Not supported");
		}
		this.value = value;
	}

	public int getValue() {
		return value;
	}
}
