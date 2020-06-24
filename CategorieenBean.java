package nl.calco.biblio;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.naming.NamingException;

import org.jboss.logging.Logger;
import org.jboss.logging.Logger.Level;

@ManagedBean (name="CategorieenBean")
@ViewScoped

public class CategorieenBean implements java.io.Serializable {
	
	private List<Categorie> categorieen;
	private Categorie geselecteerdeCategorie;
	
	//lijst met alle categorieen uit de database
	public List<Categorie> getCategorieen() {
		if (categorieen == null) {
			//haal categorie uit database
			AfhandelenCategorie afhandelen = new AfhandelenCategorie();
			try {
				categorieen = afhandelen.getCategorieen();
			} catch (SQLException e) {
				FacesContext context = FacesContext.getCurrentInstance();
				context.addMessage(null,  new FacesMessage("Fout bij het ophalen van categorieën"));
			} catch (NamingException e) {
				FacesContext context = FacesContext.getCurrentInstance();
				context.addMessage(null,  new FacesMessage("Fout bij het verbinden met database"));
			}
		}
		return categorieen;
	}
		
	//methode om categorie te selecteren
	public Categorie getGeselecteerdeCategorie() {
		return geselecteerdeCategorie;
	}
	
	public void selecteer( Integer categorieId ) {
		Logger.getLogger(CategorieenBean.class.getName()).log(Level.INFO, categorieId);
		for (Categorie c : categorieen) {
			if (c.getCategorieId() == categorieId ) {
				geselecteerdeCategorie = c;
			}
		}
	}
	//Methode om categorie uit de database te verwijderen	
	public void verwijderen() {
		AfhandelenCategorie afhandelen = new AfhandelenCategorie();
		try {
			afhandelen.verwijderCategorie(geselecteerdeCategorie);
		} catch (SQLException ex) {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null,  new FacesMessage("Fout bij het ophalen van categorieën" + "-"+ ex.getMessage().toString()));
		} catch (NamingException ex) {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null,  new FacesMessage("Fout bij het verbinden met de database" + "-"+ ex.getMessage().toString()));
		}
	}
	
	//Methode achter knoppen
	//geselecteerde categorie wordt opgeslagen in sessionMap
	public String nieuw() {
		FacesContext context = FacesContext.getCurrentInstance();
		Map<String, Object> sessionMap = context.getExternalContext().getSessionMap();
		sessionMap.put("categorie", new Categorie());
		return "edit";
	}
	
	public String wijzigen() {
		FacesContext context = FacesContext.getCurrentInstance();
		Map<String, Object> sessionMap = context.getExternalContext().getSessionMap();
		sessionMap.put("categorie", geselecteerdeCategorie);
		return "edit";
	}
	//terug naar boeken.xhtml
	public String terug() {
		return "terug";
	}
	
}
