package com.example.diego.myapplication.Auxiliares;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.plus.PlusClient;

public class LoginHelper extends ActionBarActivity implements
        GooglePlayServicesClient.ConnectionCallbacks, GooglePlayServicesClient.OnConnectionFailedListener {
    private static final int REQUEST_CODE_RESOLVE_ERR = 9000;

    private ProgressDialog mConnectionProgressDialog;
    private static PlusClient mPlusClient;
    private ConnectionResult mConnectionResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPlusClient = new PlusClient.Builder(this, this, this)
                .setVisibleActivities("http://schemas.google.com/AddActivity", "http://schemas.google.com/BuyActivity")
                .build();
        // A barra de progresso deve ser exibida se a falha de conexão não for resolvida.
        mConnectionProgressDialog = new ProgressDialog(this);
        mConnectionProgressDialog.setMessage("Logando...");

        login();
    }

    public void login() {
        if (mConnectionResult == null) {
            mConnectionProgressDialog.show();
        } else {
            try {
                mConnectionResult.startResolutionForResult(this, REQUEST_CODE_RESOLVE_ERR);
            } catch (IntentSender.SendIntentException e) {
                // Tente se conectar novamente.
                mConnectionResult = null;
                mPlusClient.connect();
            }
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        mPlusClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mPlusClient.disconnect();
    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        if (result.hasResolution()) {
            try {
                result.startResolutionForResult(this, REQUEST_CODE_RESOLVE_ERR);
            } catch (IntentSender.SendIntentException e) {
                mPlusClient.connect();
            }
        }
        // Salvar o resultado e solucionar a falha de conexão mediante clique do usuário.
        mConnectionResult = result;
    }

    @Override
    protected void onActivityResult(int requestCode, int responseCode, Intent intent) {
        if (requestCode == REQUEST_CODE_RESOLVE_ERR && responseCode == RESULT_OK) {
            mConnectionResult = null;
            mPlusClient.connect();
        }
    }

    @Override
    public void onConnected() {
        String id = mPlusClient.getAccountName();
        Intent it = new Intent();
        it.putExtra("id", id);
        setResult(1, it);
        finish();
    }

    @Override
    public void onDisconnected() {
        finish();
    }

    public static void sair() {
        if (mPlusClient.isConnected()) {
            mPlusClient.clearDefaultAccount();
            mPlusClient.disconnect();
            mPlusClient.connect();
        }
    }
}
