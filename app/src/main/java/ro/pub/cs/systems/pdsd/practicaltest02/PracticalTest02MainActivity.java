package ro.pub.cs.systems.pdsd.practicaltest02;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class PracticalTest02MainActivity extends AppCompatActivity {

    private EditText serverPortEditText = null;

    private EditText clientAddressEditText = null;
    private EditText clientPortEditText = null;
    private EditText cityEditText = null;
    private TextView definitionTextView = null;

    private ServerThread serverThread = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test02_main);
        serverPortEditText = findViewById(R.id.server_port_edit_text);
        Button connectButton = findViewById(R.id.connect_button);
        connectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String serverPort = serverPortEditText.getText().toString();
                if (serverPort.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "[MAIN ACTIVITY] Server port should be filled!", Toast.LENGTH_SHORT).show();
                    return;
                }
                serverThread = new ServerThread(Integer.parseInt(serverPort));
                if (serverThread.getServerSocket() == null) {
                    Log.e(Constants.TAG, "[MAIN ACTIVITY] Could not create server thread!");
                    return;
                }
                serverThread.start();
            }
        });

        clientAddressEditText = findViewById(R.id.client_address_edit_text);
        clientPortEditText = findViewById(R.id.client_port_edit_text);
        cityEditText = findViewById(R.id.city_edit_text);
        Button getWeatherForecastButton = findViewById(R.id.get_weather_forecast_button);
        getWeatherForecastButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String clientAddress = clientAddressEditText.getText().toString();
                String clientPort = clientPortEditText.getText().toString();
                if (clientAddress.isEmpty() || clientPort.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "[MAIN ACTIVITY] Client connection parameters should be filled!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (serverThread == null || !serverThread.isAlive()) {
                    Toast.makeText(getApplicationContext(), "[MAIN ACTIVITY] There is no server to connect to!", Toast.LENGTH_SHORT).show();
                    return;
                }
                String word = cityEditText.getText().toString();
                if (word.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "[MAIN ACTIVITY] Parameters from client (city / information type) should be filled", Toast.LENGTH_SHORT).show();
                    return;
                }

                definitionTextView.setText(Constants.EMPTY_STRING);

                ClientThread clientThread = new ClientThread(clientAddress, Integer.parseInt(clientPort), word, definitionTextView);
                clientThread.start();
            }
        });
        definitionTextView = findViewById(R.id.definition_text_view);
    }
}