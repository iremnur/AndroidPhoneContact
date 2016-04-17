package com.irem.myphonecontactapp;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;


public class MainActivity extends ActionBarActivity {

    ListView list ;
    ArrayList<Person> contacts;
    ArrayList<Person> contacts2;//used for all contact when click all radiobutton
    ListAdapter adapter ;
    TextView textView1;
    TextView textView2;
    RadioButton all;
    String phoneNumber;
    String name;
    String name1;
    String number1;
    ArrayList<Person> selected;//used when filtering
    String fileName="recovery.txt";
    int click=0;
    boolean allButtonClicked=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        contacts =new ArrayList<Person>();
        contacts2 =new ArrayList<Person>();
        list = (ListView) findViewById(R.id.listView);
        textView2 =(TextView)findViewById(R.id.textView2);
        textView1 = (TextView)findViewById(R.id.textView1);
        all =(RadioButton) findViewById(R.id.radioButton4);
        //if 'all' radioButton not clicked
        if(allButtonClicked==false) {
            getContacts();
            showList(contacts);
        }else{
            returnMainScreen(all);
        }

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+ contacts.get(position).getNumbers()));
                startActivity(intent);
            }

        });

    }



    public void getContacts(){

        Uri CONTENT_URI = ContactsContract.Contacts.CONTENT_URI;
        String _ID = ContactsContract.Contacts._ID;
        String DISPLAY_NAME = ContactsContract.Contacts.DISPLAY_NAME;
        String HAS_PHONE_NUMBER = ContactsContract.Contacts.HAS_PHONE_NUMBER;
        Uri PhoneCONTENT_URI = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        String Phone_CONTACT_ID = ContactsContract.CommonDataKinds.Phone.CONTACT_ID;
        String NUMBER = ContactsContract.CommonDataKinds.Phone.NUMBER;
        ContentResolver contentResolver = getContentResolver();
        Cursor cursor = contentResolver.query(CONTENT_URI, null, null, null, null);

        if (cursor.getCount() > 0) {

            while (cursor.moveToNext()) {

                String contact_id = cursor.getString(cursor.getColumnIndex( _ID ));
                name=cursor.getString(cursor.getColumnIndex( DISPLAY_NAME ));
                phoneNumber = null;

                int hasPhoneNumber = Integer.parseInt(cursor.getString(cursor.getColumnIndex( HAS_PHONE_NUMBER )));

                if (hasPhoneNumber > 0) {

                Cursor phoneCursor = contentResolver.query(PhoneCONTENT_URI, null, Phone_CONTACT_ID + " = ?", new String[] { contact_id }, null);

                while (phoneCursor.moveToNext()) {

                    phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(NUMBER));
                    if(allButtonClicked==false){
                        contacts.add(new Person(name, phoneNumber ,R.drawable.contact));
                    }else{//when clicked 'all' button add new contact2 object
                        contacts2.add(new Person(name, phoneNumber, R.drawable.contact));
                    }

                }
                phoneCursor.close();

            }

        }
        }
        adapter = new MyAdapter(this,R.layout.layout2,contacts);
        if(list != null){
            list.setAdapter(adapter);

        }
    }
    //shows all contacts in the listview
    public void showList(ArrayList<Person>contacts) {
        Person person =null;
        for (int i=0; i<contacts.size();i++) {
            person = contacts.get(i);
        }

    }
    public void returnMainScreen(View view) {
        allButtonClicked=true;
        getContacts();
        for(int i=0; i<contacts.size();i++) {
            //Log.i("contact",contacts.get(i).getName());
        }

        showList(contacts);

}

    public void filter(View view) {

        Person person =null;
        String phonenumbers =null;

        selected=new ArrayList<Person>();


        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radioButton1:
                if (checked)

                    for (int i=0; i<contacts.size();i++) {
                        person = contacts.get(i);
                        phonenumbers = person.getNumbers();
                        String sub = phonenumbers.substring(1,3);

                        if(sub.contains("50")==true||sub.contains("55")==true ){
                            selected.add(new Person(person.getName(), phonenumbers ,R.drawable.contact));
                        }

                    }
                adapter = new MyAdapter(this,R.layout.layout2,selected);
                if(list != null){
                    list.setAdapter(adapter);
                }

                break;
            case R.id.radioButton2:
                if (checked)

                    for (int i=0; i<contacts.size();i++) {
                        person = contacts.get(i);
                        phonenumbers = person.getNumbers();
                        String sub = phonenumbers.substring(1,3);

                        if(sub.contains("53")==true ){
                            selected.add(new Person(person.getName(), phonenumbers ,R.drawable.contact));
                        }

                    }
                adapter = new MyAdapter(this,R.layout.layout2,selected);
                if(list != null){
                    list.setAdapter(adapter);

                }

                break;
            case R.id.radioButton3:
                if (checked)

                    for (int i=0; i<contacts.size();i++) {
                        person = contacts.get(i);
                        phonenumbers = person.getNumbers();
                        String sub = phonenumbers.substring(1,3);

                        if(sub.contains("54")==true ){
                            selected.add(new Person(person.getName(), phonenumbers ,R.drawable.contact));
                        }

                    }
                //now new adapter has selected items
                adapter = new MyAdapter(this,R.layout.layout2,selected);
                if(list != null){
                    list.setAdapter(adapter);

                }
                break;
        }
        adapter = new MyAdapter(this, R.layout.layout2,contacts);

    }



 //back up a basınca dosyaya yazacak
    public void backUp(View view) throws IOException {
        click=1;
        PrintStream fout = new PrintStream(openFileOutput(fileName,MODE_PRIVATE));
        for(int i=0; i<contacts.size(); i++) {
            fout.println(contacts.get(i).getName().toString()+" "+contacts.get(i).getNumbers().toString());
            //Log.i("control", contacts.get(i).getName().toString());

        }
        Toast.makeText(getApplicationContext(),"BackUp Done!", Toast.LENGTH_LONG).show();
        fout.close();

    }


    //recoverye basınca back-up ı alınan contactlar gelecek
    public void recovery(View view)throws IOException {
        //ilk kez basılıyorsa uyarı vermeli
        //önce backUp al diye
        Log.i("clickControl",String.valueOf(click));
        if(click==1){
            Toast.makeText( getApplicationContext(),"Please first take back up!", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText( getApplicationContext(),"Recovery Done!", Toast.LENGTH_LONG).show();
        }
        Scanner scan = new Scanner(openFileInput("recovery.txt"));
        Person person = new Person(null,null,R.drawable.contact);

        while(scan.hasNextLine()){
            String line=scan.nextLine();
            String [] split = line.split(" ");//do split according to space
            name1 = split[0];
            number1 = split[1];
        }


        //eğer dosyadan okuduğum contactta varsa person a set etme
        for(int i=0; i<contacts.size(); i++) {
            if(name1!=contacts.get(i).getName()&& number1!=contacts.get(i).getNumbers()){
                person.setName(name1);
                person.setNumbers(number1);
            }
        }
        contacts.add(person);
        adapter = new MyAdapter(this,R.layout.layout2,contacts);
        list.setAdapter(adapter);

    }

}
