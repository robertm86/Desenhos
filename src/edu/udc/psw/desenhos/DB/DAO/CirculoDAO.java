package edu.udc.psw.desenhos.DB.DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import edu.udc.psw.desenhos.DB.DBConnection;
import edu.udc.psw.modelo.Circulo;

public class CirculoDAO {
	private int desenho;
	private Statement statement;
	private ResultSet resultSet;
	private int numberOfRows;
	private String query = "SELECT circulos.id_circulo, circulos.x, circulos.y, circulos.raio FROM circulos WHERE desenho = ";
	private String update = "UPDATE (x, y, raio) FROM circulos with ";
	private String insert = "INSERT into circulos (x, y, raio, desenho) values ";

	public CirculoDAO(int desenho) {
		this.desenho = desenho;
		//query = query + "'" + desenho + "';";
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

	public Circulo getCirculo() {
		Circulo circulo;
		try {
			circulo = new Circulo(resultSet.getFloat(2), resultSet.getFloat(3), resultSet.getFloat(4));
			return circulo;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public int getNumberOfRows() {
		return numberOfRows;
	}

	public void save(Circulo circulo) throws SQLException, IllegalStateException {
		String newData = update + "('" + circulo.getA().getX() + "', '" + circulo.getA().getY() + "', '" 
				+ circulo.raio() +  "')"
				+ " WHERE id_ponto = " + "', '" + resultSet.getInt(1) + "');";
		statement.executeUpdate(newData);
	}

	public void insert(Circulo circulo) throws SQLException, IllegalStateException {
		String newData = insert + "('" + circulo.getA().getX() + "', '" + circulo.getA().getY() + "', '"
				+ circulo.raio() + "', '" + desenho + "');";
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
