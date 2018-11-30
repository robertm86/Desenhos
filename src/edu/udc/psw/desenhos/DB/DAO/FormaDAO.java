package edu.udc.psw.desenhos.DB.DAO;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import edu.udc.psw.desenhos.DB.DBConnection;
import edu.udc.psw.modelo.Linha;

public class FormaDAO {
	private int desenho;
	private Statement statement;
	private ResultSet resultSet;
	private int numberOfRows;
	private String query = "SELECT formas.id_formas, formas.tipo, formas.texto, formas.binario FROM formas WHERE desenho = ";
	private String update = "UPDATE (tipo, texto, binario) FROM formas with ";
	private String insert = "INSERT into formas (tipo, texto, binario, desenho) values ";

	public FormaDAO(int desenho) {
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

	
	public Linha getLinha() {
		Linha linha;
		try {
			linha = new Linha(resultSet.getFloat(2), resultSet.getFloat(3), resultSet.getFloat(4), resultSet.getFloat(5));
			return linha;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	

	public int getNumberOfRows() {
		return numberOfRows;
	}

	public void saveTxt(String texto, int formaGeometrica) throws SQLException, IllegalStateException {
		
		String newData = update + "('" + formaGeometrica + "', '"+ texto +"', ' ', '" + desenho + "');"
							 + " WHERE id_formas = " + "', '" + resultSet.getInt(1) + "');";

		statement.executeUpdate(newData);
	}
	
	public void saveSer(ObjectOutputStream texto, int formaGeometrica) throws SQLException, IllegalStateException {
		
		String newData = update + "('" + formaGeometrica + "', ' ', '"+ texto +"', '" + desenho + "');"
							 + " WHERE id_formas = " + "', '" + resultSet.getInt(1) + "');";

		statement.executeUpdate(newData);
	}
	
	public void saveBin(ObjectOutputStream texto, int formaGeometrica) throws SQLException, IllegalStateException {
		
		String newData = update + "('" + formaGeometrica + "', ' ', '"+ texto +"', '" + desenho + "');"
							 + " WHERE id_formas = " + "', '" + resultSet.getInt(1) + "');";

		statement.executeUpdate(newData);
	}

	public void insertTxt(String texto, int formaGeometrica) throws SQLException, IllegalStateException {
		
		String newData = insert + "('" + formaGeometrica + "', '"+ texto +"', ' ', '" + desenho + "');";

		statement.executeUpdate(newData);

		setQuery();
	}
	
	public void insertSer(Object serial, int formaGeometrica) throws SQLException, IllegalStateException {
		
		String newData = insert + "('" + formaGeometrica + "', ' ', '"+ serial +"', '" + desenho + "');";
		
		statement.executeUpdate(newData);

		setQuery();
	}
	
	public void insertBin(ObjectOutputStream texto, int formaGeometrica) throws SQLException, IllegalStateException {
		
		String newData = insert + "('" + formaGeometrica + "', ' ', '"+ texto +"', '" + desenho + "');";

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
