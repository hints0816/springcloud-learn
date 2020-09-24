package springtest;

import com.hints.rabbit.RabbitApplication;
import com.hints.rabbit.message.Sender;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RabbitApplication.class)
public class Test01 {


    @Autowired
    private Sender sender;

    @Test
    public void hello(){
        for(int i = 0;i<100;i++){
            sender.send();
            sender.send1();
        }
    }
}
