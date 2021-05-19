package com.github.shoothzj.demo.iotda;

import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import javax.net.SocketFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.SecureRandom;

public class MqttDemo {
    //IoT平台mqtt对接地址
    private String serverIp = "iot-mqtts.cn-north-4.myhuaweicloud.com";
    private int qosLevel = 1;
    private MqttAsyncClient client;
    // 创建设备时获得的deviceId，密钥（要替换为自己注册的设备ID与密钥）
    static String deviceId = "yourDeviceId";
    static String secret = "yourSecret";
    private long minBackoff = 1000;
    private long maxBackoff = 30 * 1000; //30 seconds
    private long defaultBackoff = 1000;
    private static int retryTimes = 0;
    private SecureRandom random = new SecureRandom();
    private static final int DEFAULT_CONNECT_TIMEOUT = 60;
    private boolean connectFinished = false;

    public static void main(String[] args) throws MqttException, InterruptedException {
        MqttDemo mqttDemo = new MqttDemo();
//        mqttDemo.connect(false); //false：mqtt连接示例
        mqttDemo.connect(true); //true：mqtts连接示例

        while (true) {
            mqttDemo.publishMessage();

            Thread.sleep(60000);
        }
    }

    /**
     * mqtt建链
     *
     * @param isSSL true：Mqtts加密连接
     *              false：Mqtt不加密连接
     */
    private void connect(boolean isSSL) {
        String url;
        if (isSSL) {
            url = "ssl://" + serverIp + ":" + 8883; //mqtts连接
        } else {
            url = "tcp://" + serverIp + ":" + 1883; //mqtt连接
        }
        try {
            MqttConnectOptions options = new MqttConnectOptions();
            if (isSSL) {
                options.setSocketFactory(getOptionSocketFactory(MqttDemo.class.getClassLoader().getResource("ca.jks").getPath()));
                options.setHttpsHostnameVerificationEnabled(false);
            }
            options.setCleanSession(false);
            options.setKeepAliveInterval(120);
            options.setConnectionTimeout(5000);
            options.setAutomaticReconnect(true);
            options.setUserName(deviceId);
            options.setPassword(getPassword().toCharArray());

            System.out.println("Start mqtt connect, url:" + url);
            //设置MqttClient
            client = new MqttAsyncClient(url, getClientId(), new MemoryPersistence());
            client.setCallback(callback);
            //建立连接
            client.connect(options, null, new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken iMqttToken) {
                    retryTimes = 0;
                    System.out.println("Mqtt connect success.");

                    synchronized (MqttDemo.this) {
                        connectFinished = true;
                        MqttDemo.this.notifyAll();
                    }
                }

                @Override
                public void onFailure(IMqttToken iMqttToken, Throwable throwable) {
                    System.out.println("Mqtt connect fail.");

                    //退避重连
                    int lowBound = (int) (defaultBackoff * 0.8);
                    int highBound = (int) (defaultBackoff * 1.2);
                    long randomBackOff = random.nextInt(highBound - lowBound);
                    long backOffWithJitter = (int) (Math.pow(2.0, (double) retryTimes)) * (randomBackOff + lowBound);
                    long waitTImeUntilNextRetry = (int) (minBackoff + backOffWithJitter) > maxBackoff ? maxBackoff : (minBackoff + backOffWithJitter);
                    System.out.println("----  " + waitTImeUntilNextRetry);
                    try {
                        Thread.sleep(waitTImeUntilNextRetry);
                    } catch (InterruptedException e) {
                        System.out.println("sleep failed, the reason is" + e.getMessage().toString());
                    }
                    retryTimes++;
                    MqttDemo.this.connect(true);
                }
            });

            synchronized (this) {

                while (!connectFinished) {
                    wait(DEFAULT_CONNECT_TIMEOUT * 1000);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Mqtt回调
     */
    private MqttCallback callback = new MqttCallbackExtended() {
        @Override
        public void connectComplete(boolean reconnect, String serviceURI) {
            System.out.println("Mqtt client connected, address:" + serviceURI);
            subScribeTopic();
        }

        @Override
        public void connectionLost(Throwable throwable) {
            System.out.println("Connection lost.");
            //可在此处实现重连
        }

        @Override
        public void messageArrived(String topic, MqttMessage message) throws Exception {
            System.err.println("Receive mqtt topic:" + topic + ", message:" + message);

            //上报命令下发响应，此处为demo
            if (topic.contains("/sys/commands/")) {
                int index = topic.indexOf("request_id=");
                String requestId = topic.substring(index + "request_id=".length());

                String jsonMsg = "{\"result_code\": 0}";
                MqttMessage cmdRsp = new MqttMessage(jsonMsg.getBytes());
                try {
                    MqttDemo.this.client.publish(getCmdRspTopic(requestId), cmdRsp, qosLevel, null);
                } catch (MqttException e) {
                    e.printStackTrace();
                }

            }
        }

        @Override
        public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
            System.out.println("Mqtt message deliver complete.");
        }
    };

    /**
     * 订阅接收命令topic
     */
    private void subScribeTopic() {
        try {
            client.subscribe(getCmdRequestTopic(), qosLevel, null, new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken iMqttToken) {
                    System.out.println("Subscribe mqtt topic success");
                }

                @Override
                public void onFailure(IMqttToken iMqttToken, Throwable throwable) {
                    System.out.println("Subscribe mqtt topic fail");
                }
            });
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    /**
     * 上报json数据，注意serviceId要与Profile中的定义对应
     */
    private void publishMessage() {
        String jsonMsg = "{\"services\":[{\"service_id\":\"BasicData\",\"properties\":{\"luminance\":%s},\"eventTime\":null}]}";

        String upMsg = String.format(jsonMsg, random.nextInt(100));

        MqttMessage message = new MqttMessage(upMsg.getBytes());

        try {
            client.publish(getReportTopic(), message, qosLevel, new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken iMqttToken) {
                    System.out.println("Publish message is " + upMsg);
                    System.out.println("Publish mqtt message success");
                }

                @Override
                public void onFailure(IMqttToken iMqttToken, Throwable throwable) {
                    System.out.println("Publish mqtt message fail");
                }
            });
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }


    /**
     * 属性上报topic
     *
     * @return
     */
    private String getReportTopic() {
        return "$oc/devices/" + deviceId + "/sys/properties/report";
    }

    /**
     * 命令响应topic
     */
    private String getCmdRspTopic(String requestId) {
        return "$oc/devices/" + deviceId + "}/sys/commands/response/request_id=" + requestId;
    }

    /**
     * 订阅命令下发topic
     *
     * @return
     */
    private String getCmdRequestTopic() {
        return "$oc/devices/" + deviceId + "/sys/commands/#";
    }

    /**
     * 加载SSL证书
     *
     * @param certPath 证书存放的相对路径
     * @return
     */
    private SocketFactory getOptionSocketFactory(String certPath) {
        SSLContext sslContext;

        InputStream stream = null;
        try {
            stream = new FileInputStream(certPath);
            sslContext = SSLContext.getInstance("TLS");
            KeyStore ts = KeyStore.getInstance("JKS");
            ts.load(stream, null);
            TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            tmf.init(ts);
            TrustManager[] tm = tmf.getTrustManagers();
            sslContext.init(null, tm, new SecureRandom());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return sslContext.getSocketFactory();
    }

    private String getClientId() {
        return deviceId + "_0_0_" + TimeUtil.getTimeStamp();
    }

    private String getPassword() {
        return Sha256Mac.sha256Mac(secret, TimeUtil.getTimeStamp());
    }

    private void close() {
        try {
            client.disconnect();
            client.close();
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
}