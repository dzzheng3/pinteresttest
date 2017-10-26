package interview.legacy.pinterest.dagger;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import interview.legacy.pinterest.api.PinService;
import retrofit2.Retrofit;

@Module
public class ApiModule {
    @Provides
    @Singleton
    public PinService providesBasicInterface(Retrofit retrofit) {
        return retrofit.create(PinService.class);
    }
}
