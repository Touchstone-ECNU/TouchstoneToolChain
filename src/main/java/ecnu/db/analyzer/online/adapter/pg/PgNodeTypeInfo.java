package ecnu.db.analyzer.online.adapter.pg;

import ecnu.db.analyzer.online.NodeTypeTool;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PgNodeTypeInfo implements NodeTypeTool {
    protected static final Set<String> PASS_NODE_TYPES = new HashSet<>(Arrays.asList("Sort", "Hash", "Gather", "Limit", "Gather Merge", "Materialize", "Incremental Sort", "Memoize"));
    protected static final Set<String> JOIN_NODE_TYPES = new HashSet<>(Arrays.asList("Hash Join", "Nested Loop", "Merge Join"));
    protected static final Set<String> FILTER_NODE_TYPES = new HashSet<>(Arrays.asList("Seq Scan", "Index Scan", "Bitmap Heap Scan", "Bitmap Index Scan"));
    protected static final Set<String> AGG_NODE_TYPES = new HashSet<>(List.of("Aggregate"));
    protected static final Set<String> INDEX_SCAN_NODE_TYPES = new HashSet<>(List.of("Index Scan"));

    @Override
    public boolean isReaderNode(String nodeType) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isPassNode(String nodeType) {
        return PASS_NODE_TYPES.contains(nodeType);
    }

    @Override
    public boolean isJoinNode(String nodeType) {
        return JOIN_NODE_TYPES.contains(nodeType);
    }

    @Override
    public boolean isTableScanNode(String nodeType) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isFilterNode(String nodeType) {
        return FILTER_NODE_TYPES.contains(nodeType);
    }

    @Override
    public boolean isIndexScanNode(String nodeType) {
        return INDEX_SCAN_NODE_TYPES.contains(nodeType);
    }

    @Override
    public boolean isRangeScanNode(String nodeType) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isAggregateNode(String nodeType) {
        return AGG_NODE_TYPES.contains(nodeType);
    }
}
