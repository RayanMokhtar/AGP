package business.tools;

public class SearchCriteria {
	
	private String critere;
	private String valeur;


	public String getCritere() {
		return critere;
	}


	public void setCritere(String critere) {
		this.critere = critere;
	}


	public String getValeur() {
		return valeur;
	}


	public void setValeur(String valeur) {
		this.valeur = valeur;
	}
	
	 public void getSearchType(String critere, String valeur) {
        System.out.println("SearchService - Critère: " + critere + ", Valeur: " + valeur);

}
}
