package edu.ucu.assigmenttwo.mailsender;

import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(RUNTIME)
public @interface MailCode {
    int value();
}
