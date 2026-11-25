package com.agrilink.auth.dto;

import java.math.BigDecimal;

import com.agrilink.auth.enums.ModeLocalisation;

public class InscriptionAgriculteurRequest {

    // Informations personnelles
    private String nom;
    private String prenom;
    private String email;
    private String motDePasse;
    private String telephone;

    // Informations sur l'exploitation
    private String nomExploitation;
    private BigDecimal superficie;

    // Localisation
    private ModeLocalisation modeLocalisation;
    private String region;
    private String province;
    private String commune;
    private String douar;

    private BigDecimal latitude;
    private BigDecimal longitude;
    private BigDecimal precisionLocalisation;

    public InscriptionAgriculteurRequest() {
    }
    
    // Générer les getters et setters avec STS

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMotDePasse() {
		return motDePasse;
	}

	public void setMotDePasse(String motDePasse) {
		this.motDePasse = motDePasse;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getNomExploitation() {
		return nomExploitation;
	}

	public void setNomExploitation(String nomExploitation) {
		this.nomExploitation = nomExploitation;
	}

	public BigDecimal getSuperficie() {
		return superficie;
	}

	public void setSuperficie(BigDecimal superficie) {
		this.superficie = superficie;
	}

	public ModeLocalisation getModeLocalisation() {
		return modeLocalisation;
	}

	public void setModeLocalisation(ModeLocalisation modeLocalisation) {
		this.modeLocalisation = modeLocalisation;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCommune() {
		return commune;
	}

	public void setCommune(String commune) {
		this.commune = commune;
	}

	public String getDouar() {
		return douar;
	}

	public void setDouar(String douar) {
		this.douar = douar;
	}

	public BigDecimal getLatitude() {
		return latitude;
	}

	public void setLatitude(BigDecimal latitude) {
		this.latitude = latitude;
	}

	public BigDecimal getLongitude() {
		return longitude;
	}

	public void setLongitude(BigDecimal longitude) {
		this.longitude = longitude;
	}

	public BigDecimal getPrecisionLocalisation() {
		return precisionLocalisation;
	}

	public void setPrecisionLocalisation(BigDecimal precisionLocalisation) {
		this.precisionLocalisation = precisionLocalisation;
	}

    
}
