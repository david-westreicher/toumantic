package at.ac.uibk.toumantic.adapter;

import android.support.v4.view.ViewCompat;
import android.support.v7.util.SortedList;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import at.ac.uibk.toumantic.OfferDetailActivity;
import at.ac.uibk.toumantic.R;
import at.ac.uibk.toumantic.model.Offer;
import me.gujun.android.taggroup.TagGroup;

/**
 * Created by david on 5/23/16.
 */
public class OfferAdapter
        extends RecyclerView.Adapter<OfferAdapter.ViewHolder> {

    private final SortedList<Offer> mValues;
    private final OnItemClickListener onItemClick;

    public OfferAdapter(OnItemClickListener onClick) {
        onItemClick = onClick;
        mValues = new SortedList<>(Offer.class, new SortedList.Callback<Offer>() {

            @Override
            public int compare(Offer o1, Offer o2) {
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
        });
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
        holder.mIdView.setText(item.getName());
        holder.mView.setOnClickListener((View v) -> onItemClick.onClick(holder.mImageView, holder.mIdView, holder.tagGroup, holder.item.getID()));
        holder.tagGroup.setTags(item.getItems());
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
        mValues.add(offer);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        private final ImageView mImageView;
        private final TagGroup tagGroup;
        private Offer item;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.title);
            mImageView = (ImageView) view.findViewById(R.id.image);
            tagGroup = (TagGroup) view.findViewById(R.id.tag_group);
            ViewCompat.setTransitionName(mImageView, OfferDetailActivity.TRANSITION_IMAGE);
            ViewCompat.setTransitionName(tagGroup, OfferDetailActivity.TRANSITION_TAGS);
        }

    }

    public interface OnItemClickListener {
        void onClick(ImageView iv, TextView tv, TagGroup tg, String id);
    }
}
