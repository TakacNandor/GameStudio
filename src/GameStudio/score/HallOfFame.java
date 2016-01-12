package GameStudio.score;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.ReadOnlyBufferException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HallOfFame extends DBConnection {
	private final String SCORES_FILE = System.getProperty("user.home") + "/mnsw.scores";

	private List<UserScore> scores = new ArrayList<>();

	public void addScore(String name, int time) {
		scores.add(new UserScore(name, time));
		Collections.sort(scores);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		int index = 1;
		for (UserScore score : scores) {
			sb.append(index + ". " + score.getName() + " " + score.getTime() + "\n");
			index++;
		}
		return sb.toString();
	}

	public void loadScore() throws SQLException {
		Connection conn = getDBConnection();
		Statement stmt = null;
		ResultSet result = null;
		try {

			stmt = conn.createStatement();
			result = stmt.executeQuery("SELECT name, time FROM `scores`");
			while (result.next()) {
				System.out.print(result.getString("name") + ": ");
				System.out.print(result.getString("time"));
				System.out.println();

			}

		} finally {
			if (stmt != null) {
				stmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		}

	}

	public void load() throws IOException, ClassNotFoundException {

		File file = new File(SCORES_FILE);
		if (file.exists()) {
			try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
				scores = (List) ois.readObject();
			}
		}
	}

	public void saveScore(String name, int time, String game) throws SQLException {
		Connection conn = getDBConnection();
		Statement stmt = null;

		try {

			stmt = conn.createStatement();
			stmt.executeUpdate("INSERT INTO `scores` ( `name`, `time`, `game`) VALUES ('" + name + "', '" + time + "', '" + game + "');");

		} finally {
			if (stmt != null) {
				stmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		}

	}

	public void save() throws IOException {
		File file = new File(SCORES_FILE);
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
			oos.writeObject(scores);
		}
	}
}
