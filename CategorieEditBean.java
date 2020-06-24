package nl.calco.biblio;

import java.sql.SQLException;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.naming.NamingException;

@ManagedBean(name = "CategorieEditBean")
@ViewScoped

public class CategorieEditBean {

	private Categorie categorie = null;
	
	//Haalt informatie van geselecteerde categorie uit sessionMap
	public Categorie getCategorie() {
		if (categorie == null) {
			FacesContext context = FacesContext.getCurrentInstance();
			Map<String, Object> sessionMap = context.getExternalContext().getSessionMap();
			categorie = (Categorie) sessionMap.get("categorie");
			sessionMap.remove("categorie");
		}
		return categorie;
	}
	
	//Methode om categorie op te slaan
	public String opslaan() throws NamingException, SQLException {
		FacesContext context = FacesContext.getCurrentInstance();
		if (categorie.getOmschrijving() == null) {
			context.addMessage(null, new FacesMessage("De omschrijving is verplicht"));
		} else {
			AfhandelenCategorie afhandelen = new AfhandelenCategorie();
			if (categorie.getCategorieId() == null) {
				//nieuwe categorie toevoegen
				afhandelen.insertCategorie(categorie);
			} else {
				//geselecteerde categorie bewerken
				afhandelen.updateCategorie(categorie);
			}
		}
		return "terugNaarCategorieen";
	}
	
	//terug naar categorieen.xhtml
	public String annuleren() {
		return "terugNaarCategorieen";
	}

}
