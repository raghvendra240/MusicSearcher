package com.example.musicsearcher;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class GroupedListRecyclerAdapter
        extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<ListItem> listWithHeaders;

    public GroupedListRecyclerAdapter(List<ListItem> lwh) {
        this.listWithHeaders = lwh;
    }

    @Override
    public int getItemViewType(int position) {
        return listWithHeaders.get(position).getType();
    }

    @Override
    public int getItemCount() {
        return listWithHeaders != null ? listWithHeaders.size() : 0;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                      int viewType) {

        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {

            case ListItem.TYPE_GENERAL:
                View v1 = inflater.inflate(R.layout.feed, parent,
                        false);
                viewHolder = new GeneralViewHolder(v1);
                break;

            case ListItem.TYPE_HEADER:
                View v2 = inflater.inflate(R.layout.header_layout, parent, false);
                viewHolder = new DateViewHolder(v2);
                break;
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder,
                                 int position) {

        switch (viewHolder.getItemViewType()) {

            case ListItem.TYPE_GENERAL:
                GeneralItem generalItem
                        = (GeneralItem) listWithHeaders.get(position);
                GeneralViewHolder generalViewHolder
                        = (GeneralViewHolder) viewHolder;

                generalViewHolder.tId.setText(generalItem.getGeneralItem().getTrackId().toString());
                generalViewHolder.tName.setText(generalItem.getGeneralItem().getTrackName());
                generalViewHolder.tracktimemillis.setText(generalItem.getGeneralItem().getTrackTimeMillis().toString());
                generalViewHolder.primaryGenreName.setText(generalItem.getGeneralItem().getPrimaryGenreName());
                generalViewHolder.artistName.setText(generalItem.getGeneralItem().getArtistName());
                generalViewHolder.artistId.setText(generalItem.getGeneralItem().getArtistId().toString());


                break;

            case ListItem.TYPE_HEADER:
                HeadItem dateItem
                        = (HeadItem) listWithHeaders.get(position);
                DateViewHolder dateViewHolder
                        = (DateViewHolder) viewHolder;
                dateViewHolder.txtTitle.setText(dateItem.getHeader());


                break;
        }
    }



// ViewHolder for Header
class DateViewHolder extends RecyclerView.ViewHolder {
    protected TextView txtTitle;

    public DateViewHolder(View v) {
        super(v);
        this.txtTitle = (TextView) v.findViewById(R.id.textHeader);

    }
}

// View holder for general row item
class GeneralViewHolder extends RecyclerView.ViewHolder {


    TextView tId,tName,tracktimemillis,primaryGenreName,artistName,artistId;

    public GeneralViewHolder(View v) {
        super(v);

        tId=itemView.findViewById(R.id.textTrackId);
        tName=itemView.findViewById(R.id.textTrackName);
        tracktimemillis=itemView.findViewById(R.id.textTrackTimeMillis);
        primaryGenreName=itemView.findViewById(R.id.textPrimaryGenre);
        artistName=itemView.findViewById(R.id.textArtistName);
        artistId=itemView.findViewById(R.id.textArtistId);

    }
}

}
