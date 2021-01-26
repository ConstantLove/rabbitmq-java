package cn.jackie.rabbit.entity;

import java.io.Serializable;
import java.util.Map;

/**
 * 消息实体
 * @author Jackie Ke
 */
public class RabbitMessage implements Serializable {

    private String exchange;

    private String content;

    private String routingKey;

    private Map<String, Object> properties;

    public RabbitMessage(String exchange, String content, String routingKey, Map<String, Object> properties) {
        this.exchange = exchange;
        this.content = content;
        this.routingKey = routingKey;
        this.properties = properties;
    }

    public RabbitMessage() {
    }

    public String getExchange() {
        return exchange;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getRoutingKey() {
        return routingKey;
    }

    public void setRoutingKey(String routingKey) {
        this.routingKey = routingKey;
    }

    public Map<String, Object> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, Object> properties) {
        this.properties = properties;
    }
}
