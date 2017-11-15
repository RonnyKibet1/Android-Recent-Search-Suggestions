package codestart.info.androidsearchbar;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.SearchRecentSuggestions;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;

import codestart.info.androidsearchbar.util.SuggestionsProvider;

public class MainActivity extends AppCompatActivity {

    private TextView mTextView;
    private Button mButton;
    private String searchQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //assign the variables
        mTextView = (TextView)findViewById(R.id.textview);
        mButton = (Button)findViewById(R.id.button);

        /**clear search history on button click**/
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchRecentSuggestions suggestions = new SearchRecentSuggestions(MainActivity.this,
                        SuggestionsProvider.AUTHORITY, SuggestionsProvider.MODE);
                suggestions.clearHistory();

                //reset text if necessary
                mTextView.setText("Hello World!");
            }
        });


        handleIntent(getIntent());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.home_menu, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false); // Do not iconify the widget; expand it by default

        return true;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {

        //store the search and display it
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            //get search query
            searchQuery = intent.getStringExtra(SearchManager.QUERY);

            //save search
            SearchRecentSuggestions suggestions = new SearchRecentSuggestions(this,
                    SuggestionsProvider.AUTHORITY, SuggestionsProvider.MODE);
            suggestions.saveRecentQuery(searchQuery, null);

            //set search to the textview
            mTextView.setText(searchQuery);

        }else if (Intent.ACTION_VIEW.equals(intent.getAction())) {
            // Handle a suggestions click (because the suggestions all use ACTION_VIEW)
            searchQuery = intent.getStringExtra(SearchManager.QUERY);

            //set search term to the textview
            mTextView.setText(searchQuery);
        }

    }
}
