package com.eddy.bookworm.models.mappers;

import com.eddy.bookworm.models.ParcelableBuyLink;
import com.eddy.data.models.BuyLink;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

class ParcelableBuyLinkMapper {

    private ParcelableBuyLink transform(BuyLink buyLink) {
        ParcelableBuyLink parcelableBuyLink = new ParcelableBuyLink();

        parcelableBuyLink.setName(buyLink.getName());
        parcelableBuyLink.setUrl(buyLink.getUrl());

        return parcelableBuyLink;
    }

    List<ParcelableBuyLink> transform(Collection<BuyLink> buyLinks) {
        List<ParcelableBuyLink> parcelableBuyLinks = new ArrayList<>();

        if (buyLinks != null) {
            for(BuyLink buyLink: buyLinks) {
                ParcelableBuyLink parcelableBuyLink = transform(buyLink);
                if (parcelableBuyLink != null) {
                    parcelableBuyLinks.add(parcelableBuyLink);
                }
            }
        }

        return parcelableBuyLinks;
    }

}
