package org.launchpadcs.flokk;

import android.accounts.Account;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

public class LoginActivity extends AppCompatActivity {

    private SignInButton signInButton;
    private GoogleApiClient apiClient;
    public static String email;

    // Bundle key for account object
    private static final String KEY_ACCOUNT = "key_account";

    // Request codes
    private static final int RC_SIGN_IN = 9001;
    private static final int RC_RECOVERABLE = 9002;
    private Account mAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        signInButton = (SignInButton)findViewById(R.id.sign_in_button);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = Auth.GoogleSignInApi.getSignInIntent(apiClient);
                startActivityForResult(myIntent, RC_SIGN_IN);
            }
        });
        signInButton.setSize(SignInButton.SIZE_STANDARD);

//        if (savedInstanceState != null) {
//            mAccount = savedInstanceState.getParcelable(KEY_ACCOUNT);
//        }

        GoogleSignInOptions signInOptions = new GoogleSignInOptions.Builder(
                GoogleSignInOptions.DEFAULT_SIGN_IN).
                requestEmail().
                requestProfile().
                requestId().
                requestIdToken(getApplicationContext().getString(R.string.google_client_id)).build();

        apiClient = new GoogleApiClient.Builder(this).addApi(
                Auth.GOOGLE_SIGN_IN_API, signInOptions).build();



    }

    @Override
    public void onStart() {
        super.onStart();
        apiClient.connect();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(KEY_ACCOUNT, mAccount);
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);

        if(requestCode == RC_SIGN_IN) {
            GoogleSignInResult res = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(res);
        }
    }



    private void handleSignInResult(GoogleSignInResult res) {
        Log.d("GOOGLE_RESULT", "Result = " + res.isSuccess());
        Log.d("GOOGLE_STATUS_CODE", String.valueOf(res.getStatus().getStatusCode()));
        Log.d("GOOGLE_STATUS", res.toString());
        if(res.isSuccess()) {
            GoogleSignInAccount account = res.getSignInAccount();
            String token = account.getIdToken();
            Log.d("Display Name",account.getDisplayName());
            Log.d("Photo URL",account.getPhotoUrl().toString(), null);
            Log.d("Family Name",account.getFamilyName());
            Log.d("Given Name",account.getGivenName());
            Log.d("Account",account.getAccount().toString());
            authWithServer(token, account.getPhotoUrl().toString());
            email = account.getEmail();
        }
        else
            mAccount = null;
    }

    private void signOut() {
        apiClient.connect();
        Auth.GoogleSignInApi.signOut(apiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {}
                });
    }

    private void showErrorDialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("An error has occurred");
        alertDialogBuilder.setMessage("You may have no connection or are unauthorized.");
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private void authWithServer(String TOKEN, String photoUrl) {
        if(TOKEN == null) {
            return;
        }

        Log.d("TOK MASTER", TOKEN);
        Log.d("PHOTO_URL", photoUrl);
        Intent myIntent = new Intent(LoginActivity.this, TabActivity.class);
        startActivity(myIntent);
        finish();

    }


    /*private Button loginButton;
    private EditText usernameText;
    private EditText passwordText;
    private GoogleSignInAccount mGoogleSignInAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginButton = (Button) findViewById(R.id.loginButton);
        usernameText = (EditText) findViewById(R.id.usernameText);
        passwordText = (EditText) findViewById(R.id.passwordText);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(usernameText.getText().toString().equals("") || passwordText.getText().toString().equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                    builder.setCancelable(false);
                    builder.setTitle("Error");
                    builder.setMessage("You must input your username and password into the text fields.");
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    builder.create().show();
                }
                else {
                    Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                    startActivity(intent);
                }
            }
        });
    }*/
}
