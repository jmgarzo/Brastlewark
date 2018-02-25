package com.jmgarzo.brastlewark.View;

import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
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
 * Created by jmgarzo on 25/02/18.
 */

public class InhabitantAdapter extends RecyclerView.Adapter<InhabitantAdapter.InhabitantAdapterViewHolder> {

    private Context mContext;


    private final InhabitantAdapterOnClickHandler mClickHandler;


    public interface InhabitantAdapterOnClickHandler {

        void onClick(Inhabitant inhabitant);
    }

    private Cursor mCursor;

    public InhabitantAdapter (@NonNull Context context, InhabitantAdapterOnClickHandler clickHandler) {
        mContext = context;
        mClickHandler = clickHandler;

    }

    @Override
    public InhabitantAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.inhabitant_list_item, parent, false);
        view.setFocusable(true);
        return new InhabitantAdapterViewHolder(view);      }

    @Override
    public void onBindViewHolder(InhabitantAdapterViewHolder holder, int position) {
        mCursor.moveToPosition(position);
        String thumbnailPath = mCursor.getString(DbUtils.COL_INHABITANT_THUMBNAIL);

//        Picasso.with(mContext)
//                .load(thumbnailPath)
//                .placeholder(R.drawable.placeholder)
//                .error(R.drawable.ic_broken_image_black_48px)
//                .tag(mContext)
//                .into(holder.mImage);

        Glide.with(mContext).load(thumbnailPath).
                apply(RequestOptions.circleCropTransform()).into(holder.mImage);

        String name = mCursor.getString(DbUtils.COL_INHABITANT_PROFESSION_INHABITANT_NAME);
        String professions = mCursor.getString(DbUtils.COL_INHABITANT_PROFESSION_PROFESSION_NAMES);

        holder.mName.setText(name);
        holder.mPropfessions.setText(professions);

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


    public class InhabitantAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.inhabitant_image)
        ImageView mImage;
        @BindView(R.id.inhabitant_name)
        TextView mName;
        @BindView(R.id.inhabitant_professions)
        TextView mPropfessions;

        public InhabitantAdapterViewHolder(View view) {
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
