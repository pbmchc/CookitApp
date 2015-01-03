package com.example.piotrek.cookitapp;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


public class RecipeAdd extends ActionBarActivity {

    private static final int SELECTED_PICTURE =1;
    EditText tytul;
    EditText skladniki;
    EditText etapy;
    ImageView zdjecie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_add);
        tytul = (EditText) findViewById(R.id.tytulTXT);
        skladniki = (EditText) findViewById(R.id.skladnikTXT);
        etapy = (EditText) findViewById(R.id.etapTXT);
        zdjecie = (ImageView) findViewById(R.id.foto);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // DODAWANIE ZDJĘCIA Z GALERII
    public void SzukajZdjecia(View view)
    {
        Intent search = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(search, SELECTED_PICTURE);
    }
    @Override
    public void onActivityResult (int rCode, int result, Intent data)
    {
        super.onActivityResult(rCode, result, data);
        switch (rCode)
        {
            case SELECTED_PICTURE:
                if (result == RESULT_OK)
                {
                    Uri uri = data.getData();
                    String[] projection = {MediaStore.Images.Media.DATA};
                    Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(projection[0]);
                    String filePath = cursor.getString(columnIndex);
                    cursor.close();

                    Bitmap photo = BitmapFactory.decodeFile(filePath);
                    Drawable d = new BitmapDrawable(photo);
                    zdjecie.setBackground(d);
                }
        }
    }

    // DODWANIE PRZEPISU DO BD
    public void DodajPrzepis(View view)
    {
        Toast dodano = Toast.makeText(this, "Dodano przepis", Toast.LENGTH_LONG);
        dodano.show();

    }
}
