package at.ac.uibk.toumantic;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.NavUtils;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import at.ac.uibk.toumantic.model.Offer;
import at.ac.uibk.toumantic.service.ServiceFactory;
import at.ac.uibk.toumantic.service.TouristicService;
import me.gujun.android.taggroup.TagGroup;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * An activity representing a single Offer detail screen. This
 * activity is only used narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link OfferListActivity}.
 */
public class OfferDetailActivity extends AppCompatActivity {

    public static final String ARG_ITEM_ID = "item_id";
    public static final String TRANSITION_IMAGE = "transition_image";
    public static final String TRANSITION_TAGS = "transition_tags";
    private CollapsingToolbarLayout appBarLayout;
    private ImageView imageView;
    private FloatingActionButton fab;
    private ShareActionProvider shareProvider;
    private Offer offer;
    private TextView teaserText;
    private TagGroup tagGroup;
    private TextView priceText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);

        offer = null;
        fab = (FloatingActionButton) findViewById(R.id.fab);

        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        initGUI();
        String itemID = getIntent().getStringExtra(ARG_ITEM_ID);
        fetchItem(itemID);
    }

    private void initGUI() {
        appBarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        imageView = (ImageView) findViewById(R.id.image);
        teaserText = (TextView) findViewById(R.id.teaser);
        tagGroup = (TagGroup) findViewById(R.id.tag_group);
        priceText = (TextView) findViewById(R.id.price);
        ViewCompat.setTransitionName(imageView, TRANSITION_IMAGE);
        ViewCompat.setTransitionName(tagGroup, TRANSITION_TAGS);
    }


    private void fetchItem(String id) {
        TouristicService service = ServiceFactory.createRetrofitService(TouristicService.class, TouristicService.SERVICE_ENDPOINT);
        service.getOffer(id)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(offer -> setViewTo(offer),
                        error -> Log.d("error", error.toString()),
                        () -> onCompletion());
    }

    private void setViewTo(Offer offer) {
        this.offer = offer;
        appBarLayout.setTitle(offer.getName());
        Log.d("", imageView.toString() + "," + offer.getImage());
        Picasso
                .with(imageView.getContext())
                .load(offer.getImage())
                .fit()
                .centerCrop()
                .into(imageView);
        fab.setOnClickListener((View v) -> {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(offer.getAction()));
            startActivity(browserIntent);
        });
        teaserText.setText(offer.getTeaser());
        if (shareProvider != null)
            shareProvider.setShareIntent(getOfferShareIntent(offer));
        tagGroup.setTags(offer.getItems());
        priceText.setText(String.format("%.2f", offer.getPrice()));
    }

    private Intent getOfferShareIntent(Offer offer) {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("*/*");
        if (offer == null) {
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "bla");
            shareIntent.putExtra(Intent.EXTRA_TEXT, "bla");
            shareIntent.putExtra(Intent.EXTRA_STREAM, "https://none.com/test.jpg");
        } else {
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, offer.getName());
            shareIntent.putExtra(Intent.EXTRA_TEXT, offer.getTeaser());
        }
        return shareIntent;
    }

    private void onCompletion() {
        Log.d("oncompletion", "oncompletion");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_offer, menu);
        shareProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(menu.findItem(R.id.menu_share));
        shareProvider.setShareIntent(getOfferShareIntent(offer));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown. Use NavUtils to allow users
            // to navigate up one level in the application structure. For
            // more details, see the Navigation pattern on Android Design:
            //
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
            //
            NavUtils.navigateUpTo(this, new Intent(this, OfferListActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
