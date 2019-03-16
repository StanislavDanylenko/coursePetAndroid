package stanisalv.danylenko.coursepet.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import stanisalv.danylenko.coursepet.PetApplication;
import stanisalv.danylenko.coursepet.R;
import stanisalv.danylenko.coursepet.entity.auth.AuthenticationRequestModel;
import stanisalv.danylenko.coursepet.entity.auth.GetAuthenticationModel;
import stanisalv.danylenko.coursepet.network.RetrofitService;
import stanisalv.danylenko.coursepet.network.retrofit.AuthService;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;
    private PetApplication application;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
        mPasswordView = (EditText) findViewById(R.id.password);

        application = (PetApplication) getApplication();

        setDefaultValues();

        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                authorize();
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
    }

    private void authorize() {
        showProgress(true);

        RetrofitService retrofitService = application.getRetrofitService();
        AuthService service = retrofitService.getRetrofit().create(AuthService.class);
        AuthenticationRequestModel model = getAuthValues();

        service.authorize(model).enqueue(new Callback<GetAuthenticationModel>() {
            @Override
            public void onResponse(Call<GetAuthenticationModel> call, Response<GetAuthenticationModel> response) {
                showProgress(false);
                if(response.isSuccessful()) {
                    handleSuccessAuth(response);
                } else {
                    handleFailedAuth();
                }
            }

            @Override
            public void onFailure(Call<GetAuthenticationModel> call, Throwable t) {
                showProgress(false);
                t.printStackTrace();
                handleFailedAuth();
            }
        });
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    private void setDefaultValues() {
        mEmailView.setText("user");
        mPasswordView.setText("password");
    }

    private AuthenticationRequestModel getAuthValues() {
        String email = mEmailView.getText().toString();
        String password =  mPasswordView.getText().toString();
        return new AuthenticationRequestModel(email, password);
    }

    private void handleSuccessAuth(Response<GetAuthenticationModel> response) {
        GetAuthenticationModel userAuthEntity = response.body();
        application.setTOKEN(userAuthEntity.getToken());
        mEmailView.setError(null);
        mPasswordView.setError(null);
        Toast.makeText(getApplicationContext(), "AUTH SUCCESS", Toast.LENGTH_LONG).show();
        goToMainActivity();
    }

    private void handleFailedAuth() {
        Toast.makeText(getApplicationContext(), "AUTH FAILED", Toast.LENGTH_LONG).show();
        mEmailView.setError("Invalid value");
        mPasswordView.setError("Invalid value");
    }

    public void goToMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}

