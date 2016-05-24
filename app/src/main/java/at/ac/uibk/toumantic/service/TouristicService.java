package at.ac.uibk.toumantic.service;

import java.util.List;

import at.ac.uibk.toumantic.model.Offer;
import retrofit.http.GET;
import retrofit.http.Path;
import rx.Observable;

/**
 * Created by david on 5/23/16.
 */
public interface TouristicService {
    String SERVICE_ENDPOINT = "https://api.github.com";

    @GET("/offers/{offerID}")
    Observable<Offer> getOffer(@Path("offerID") String id);

    @GET("/offers")
    Observable<List<Offer>> getOffers();
}
