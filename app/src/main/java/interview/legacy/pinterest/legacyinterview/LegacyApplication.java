package interview.legacy.pinterest.legacyinterview;


import android.app.Application;

import interview.legacy.pinterest.dagger.ActivityComponent;
import interview.legacy.pinterest.dagger.ApiModule;
import interview.legacy.pinterest.dagger.AppModule;
import interview.legacy.pinterest.dagger.DaggerActivityComponent;
import interview.legacy.pinterest.dagger.NetModule;

public class LegacyApplication extends Application {
    private static final String BASE_URL = "https://api.pinterest.com/v1/";
    private ActivityComponent _activityComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        _activityComponent = createComponent();

    }

    protected ActivityComponent createComponent() {
        return DaggerActivityComponent.builder()
                .appModule(new AppModule(this))
                .netModule(new NetModule(BASE_URL))
                .apiModule(new ApiModule())
                .build();
    }

    public ActivityComponent getActivityComponent() {
        return _activityComponent;
    }
}
