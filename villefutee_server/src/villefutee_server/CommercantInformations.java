package villefutee_server;

public class CommercantInformations extends UserInformations{
	
	private String nom_magasin;
	private String adresse;
	private String infos_supp; 
	
	
	
	public CommercantInformations(){super();}



	public void setNom_magasin(String nom_magasin) {
		this.nom_magasin = nom_magasin;
	}



	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}



	public void setInfos_supp(String infos_supp) {
		this.infos_supp = infos_supp;
	}



	public String getNom_magasin() {
		return nom_magasin;
	}



	public String getAdresse() {
		return adresse;
	}



	public String getInfos_supp() {
		return infos_supp;
	}

}
