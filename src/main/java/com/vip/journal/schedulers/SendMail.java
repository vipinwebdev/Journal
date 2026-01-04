package com.vip.journal.schedulers;

import com.vip.journal.entity.User;
import com.vip.journal.repository.UserRepositoryImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

@Slf4j
@Repository
public class SendMail {

    @Resource
    private JavaMailSender javaMailSender;

    @Resource
    private UserRepositoryImpl userRepositoryImpl;

//    @Scheduled(cron = "* * * * * *")
    public void sendMail() {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        for (User user : userRepositoryImpl.findAll()) {
            try{
                mailMessage.setTo(user.getEmail());
                mailMessage.setSubject("Your email has been sent");
                mailMessage.setText("Your email has been sent to "+user.getEmail() );
                javaMailSender.send(mailMessage);
            }catch(Exception e){
                log.error(e.getMessage());
            }
        }

    }
}
