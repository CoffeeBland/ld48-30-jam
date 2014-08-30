package com.coffeebland.input;

import com.badlogic.gdx.math.Rectangle;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by dagothig on 8/24/14.
 */
public class ClickManager {
    public ClickManager(InputDispatcher dispatcher) {
        dispatcher.listenTo(Control.LEFT_CLICK, new InputDispatcher.OnMouseListener() {
            @Override
            public void onMouseDown(int x, int y) {
                for (OnClickListener entry : buttons) {
                    entry.buttonStatus = entry.region.contains(x, y);
                }
            }

            @Override
            public void onMouseUp(int x, int y) {
                List<OnClickListener> entries = new ArrayList< OnClickListener>();
                for (OnClickListener listener : buttons) {
                    listener.buttonStatus = false;
                    if (listener.region.contains(x, y)) {
                        entries.add(listener);
                    }
                }

                for (OnClickListener entry : entries) {
                    entry.onClick();
                }
            }

            @Override
            public void onMouseIsDown(int x, int y) {
                for (OnClickListener listener : buttons) {
                    listener.buttonStatus = listener.region.contains(x, y);
                }
            }
        });
    }

    private Set<OnClickListener> buttons = new HashSet<OnClickListener>();

    public void addButton(OnClickListener button) {
        buttons.add(button);
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
