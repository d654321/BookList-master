package com.casper.testdrivendevelopment.data;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.casper.testdrivendevelopment.BookListMainActivity;
import com.casper.testdrivendevelopment.R;

/**
 * A simple {@link Fragment} subclass.
 */

//最主要功能：由布局生成相应视图
@SuppressLint("ValidFragment")
public class BookListFragment extends Fragment {

    BookListMainActivity.BookAdapter adapter;
    public BookListFragment(BookListMainActivity.BookAdapter adapter) {
        this.adapter=adapter;
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_book_list,container,false);
        ListView listViewBooks=(ListView) view.findViewById(R.id.list_view_books);
        listViewBooks.setAdapter(adapter);
        this.registerForContextMenu(listViewBooks);
        // Inflate the layout for this fragment
        return view;
    }

}
