package at.ac.uibk.toumantic;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.afollestad.materialdialogs.MaterialDialog;

import at.ac.uibk.toumantic.adapter.OfferAdapter;
import at.ac.uibk.toumantic.service.ServiceFactory;
import at.ac.uibk.toumantic.service.TouristicService;
import me.gujun.android.taggroup.TagGroup;
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
public class OfferListActivity extends AppCompatActivity {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;
    private OfferAdapter offerAdapter;
    private SwipeRefreshLayout srl;

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
        getMenuInflater().inflate(R.menu.menu_offer_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_filter) {
            new MaterialDialog.Builder(this)
                    .positiveText(R.string.agree)
                    .negativeText(R.string.cancel)
                    .show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        offerAdapter = new OfferAdapter((ImageView iv, TextView tv, TagGroup tg, String id) -> {
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
                Pair<View, String> tags = Pair.create(tg, OfferDetailActivity.TRANSITION_TAGS);
                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, image, tags);
                ActivityCompat.startActivity(this, intent, options.toBundle());
            }
        });
        recyclerView.setAdapter(offerAdapter);
    }
}
