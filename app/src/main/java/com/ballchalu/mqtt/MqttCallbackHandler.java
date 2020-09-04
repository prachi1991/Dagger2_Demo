package com.ballchalu.mqtt;


import android.content.Context;
import android.content.Intent;

import com.ballchalu.shared.util.ConstantsBase;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.json.JSONObject;

/**
 * Handles call backs from the MQTT Client
 */
public class MqttCallbackHandler implements MqttCallback {

    /**
     * {@link Context} for the application used to format and import external strings
     **/
    private Context context;

    /**
     * Creates an <code>MqttCallbackHandler</code> object
     *
     * @param context      The application's context
     * @param clientHandle The handle to a {@link clientHandle} object
     */
    public MqttCallbackHandler(Context context, String clientHandle) {
        this.context = context;
        /**
         * Client handle to reference the connection that this handler is attached to
         **/
    }

    /**
     * @see MqttCallback#connectionLost(Throwable)
     */
    @Override
    public void connectionLost(Throwable cause) {

        context.sendBroadcast(new Intent("mqtt_connection_state"));

    }

    /**
     * @see MqttCallback#messageArrived(String, MqttMessage)
     */
    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {


        //create arguments to format message arrived notifcation string
        String[] args = new String[2];
        args[0] = new String(message.getPayload());
        args[1] = topic + ";qos:" + message.getQos() + ";retained:" + message.isRetained();

        //get the string from strings.xml and format
        //String messageString = context.getString(R.string.messageRecieved, (Object[]) args);


        JSONObject jsonObject = new JSONObject(args[0]);
        String type = Helper.INSTANCE.getSafeStringObjectFromJson(jsonObject, "type");
        Intent refreshIntent = new Intent();

        if (type.equalsIgnoreCase(ConstantsBase.HEROIC_COMMENTARY)) {

            refreshIntent.setAction(ConstantsBase.ODDS_UPDATES);
            refreshIntent.putExtra(ConstantsBase.ODDS, args[0]);

        } else if (type.equalsIgnoreCase(ConstantsBase.MATCH_DECLARED)) {

            refreshIntent.setAction(ConstantsBase.MATCH_DECLARED);
            refreshIntent.putExtra(ConstantsBase.ACTION_DECLARE, args[0]);

        } else {
            refreshIntent.setAction(ConstantsBase.ACTION_PUB_NUB);
            refreshIntent.putExtra(ConstantsBase.PUB_NUB, args[0]);
        }

        context.sendBroadcast(refreshIntent);


    }

    /**
     * @see MqttCallback#deliveryComplete(IMqttDeliveryToken)
     */
    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        // Do nothing

    }

}
