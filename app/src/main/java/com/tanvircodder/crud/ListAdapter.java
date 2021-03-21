package com.tanvircodder.crud;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tanvircodder.crud.database.CURDContract;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ListViewHolder> {
    private static final String LOG_TAG = ListAdapter.class.getSimpleName();
//    nwo to hold the data base data we are going to declare the cursor..//
    private Cursor mCursor;
    private Context mContext;

    private   clikeMyListener myCallbacks;
    public interface clikeMyListener{
        void onClick(int position);
    };


    public ListAdapter(Context context,clikeMyListener onClickListener){
        mContext = context;
        myCallbacks = onClickListener;
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.list_item_view,parent,false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {
        // : 2/25/2021 I also need to b bind the view with the proper statement..// and column
        int columnIndex = mCursor.getColumnIndex(CURDContract.ListEntry.BOOK_NAME);
        mCursor.moveToPosition(position);
        String bookName = mCursor.getString(columnIndex);
        int id = mCursor.getInt(mCursor.getColumnIndex(CURDContract.ListEntry._ID));
        holder.mTextView.setText(bookName);
        holder.itemView.setTag(id);
        Log.e(LOG_TAG,"The id is s:" + id);


    }

    @Override
    public int getItemCount() {
        if (mCursor == null){
            return 0;
        }
        return mCursor.getCount();
    }

    public Cursor swapCursor(Cursor data) {
        // check if this cursor is the same as the previous cursor (mCursor)
        if (mCursor == data) {
            return null; // bc nothing has changed
        }
        Cursor temp = mCursor;
        this.mCursor = data; // new cursor value assigned

        //check if this is a valid cursor, then update the cursor
        if (data != null) {
            this.notifyDataSetChanged();
        }
        return temp;
    }

    /*nwo i am going to create a class
    * that will extends from the recyclerview adapter classs*/
    public class ListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView mTextView;
        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            mTextView = (TextView) itemView.findViewById(R.id.list_text_view);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            myCallbacks.onClick(position);
        }
    }
}
