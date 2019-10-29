package com.casper.testdrivendevelopment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.casper.testdrivendevelopment.data.BookFragmentAdapter;
import com.casper.testdrivendevelopment.data.BookListFragment;
import com.casper.testdrivendevelopment.data.BookSaver;
import com.casper.testdrivendevelopment.data.model.Book;

import java.util.ArrayList;
import java.util.List;

public class BookListMainActivity extends AppCompatActivity {

    public static final int CONTEXT_MENU_ITEM_NEW=1;
    public static final int CONTEXT_MENU_ITEM_UPDATE=CONTEXT_MENU_ITEM_NEW+1;
    public static final int CONTEXT_MENU_ITEM_DELETE=CONTEXT_MENU_ITEM_UPDATE+1;
    public static final int CONTEXT_MENU_ITEM_ABOUT=CONTEXT_MENU_ITEM_DELETE+1;
    public static final int REQUEST_CODE_NEW_BOOK = 901;
    public static final int REQUEST_CODE_UPDATE_BOOK = 902;
    private ArrayList<Book> listBooks;
    private BookSaver bookSaver;
    private BookAdapter adapter;

    @Override
    protected void onStop() {
        super.onStop();
        bookSaver.save();
    }

    /*@Override
    protected void onDestroy() {   //重载onDestroy函数
        super.onDestroy();
        bookSaver.save();  //在程序退出时调用save方法保存
    }*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list_main);

        bookSaver=new BookSaver(this);
        listBooks=bookSaver.load();
        if(listBooks.size()==0) {    //如果没数据，就初始化几条数据
            init();
        }
        adapter=new BookAdapter(BookListMainActivity.this,R.layout.list_view_item_book,listBooks);
        BookFragmentAdapter myPageAdapter = new BookFragmentAdapter(getSupportFragmentManager());

        ArrayList<Fragment> datas = new ArrayList<Fragment>();
        datas.add(new BookListFragment(adapter));
        datas.add(new WebFragment());
        datas.add(new MapFragment());
        myPageAdapter.setData(datas);

        ArrayList<String> titles = new ArrayList<String>();
        titles.add("图书");
        titles.add("新闻");
        titles.add("地图");
        myPageAdapter.setTitles(titles);

        //使ViewPager和TabLayout相关联
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tablayout);
        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
        viewPager.setAdapter(myPageAdapter);
        tabLayout.setupWithViewPager(viewPager);

    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        if(v==this.findViewById(R.id.list_view_books)){
            int itemPosition=((AdapterView.AdapterContextMenuInfo)menuInfo).position;
            menu.setHeaderTitle(listBooks.get(itemPosition).getTitle());
            menu.add(0,CONTEXT_MENU_ITEM_NEW,0,"新建");
            menu.add(0,CONTEXT_MENU_ITEM_UPDATE,0,"修改");
            menu.add(0,CONTEXT_MENU_ITEM_DELETE,0,"删除");
            menu.add(0,CONTEXT_MENU_ITEM_ABOUT,0,"关于...");
        }
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode){
            case REQUEST_CODE_NEW_BOOK: {
                if (resultCode == RESULT_OK) {
                    int insertPosition = data.getIntExtra("insert_position", 0);
                    String title = data.getStringExtra("title");
                    listBooks.add(insertPosition, new Book(title,R.drawable.book_3));
                    adapter.notifyDataSetChanged();

                    Toast.makeText(this, "新建成功", Toast.LENGTH_SHORT).show();
                }
                break;
            }
            case REQUEST_CODE_UPDATE_BOOK: {
                if (resultCode == RESULT_OK) {
                    int position = data.getIntExtra("insert_position", 0);
                    String title = data.getStringExtra("title");

                    Book book = listBooks.get(position);
                    book.setTitle(title);
                    adapter.notifyDataSetChanged();

                    Toast.makeText(this, "修改成功", Toast.LENGTH_SHORT).show();
                }
                break;
            }
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo menuInfo=(AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        switch(item.getItemId()){
            case CONTEXT_MENU_ITEM_NEW:{
                Intent intent = new Intent(BookListMainActivity.this,newBookActivity.class);
                intent.putExtra("title","人生的驿站");
                //intent.putExtra("price",0);
                intent.putExtra("insert_position",menuInfo.position);
                startActivityForResult(intent, REQUEST_CODE_NEW_BOOK);
                /*
                books.add(menuInfo.position, new Book("人生的驿站",R.drawable.book_3));
                adapter.notifyDataSetChanged();   //界面刷新
                Toast.makeText(this, "新建成功", Toast.LENGTH_SHORT).show();
                */
                break;
            }

            case CONTEXT_MENU_ITEM_UPDATE:{
                Book book=listBooks.get(menuInfo.position);

                Intent intent = new Intent(BookListMainActivity.this,newBookActivity.class);
                intent.putExtra("insert_position",menuInfo.position);
                intent.putExtra("title",book.getTitle());
                //intent.putExtra("price",book.getPrice());
                startActivityForResult(intent, REQUEST_CODE_UPDATE_BOOK);
                break;
            }

            case CONTEXT_MENU_ITEM_DELETE:{
                final int itemPosition=menuInfo.position;
                new android.app.AlertDialog.Builder(this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Notice")
                        .setMessage("Are you sure to delete it?")
                        .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                listBooks.remove(itemPosition);  //移走删除
                                adapter.notifyDataSetChanged();    //界面刷新
                                Toast.makeText(BookListMainActivity.this, "删除成功！", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        })
                        .create().show();
                break;
            }

            case CONTEXT_MENU_ITEM_ABOUT:{
                Toast.makeText(this,"更多详细信息",Toast.LENGTH_SHORT).show();
                break;
            }
        }
        return super.onContextItemSelected(item);
    }

    private void init() {
        //listBooks=new ArrayList<Book>();
        listBooks.add(new Book("软件项目管理案例教程（第四版）", R.drawable.book_2));
        listBooks.add(new Book("创新工程实践", R.drawable.book_no_name));
        listBooks.add(new Book("信息安全数学基础（第二版）", R.drawable.book_1));
    }

    public class BookAdapter extends ArrayAdapter<Book> {

        private int resourceId;

        public BookAdapter(Context context, int resource, List<Book> objects) {
            super(context, resource, objects);
            resourceId = resource;
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater bInflater=LayoutInflater.from(this.getContext());
            View item=bInflater.inflate(this.resourceId,null);

            ImageView img=(ImageView)item.findViewById(R.id.image_view_book_cover);
            TextView title=(TextView)item.findViewById(R.id.text_view_book_title);

            Book book_item=this.getItem(position);
            img.setImageResource(book_item.getCoverResourceId());
            title.setText(book_item.getTitle());

            /*Book book = getItem(position);//获取当前项的实例
            View view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
            ((ImageView) view.findViewById(R.id.image_view_book_cover)).setImageResource(book.getCoverResourceId());
            ((TextView) view.findViewById(R.id.text_view_book_title)).setText(book.getTitle());
            return view;*/

            return item;
        }
    }
}
