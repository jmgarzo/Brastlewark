package com.jmgarzo.brastlewark.View;


import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.jmgarzo.brastlewark.R;
import com.jmgarzo.brastlewark.model.Inhabitant;
import com.jmgarzo.brastlewark.model.data.BrastlewarkContract;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailActivityFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    Inhabitant mInhabitant;

    ArrayList<String> mProfessionsList;

    private static final int PROFESSION_LOADER = 12;
    private static final int FRIENDS_LOADER = 13;



    @BindView(R.id.detail_age_textview)
    TextView mAge;
    @BindView(R.id.detail_weight_textview)
    TextView mWeight;
    @BindView(R.id.detail_height_textview)
    TextView mHeight;
    @BindView(R.id.detail_hair_color_value)
    TextView mHairColor;
    @BindView(R.id.detail_imageView)
    ImageView mImageView;
    @BindView(R.id.detail_profession_values)
    TextView mProfessions;
    @BindView(R.id.detail_friend_recyclerview)
    RecyclerView mFriendRecyclerView;


    public DetailActivityFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View viewRoot = inflater.inflate(R.layout.fragment_detail_activity, container, false);

        ButterKnife.bind(this, viewRoot);


        Intent intent = getActivity().getIntent();
        if (null != intent) {
            mInhabitant = intent.getParcelableExtra(MainActivityFragment.INHABITANT_INTENT_TAG);
        }

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.placeholder);
        requestOptions.error(R.drawable.ic_broken_image_black_48px);
        Glide.with(getContext()).setDefaultRequestOptions(requestOptions).
                load(mInhabitant.getThumbnail()).
                apply(RequestOptions.circleCropTransform()).
                into(mImageView);

        String age = mInhabitant.getAge() + " " + getString(R.string.years_label);
        mAge.setText(age);
        String weight = mInhabitant.getWeight() + " " + getString(R.string.kg_label);
        mWeight.setText(weight);
        String height = mInhabitant.getHeight() + " " + getString(R.string.cm_label);
        mHeight.setText(height);
        mHairColor.setText(mInhabitant.getHair_color());

        getActivity().getSupportLoaderManager().initLoader(PROFESSION_LOADER, null, this);
        getActivity().getSupportLoaderManager().initLoader(FRIENDS_LOADER, null, this);



        return viewRoot;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        switch (id) {
            case PROFESSION_LOADER: {

                Uri uri = BrastlewarkContract.ProfessionsEntry.buildInhabitantAndProfessions(Integer.valueOf(mInhabitant.getId()).toString());
                return new CursorLoader(getActivity(),
                        uri,
                        new String[]{BrastlewarkContract.ProfessionsEntry.TABLE_NAME + "." + BrastlewarkContract.ProfessionsEntry.NAME},
                        null,
                        null,
                        null);
            }
            case FRIENDS_LOADER:{
                Uri uri = BrastlewarkContract.InhabitantFriendEntry.buildInhabitantAndProfessions(Integer.valueOf(mInhabitant.getId()).toString());

                return new CursorLoader((getActivity(),
                        BrastlewarkContract.ProfessionsEntry.TABLE_NAME.))
            }
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

        if (loader.getId() == PROFESSION_LOADER) {
            mProfessionsList = new ArrayList<>();
            String professions = "";
            if (data.moveToFirst()) {
                do {
                    int nameIndex = data.getColumnIndex(BrastlewarkContract.ProfessionsEntry.NAME);
                    String name = data.getString(nameIndex);
                    mProfessionsList.add(name);
                } while (data.moveToNext());

                for (int i = 0; i < mProfessionsList.size(); i++) {
                    if (i == mProfessionsList.size()-1) {
                        professions = professions.concat(mProfessionsList.get(i));
                    } else {
                        professions = professions.concat(mProfessionsList.get(i)).concat(", ");
                    }
                }
            }
            mProfessions.setText(professions);

        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
