package interview.legacy.pinterest.legacyinterview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.google.gson.Gson;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import interview.legacy.pinterest.api.PinService;
import interview.legacy.pinterest.model.DataList;
import interview.legacy.pinterest.model.Pin;
import retrofit2.adapter.rxjava.HttpException;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


// TODO make sharedTransition less of a hack
public class MainActivity extends AppCompatActivity {

    @Inject PinService _basicInterfaces;
    @Inject Gson _gson;

    @BindView(R.id.main_recycler_view) RecyclerView _recyclerView;

    Subscription _subscription;
    PinAdapter _pinAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((LegacyApplication) getApplication()).getActivityComponent().inject(this);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        _pinAdapter = new PinAdapter(MainActivity.this, _gson);
        _recyclerView.setAdapter(_pinAdapter);
        _recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        getPins();

    }

    private void getPins(){
        Observable<DataList<Pin>> call = _basicInterfaces.getPins("id,note,url,image,board");
        _subscription = call
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<DataList<Pin>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof HttpException) {
                            HttpException response = (HttpException)e;
                            int code = response.code();
                        }
                    }

                    @Override
                    public void onNext(DataList<Pin> pins) {
                        _pinAdapter.addPins(pins.getData());
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        _subscription.unsubscribe();
    }
}
