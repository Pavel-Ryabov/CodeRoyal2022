package xyz.pary.raic.coderoyal2022.model;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import xyz.pary.raic.coderoyal2022.util.StreamUtil;

public class Order {

    private Map<Integer, UnitOrder> unitOrders;

    public Map<Integer, UnitOrder> getUnitOrders() {
        return unitOrders;
    }

    public void setUnitOrders(Map<Integer, UnitOrder> value) {
        this.unitOrders = value;
    }

    public Order(Map<Integer, UnitOrder> unitOrders) {
        this.unitOrders = unitOrders;
    }

    public static Order readFrom(InputStream stream) throws IOException {
        Map<Integer, UnitOrder> unitOrders;
        int unitOrdersSize = StreamUtil.readInt(stream);
        unitOrders = new HashMap<>(unitOrdersSize);
        for (int unitOrdersIndex = 0; unitOrdersIndex < unitOrdersSize; unitOrdersIndex++) {
            int unitOrdersKey;
            unitOrdersKey = StreamUtil.readInt(stream);
            UnitOrder unitOrdersValue;
            unitOrdersValue = UnitOrder.readFrom(stream);
            unitOrders.put(unitOrdersKey, unitOrdersValue);
        }
        return new Order(unitOrders);
    }

    public void writeTo(OutputStream stream) throws IOException {
        StreamUtil.writeInt(stream, unitOrders.size());
        for (Map.Entry<Integer, UnitOrder> unitOrdersEntry : unitOrders.entrySet()) {
            int unitOrdersKey = unitOrdersEntry.getKey();
            StreamUtil.writeInt(stream, unitOrdersKey);
            UnitOrder unitOrdersValue = unitOrdersEntry.getValue();
            unitOrdersValue.writeTo(stream);
        }
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("Order { ");
        stringBuilder.append("unitOrders: ");
        stringBuilder.append(String.valueOf(unitOrders));
        stringBuilder.append(" }");
        return stringBuilder.toString();
    }
}
