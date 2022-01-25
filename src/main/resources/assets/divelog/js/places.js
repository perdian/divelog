// TODO: Localize the dynamically generated elements of the search form

function searchPlace(locationValue, targetFields) {
    searchPlacePrepare(locationValue);
    searchPlaceModal().modal("show");
    searchPlaceExecute(locationValue, targetFields);
}

function searchPlacePrepare(locationValue) {
    searchPlaceModal().empty().append(
        $("<i class='close icon'></i>"),
        $("<div class='ui icon header'></div>").append(
            $("<i class='compass icon'></i>"),
            $("<span id='searchPlaceTitle'>Searching for location '" + locationValue + "'</span>")
        ),
        $("<div id='searchPlaceBody' class='ui segment'></div>").append(
            $("<div class='ui active inverted dimmer'></div>").append(
                $("<div class='ui text loader'>Loading</div>")
            ),
            $("<p>&nbsp;</p>"),
            $("<p>&nbsp;</p>"),
            $("<p>&nbsp;</p>")
        )
    );
}

function searchPlaceExecute(locationValue, targetFields) {
    $.ajax({
        url: $("meta[name='endpointPlacesSearch").attr("content") + "?q=" + encodeURIComponent(locationValue),
        success: function(data) { searchPlaceShowResults(data, locationValue, targetFields); },
        error: function(_xhr, _textStatus, thrownError) { searchPlaceShowError(thrownError, locationValue); }
    });
}

function searchPlaceShowResults(result, locationValue, targetFields) {
    $("#searchPlaceTitle").text("Search results for location: '" + locationValue + "'");
    $("#searchPlaceBody").empty();
    if (result.items.length < 1) {
        $("#searchPlaceBody").append(
            $("<div class='ui warning message'></div>").append(
                $("<div class='header'></div>").text("No results"),
                $("<p></p>").text("The search didn't return any results")
            )
        );
    } else {
        let mapRenderActions = [];
        let parentElement = $("<div class='ui divided items'></div>");
        for (let itemIndex = 0; itemIndex < result.items.length; itemIndex++) {
            let item = result.items[itemIndex];
            let mapElement = document.createElement("div");
            mapRenderActions.push(() => renderMap(mapElement, item.longitude, item.latitude))
            parentElement.append(
                $("<div class='item'></div>").append(
                    $(mapElement).attr("class", "ui tiny image").attr("style", "width: 200px !important; height: 150px !important; border: 1px solid black;"),
                    $("<div class='middle aligned content'></div>").append(
                        $("<a class='header'></a>").text(item.name).click(() => searchPlaceSelect(item, targetFields)),
                        $("<div class='meta'></div>").text(item.description),
                        $("<div class='description'></div>").append(
                            $("<button class='ui right primary button'>Select</button>").click(() => searchPlaceSelect(item, targetFields))
                        )
                    )
                )
            );
        }
        $("#searchPlaceBody").append(parentElement);
        mapRenderActions.forEach(action => action());
    }
}

function searchPlaceShowError(error, locationValue) {
    $("#searchPlaceTitle").text("Search failed for location: '" + locationValue + "'");
    $("#searchPlaceBody").empty().append(
        $("<div class='ui negative message'></div>").append(
            $("<div class='header'></div>").text("Search failed"),
            $("<p></p>").text(error)
        )
    );
}

function searchPlaceSelect(item, targetFields) {
    targetFields.latitudeField.val(localeNumberFormat(item.latitude));
    targetFields.latitudeField.closest(".field").removeClass("error");
    targetFields.longitudeField.val(localeNumberFormat(item.longitude));
    targetFields.longitudeField.closest(".field").removeClass("error");
    targetFields.timezoneIdField.val(item.timezoneId);
    targetFields.timezoneIdField.closest(".field").removeClass("error");
    targetFields.countryCodeField.val(item.countryCode);
    targetFields.countryCodeField.closest(".field").removeClass("error");
    targetFields.countryFlagField.attr("class", item.countryCode == null ? "" : (item.countryCode.toLowerCase() + " flag"));
    if (targetFields.addressField != null) {
        targetFields.addressField.val(item.address);
        targetFields.addressField.closest(".field").removeClass("error");
    }
    updateMap(targetFields.mapDiv, item.longitude, item.latitude);
    searchPlaceModal().modal("hide");
}

function searchPlaceModal() {
    let searchPlaceModalElement = $("#searchPlaceModal")
    if (!searchPlaceModalElement.length) {
        searchPlaceModalElement = $("<div id='searchPlaceModal' class='ui modal'></div>");
        $("body").append(searchPlaceModalElement);
    }
    return searchPlaceModalElement;
}

function updateMapFromStrings(target, longitudeValue, latitudeValue) {
    if (longitudeValue.length > 0 && latitudeValue.length > 0) {
        updateMap(target, localeNumberParse(longitudeValue), localeNumberParse(latitudeValue));
    }
}

function updateMap(target, longitude, latitude) {
    target.empty();
    let longitudeValid = typeof(longitude) == 'number' || longitude.length > 0
    let latitudeValid = typeof(latitude) == 'number' || latitude.length > 0
    if (longitudeValid && latitudeValid) {
        renderMap(target.get(0), longitude, latitude);
    }
}

function renderMap(target, longitude, latitude) {

    let mapLayer = new ol.layer.Tile({
        source: new ol.source.OSM()
    })

    let locationFeature = new ol.Feature({
        geometry: new ol.geom.Point(ol.proj.transform([longitude, latitude], 'EPSG:4326', 'EPSG:3857'))
    });
    let locationSource = new ol.source.Vector();
    locationSource.addFeature(locationFeature);
    let locationLayer = new ol.layer.Vector({ source: locationSource })

    var map = new ol.Map({
        target: target,
        projection: "EPSG:3857",
        layers: [mapLayer, locationLayer],
        view: new ol.View({
          center: ol.proj.fromLonLat([longitude, latitude]),
          zoom: 11
        }),
        controls: [
          new ol.control.Zoom()
        ]
    });
    map.render();

    $(".ol-zoom-in").attr("tabindex", "-1");
    $(".ol-zoom-out").attr("tabindex", "-1");

}

function registerMap(target, longitudeField, latitudeField) {
    changeListener = () => updateMapFromStrings(target, longitudeField.val().trim(), latitudeField.val().trim());
    longitudeField.on("change", changeListener);
    latitudeField.on("change", changeListener);
    $(document).ready(changeListener);
}

function updateCountryFlag(country, targetElement) {
  targetElement.attr("class", country.toLowerCase() + " flag");
}
