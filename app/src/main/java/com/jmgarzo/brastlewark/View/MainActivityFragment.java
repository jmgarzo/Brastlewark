package com.jmgarzo.brastlewark.View;


import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
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
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
    public static final String INHABITANT_INTENT_TAG = "inhabitant_tag";

    private static final int ID_INHABITANT_LOADER = 21;
    public static final String FILTER_TAG = "arg_filter_tag";


    private String searchViewText = "";



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
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main_activity_fragment, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);

        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = null;
        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
        }
        if (searchView != null) {

            searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
            searchView.setActivated(true);
            searchView.setQueryHint(getString(R.string.main_search_hint));
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    if(newText.contains("'")){
                        newText = newText.replace("'", "");
                    }
                    if(newText.contains("%")){
                        newText = newText.replace("%", "");
                    }
                    onQueryTextChanged(newText);
                    return false;
                }
            });

            if (!searchViewText.isEmpty()) {

                searchView.setIconified(false);
                searchView.setQuery(searchViewText, false);
                searchView.onActionViewExpanded();

            }
        }

        super.onCreateOptionsMenu(menu, inflater);
    }

    public boolean onQueryTextChanged(String newText) {

        searchViewText = newText;
        Bundle args = new Bundle();
        args.putString(FILTER_TAG, newText);
        getLoaderManager().restartLoader(ID_INHABITANT_LOADER, args, this);
        return true;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        switch (id) {
            case ID_INHABITANT_LOADER: {
                if(args!= null){
                    String filterArg = args.getString(FILTER_TAG);
                    String selection = " ( " +
                            BrastlewarkContract.InhabitantsEntry.TABLE_NAME + "."+ BrastlewarkContract.InhabitantsEntry.NAME
                            + "  LIKE '" + filterArg + "%' " +
                            " OR " + BrastlewarkContract.ProfessionsEntry.TABLE_NAME + "." + BrastlewarkContract.ProfessionsEntry.NAME
                            + " LIKE '" + filterArg + "%')";

                    return new CursorLoader(
                            getContext(),
                            BrastlewarkContract.InhabitantProfessionEntry.CONTENT_URI,
                            DbUtils.INHABITANT_PROFESSION_COLUMNS,
                            selection,
                            null,
                            null);
                }else {

                    return new CursorLoader(
                            getContext(),
                            BrastlewarkContract.InhabitantProfessionEntry.CONTENT_URI,
                            DbUtils.INHABITANT_PROFESSION_COLUMNS,
                            null,
                            null,
                            null);
                }
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

        Intent intent = new Intent(getContext(), DetailActivity.class);
        intent.putExtra(INHABITANT_INTENT_TAG, inhabitant);
        startActivity(intent);

    }
}
