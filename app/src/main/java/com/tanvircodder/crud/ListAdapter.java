package com.tanvircodder.crud;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tanvircodder.crud.database.CURDContract;

import org.w3c.dom.Text;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ListViewHolder> {
//    nwo to hold the data base data we are going to declare the cursor..//
    private Cursor mCursor;
    private Context mContext;
    public ListAdapter(Context context){
        mContext = context;
    }
    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.list_item_view,parent,false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {
        // TODO: 2/25/2021 I also need to b bind the view with the proper statement..// and column

//        int columnIdIndex = mCursor.getColumnIndex(CURDContract.ListEntry._ID);
        int idIndex = mCursor.getColumnIndex(CURDContract.ListEntry._ID);
        int columnIndex = mCursor.getColumnIndex(CURDContract.ListEntry.BOOK_NAME);
        mCursor.moveToPosition(position);
// TODO: 3/17/2021 the problem is heir
        final int id = mCursor.getInt(idIndex);
        String bookName = mCursor.getString(columnIndex);
//        int id = mCursor.getInt(columnIdIndex);
//        holder.itemView.setTag(id);

        holder.itemView.setTag(id);
        holder.mTextView.setText(bookName);

    }

    @Override
    public int getItemCount() {
        if (null == mCursor){
            return 0;
        }
        return mCursor.getCount();
    }

    public void swapCursor(Cursor data) {
        mCursor = data;
        if (data != null){
            this.notifyDataSetChanged();
        }
    }

    /*nwo i am going to create a class
    * that will extends from the recyclerview adapter classs*/
    public class ListViewHolder extends RecyclerView.ViewHolder{
        private TextView mTextView;
        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            mTextView = (TextView) itemView.findViewById(R.id.list_text_view);
        }
    }
}
