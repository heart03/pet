package com.lzc.pet.service.Impl;

import com.lzc.pet.domain.User;
import com.lzc.pet.repository.UserRepository;
import com.lzc.pet.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private UserRepository userRepository;
    public String email(String url){
        String result = "";
        if(userRepository.findByEmail(url).size()==1)
            result="1";
        else{
            try {
                final MimeMessage mimeMessage = this.javaMailSender.createMimeMessage();
                final MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
                mimeMessageHelper.setFrom("17859909035@163.com");
                mimeMessageHelper.setTo(url);
                Random random = new Random();
                for (int i = 0; i < 6; i++) {
                    result += random.nextInt(9) % (10) + "";
                }
                mimeMessageHelper.setSubject("宠物服务平台");
                mimeMessageHelper.setText("注册验证码:" + result);
                this.javaMailSender.send(mimeMessage);
            } catch (Exception ex) {
                result = "-1";
            }
        }
        return result;
    }
    public String pass(String message){
        try {
            byte buff[]=null;
            for(int i=0;i<2;i++) {
                MessageDigest md = MessageDigest.getInstance("MD5");
                byte[] input = message.getBytes();
                buff = md.digest(input);
            }
            StringBuffer md5str = new StringBuffer();
            int digital;
            for (int i = 0; i < buff.length; i++) {
                digital = buff[i];

                if (digital < 0) {
                    digital += 256;
                }
                if (digital < 16) {
                    md5str.append("0");
                }
                md5str.append(Integer.toHexString(digital));
            }

            return md5str.toString().toUpperCase();
        }
        catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }//md5加密

    public String checked(User user,String email){
        if(userRepository.findByUname(user.getUname()).size()==1)
            return "用户名已被使用";
        else if(!user.getEmail().equals(email))
            return "邮箱不对应";
        else
        {
            user.setUrl("4.jpg");
            user.setIdentity("0");
            user.setBalance(0);
            user.setAuthorize(pass(user.getUname()));
            userRepository.save(user);
            List<User> l=userRepository.findByUname(user.getUname());
            return "注册成功账号为：" +l.get(0).getUid();
        }
    }
}
