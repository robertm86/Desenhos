package edu.udc.psw.desenhos.DB.DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import edu.udc.psw.desenhos.DB.DBConnection;
import edu.udc.psw.modelo.Linha;
import edu.udc.psw.modelo.Retangulo;

public class RetanguloDAO {
	private int desenho;
	private Statement statement;
	private ResultSet resultSet;
	private int numberOfRows;
	private String query = "SELECT retangulos.id_retangulo, retangulos.x_a, retangulos.y_a, retangulos.x_b, retangulos.y_b FROM retangulos WHERE desenho = ";
	private String update = "UPDATE (x_a, y_a, x_b, y_b) FROM retangulos with ";
	private String insert = "INSERT into retangulos (x_a, y_a, x_b, y_b, desenho) values ";

	public RetanguloDAO(int desenho) {
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

	public Retangulo getRetangulo() {
		Retangulo retangulo;
		try {
			retangulo = new Retangulo(resultSet.getFloat(2), resultSet.getFloat(3), resultSet.getFloat(4), resultSet.getFloat(5));
			return retangulo;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public int getNumberOfRows() {
		return numberOfRows;
	}

	public void save(Retangulo retangulo) throws SQLException, IllegalStateException {
		String newData = update + "('" + retangulo.getA().getX() + "', '" + retangulo.getA().getY() + "', '" 
				+ retangulo.getB().getX() + "', '" + retangulo.getB().getY() + "')"
				+ " WHERE id_ponto = " + "', '" + resultSet.getInt(1) + "');";
		statement.executeUpdate(newData);
	}

	public void insert(Retangulo retangulo) throws SQLException, IllegalStateException {
		String newData = insert + "('" + retangulo.getA().getX() + "', '" + retangulo.getA().getY() + "', '"
				+ retangulo.getB().getX() + "', '" + retangulo.getB().getY() + "', '" + desenho + "');";
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
