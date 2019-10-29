package com.casper.testdrivendevelopment.data;

import android.content.Context;

import com.casper.testdrivendevelopment.data.model.Book;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
//序列化读写
public class BookSaver implements Serializable {
    public BookSaver(Context context) {  //生成构造函数
        this.context = context;
    }

    Context context;   //用于读写内部文件

    public ArrayList<Book> getBooks() {  //生成数据的Getter
        return books;
    }

    ArrayList<Book> books=new ArrayList<Book>();  //用于保存数据  //数据初始化一下

    public void save(){
        try{
            //序列化
            ObjectOutputStream outputStream = new ObjectOutputStream(context.openFileOutput("Serializable.txt",Context.MODE_PRIVATE));
            outputStream.writeObject(books);
            outputStream.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public ArrayList<Book> load(){  //返回读入的数据
        try{
            //反序列化
            ObjectInputStream inputStream = new ObjectInputStream(context.openFileInput("Serializable.txt"));
            books = (ArrayList<Book>) inputStream.readObject();
            inputStream.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        return books; //返回读入的数据
    }
}
