package GameStudio.MineSweeperGame.Minesweeper;

import java.sql.SQLException;

import GameStudio.MineSweeperGame.Minesweeper.consoleui.ConsoleUI;
import GameStudio.MineSweeperGame.Minesweeper.core.Field;

public class Minesweeper {

	public static void main(String[] args) throws SQLException {
		Field field = new Field(9, 9, 1);
		ConsoleUI ui = new ConsoleUI(field);
		ui.play();				
	}
}
