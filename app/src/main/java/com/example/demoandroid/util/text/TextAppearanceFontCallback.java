package com.example.demoandroid.util.text;

import android.graphics.Typeface;

public abstract class TextAppearanceFontCallback {
    /**
     * Called when an asynchronous font was finished loading.
     *
     * @param typeface Font that was loaded.
     * @param fontResolvedSynchronously Whether the font was loaded synchronously, because it was
     *     already available.
     */
    public abstract void onFontRetrieved(Typeface typeface, boolean fontResolvedSynchronously);

    /** Called when an asynchronous font failed to load. */
    public abstract void onFontRetrievalFailed(int reason);
}
