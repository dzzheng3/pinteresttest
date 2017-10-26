package interview.legacy.pinterest.dagger;

import javax.inject.Singleton;

import dagger.Component;
import interview.legacy.pinterest.legacyinterview.MainActivity;
import interview.legacy.pinterest.legacyinterview.PinDetailActivity;

@Singleton
@Component(modules={AppModule.class, NetModule.class, ApiModule.class})
public interface ActivityComponent {
    void inject(MainActivity activity);
    void inject(PinDetailActivity activity);
}
