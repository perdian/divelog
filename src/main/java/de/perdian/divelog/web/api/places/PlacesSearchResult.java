package de.perdian.divelog.web.api.places;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class PlacesSearchResult implements Serializable {

    static final long serialVersionUID = 1L;

    private boolean providerAvailable = false;
    private String providerName = null;
    private List<PlacesSearchResultItem> items = null;

    public PlacesSearchResult(boolean providerAvailable, String providerName, List<PlacesSearchResultItem> items) {
        this.setProviderAvailable(providerAvailable);
        this.setProviderName(providerName);
        this.setItems(items);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.NO_CLASS_NAME_STYLE);
    }

    public boolean isProviderAvailable() {
        return this.providerAvailable;
    }
    private void setProviderAvailable(boolean providerAvailable) {
        this.providerAvailable = providerAvailable;
    }

    public String getProviderName() {
        return this.providerName;
    }
    private void setProviderName(String providerName) {
        this.providerName = providerName;
    }

    public List<PlacesSearchResultItem> getItems() {
        return this.items;
    }
    private void setItems(List<PlacesSearchResultItem> items) {
        this.items = items;
    }

}
