package com.jmgarzo.brastlewark.View;


import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jmgarzo.brastlewark.R;
import com.jmgarzo.brastlewark.Utilities.DbUtils;
import com.jmgarzo.brastlewark.model.Inhabitant;
import com.jmgarzo.brastlewark.model.data.BrastlewarkContract;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainActivityFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>, InhabitantAdapter.InhabitantAdapterOnClickHandler {

    private static final String LOG_TAG = MainActivityFragment.class.getSimpleName();

    private static final int ID_INHABITANT_LOADER = 21;


    @BindView(R.id.inhabitant_recycler_view)
    RecyclerView mRecyclerView;

    InhabitantAdapter mInhabitantAdapter;


    public MainActivityFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View viewRoot = inflater.inflate(R.layout.fragment_main_activity, container, false);

        ButterKnife.bind(this, viewRoot);
        setHasOptionsMenu(true);

        LinearLayoutManager inhabitantLayoutManager =
                new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);

        mRecyclerView.setLayoutManager(inhabitantLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),
                DividerItemDecoration.VERTICAL));

        mInhabitantAdapter = new InhabitantAdapter(getContext(), this);
        mRecyclerView.setAdapter(mInhabitantAdapter);
        getActivity().getSupportLoaderManager().initLoader(ID_INHABITANT_LOADER, null, this);

        return viewRoot;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        switch (id) {
            case ID_INHABITANT_LOADER: {

//                buildInhabitantAndPropfessions
                return new CursorLoader(
                        getContext(),
                        BrastlewarkContract.InhabitantProfessionEntry.CONTENT_URI,
                        DbUtils.INHABITANT_PROFESSION_COLUMNS,
                        null,
                        null,
                        null);
            }
            default:
                throw new RuntimeException("Loader Not Implemented: " + id);
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mInhabitantAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mInhabitantAdapter.swapCursor(null);

    }

    @Override
    public void onClick(Inhabitant inhabitant) {

    }
}
