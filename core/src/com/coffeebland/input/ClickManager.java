package com.coffeebland.input;

import com.badlogic.gdx.math.Rectangle;

import java.util.*;

/**
 * Created by dagothig on 8/24/14.
 */
public class ClickManager {
    public ClickManager(InputDispatcher dispatcher) {
        dispatcher.listenTo(Control.LEFT_CLICK, new InputDispatcher.OnMouseListener() {
            @Override
            public void onMouseDown(int x, int y) {
                for (Map.Entry<Rectangle, OnClickListener> entry : buttons.entrySet()) {
                    entry.getValue().buttonStatus = entry.getKey().contains(x, y);
                }
            }

            @Override
            public void onMouseUp(int x, int y) {
                List<Map.Entry<Rectangle, OnClickListener>> entries = new ArrayList<Map.Entry<Rectangle, OnClickListener>>();
                for (Map.Entry<Rectangle, OnClickListener> entry : buttons.entrySet()) {
                    entry.getValue().buttonStatus = false;
                    if (entry.getKey().contains(x, y)) {
                        entries.add(entry);
                    }
                }

                for (Map.Entry<Rectangle, OnClickListener> entry : entries) {
                    entry.getValue().onClick();
                }
            }

            @Override
            public void onMouseIsDown(int x, int y) {
                for (Map.Entry<Rectangle, OnClickListener> entry : buttons.entrySet()) {
                    entry.getValue().buttonStatus = entry.getKey().contains(x, y);
                }
            }
        });
    }

    private Map<Rectangle, OnClickListener> buttons = new HashMap<Rectangle, OnClickListener>();

    public void addButton(OnClickListener button) {
        buttons.put(button.region, button);
    }
    public void removeButton(OnClickListener button) {
        buttons.remove(button.region);
    }

    public static abstract class OnClickListener {
        public OnClickListener(Rectangle region) {
            this.region = region;
        }

        public boolean buttonStatus;
        public Rectangle region;

        public abstract void onClick();
    }
}
