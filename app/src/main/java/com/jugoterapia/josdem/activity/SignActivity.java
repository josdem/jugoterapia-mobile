/*
  Copyright 2017 José Luis De la Cruz Morales <joseluis.delacruz@gmail.com>

  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
*/

package com.jugoterapia.josdem.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.content.Intent;
import android.widget.TextView;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.jugoterapia.josdem.R;
import com.jugoterapia.josdem.model.Credentials;
import com.jugoterapia.josdem.state.ApplicationState;

/**
 * @understands It handle user's authentication using Google oauth2
 */

public class SignActivity extends FragmentActivity implements GoogleApiClient.OnConnectionFailedListener, View.OnClickListener {

  private static final int RC_SIGN_IN = 9001;
  private static final String TAG = "Oauth2Google";

  private GoogleApiClient mGoogleApiClient;
  private TextView statusTextView;
  private SignInButton signInButton;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_sign);

    if(!ApplicationState.CURRENT_USER.isEmpty()){
      startCategoryActivity();
    }

    statusTextView = (TextView) findViewById(R.id.status);

    GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build();

    mGoogleApiClient = new GoogleApiClient.Builder(this)
            .enableAutoManage(this, this)
            .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
            .build();

    signInButton = (SignInButton) findViewById(R.id.sign_in_button);
    signInButton.setSize(SignInButton.SIZE_WIDE);
    signInButton.setOnClickListener(this);
  }

  @Override
  public void onClick(View view) {
    signIn();
  }

  private void signIn() {
    Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
    startActivityForResult(signInIntent, RC_SIGN_IN);
  }

  @Override
  public void onConnectionFailed(ConnectionResult connectionResult) {
    Log.d(TAG, "onConnectionFailed:" + connectionResult);
  }

  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);

    if (requestCode == RC_SIGN_IN) {
      GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data); 
      handleSignInResult(result);
    }
  }

  private void handleSignInResult(GoogleSignInResult result) {
    Log.d(TAG, "handleSignInResult:" + result.isSuccess());
    if (result.isSuccess()) {
      signInButton.setVisibility(View.GONE);
      GoogleSignInAccount account = result.getSignInAccount();
      ApplicationState.CURRENT_USER = account.getEmail();
      sendCredentials(account);
      startCategoryActivity();
    } else {
      statusTextView.setText(getText(R.string.failure_sign));
    }
  }

  private void sendCredentials(GoogleSignInAccount account) {
    Credentials credentials = new Credentials();
    credentials.setName(account.getDisplayName());
    credentials.setEmail(account.getEmail());
    credentials.setToken(account.getIdToken());
    //TODO: Send credentials to the server
  }

  private void startCategoryActivity(){
    Intent intent = new Intent(this, CategoryActivity.class);
    startActivity(intent);
    finish();
  }

}
