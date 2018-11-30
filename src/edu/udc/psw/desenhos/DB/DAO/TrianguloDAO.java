package edu.udc.psw.desenhos.DB.DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import edu.udc.psw.desenhos.DB.DBConnection;
import edu.udc.psw.modelo.Triangulo;

public class TrianguloDAO {
	private int desenho;
	private Statement statement;
	private ResultSet resultSet;
	private int numberOfRows;
	private String query = "SELECT triangulos.id_triangulo, triangulos.x_a, triangulos.y_a, triangulos.x_b, triangulos.y_b, triangulos.x_c, triangulos.y_c FROM triangulos WHERE desenho = ";
	private String update = "UPDATE (x_a, y_a, x_b, y_b, x_c, y_c) FROM triangulos with ";
	private String insert = "INSERT into triangulos (x_a, y_a, x_b, y_b, x_c, y_c, desenho) values ";

	public TrianguloDAO(int desenho) {
		this.desenho = desenho;
		query = query + "" + desenho + ";";
		try {
			// cria Statement para consultar banco de dados
			statement = DBConnection.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			// configura consulta e a executa
			setQuery();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	public void moveToRow(int row) {
		try {
			resultSet.absolute(row);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	public Triangulo getTriangulo() {
		Triangulo triangulo;
		try {
			triangulo = new Triangulo(resultSet.getFloat(2), resultSet.getFloat(3), resultSet.getFloat(4), resultSet.getFloat(5), resultSet.getFloat(6), resultSet.getFloat(7));
			return triangulo;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public int getNumberOfRows() {
		return numberOfRows;
	}

	public void save(Triangulo triangulo) throws SQLException, IllegalStateException {
		String newData = update + "('" + triangulo.getP1().getX() + "', '" + triangulo.getP1().getY() + "', '" 
				+ triangulo.getP2().getX() + "', '" + triangulo.getP2().getY() + "', '"
				+ triangulo.getP3().getX() + "', '" + triangulo.getP3().getY() + "')"
				+ " WHERE id_ponto = " + "', '" + resultSet.getInt(1) + "');";
		statement.executeUpdate(newData);
	}

	public void insert(Triangulo triangulo) throws SQLException, IllegalStateException {
		String newData = insert + "('" + triangulo.getP1().getX() + "', '" + triangulo.getP1().getY() + "', '"
				+ triangulo.getP2().getX() + "', '" + triangulo.getP2().getY() + "', '"
				+ triangulo.getP3().getX() + "', '" + triangulo.getP3().getY() + "', '" + desenho + "');";
		statement.executeUpdate(newData);

		setQuery();
	}

	// configura nova string de consulta de banco de dados
	public void setQuery() throws SQLException, IllegalStateException {
		// especifica consulta e a executa
		resultSet = statement.executeQuery(query); 

		// determina o número de linhas em ResultSet
		resultSet.last(); // move para a última linha
		numberOfRows = resultSet.getRow(); // obtém número de linha
		resultSet.first();
	}
	// configura nova string de consulta de banco de dados
	public ResultSet BuscarID(int id) throws SQLException, IllegalStateException {
		// especifica consulta e a executa
			
		resultSet = statement.executeQuery(query); 
			
		return resultSet;
	}
	
	// fecha Statement e Connection
	public void disconnectFromDatabase() {
		try {
			statement.close();
			DBConnection.getConnection().close();
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
		}
	}
}
