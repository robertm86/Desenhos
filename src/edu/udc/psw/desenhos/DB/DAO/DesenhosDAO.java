package edu.udc.psw.desenhos.DB.DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import edu.udc.psw.desenhos.DB.DBConnection;
import edu.udc.psw.desenhos.DB.data.Desenhos;

public class DesenhosDAO {
	private Statement statement;
	private ResultSet resultSet;
	private int numberOfRows;
	private String query = "SELECT desenhos.nome, desenhos.criacao, desenhos.alteracao, desenhos.id_desenho, desenhos.formato FROM desenhos";
	private String update = "UPDATE (nome, criacao, alteracao, formato) from desenhos with ";
	private String insert = "INSERT into desenhos (nome, criacao, alteracao, formato) values ";

	public DesenhosDAO() {
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

	public Desenhos getDesenho() {
		Desenhos desenhos;
		try {
			desenhos = new Desenhos(
					resultSet.getInt(4),
					resultSet.getString(1),
					resultSet.getTimestamp(2),
					resultSet.getTimestamp(3),
					resultSet.getInt(5));
			return desenhos;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public int getNumberOfRows() {
		return numberOfRows;
	}

	public void save(Desenhos desenho) throws SQLException, IllegalStateException {
		String newData = update + "('" + desenho.getNome() + "', '" + desenho.getCriacao() + "', '" 
				+ desenho.getModificacao() + "', '" + desenho.getFormato() + "')" 
				+ " WHERE id_desenho = " + "', '" + desenho.getId() + "');";
		statement.executeUpdate(newData);
	}

	public void insert(Desenhos desenho) throws SQLException, IllegalStateException {
		String newData = insert + "('" + desenho.getNome() + "', '" + desenho.getCriacao() + "', '" 
				+ desenho.getModificacao() + "', '" + desenho.getFormato() + "');";
		int affectedRows = statement.executeUpdate(newData, Statement.RETURN_GENERATED_KEYS);
		
        if (affectedRows == 0) {
            throw new SQLException("Creating Desenhos failed, no rows affected.");
        }

        try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                desenho.setId(generatedKeys.getLong(1));
            }
            else {
                throw new SQLException("Creating user failed, no ID obtained.");
            }
        }
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
	public ResultSet BuscarTudo() throws SQLException, IllegalStateException {
		// especifica consulta e a executa
		resultSet = statement.executeQuery(query);

		// determina o número de linhas em ResultSet
		// resultSet.last(); // move para a última linha
		// numberOfRows = resultSet.getRow(); // obtém número de linha
		return resultSet;
	}
	
	// configura nova string de consulta de banco de dados
	public Desenhos BuscarNome(String nome) throws SQLException, IllegalStateException {
		
		Desenhos desenho = null;
		
		// especifica consulta e a executa
		resultSet = statement.executeQuery(query + "where nome = " + nome);

		desenho.setNome(resultSet.getString(1));              //Nome
		desenho.setCriacao(resultSet.getTimestamp(2));        //Criacao
		desenho.setModificacao(resultSet.getTimestamp(3));    //Modificacao
		desenho.setId(resultSet.getInt(5));					  //id_desenho	
		desenho.setFormato(resultSet.getInt(6));
		
		// determina o número de linhas em ResultSet
		// resultSet.last(); // move para a última linha
		// numberOfRows = resultSet.getRow(); // obtém número de linha
		
		return desenho;
	}
	
	// configura nova string de consulta de banco de dados
	public ResultSet BuscarID(String nome) throws SQLException, IllegalStateException {
		// especifica consulta e a executa
		resultSet = statement.executeQuery(query  + " WHERE nome = " + "'" + nome + "'");

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
