package GameStudio.Stones.Main;

import java.sql.SQLException;

import com.mysql.jdbc.Field;

import GameStudio.Stones.core.GameField;

public class Main {

	public static void main(String[] args) throws SQLException {
		GameField field = new GameField(5);
		field.play();
		
		

	}

}
