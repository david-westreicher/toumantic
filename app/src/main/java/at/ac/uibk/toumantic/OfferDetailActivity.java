package at.ac.uibk.toumantic;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

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
    private static final int CALL_PERMSSION = 32;
    private CollapsingToolbarLayout appBarLayout;
    private ImageView imageView;
    private FloatingActionButton fab;
    private ShareActionProvider shareProvider;
    private Offer offer;
    private TextView teaserText;
    private TagGroup tagGroup;
    private Map<Integer, Detail> detailmap = new HashMap<>();
    private String telnum;
    private ImageView typeIcon;
    private TextView typeName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);

        offer = null;
        fab = (FloatingActionButton) findViewById(R.id.fab);

        initGUI();
        String itemID = getIntent().getStringExtra(ARG_ITEM_ID);
        fetchItem(itemID);
    }

    private void initGUI() {
        appBarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        imageView = (ImageView) findViewById(R.id.image);
        typeIcon = (ImageView) findViewById(R.id.typeicon);
        typeName = (TextView) findViewById(R.id.typename);
        teaserText = (TextView) findViewById(R.id.teaser);
        tagGroup = (TagGroup) findViewById(R.id.tag_group);
        int[][] details = new int[][]{
                new int[]{R.id.linearaddress, R.id.address},
                new int[]{R.id.linearopening, R.id.opening},
                new int[]{R.id.linearprice, R.id.price},
                new int[]{R.id.lineartelephone, R.id.telephone},
        };
        for (int[] ress : details)
            detailmap.put(ress[1], new Detail(ress[0], ress[1]));

        ViewCompat.setTransitionName(imageView, TRANSITION_IMAGE);
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
        typeIcon.setImageResource(Util.IconForOfferType(offer.type));
        typeName.setText(offer.type.name);
        teaserText.setText(offer.getDescription());
        if (shareProvider != null)
            shareProvider.setShareIntent(getOfferShareIntent(offer));

        if (offer.getItems().length == 0) {
            tagGroup.setVisibility(View.GONE);
            tagGroup.setTags(new String[]{});
        } else {
            tagGroup.setVisibility(View.VISIBLE);
            tagGroup.setTags(offer.getItems());
        }
        for (Detail d : detailmap.values())
            d.hide();
        switch (offer.type) {
            case Restaurant:
            case LodgingBusiness:
                //LocalBusiness
                detailmap.get(R.id.opening).setText(offer.openingHours);
                detailmap.get(R.id.price).setText(offer.priceRange);
            case TouristAttraction:
                // PLACE
                detailmap.get(R.id.address).setText(offer.address);
                detailmap.get(R.id.address).onClickMap(offer.address);
                detailmap.get(R.id.telephone).setText(offer.telephone);
                detailmap.get(R.id.telephone).onClickCall(offer.telephone);
                break;
            case Event:
                detailmap.get(R.id.address).setText(offer.location);
                detailmap.get(R.id.address).onClickMap(offer.location);
                detailmap.get(R.id.opening).setText(offer.startDate + ", " + offer.doorTime);
                //TODO performer?
                break;
            case Offer:
                detailmap.get(R.id.price).setText(String.format("%.2f", offer.price));
                break;
        }
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
            shareIntent.putExtra(Intent.EXTRA_TEXT, offer.getDescription());
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

    private class Detail {
        private final TextView text;
        private final LinearLayout view;

        public Detail(int linear, int text) {
            this.view = (LinearLayout) findViewById(linear);
            this.text = (TextView) findViewById(text);
        }

        public void hide() {
            view.setVisibility(View.GONE);
        }

        public void setText(String txt) {
            view.setVisibility(View.VISIBLE);
            text.setText(txt);
        }

        public void onClickMap(String location) {
            view.setOnClickListener((view) -> {
                Uri gmmIntentUri = Uri.parse("geo:0,0?q=" + location);
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
            });
        }

        public void onClickCall(String telephone) {
            view.setOnClickListener((view) -> callNumber(telephone));
        }
    }

    private void callNumber(String telephone) {
        this.telnum = telephone;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.CALL_PHONE)) {
            } else
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, CALL_PERMSSION);
            return;
        }
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.fromParts("tel", telnum, null));
        startActivity(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case CALL_PERMSSION: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d("debug", "permission granted");
                    callNumber(telnum);
                } else {
                    Log.d("debug", "permission denied");
                }
                return;
            }
        }
    }
}
