package com.gabrieldchartier.compendia.recycler_views;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.gabrieldchartier.compendia.FragmentInterface;
import com.gabrieldchartier.compendia.R;
import com.gabrieldchartier.compendia.models.Comic;
import com.gabrieldchartier.compendia.util.TempUtilClass;

import java.util.List;

public class ComicCoversAdapter extends RecyclerView.Adapter<ComicCoversAdapter.ViewHolder>
{
    // Constants
    private static final String TAG = "RecyclerViewAdapter";

    // Variables
    private List<Comic> mComics;
    private Context mContext;
    private FragmentInterface fragmentRelay;
    private NavController navController;
    private Bundle bundle;

    public ComicCoversAdapter(Context mContext, List<Comic> mComics)
    {
        this.mComics = mComics;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        Log.d(TAG, "onCreateViewHolder called");

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_list_item, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i)
    {
        Log.d(TAG, "OnBindViewHolder called");
        //TODO replace the glide load parameter with: mComics.get(viewHolder.getAdapterPosition()).getCover()
        Glide.with(mContext).asBitmap().load(TempUtilClass.getImage(mContext ,mComics.get(viewHolder.getAdapterPosition()).getCover())).into(viewHolder.image);
        bundle = new Bundle();
        bundle.putParcelable(mContext.getString(R.string.intent_comic), mComics.get(viewHolder.getAdapterPosition()));
        viewHolder.image.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_homeFragment_to_comicDetailFragment, bundle));
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView)
    {
        super.onAttachedToRecyclerView(recyclerView);
        fragmentRelay = (FragmentInterface) mContext;
    }

    @Override
    public int getItemCount()
    {
        return mComics.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        ImageView image;
        public ViewHolder(View itemView)
        {
            super(itemView);
            image = itemView.findViewById(R.id.comicListItemCover);

        }
    }
}
