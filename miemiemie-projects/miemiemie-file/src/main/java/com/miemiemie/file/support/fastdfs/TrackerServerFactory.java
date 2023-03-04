package com.miemiemie.file.support.fastdfs;

import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.ProtoCommon;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;

import java.util.Properties;

/**
 * @author yangshunxiang
 * @since 2023/2/26
 */
public class TrackerServerFactory extends BasePooledObjectFactory<TrackerServer> {

    private final FastDfsFileClientProperties properties;

    public TrackerServerFactory(FastDfsFileClientProperties properties) {
        this.properties = properties;
    }

    private void initClientGlobal(FastDfsFileClientProperties fastDfsFileClientProperties) throws Exception {
        Properties properties = new Properties();
        properties.put(ClientGlobal.PROP_KEY_CONNECT_TIMEOUT_IN_SECONDS, fastDfsFileClientProperties.getConnectTimeoutInSeconds());
        properties.put(ClientGlobal.PROP_KEY_NETWORK_TIMEOUT_IN_SECONDS, fastDfsFileClientProperties.getNetworkTimeoutInSeconds());
        properties.put(ClientGlobal.PROP_KEY_CHARSET, fastDfsFileClientProperties.getCharset());
        properties.put(ClientGlobal.PROP_KEY_TRACKER_SERVERS, fastDfsFileClientProperties.getTrackerServers());
        properties.put(ClientGlobal.PROP_KEY_HTTP_TRACKER_HTTP_PORT, fastDfsFileClientProperties.getHttpTrackerHttpPort());
        properties.put(ClientGlobal.PROP_KEY_HTTP_ANTI_STEAL_TOKEN, fastDfsFileClientProperties.getHttpAntiStealToken());
        properties.put(ClientGlobal.PROP_KEY_HTTP_SECRET_KEY, fastDfsFileClientProperties.getHttpSecretKey());
        ClientGlobal.initByProperties(properties);
    }

    @Override
    public TrackerServer create() throws Exception {
        initClientGlobal(properties);

        TrackerClient trackerClient = new TrackerClient();
        return trackerClient.getConnection();
    }

    @Override
    public PooledObject<TrackerServer> wrap(TrackerServer trackerServer) {
        return new DefaultPooledObject<>(trackerServer);
    }

    @Override
    public void destroyObject(PooledObject<TrackerServer> p) throws Exception {
        TrackerServer trackerServer = p.getObject();
        trackerServer.close();
    }

    @Override
    public boolean validateObject(PooledObject<TrackerServer> p) {
        try {
            return ProtoCommon.activeTest(p.getObject().getSocket());
        } catch (Exception ignore) {
        }
        return false;
    }
}
