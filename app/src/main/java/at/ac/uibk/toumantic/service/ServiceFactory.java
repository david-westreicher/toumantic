package at.ac.uibk.toumantic.service;

import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.Collections;

import retrofit.RestAdapter;
import retrofit.client.Client;
import retrofit.client.Request;
import retrofit.client.Response;
import retrofit.mime.TypedByteArray;

public class ServiceFactory {
    public static final Gson gson = new Gson();

    /**
     * Creates a retrofit service from an arbitrary class (clazz)
     *
     * @param clazz    Java interface of the retrofit service
     * @param endPoint REST endpoint url
     * @return retrofit service with defined endpoint
     */
    public static <T> T createRetrofitService(final Class<T> clazz, final String endPoint) {
        final RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(endPoint)
                .setClient(new MockClient())
                .build();
        T service = restAdapter.create(clazz);

        return service;
    }

    private static class MockClient implements Client {
        @Override
        public Response execute(Request request) throws IOException {
            String[] url = request.getUrl().split("/");
            String last = url[url.length - 1];
            StringBuilder responseString = new StringBuilder();
            if (last.equals("offers")) {
                responseString.append(gson.toJson(MockData.OFFERS));
            } else {
                responseString.append(gson.toJson(MockData.ID_MAPPING.get(last)));
            }
            Log.d(url[url.length - 1], request.getMethod());
            return new Response(request.getUrl(), 200, "nothing", Collections.EMPTY_LIST, new TypedByteArray("application/json", responseString.toString().getBytes()));
        }
    }
}
