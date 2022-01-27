package de.perdian.divelog.web.modules.overview.contributors;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Stream;

import org.springframework.stereotype.Component;

import de.perdian.divelog.model.entities.Dive;
import de.perdian.divelog.model.entities.components.AirType;
import de.perdian.divelog.model.entities.components.PlaceType;
import de.perdian.divelog.model.entities.components.SuitType;
import de.perdian.divelog.model.entities.components.WaterType;
import de.perdian.divelog.web.modules.overview.Overview;
import de.perdian.divelog.web.modules.overview.OverviewAggregationItem;
import de.perdian.divelog.web.modules.overview.OverviewContributor;
import de.perdian.divelog.web.modules.overview.OverviewOptions;

@Component
class AggregatedEnumsContributor implements OverviewContributor {

    @Override
    public void contribute(Overview overview, List<Dive> dives, OverviewOptions options) {
        overview.setAggregatedLocationTypes(this.computeAggregationItems(dives, PlaceType.class, dive -> dive.getStart() == null ? null : dive.getStart().getType()));
        overview.setAggregatedAirTypes(this.computeAggregationItems(dives, AirType.class, dive -> dive.getAir() == null ? null : dive.getAir().getType()));
        overview.setAggregatedSuitTypes(this.computeAggregationItems(dives, SuitType.class, dive -> dive.getEquipment() == null ? null : dive.getEquipment().getSuitType()));
        overview.setAggregatedWaterTypes(this.computeAggregationItems(dives, WaterType.class, dive -> dive.getEnvironment() == null ? null : dive.getEnvironment().getWaterType()));
    }

    private <T, E extends Enum<E>> List<OverviewAggregationItem> computeAggregationItems(List<T> sourceList, Class<E> enumClass, Function<T, E> enumExtractor) {

        Map<E, AtomicInteger> enumValues = new HashMap<>();
        for (T sourceItem : sourceList) {
            enumValues.compute(enumExtractor.apply(sourceItem), (k, v) -> v == null ? new AtomicInteger() : v).incrementAndGet();
        }

        List<OverviewAggregationItem> resultItems = new ArrayList<>();
        Stream.of(enumClass.getEnumConstants()).forEach(enumConstant -> {
            Number enumValue = enumValues.getOrDefault(enumConstant, new AtomicInteger(0));
            OverviewAggregationItem aggregationItem = new OverviewAggregationItem();
            aggregationItem.setTitleKey("enum." + enumClass.getSimpleName() + "." + enumConstant.name());
            aggregationItem.setTotalValue(enumValue.intValue());
            if (enumValue.intValue() > 0) {
                aggregationItem.setPercentValue(100d / sourceList.size() * enumValue.doubleValue());
            }
            resultItems.add(aggregationItem);
        });

        Number unknownValue = enumValues.get(null);
        if (unknownValue != null && unknownValue.intValue() > 0) {
            OverviewAggregationItem unknownItem = new OverviewAggregationItem();
            unknownItem.setTitleKey("unknown");
            unknownItem.setTotalValue(unknownValue.intValue());
            unknownItem.setPercentValue(100d / sourceList.size() * unknownValue.doubleValue());
            resultItems.add(unknownItem);
        }
        return resultItems;

    }

}
