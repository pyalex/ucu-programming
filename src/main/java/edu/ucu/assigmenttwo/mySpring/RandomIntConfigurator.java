package edu.ucu.assigmenttwo.mySpring;

import lombok.SneakyThrows;
import org.reflections.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.Random;
import java.util.Set;

public class RandomIntConfigurator implements ObjectConfigurator {
    private Random random = new Random();

    @Override
    @SneakyThrows
    public <T> void configure(Class<T> type, Object o) {
        Set<Field> fields = ReflectionUtils.getAllFields(type, ReflectionUtils.withAnnotation(InjectRandomInt.class));
        for (Field field : fields) {
            InjectRandomInt annotation = field.getAnnotation(InjectRandomInt.class);
            int min = annotation.min();
            int max = annotation.max();
            int randomIntValue = random.nextInt(max - min) + min;
            field.setAccessible(true);
            field.set(o, randomIntValue);
        }
    }
}
