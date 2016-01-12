package GameStudio.MineSweeperGame.Minesweeper.core;

import java.util.Random;

public class Field {
	private final int rowCount;
	private final int columnCount;
	private final int mineCount;
	private final Tile[][] tiles;
	private GameState state = GameState.PLAYING;
	private int openCount = 0; 
	private long startMillis;

	public Field(int rowCount, int columnCount, int mineCount) {
		this.rowCount = rowCount;
		this.columnCount = columnCount;
		this.mineCount = mineCount;
		
		if(!(rowCount > 0 && columnCount > 0 && mineCount < rowCount * columnCount)) {
			throw new IllegalArgumentException("Cele zle");
		}
		
		tiles = new Tile[rowCount][columnCount];
		generate();
		startMillis = System.currentTimeMillis();
	}

	private void generate() {
		generateMines();
		fillWithClues();
	}

	private void generateMines() {
		Random random = new Random();
		int minesToSet = mineCount;

		while (minesToSet > 0) {
			int row = random.nextInt(rowCount);
			int column = random.nextInt(columnCount);
			if (tiles[row][column] == null) {
				tiles[row][column] = new Mine();
				minesToSet--;
			}
		}
	}

	private void fillWithClues() {
		for (int row = 0; row < rowCount; row++) {
			for (int column = 0; column < columnCount; column++) {
				if (tiles[row][column] == null) {
					tiles[row][column] = new Clue(countNeighbourMines(row, column));
				}
			}
		}
	}

	private int countNeighbourMines(int row, int column) {
		int count = 0;

		for (int rowOffset = -1; rowOffset <= 1; rowOffset++) {
			int actRow = row + rowOffset;
			if (actRow >= 0 && actRow < rowCount) {
				for (int columnOffset = -1; columnOffset <= 1; columnOffset++) {
					int actColumn = column + columnOffset;
					if (actColumn >= 0 && actColumn < columnCount) {
						if (tiles[actRow][actColumn] instanceof Mine) {
							count++;
						}
					}
				}
			}
		}

		return count;
	}

	public int getRowCount() {
		return rowCount;
	}

	public int getColumnCount() {
		return columnCount;
	}

	public int getMineCount() {
		return mineCount;
	}

	public Tile getTile(int row, int column) {
		return tiles[row][column];
	}
	
	public GameState getState() {
		return state;
	}
	
	public void openTile(int row, int column) {
		Tile tile = getTile(row, column);
		if (tile.getState().equals(TileState.CLOSED)) {
			tile.setState(TileState.OPEN);
			openCount++;
			
			if(tile instanceof Mine) {
				state = GameState.FAILED;
				return;
			}
			
			if(((Clue)tile).getValue() == 0) {
				openNeighbourTiles(row, column);
			}		
			
			if(isSolved()) {
				state = GameState.SOLVED;
			}
		}
	}

	private boolean isSolved() {
		return rowCount * columnCount - openCount == mineCount; 
	}
	
	private void openNeighbourTiles(int row, int column) {
		for (int rowOffset = -1; rowOffset <= 1; rowOffset++) {
			int actRow = row + rowOffset;
			if (actRow >= 0 && actRow < rowCount) {
				for (int columnOffset = -1; columnOffset <= 1; columnOffset++) {
					int actColumn = column + columnOffset;
					if (actColumn >= 0 && actColumn < columnCount) {
						openTile(actRow, actColumn);
					}
				}
			}
		}
	}

	public void markTile(int row, int column) {
		Tile tile = getTile(row, column);
		if (tile.getState().equals(TileState.CLOSED)) {
			tile.setState(TileState.MARKED);
		}else if (tile.getState().equals(TileState.MARKED)) {
			tile.setState(TileState.CLOSED);
		}
	}
	
	public int getPlayingSeconds() {
		return (int)((System.currentTimeMillis() - startMillis) / 1000);
	}
}
