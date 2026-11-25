package com.agrilink.auth.enums;

public enum StatutCompte {

    ACTIF, 
    // compte est utilisable 
    INACTIF, 
    // le compte est desactive
    EN_ATTENTE_VALIDATION,  
    // le compte attend une validation 
    SUSPENDU
    // le compte est temporairement bloque

}