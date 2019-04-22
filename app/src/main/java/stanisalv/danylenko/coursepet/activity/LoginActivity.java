package stanisalv.danylenko.coursepet.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import stanisalv.danylenko.coursepet.PetApplication;
import stanisalv.danylenko.coursepet.R;
import stanisalv.danylenko.coursepet.entity.CacheModel;
import stanisalv.danylenko.coursepet.entity.auth.AuthenticationRequestModel;
import stanisalv.danylenko.coursepet.entity.auth.AuthenticationResponseModel;
import stanisalv.danylenko.coursepet.network.RetrofitService;
import stanisalv.danylenko.coursepet.network.retrofit.AuthService;
import stanisalv.danylenko.coursepet.network.retrofit.UserService;

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

        service.authorize(model).enqueue(new Callback<AuthenticationResponseModel>() {
            @Override
            public void onResponse(Call<AuthenticationResponseModel> call, Response<AuthenticationResponseModel> response) {
                if (response.isSuccessful()) {
                    handleSuccessAuth(response);
                } else {
                    handleFailedAuth();
                }
            }

            @Override
            public void onFailure(Call<AuthenticationResponseModel> call, Throwable t) {
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
        String password = mPasswordView.getText().toString();
        return new AuthenticationRequestModel(email, password);
    }

    private void handleSuccessAuth(Response<AuthenticationResponseModel> response) {
        AuthenticationResponseModel userAuthEntity = response.body();
        application.setTOKEN("Bearer " + userAuthEntity.getToken());
        mEmailView.setError(null);
        mPasswordView.setError(null);

        getCache(userAuthEntity);
    }

    private void handleFailedAuth() {
        Snackbar.make(getWindow().getDecorView().getRootView(), "AUTH FAILED", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
        mEmailView.setError("Invalid value");
        mPasswordView.setError("Invalid value");
        showProgress(false);
    }

    public void goToMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void getCache(AuthenticationResponseModel userAuthEntity) {
        RetrofitService retrofitService = application.getRetrofitService();
        UserService userService = retrofitService.getRetrofit().create(UserService.class);
        userService.getCache(application.getTOKEN(), userAuthEntity.getId()).enqueue(new Callback<CacheModel>() {
            @Override
            public void onResponse(Call<CacheModel> call, Response<CacheModel> response) {
                showProgress(false);
                if (response.isSuccessful()) {
                    CacheModel cacheModel = response.body();

                    application.setUser(cacheModel.getUser());
                    application.setAnimals(cacheModel.getAnimals());
                    application.setCountries(cacheModel.getCountries());
                    application.setBreeds(cacheModel.getBreeds());
                    application.setStatistic(cacheModel.getStatistic());
                    application.setDiseases(cacheModel.getDiseases());
                    application.setGrafts(cacheModel.getGrafts());

                    goToMainActivity();

                } else {
                    Snackbar.make(getWindow().getDecorView().getRootView(), "Cannot load user's data, try later", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }

            @Override
            public void onFailure(Call<CacheModel> call, Throwable throwable) {
                showProgress(false);
                Snackbar.make(getWindow().getDecorView().getRootView(), "Cannot load user's data, try later", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

}


