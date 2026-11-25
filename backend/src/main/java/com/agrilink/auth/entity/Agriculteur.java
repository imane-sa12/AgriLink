
package com.agrilink.auth.entity;

import java.math.BigDecimal;

import com.agrilink.auth.enums.ModeLocalisation;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Column;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;

@Entity
@Table(name = "AGRICULTEUR")
@PrimaryKeyJoinColumn(name = "id_utilisateur")
// id_utilisateur cle primaire et clé étrangère vers PERSONNE
public class Agriculteur extends Personne {

    @Column(name = "nom_exploitation", nullable = false, length = 150)
    private String nomExploitation;

    @Column(name = "superficie", nullable = false, precision = 10, scale = 2)
    private BigDecimal superficie;

    @Enumerated(EnumType.STRING)
    @Column(name = "mode_localisation", nullable = false)
    private ModeLocalisation modeLocalisation;

    @Column(name = "region", nullable = false, length = 100)
    private String region;

    @Column(name = "province", nullable = false, length = 100)
    private String province;

    @Column(name = "commune", nullable = false, length = 100)
    private String commune;

    @Column(name = "douar", nullable = false, length = 100)
    private String douar;

    @Column(name = "latitude", precision = 10, scale = 7)
    private BigDecimal latitude;

    @Column(name = "longitude", precision = 10, scale = 7)
    private BigDecimal longitude;

    @Column(name = "precision_localisation", precision = 10, scale = 2)
    private BigDecimal precisionLocalisation;

    public Agriculteur() {
    }

    // Getters et Setters

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
