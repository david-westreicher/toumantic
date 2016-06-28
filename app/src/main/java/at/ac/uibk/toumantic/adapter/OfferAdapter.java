package at.ac.uibk.toumantic.adapter;

import android.location.Location;
import android.support.v4.view.ViewCompat;
import android.support.v7.util.SortedList;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import at.ac.uibk.toumantic.OfferDetailActivity;
import at.ac.uibk.toumantic.R;
import at.ac.uibk.toumantic.Util;
import at.ac.uibk.toumantic.model.Offer;

/**
 * Created by david on 5/23/16.
 */
public class OfferAdapter
        extends RecyclerView.Adapter<OfferAdapter.ViewHolder> {

    private static final float NEARBY_THRESHOLD_METERS = 30000;
    private final SortedList<Offer> mValues;
    private final OnItemClickListener onItemClick;
    private Set<Offer.OfferType> filteredTypes = new HashSet<>();
    private Set<Offer> allOffers = new HashSet<>();
    private Location location;
    private Set<String> interests = new HashSet<>();

    public OfferAdapter(OnItemClickListener onClick) {
        onItemClick = onClick;
        mValues = new SortedList<>(Offer.class, new SortedList.Callback<Offer>() {

            @Override
            public int compare(Offer o1, Offer o2) {
                String name1 = o1.name.toLowerCase();
                String name2 = o2.name.toLowerCase();
                if (interests.contains(name1)) {
                    if (!interests.contains(name2)) {
                        return -1;
                    }
                } else {
                    if (interests.contains(name2)) {
                        return 1;
                    }
                }
                return o1.compareTo(o2);
            }

            @Override
            public void onInserted(int position, int count) {
                notifyItemRangeInserted(position, count);
            }

            @Override
            public void onRemoved(int position, int count) {
                notifyItemRangeRemoved(position, count);
            }

            @Override
            public void onMoved(int fromPosition, int toPosition) {
                notifyItemMoved(fromPosition, toPosition);
            }

            @Override
            public void onChanged(int position, int count) {
                notifyItemRangeChanged(position, count);
            }

            @Override
            public boolean areContentsTheSame(Offer oldItem, Offer newItem) {
                if (!oldItem.getName().equals(newItem.getName()))
                    return false;
                if (!oldItem.getImage().equals(newItem.getImage()))
                    return false;
                return true;
            }

            @Override
            public boolean areItemsTheSame(Offer item1, Offer item2) {
                return item1.equals(item2);
            }
        }

        );
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.offer_list_content, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Offer item = mValues.get(position);
        holder.item = item;
        if (interests.contains(item.name.toLowerCase()))
            holder.interestedIcon.setVisibility(View.VISIBLE);
        else
            holder.interestedIcon.setVisibility(View.GONE);
        switch (item.type) {
            case Offer:
                holder.mSpecialOffer.setVisibility(View.VISIBLE);
                break;
            case LodgingBusiness:
            case Event:
            case Restaurant:
            case TouristAttraction:
                holder.mSpecialOffer.setVisibility(View.GONE);
                break;
        }
        holder.mIcon.setImageResource(Util.IconForOfferType(item.type));
        holder.mIdView.setText(item.getName());
        holder.mView.setOnClickListener((View v) -> {
            ViewCompat.setTransitionName(holder.mImageView, OfferDetailActivity.TRANSITION_IMAGE);
            onItemClick.onClick(holder.mImageView, holder.mIdView, holder.item.getID());
        });
        Picasso
                .with(holder.mImageView.getContext())
                .load(item.getImage())
                .fit()
                .centerCrop()
                .into(holder.mImageView);
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public void addOffer(Offer offer) {
        allOffers.add(offer);
        if (!filteredTypes.contains(offer.type) && !islocfiltered(offer))
            mValues.add(offer);
    }

    public void filter(Integer[] which) {
        List<Integer> filtered = Arrays.asList(which);
        filteredTypes.clear();
        int i = 0;
        for (Offer.OfferType t : Offer.OfferType.values()) {
            if (!filtered.contains(i))
                filteredTypes.add(t);
            i++;
        }
        filtermValues();
    }

    private void filtermValues() {
        List<Offer> toRemove = new ArrayList<>();
        for (int i = 0; i < mValues.size(); i++) {
            Offer o = mValues.get(i);
            if (filteredTypes.contains(o.type) || islocfiltered(o))
                toRemove.add(o);
        }
        for (Offer o : toRemove)
            mValues.remove(o);

        for (Offer o : allOffers) {
            if (mValues.indexOf(o) < 0 && !filteredTypes.contains(o.type) && !islocfiltered(o)) {
                mValues.add(o);
            }
        }
    }

    private boolean islocfiltered(Offer o) {
        if (location == null)
            return false;
        Location tmploc = new Location(location);
        tmploc.setLatitude(o.geo.latitude);
        tmploc.setLongitude(o.geo.longitude);
        return location.distanceTo(tmploc) > NEARBY_THRESHOLD_METERS;
    }

    public Integer[] notfiltered() {
        List<Integer> notfiltered = new ArrayList<>();
        int i = 0;
        for (Offer.OfferType t : Offer.OfferType.values()) {
            if (!filteredTypes.contains(t))
                notfiltered.add(i);
            i++;
        }
        return notfiltered.toArray(new Integer[notfiltered.size()]);
    }

    public void setLocation(Location location) {
        this.location = location;
        filtermValues();
    }

    public void setInterests(List<String> ints, FoundInterestCb callback) {
        for (String s : ints)
            interests.add(s.toLowerCase());
        mValues.clear();
        filtermValues();
        for (int i = 0; i < mValues.size(); i++) {
            Offer o = mValues.get(i);
            if (interests.contains(o.name.toLowerCase()))
                callback.found(o);
        }
    }

    public void clearInterests() {
        interests.clear();
        mValues.clear();
        filtermValues();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        private final ImageView mImageView;
        private final ImageView mIcon;
        private final ImageView mSpecialOffer;
        private final ImageView interestedIcon;
        private Offer item;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.title);
            mIcon = (ImageView) view.findViewById(R.id.icon);
            mSpecialOffer = (ImageView) view.findViewById(R.id.specialoffer);
            mImageView = (ImageView) view.findViewById(R.id.image);
            interestedIcon = (ImageView) view.findViewById(R.id.icon2);
        }

    }

    public interface OnItemClickListener {
        void onClick(ImageView iv, TextView tv, String id);
    }

    public interface FoundInterestCb {
        void found(Offer o);
    }
}
