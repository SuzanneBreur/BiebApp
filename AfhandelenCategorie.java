package nl.calco.biblio;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.naming.NamingException;

public class AfhandelenCategorie {
	
	public List<Categorie> getCategorieen() throws SQLException, NamingException {
		List<Categorie> result = new ArrayList<Categorie>();
		String sql = "select * from Categorien WHERE Omschrijving IS NOT NULL order by Omschrijving";
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try {
			connection = Database.getConnection(); //haalt connection op
			preparedStatement = connection.prepareStatement(sql); //maakt statement voor connection met sql query
			resultSet = preparedStatement.executeQuery(); //het resultaat van sql query
			
			//lus waar Categorie object wordt geinstantieerd en wordt toegevoegd aand de lijst
			while (resultSet.next()) {
				Categorie categorie = new Categorie();
				categorie.setCategorieId(
						resultSet.getInt("Categorie_ID"));
				categorie.setOmschrijving(
						resultSet.getString("Omschrijving"));
				result.add(categorie);
				
			}
		} finally { //objecten moeten gesloten worden in omgekeerde volgorde
			if (resultSet != null && !resultSet.isClosed()) {
				resultSet.close();
			}
			if (preparedStatement != null && !preparedStatement.isClosed()) {
				preparedStatement.close();
			}
			if (connection != null && !connection.isClosed()) {
				connection.close(); //Connection object wordt teruggegeven aan connection pool
			}	
		}
		return result;
	}
	
	public void verwijderCategorie( Categorie categorie ) throws NamingException, SQLException {	
		Connection conn = null;
		PreparedStatement preparedStatement = null;
		String sqlVerwijderCategorie = "delete from categorien where categorie_id = ?";
		try {
			conn = Database.getConnection();
			preparedStatement = conn.prepareStatement(sqlVerwijderCategorie);
			preparedStatement.setInt(1, categorie.getCategorieId());
			preparedStatement.execute();
			
		} catch (SQLException ex) {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage( "Categorie kan niet verwijderd worden"));
		} finally {
			if (preparedStatement != null && !preparedStatement.isClosed()) {
				preparedStatement.close();
			}
			if (conn != null && !conn.isClosed()) {
				conn.close();
			}
		}
	}
	public void updateCategorie( Categorie categorie ) throws NamingException, SQLException {
		Connection conn = null;
		PreparedStatement preparedStatement = null;
		String sqlUpdateCategorie = "update categorien set omschrijving = ? where categorie_id = ?";
		try {
			conn = Database.getConnection();
			preparedStatement = conn.prepareStatement(sqlUpdateCategorie);
			preparedStatement.setString(1, categorie.getOmschrijving());
			preparedStatement.setInt(2, categorie.getCategorieId());
			preparedStatement.execute();
		} catch (NamingException ex ){
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage( "Nieuwe omschrijving is ongeldig"));
		} catch (NullPointerException ex ) {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage( "Vul een naam in voor de categorie"));
		} catch (SQLException ex) {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage( "Categorie kan niet gewijzigd worden"));
		} finally {
			if (preparedStatement != null && !preparedStatement.isClosed()) {
				preparedStatement.close();
			}
			if (conn != null && !conn.isClosed()) {
				conn.close();
			}
		}
	}
	
	public void insertCategorie( Categorie categorie ) throws NamingException, SQLException {
		Connection conn = null;
		PreparedStatement preparedStatement = null;
		String sqlNieuweCategorie = "insert into categorien (omschrijving) values (?)";
		try {
			conn = Database.getConnection();
			preparedStatement = conn.prepareStatement(sqlNieuweCategorie);
			preparedStatement.setString(1, categorie.getOmschrijving());
			preparedStatement.execute();
		} catch (NamingException ex ){
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage( "Nieuwe omschrijving is ongeldig"));
		} catch (NullPointerException ex ) {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage( "Vul een naam in voor de categorie"));
		} catch (SQLException ex) {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage( "Categorie kan niet gewijzigd worden"));
		} finally {
			if (preparedStatement != null && !preparedStatement.isClosed()) {
				preparedStatement.close();
			}
			if (conn != null && !conn.isClosed()) {
				conn.close();
			}
		}
	}
	
	public Categorie getCategorie(Integer categorieId) throws SQLException, NamingException {
		Categorie result = null;
		String sql = "select * from categorien where categorie_id = ?";
		Connection conn = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try {
			conn = Database.getConnection();
			preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setInt(1,  categorieId);;
			resultSet = preparedStatement.executeQuery();
			
			if (resultSet.next()) {
				result = new Categorie();
				result.setCategorieId(
						resultSet.getInt("Categorie_ID"));
				result.setOmschrijving(
						resultSet.getString("Omschrijving"));
			}
		} finally {
			if (resultSet != null && !resultSet.isClosed()) {
				resultSet.close();
			}
			if (preparedStatement != null && !preparedStatement.isClosed()) {
				preparedStatement.close();
			}
			if (conn != null && !conn.isClosed()) {
				conn.close();
			}
		}
		return result;
	}

}
