package nl.calco.biblio;

public class Categorie implements java.io.Serializable {
	
	//attributen
	private Integer categorieId;
	private String omschrijving;
	
	//getters en setters
	public Integer getCategorieId() {
		return categorieId;
	}
	public void setCategorieId(Integer categorieId) {
		this.categorieId = categorieId;
	}
	public String getOmschrijving() {
		return omschrijving;
	}
	public void setOmschrijving(String omschrijving) {
		this.omschrijving = omschrijving.trim();
		if(omschrijving.length() == 0) {
			omschrijving = null;
		}
	}
	
}
