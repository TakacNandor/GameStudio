package GameStudio.Stones.core;

public class Position {
	private final int row;
	private final int column;
	
	public Position(int row, int column) {
		super();
		this.row = row;
		this.column = column;
	}
	
	public int getRow() {
		return row;
	}
	
	public int getColumn() {
		return column;
	}
}
