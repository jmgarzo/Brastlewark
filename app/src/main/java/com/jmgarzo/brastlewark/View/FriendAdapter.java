package com.jmgarzo.brastlewark.View;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.jmgarzo.brastlewark.R;
import com.jmgarzo.brastlewark.Utilities.DbUtils;
import com.jmgarzo.brastlewark.model.Inhabitant;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by jmgarzo on 2/26/2018.
 */

public class FriendAdapter extends RecyclerView.Adapter<FriendAdapter.FriendAdapterViewHolder>
{

    private Context mContext;


    private final FriendAdapter.FriendAdapterOnClickHandler mClickHandler;


    public interface FriendAdapterOnClickHandler {

        void onClick(Inhabitant inhabitant);
    }

    private Cursor mCursor;

    public FriendAdapter (@NonNull Context context, FriendAdapter.FriendAdapterOnClickHandler clickHandler) {
        mContext = context;
        mClickHandler = clickHandler;

    }

    @Override
    public FriendAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.friend_list_item, parent, false);
        view.setFocusable(true);
        return new FriendAdapterViewHolder(view);      }

    @Override
    public void onBindViewHolder(FriendAdapterViewHolder holder, int position) {
        mCursor.moveToPosition(position);
        String thumbnailPath = mCursor.getString(DbUtils.COL_FRIEND_THUMBNAIL);

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.placeholder);
        requestOptions.error(R.drawable.ic_broken_image_black_48px);
        Glide.with(mContext).setDefaultRequestOptions(requestOptions).
                load(thumbnailPath).
                apply(RequestOptions.circleCropTransform()).
                into(holder.mImage);

        String name = mCursor.getString(DbUtils.COL_FRIEND_NAME);
        String professionsResult = mCursor.getString(DbUtils.COL_FRIEND_PROFESSION_NAMES);
        if(professionsResult!= null && !professionsResult.isEmpty()) {
            String professions = professionsResult.replace(",", ", ");
            holder.mPropfessions.setText(professions);
        }

        holder.mName.setText(name);

    }

    @Override
    public int getItemCount() {
        if (null == mCursor) return 0;
        return mCursor.getCount();
    }

    void swapCursor(Cursor newCursor) {
        mCursor = newCursor;
        notifyDataSetChanged();
    }
    public class FriendAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.friend_image)
        ImageView mImage;
        @BindView(R.id.friend_name)
        TextView mName;
        @BindView(R.id.friend_professions)
        TextView mPropfessions;

        public FriendAdapterViewHolder(View view) {
            super(view);
            ButterKnife.bind(this,view);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            Inhabitant inhabitant = new Inhabitant(mCursor, adapterPosition);
            mClickHandler.onClick(inhabitant);
        }
    }
}
