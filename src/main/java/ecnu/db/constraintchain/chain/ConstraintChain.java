package ecnu.db.constraintchain.chain;

import ecnu.db.constraintchain.filter.Parameter;
import ecnu.db.exception.TouchstoneToolChainException;
import ecnu.db.schema.Schema;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author wangqingshuai
 */
public class ConstraintChain {

    private final List<ConstraintChainNode> nodes = new ArrayList<>();
    private String tableName;
    private List<Parameter> parameters = new ArrayList<>();

    public ConstraintChain() {
    }

    public ConstraintChain(String tableName) {
        this.tableName = tableName;
    }

    public void addNode(ConstraintChainNode node) {
        nodes.add(node);
    }

    public List<ConstraintChainNode> getNodes() {
        return nodes;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public void addParameters(List<Parameter> parameters) {
        this.parameters.addAll(parameters);
    }

    public List<Parameter> getParameters() {
        return parameters;
    }

    public void setParameters(List<Parameter> parameters) {
        this.parameters = parameters;
    }

    @Override
    public String toString() {
        return "{tableName:" + tableName + ",nodes:" + nodes + "}";
    }

    public void evaluate(Schema schema, int size, Map<Integer, boolean[]> pkBitMap, Map<Integer, boolean[]> fkBitMap) throws TouchstoneToolChainException {
        boolean[] flag = new boolean[size];
        Arrays.fill(flag, true);
        ThreadLocalRandom rand = ThreadLocalRandom.current();
        for (ConstraintChainNode node : nodes) {
            if (node instanceof ConstraintChainPkJoinNode) {
                boolean[] pkBit = new boolean[size];
                System.arraycopy(flag, 0, pkBit, 0, size);
                pkBitMap.put(((ConstraintChainPkJoinNode) node).getPkTag(), pkBit);
            }
            else if (node instanceof ConstraintChainFilterNode) {
                boolean[] filter  = ((ConstraintChainFilterNode) node).evaluate(schema, size);
                System.arraycopy(filter, 0, flag, 0, size);
            }
            else if (node instanceof ConstraintChainFkJoinNode) {
                double probability = ((ConstraintChainFkJoinNode) node).getProbability().doubleValue();
                boolean[] fkBit = new boolean[size];
                for (int i = 0; i < size; i++) {
                    if ((1 - rand.nextDouble(0, 1)) >= probability) {
                        flag[i] = false;
                    }
                    fkBit[i] = flag[i];
                }
                fkBitMap.put(((ConstraintChainFkJoinNode) node).getPkTag(), fkBit);
            }
        }
    }
}
