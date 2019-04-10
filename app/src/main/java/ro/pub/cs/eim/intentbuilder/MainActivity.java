package ro.pub.cs.eim.intentbuilder;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.FileUriExposedException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button goButton = (Button)findViewById(R.id.button_go);
        final Spinner actionSpinner = (Spinner)findViewById(R.id.spinner_action);
        final EditText datauri_et = (EditText)findViewById(R.id.edittext_datauri);
        EditText class_et = (EditText)findViewById(R.id.edittext_class);
        EditText package_et = (EditText)findViewById(R.id.edittext_package);
        EditText extrakey_et = (EditText)findViewById(R.id.edittext_extrakey);
        EditText extravalue_et = (EditText)findViewById(R.id.edittext_extravalue);

        goButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(actionSpinner.getSelectedItem().toString());
                intent.setData(Uri.parse(datauri_et.getText().toString()));
                try {
                    startActivity(intent);
                } catch (ActivityNotFoundException e){
                    Log.d(Constants.TAG, "App not found");
                    Toast.makeText(view.getContext(), "App not found to handle uri:\n" + datauri_et.getText().toString(),
                                    Toast.LENGTH_LONG).show();
                } catch (FileUriExposedException e){
                    Toast.makeText(view.getContext(), "File uri exposed:\n" + datauri_et.getText().toString(),
                            Toast.LENGTH_LONG).show();
                }
            }

        });

        Intent intent = getIntent();
        if (intent != null) {
            String action = intent.getAction();
            Uri data = intent.getData();
            Bundle extras = intent.getExtras();
            if(data != null)
                Log.d(Constants.TAG,"Received uri data: " + data.toString());
            if(extras != null) {
                for (String key : extras.keySet()) {
                    Object obj = extras.get(key);
                    if(key.equals("android.intent.extra.TEXT")){
                        datauri_et.setText(obj.toString());
                    } else {
                        extrakey_et.setText(extrakey_et.getText().toString() + "," + key);
                        extravalue_et.setText(extravalue_et.getText().toString() + "," + obj.toString());
                    }
                    Log.d(Constants.TAG, "Received bundle key: " + key + " value: " + obj.toString());
                }
            }
        }

    }
}
