package com.malabarba.webtestapp;

import android.view.inputmethod.InputMethodManager;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.Context;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.EditText;

public class MainActivity extends Activity {

	// This is the method that is called when the submit button is clicked
    public void fetchRepos(View view) {
        EditText usernameEditText = (EditText) findViewById(R.id.username);
    	String username = usernameEditText.getText().toString();

        if( username != null && !username.isEmpty()) {
            ListView results = (ListView) findViewById(R.id.repos_list_view);
            // Set the spinning circle.
            findViewById(R.id.empty_list_view).setVisibility(View.GONE);
            results.setEmptyView(findViewById(R.id.progress_view));
            
            // The github interface starts an Async Task and returns
            // the (empty) adapter which will be populated with
            // results.
            results.setAdapter(GithubInterface.fetchRepos(this, username));

            // Remove keyboard.
            // hide virtual keyboard
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(usernameEditText.getWindowToken(), 0);
        }  
    }
    
    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
        ListView results = (ListView) findViewById(R.id.repos_list_view);
        results.setEmptyView(findViewById(R.id.empty_list_view));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}
