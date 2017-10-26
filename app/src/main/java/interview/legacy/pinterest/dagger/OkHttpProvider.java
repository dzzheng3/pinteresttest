package interview.legacy.pinterest.dagger;

import interview.legacy.pinterest.api.AuthInterceptor;
import okhttp3.OkHttpClient;


public class OkHttpProvider {
    private static OkHttpClient instance = null;

    public static  OkHttpClient getOkHttpInstance() {
        if (instance == null) {
            instance = new OkHttpClient.Builder()
                    .addInterceptor(new AuthInterceptor())
                    .build();
        }
        return instance;
    }
}
