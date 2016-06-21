package at.ac.uibk.toumantic;

import android.view.View;

import at.ac.uibk.toumantic.model.Offer;

/**
 * Created by david on 6/21/16.
 */
public class Util {
    public static int IconForOfferType(Offer.OfferType t) {
        switch (t) {
            case LodgingBusiness:
                return R.drawable.ic_hotel_black_24dp;
            case Event:
                return R.drawable.ic_local_play_black_24dp;
            case Offer:
                return R.drawable.ic_local_offer_black_24dp;
            case Restaurant:
                return R.drawable.ic_restaurant_menu_black_24dp;
            case TouristAttraction:
                return R.drawable.ic_local_see_black_24dp;
        }
        return -1;
    }
}
