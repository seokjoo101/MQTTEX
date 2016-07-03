package com.example.seokjoo.mqttex;
/*MQTT BROKER
Subscribe, publish 예제*/
import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class MainActivity extends Activity implements MqttCallback {


    private static final String TAG = "seok";

    Button bConnect;

    EditText subTopic;



    EditText topic_To;

    EditText send_message;

    Button bSend;



    MqttClient sampleClient;



    String broker;

    String clientId;



    MemoryPersistence persistence = new MemoryPersistence();



    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);



        bConnect= (Button) findViewById(R.id.connect);

        subTopic = (EditText) findViewById(R.id.subtopic);



        topic_To = (EditText) findViewById(R.id.topicTo);

        send_message =(EditText)  findViewById(R.id.sendingmessage);

        bSend=(Button)findViewById(R.id.send);





        //broker 와 connection set

        connectMQTT();



        bConnect.setOnClickListener(new View.OnClickListener() {

            //topic 으로 regit.(subscribe)

            public void onClick(View v) {

                subscribe_topic();

            }

        });



        bSend.setOnClickListener(new View.OnClickListener() {

            //topic, message 로 publish

            public void onClick(View v) {

                publish();

            }

        });



    }





    private void connectMQTT() {

        broker       = "tcp://61.38.158.169:1883";

        clientId     = getDeviceSerialNumber();




        Log.i(TAG,"클라이언트 아이디 " +clientId);



        try {



            sampleClient = new MqttClient(broker, clientId, persistence);

            MqttConnectOptions connOpts = new MqttConnectOptions();

            connOpts.setCleanSession(true);

            sampleClient.connect(connOpts);



            sampleClient.setCallback(this);


            /******** MqttClient에 MqttCallback을 Set ********/


            Log.i(TAG,"Mqtt Client Connected " + sampleClient.isConnected());





        } catch(MqttException me) {

            Log.i(TAG,"reason "+me.getReasonCode());

            Log.i(TAG,"msg "+me.getMessage());

            Log.i(TAG,"loc "+me.getLocalizedMessage());

            Log.i(TAG,"cause "+me.getCause());

            Log.i(TAG,"excep"+me);



            me.printStackTrace();

        }

    }



    private void subscribe_topic(){

        String subtopic = subTopic.getText().toString();

        int qos  = 2;



        try{

            //메세지 구독할 토픽 등록하는 부분

            sampleClient.subscribe(subtopic,qos);

            Log.i(TAG,"subscribed");





        }catch(MqttException me) {

            Log.i(TAG,"reason "+me.getReasonCode());

            Log.i(TAG,"msg "+me.getMessage());

            Log.i(TAG,"loc "+me.getLocalizedMessage());

            Log.i(TAG,"cause "+me.getCause());

            Log.i(TAG,"excep"+me);



            me.printStackTrace();

        }



    }



    private void publish(){

        String topicto = topic_To.getText().toString();

        String message = send_message.getText().toString();







        int qos = 2;





        try{

            Log.i(TAG,"Publishing message: "+ message);



            sampleClient.publish(topicto,message.getBytes(),qos,true);



            Log.i(TAG,"Message published");













        }catch(MqttException me) {

            Log.i(TAG,"reason "+me.getReasonCode());

            Log.i(TAG,"msg "+me.getMessage());

            Log.i(TAG,"loc "+me.getLocalizedMessage());

            Log.i(TAG,"cause "+me.getCause());

            Log.i(TAG,"excep"+me);



            me.printStackTrace();

        }



    }







    private void disconnectMQTT(){

        try {

            sampleClient.disconnect();

            Log.i(TAG, "Disconnected");



        }catch(MqttException me) {

            Log.i(TAG,"reason "+me.getReasonCode());

            Log.i(TAG,"msg "+me.getMessage());

            Log.i(TAG,"loc "+me.getLocalizedMessage());

            Log.i(TAG,"cause "+me.getCause());

            Log.i(TAG,"excep"+me);



            me.printStackTrace();

        }

    }





    //디바이스 고유의 Serial Number 얻어오는 메서드

    @SuppressLint("NewApi")

    private static String getDeviceSerialNumber() {

        try {

            return (String) Build.class.getField("SERIAL").get(null);

        } catch (Exception ignored) {

            return null;

        }

    }





//   MqttCallback implement 한후 implement 된 method

    @Override

    public void connectionLost(Throwable cause) {



    }



    @Override

    public void messageArrived(String topic, final MqttMessage message) throws Exception {

        //message 도착했을떄 callback method



        Log.i(TAG,"Arrived topic  " + topic);



        Log.i(TAG,"Arrived message  " + message.toString());





        runOnUiThread(new Runnable() {



            TextView receive_message = (TextView)findViewById(R.id.receve_message);

            @Override

            public void run() {

                receive_message.setText(message.toString());



            }

        });



    }



    @Override

    public void deliveryComplete(IMqttDeliveryToken token) {



    }
}
