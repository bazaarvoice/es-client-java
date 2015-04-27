package org.elasticsearch.search.aggregations.bucket.filters;

import com.bazaarvoice.elasticsearch.client.core.util.aggs.AggregationsManifest;
import org.elasticsearch.action.search.helpers.InternalAggregationsHelper;
import org.elasticsearch.common.base.Predicate;
import org.elasticsearch.common.collect.ImmutableSet;
import org.elasticsearch.common.collect.Maps;
import org.elasticsearch.search.aggregations.InternalAggregations;

import java.util.Map;
import java.util.Set;

import static org.elasticsearch.common.xcontent.support.XContentMapValues.nodeLongValue;

public class FiltersBucketHelper {
    private static final String DOC_COUNT = "doc_count";
    private static final Set<String> properKeys = ImmutableSet.of(DOC_COUNT);

    public static InternalFilters.Bucket fromXContent(final String key, final Map<String, Object> map, final AggregationsManifest manifest) {
        final long docCount = nodeLongValue(map.get(DOC_COUNT));

        final Map<String, Object> subAggsMap = Maps.filterKeys(map, new Predicate<String>() {
            @Override public boolean apply(final String s) {
                return !properKeys.contains(s);
            }
        });
        final InternalAggregations aggregations = InternalAggregationsHelper.fromXContentUnwrapped(subAggsMap, manifest);
        return new InternalFilters.Bucket(key, docCount, aggregations);
    }
}
