package cn.jackie.rabbit.producer;

import cn.jackie.rabbit.entity.RabbitMessage;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * 确认消息生产者
 * @author Jackie Ke
 */
@Component
public class MessageProducer implements RabbitTemplate.ConfirmCallback{

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 发送信息的操作
     * @param message
     */
    public void send(RabbitMessage message) {
        if (message == null || message.getExchange() == null || message.getRoutingKey() == null) throw new IllegalArgumentException();

        // 1. 添加消息的附加消息
        MessageHeaders messageHeaders = new MessageHeaders(message.getProperties());
        Message<?> msg = MessageBuilder.createMessage(message, messageHeaders);

        // 2. 设置确认消息的函数
        rabbitTemplate.setConfirmCallback(this); // 为了方便直接实现ConfirmCallback，传入this即可

        // 3. 创建消息的唯一标识
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());

        // 4. 设置消息发送完成后的回调函数
        MessagePostProcessor messagePostProcessor = new MessagePostProcessor() {
            @Override
            public org.springframework.amqp.core.Message postProcessMessage(org.springframework.amqp.core.Message message) throws AmqpException {
                System.out.println("-------------- 消息发送完成 --------------");
                return message;
            }
        };

        // 5. 发送消息
        rabbitTemplate.convertAndSend(message.getExchange(), message.getRoutingKey(), msg, messagePostProcessor, correlationData);
    }

    /**
     * @param correlationData 信息的唯一标识，类似ID
     * @param ack 是否成功接收
     * @param cause 如果失败，返回异常信息
     */
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        System.out.println("消息发送" + (ack ? "成功" : "失败") + ", correlationData：" + correlationData);
    }
}
