package com.isracorporations.recyclerview;

import android.view.View;

public interface RecyclerViewClickInterface {
    void onItemClick ( View view,int position);
    void onLongItemClick(int position);

}
