
package com.agrilink.auth.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.PrimaryKeyJoinColumn;

@Entity
@Table(name = "ADMINISTRATEUR")
@PrimaryKeyJoinColumn(name = "id_utilisateur")
public class Administrateur extends Personne {

    public Administrateur() {
        super();
    }

}
