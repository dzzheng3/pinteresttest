package interview.legacy.pinterest.legacyinterview;


import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import interview.legacy.pinterest.model.Pin;

public class PinAdapter extends RecyclerView.Adapter<PinAdapter.ViewHolder> {

    private Activity _activity;
    private List<Pin> _pins;
    private Gson _gson;

    public PinAdapter(Activity activity, Gson gson) {
        _activity = activity;
        _pins = new ArrayList<>();
        _gson = gson;
    }

    public void addPins(List<Pin> pins){
        int rangeStart = _pins.size();
        int rangeEnd = rangeStart + pins.size();
        _pins.addAll(pins);
        notifyItemRangeInserted(rangeStart, rangeEnd);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(_activity);
        View contactView = inflater.inflate(R.layout.pin_item, parent, false);
        return new ViewHolder(contactView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Pin pin = _pins.get(position);


        Picasso.with(_activity)
                .load(pin.getImageWrapper().getOriginal().getUrl())
                .resize(250, 250)
        //        .resize(pin.getImageWrapper().getOriginal().getHeight(), pin.getImageWrapper().getOriginal().getWidth())
                .onlyScaleDown()
                .centerCrop()
                .into(holder._imageView);


        holder._pinBoard.setText(String.format(_activity.getString(R.string.board_name), pin.getBoard().getName()));
        if(pin.getNote().trim().isEmpty()) {
            holder._pinName.setVisibility(View.GONE);
        } else {
            holder._pinName.setVisibility(View.VISIBLE);
            holder._pinName.setText(String.format(_activity.getString(R.string.pin_note), pin.getNote()));
        }

        holder._imageView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                holder._imageView.setTransitionName(SharedTransitionHelper.getSharedTransitionName());

                Intent intent = new Intent(_activity, PinDetailActivity.class);
                intent.putExtra(PinDetailActivity.EXTRA_PIN_DETAIL_PIN, _gson.toJson(pin));
                ActivityOptionsCompat options = ActivityOptionsCompat.
                        makeSceneTransitionAnimation(_activity, holder._imageView,
                                SharedTransitionHelper.getSharedTransitionName());
                _activity.startActivity(intent, options.toBundle());

            }
        });
    }

    @Override
    public int getItemCount() {
        return _pins.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.pin_item_image) ImageView _imageView;
        @BindView(R.id.pin_board) TextView _pinBoard;
        @BindView(R.id.pin_name) TextView _pinName;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
