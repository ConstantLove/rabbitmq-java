package cn.jackie.rabbit.consumer;

import cn.jackie.rabbit.entity.RabbitMessage;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 消息的消费者
 * @author Jackie Ke
 */
@Component
public class MessageConsumer {

    /**
     *  接受消息的核心方法
     * @param message
     * @param channel
     * @throws IOException
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "${spring.rabbitmq.listener.hello.queue.value}", durable = "true"),
            exchange = @Exchange(name = "${spring.rabbitmq.listener.hello.exchange.name}",
                                type = "${spring.rabbitmq.listener.hello.exchange.type}",
                                ignoreDeclarationExceptions = "true"),
            key = "${spring.rabbitmq.listener.hello.key}"
    ))
    @RabbitHandler
    public void receiveMessage(Message message, Channel channel) throws IOException {
        //1. 收到信息
        RabbitMessage messageBody = (RabbitMessage) message.getPayload();
        System.out.println("收到消息：" + messageBody.getContent());

        // 2. 确定 deliveryTag，配置文件里设置了acknowledge-mode: manual，所以需要手动确认
        Long deliveryTag = (Long) message.getHeaders().get(AmqpHeaders.DELIVERY_TAG);
        // 3. 消息确认
        channel.basicAck(deliveryTag, false);
    }

}
