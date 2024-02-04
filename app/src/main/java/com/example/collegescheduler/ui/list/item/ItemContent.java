package com.example.collegescheduler.ui.list.item;

import com.example.collegescheduler.item.AbstractItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class ItemContent {

    /**
     * An array of sample (placeholder) items.
     */
    public static final List<AbstractItem> ITEMS = new ArrayList<AbstractItem>();

    /**
     * A map of sample (placeholder) items, by ID.
     */
    public static final Map<UUID, AbstractItem> ITEM_MAP = new HashMap<UUID, AbstractItem>();

    private static final int COUNT = 25;

    static {
        // Add some sample items.
        for (int i = 1; i <= COUNT; i++) {
            addItem(createAbstractItem(i));
        }
    }

    private static void addItem(AbstractItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.getId(), item);
    }

    private static AbstractItem createAbstractItem(int position) {
        return new AbstractItem("Item " + position, makeDetails(position));
    }

    private static String makeDetails(int position) {
        StringBuilder builder = new StringBuilder();
        builder.append("Details about Item: ").append(position);
        for (int i = 0; i < position; i++) {
            builder.append("\nMore details information here.");
        }
        return builder.toString();
    }

}