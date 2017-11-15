package codestart.info.androidsearchbar.util;

import android.content.SearchRecentSuggestionsProvider;

/**
 * Created by ronnykibet on 11/15/17.
 */

public class SuggestionsProvider extends SearchRecentSuggestionsProvider {
    public final static String AUTHORITY = "codestart.info.androidsearchbar.util.SuggestionsProvider";
    public final static int MODE = DATABASE_MODE_QUERIES;

    public SuggestionsProvider() {
        setupSuggestions(AUTHORITY, MODE);
    }
}
