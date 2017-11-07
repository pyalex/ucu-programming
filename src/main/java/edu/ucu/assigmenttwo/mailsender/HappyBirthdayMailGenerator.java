package edu.ucu.assigmenttwo.mailsender;


@MailCode(value = 2)
public class
HappyBirthdayMailGenerator implements MailGenerator {
    @Override
    public String generateHtml(MailInfo mailInfo) {
        return "happy birthday " + mailInfo.getClient().getName();
    }
}
