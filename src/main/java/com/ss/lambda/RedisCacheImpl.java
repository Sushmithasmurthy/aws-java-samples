package com.ss.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import io.lettuce.core.ReadFrom;
import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.sync.RedisCommands;
import io.lettuce.core.codec.StringCodec;
import io.lettuce.core.masterreplica.MasterReplica;
import io.lettuce.core.masterreplica.StatefulRedisMasterReplicaConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

//Reference - https://lettuce.io/core/release/reference/
public class RedisCacheImpl implements RequestHandler<String, Boolean> {
    private static final Logger LOGGER = LoggerFactory.getLogger(RedisCacheImpl.class);


    @Override
    public Boolean handleRequest(String s, Context context) {
        RedisClient redisClient = RedisClient.create();
        //https://lettuce.io/core/5.0.0.M2/reference/#ssl
        LOGGER.info("Setting up nodes");
        List<RedisURI> nodes = Arrays.asList(
                RedisURI.create("redis://redis-cache-v2-001.jfrufm.0001.use1.cache.amazonaws.com:6379"),
                RedisURI.create("redis://redis-cache-v2-002.jfrufm.0001.use1.cache.amazonaws.com:6379"),
                RedisURI.create("redis://redis-cache-v2-003.jfrufm.0001.use1.cache.amazonaws.com:6379"));

        StatefulRedisMasterReplicaConnection<String, String> connection = MasterReplica
                .connect(redisClient, StringCodec.UTF8, nodes);
        connection.setReadFrom(ReadFrom.MASTER_PREFERRED);

        System.out.println("Connected to Redis");


        RedisCommands<String, String> commands = connection.sync();
        if (commands != null) {
            String value = commands.get("foo");
            if (value != null && !value.isEmpty()) {
                LOGGER.info("Found key foo whose value is" + value);
            } else {
                LOGGER.info(commands.set("foo", "success"));
            }
        } else {
            LOGGER.info("commands is empty");
        }

        connection.close();
        redisClient.shutdown();
        return true;

    }
}

