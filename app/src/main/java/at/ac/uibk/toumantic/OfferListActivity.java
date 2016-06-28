package at.ac.uibk.toumantic;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.util.Pair;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.sromku.simple.fb.Permission;
import com.sromku.simple.fb.SimpleFacebook;
import com.sromku.simple.fb.entities.Page;
import com.sromku.simple.fb.listeners.OnLoginListener;
import com.sromku.simple.fb.listeners.OnPagesListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import at.ac.uibk.toumantic.adapter.OfferAdapter;
import at.ac.uibk.toumantic.model.Offer;
import at.ac.uibk.toumantic.service.ServiceFactory;
import at.ac.uibk.toumantic.service.TouristicService;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * An activity representing a list of Offers. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link OfferDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class OfferListActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private static final int LOCATION_PERMSSION = 113;
    private static final String USE_LOCATION_PREF = "LOC_PREF";
    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;
    private OfferAdapter offerAdapter;
    private SwipeRefreshLayout srl;
    private GoogleApiClient mGoogleApiClient;
    private SimpleFacebook mSimpleFacebook;
    private Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        View recyclerView = findViewById(R.id.offer_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);


        srl = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        srl.setOnRefreshListener(() -> fetchOffers());
        srl.post(() -> {
            srl.setRefreshing(true);
            fetchOffers();
        });

        if (findViewById(R.id.offer_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }

        // GOOGLE API
        // Create an instance of GoogleAPIClient.
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
    }

    private void fetchOffers() {
        TouristicService service = ServiceFactory.createRetrofitService(TouristicService.class, TouristicService.SERVICE_ENDPOINT);
        service.getOffers()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(offers -> Observable.from(offers))
                .subscribe(offer -> offerAdapter.addOffer(offer),
                        error -> srl.setRefreshing(false),
                        () -> srl.setRefreshing(false));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        getMenuInflater().inflate(R.menu.menu_offer_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_location) {
            new MaterialDialog.Builder(this)
                    .title("Filter by location")
                    .icon(ContextCompat.getDrawable(getBaseContext(), R.drawable.ic_my_location_black_24dp))
                    .positiveText("NEARBY")
                    .negativeText("ALL")
                    .onPositive((dialog, which) -> turnonGPS())
                    .onNegative((dialog, which) -> turnoffGPS())
                    .show();
        }
        if (id == R.id.menu_filter) {
            String[] offertypenames = new String[Offer.OfferType.values().length];
            int i = 0;
            for (Offer.OfferType t : Offer.OfferType.values())
                offertypenames[i++] = t.name;
            Log.d("debug", Arrays.toString(offertypenames));
            Log.d("debug", Arrays.toString(offerAdapter.notfiltered()));
            new MaterialDialog.Builder(this)
                    .title("Filter")
                    .icon(ContextCompat.getDrawable(getBaseContext(), R.drawable.ic_tune_black_24dp))
                    .items(offertypenames)
                    .positiveText("SEARCH")
                    .negativeText("CANCEL")
                    .itemsCallbackMultiChoice(offerAdapter.notfiltered(), (dialog, which, text) -> {
                        Log.d("debug", text + Arrays.toString(which));
                        offerAdapter.filter(which);
                        return true;
                    })
                    .show();
            return true;
        }
        if (id == R.id.menu_fb) {
            if (mSimpleFacebook.isLogin())
                item.setIcon(ContextCompat.getDrawable(getBaseContext(), R.drawable.fb_black));
            else
                item.setIcon(ContextCompat.getDrawable(getBaseContext(), R.drawable.fb_white));
            facebooktoggle();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void turnoffGPS() {
        offerAdapter.setLocation(null);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(USE_LOCATION_PREF, false);
        editor.apply();
    }

    private void turnonGPS() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(USE_LOCATION_PREF, true);
        editor.apply();
        fetchLocation();
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        offerAdapter = new OfferAdapter((ImageView iv, TextView tv, String id) -> {
            if (mTwoPane) {
                Bundle arguments = new Bundle();
                arguments.putString(OfferDetailActivity.ARG_ITEM_ID, id);
                OfferDetailFragment fragment = new OfferDetailFragment();
                fragment.setArguments(arguments);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.offer_detail_container, fragment)
                        .commit();
            } else {
                Context context = OfferListActivity.this;
                Intent intent = new Intent(context, OfferDetailActivity.class);
                intent.putExtra(OfferDetailActivity.ARG_ITEM_ID, id);
                Pair<View, String> image = Pair.create(iv, OfferDetailActivity.TRANSITION_IMAGE);
                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, image);
                ActivityCompat.startActivity(this, intent, options.toBundle());
            }
        });
        recyclerView.setAdapter(offerAdapter);
    }

    @Override
    public void onConnected(Bundle bundle) {
        Log.d("debug", "services connected");
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        if (preferences.getBoolean(USE_LOCATION_PREF, false))
            fetchLocation();
    }

    private void fetchLocation() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.d("debug", "checkPermission");
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_COARSE_LOCATION)) {
            } else
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, LOCATION_PERMSSION);
            return;
        }
        Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (mLastLocation == null)
            Log.d("debug", "null location");
        else
            offerAdapter.setLocation(mLastLocation);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case LOCATION_PERMSSION: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d("debug", "permission granted");
                    fetchLocation();
                } else {
                    Log.d("debug", "permission denied");
                }
                return;
            }
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.d("debug", "services connection suspended");
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.d("debug", "services connection failed");
    }

    protected void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }

    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSimpleFacebook = SimpleFacebook.getInstance(this);
    }

    private void facebooktoggle() {
        if (mSimpleFacebook.isLogin()) {
            mSimpleFacebook.logout(() -> {
                offerAdapter.clearInterests();
            });
        } else
            mSimpleFacebook.login(new OnLoginListener() {
                @Override
                public void onLogin(String accessToken, List<Permission> acceptedPermissions, List<Permission> declinedPermissions) {
                    Log.d("toumantic", "onlogin");
                    mSimpleFacebook.getMusic(new OnPagesListener() {
                        @Override
                        public void onComplete(List<Page> music) {
                            Log.d("toumantic getmusic", Integer.toString(music.size()));
                            List<String> interests = new ArrayList<>();
                            for (Page p : music)
                                interests.add(p.getName());
                            offerAdapter.setInterests(interests, (Offer o) -> {
                                Snackbar.make(srl, "Found an artist you like: " + o.name, Snackbar.LENGTH_LONG).show();
                            });
                        }
                    });
                }

                @Override
                public void onCancel() {
                    Log.d("toumantic", "oncancel");
                }

                @Override
                public void onException(Throwable throwable) {
                    Log.d("toumantic", "onexception");
                }

                @Override
                public void onFail(String reason) {
                    Log.d("toumantic", "onfail");
                    Log.d("toumantic", reason);
                }
            });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        mSimpleFacebook.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

}
