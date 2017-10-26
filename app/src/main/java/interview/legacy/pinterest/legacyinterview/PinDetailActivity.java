package interview.legacy.pinterest.legacyinterview;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.widget.ImageView;

import com.google.gson.Gson;

import com.squareup.picasso.Picasso;

import java.util.List;

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

public class PinDetailActivity extends AppCompatActivity {

    @BindView(R.id.pin_detail_image) ImageView pinImage;
    @BindView(R.id.pin_detail_recycler_view) RecyclerView pinRecyclerView;

    @Inject Gson _gson;
    @Inject PinService _basicInterfaces;

    public static final String EXTRA_PIN_DETAIL_PIN = "EXTRA_PIN_DETAIL_PIN";

    private Pin _pin;
    Subscription _subscription;
    PinAdapter _pinAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((LegacyApplication) getApplication()).getActivityComponent().inject(this);
        setContentView(R.layout.activity_pin_detail);
        ButterKnife.bind(this);
        pinImage.setTransitionName(SharedTransitionHelper.getSharedTransitionName());


        SharedTransitionHelper.depth++;

        Intent intent = getIntent();
        if (intent.hasExtra(EXTRA_PIN_DETAIL_PIN)) {
            _pin = _gson.fromJson(intent.getStringExtra(EXTRA_PIN_DETAIL_PIN), Pin.class);
        }
        setTitle(_pin.getBoard().getName());

        Picasso.with(this)
                .load(_pin.getImageWrapper().getOriginal().getUrl())
                .resize(250, 250)
                .onlyScaleDown()
                .centerCrop()
                .into(pinImage);

        _pinAdapter = new PinAdapter(PinDetailActivity.this, _gson);
        pinRecyclerView.setAdapter(_pinAdapter);
        pinRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        getPins();
    }


    private void getPins(){
        Observable<DataList<Pin>> call = _basicInterfaces
                .getBoardPins(_pin.getBoard().getId(), "id,note,url,image,board");
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
                        List<Pin> pinsList = pins.getData();
                        for(int i = 0; i < pinsList.size(); i++) {
                            if(pinsList.get(i).getId().equals(_pin.getId())){
                                pinsList.remove(i);
                                break;
                            }
                        }
                        _pinAdapter.addPins(pinsList);
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        _subscription.unsubscribe();
    }
}
