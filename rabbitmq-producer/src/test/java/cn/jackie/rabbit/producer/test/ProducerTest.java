package cn.jackie.rabbit.producer.test;

import cn.jackie.rabbit.entity.RabbitMessage;
import cn.jackie.rabbit.producer.MessageProducer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

/**
 * 生产者单元测试类
 * @author Jackie Ke
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProducerTest {

    @Autowired
    private MessageProducer messageProducer;

    @Test
    public void testProduce() throws InterruptedException {

        RabbitMessage rabbitMessage = new RabbitMessage();

        Map<String, Object> properties = new HashMap<>();
        properties.put("name", "张三");
        properties.put("description", "法外狂徒");

        rabbitMessage.setExchange("hello-exchange");
        rabbitMessage.setRoutingKey("springboot.rabbit");
        rabbitMessage.setContent("Hello RabbitMQ !!!");
        rabbitMessage.setProperties(properties);

        messageProducer.send(rabbitMessage);

    }

}
