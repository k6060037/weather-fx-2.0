package sample;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ResourceBundle;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import org.json.JSONObject;

public class Controller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button getWeather;

    @FXML
    private TextField city;

    @FXML
    private TextField temperature;

    @FXML
    private TextField humidity;

    @FXML
    private TextField pressure;

    @FXML
    void initialize() {



        city.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                city.clear();
                temperature.clear();
                pressure.clear();
                humidity.clear();
            }
        });

        city.setOnAction(event -> {
            showResult();
        });

        getWeather.setOnAction(event -> {
            showResult();
        });


    }

    private static String getUrl(String urlAddress){
        StringBuffer content = new StringBuffer();
        try {
            URL url = new URL(urlAddress);
            URLConnection urlConnection = url.openConnection();

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String line;

            while((line = bufferedReader.readLine()) != null) {
                content.append(line + "\n");
            }
            bufferedReader.close();
        } catch (Exception e) {
            System.out.println("Город не найден");
            return "exception";

        }
        return content.toString();
    }
    private void showResult(){
        String getCity = city.getText();
        String output = getUrl("http://api.openweathermap.org/data/2.5/weather?q=" + getCity + "&units=metric&appid=25b892d8aa25887ea3e92b9aba5f27f0");
        if (output.equals("exception")) {
            city.setText("Такого города не существует");
        } else if (!output.isEmpty()) {
            JSONObject obj = new JSONObject(output);
            Double temperInfo = obj.getJSONObject("main").getDouble("temp");
            temperature.setText(temperInfo.toString() + " градусов Цельсия");
            Double pressureInfo = obj.getJSONObject("main").getDouble("pressure");
            pressure.setText(pressureInfo.toString() + " гПа");
            Double humidityInfo = obj.getJSONObject("main").getDouble("humidity");
            humidity.setText(humidityInfo.toString() + " %");
        }
    }


}

