package edu.ucu.assigmenttwo.mailsender;


import lombok.SneakyThrows;
import org.reflections.Reflections;

import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MailSender2 {

    private Map<Integer, MailGenerator> map = new HashMap<>();

    @SneakyThrows
    public MailSender2() {
        Reflections scanner = new Reflections("edu.ucu.assigmenttwo.mailsender");
        Set<Class<? extends MailGenerator>> classes = scanner.getSubTypesOf(MailGenerator.class);
        for (Class<? extends MailGenerator> generatorClass : classes) {
            if (!Modifier.isAbstract(generatorClass.getModifiers()) &&
                    generatorClass.isAnnotationPresent(MailCode.class)) {

                MailCode code =  generatorClass.getAnnotation(MailCode.class);
                MailGenerator generator = generatorClass.newInstance();
                map.put(code.value(), generator);
            }
        }
    }

    public void sendMail(MailInfo mailInfo) {

        MailGenerator mailGenerator = map.get(mailInfo.getMailCode());
        if (mailGenerator == null) {
            throw new IllegalStateException(mailInfo.getMailCode() + " not supported yet");
        }
        String html = mailGenerator.generateHtml(mailInfo);
        send(html,mailInfo);
    }

    private void send(String html, MailInfo mailInfo) {
        System.out.println("sending to ... " + html);
    }


}
