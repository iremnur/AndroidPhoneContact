package com.irem.myphonecontactapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by irem on 01.04.2016.
 */
public class MyAdapter  extends ArrayAdapter<Person> {


    public MyAdapter(Context context, int resource, List<Person> contacts) {
        super(context, resource,contacts);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;
        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());//to retrieve a standard LayoutInflater instance
            v = vi.inflate(R.layout.layout2, null);//inflate the layout
        }
        //Get the data item associated with the specified position in the data set.
        Person myPerson = getItem(position);

        if (myPerson != null) {
            ImageView personImage = (ImageView)v.findViewById(R.id.imageView);
            TextView name = (TextView)v.findViewById(R.id.textView1);
            TextView numbers = (TextView)v.findViewById(R.id.textView2);

            if(personImage != null && name != null && numbers != null){
                name.setText(myPerson.getName());
                numbers.setText(myPerson.getNumbers());
                personImage.setImageResource(myPerson.getPictureResourceID());
            }
        }
        return v;
    }
}
