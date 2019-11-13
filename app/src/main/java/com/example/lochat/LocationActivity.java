package com.example.lochat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LocationActivity extends AppCompatActivity {

    Button mLocationButton;
    Button mMessengerButton;
    Button mSignoutButton;
    TextView mAddressTextView;
    TextView mCityTextView;
    TextView mPostalTextView;
    EditText mUsernameText;

    LocationManager locationManager;
    String provider;
    final int PERMISSION_REQUEST_CODE = 7171;
    double latitude;
    double longitude;
    String city;
    String postalCode;
    String username;



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getLocation();
                }
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        mSignoutButton = (Button) findViewById(R.id.sign_Out_button);
        mLocationButton = (Button) findViewById(R.id.buttonGetLocation);
        mAddressTextView = (TextView) findViewById(R.id.LocationText);
        mCityTextView = (TextView) findViewById(R.id.CityText);
        mPostalTextView = (TextView) findViewById(R.id.PostalText);
        mMessengerButton = findViewById(R.id.messenger);
        mUsernameText = (EditText) findViewById(R.id.UsernameEditText);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION
            }, PERMISSION_REQUEST_CODE);

        } else {
            getLocation();
        }

        mMessengerButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                username = mUsernameText.getText().toString();
                openMessenger();
            }
        });

        mLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Location myLocation = locationManager.getLastKnownLocation(provider);
                    latitude = myLocation.getLatitude();
                    longitude = myLocation.getLongitude();
                }
                catch(SecurityException e) {}

                new GetLocation().execute(String.format("%.4f,%.4f",latitude,longitude));
            }
        });

        mSignoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(LocationActivity.this, LogInActivity.class));
            }
        });
    }

    public void openMessenger() {
        Intent intent = new Intent(this, MessengerActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("postalCode", postalCode);

        bundle.putString("username", username);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    private void getLocation() {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        provider = locationManager.getBestProvider(new Criteria(), false);


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        final Location location = locationManager.getLastKnownLocation(provider);
        if(location == null)
            Log.e("Error", "LOCATION: NULL");
    }

    private class GetLocation extends AsyncTask<String,Void,String> {

        ProgressDialog mDialog = new ProgressDialog(LocationActivity.this);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mDialog.setMessage("Please wait...");
            mDialog.setCanceledOnTouchOutside(false);
            mDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            try{
                double latitude =  Double.parseDouble(strings[0].split(",")[0]);
                double longitude = Double.parseDouble(strings[0].split(",")[1]);
                String response;
                HttpHandler http = new HttpHandler();
                String url = String.format("https://maps.googleapis.com/maps/api/geocode/json?latlng=%.4f,%.4f&result_type=postal_code&sensor=false&key=AIzaSyB8Ur4e8Z57G9TgIna0Nrr4tQiYJz5SDIM ",latitude,longitude);
                response = http.GetHTTP(url);
                return response;
            }
            catch (Exception e)
            {

            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            try{
                JSONObject jsonObject = new JSONObject(s);
                String address = ((JSONArray)jsonObject.get("results")).getJSONObject(0).get("formatted_address").toString();

                JSONObject results = ((JSONArray)jsonObject.get("results")).getJSONObject(0);
                postalCode = ((JSONArray) results.get("address_components")).getJSONObject(0).get("long_name").toString();

                mAddressTextView.setText("Location: " + address);
                mPostalTextView.setText("Postal: " + postalCode);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            if(mDialog.isShowing())
                mDialog.dismiss();
        }
    }
}
