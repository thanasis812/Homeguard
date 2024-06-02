package com.hg.service.dto.mydto;

import java.io.Serializable;
import java.util.List;

public class FavoritePropertyDTO implements Serializable {

    private List<PropertyDossierDTO> favourites;
    private List<PropertyDossierDTO> alarm;

    public List<PropertyDossierDTO> getFavourites() {
        return favourites;
    }

    public void setFavourites(List<PropertyDossierDTO> favourites) {
        this.favourites = favourites;
    }

    public List<PropertyDossierDTO> getAlarm() {
        return alarm;
    }

    public void setAlarm(List<PropertyDossierDTO> alarm) {
        this.alarm = alarm;
    }
}
