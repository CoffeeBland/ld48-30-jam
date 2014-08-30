package com.coffeebland.res;

import com.badlogic.gdx.graphics.Texture;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by dagothig on 8/23/14.
 */
public class Images
{
    private Images() {}

    private static Map<String, WeakReference<Texture>> images = new HashMap<String, WeakReference<Texture>>();
    public static Texture get(String ref)
    {
        if (images.containsKey(ref)) {
            WeakReference<Texture> wref = images.get(ref);

            Texture text = wref.get();
            if (text == null) {
                images.remove(ref);
                return get(ref);
            }

            return text;
        }

        Texture texture = new Texture(ref);
        images.put(ref, new WeakReference<Texture>(texture));
        return texture;
    }
}