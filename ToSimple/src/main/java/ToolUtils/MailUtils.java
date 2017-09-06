package ToolUtils;

import model.User;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;

public class MailUtils {
    //--------------参数---------------------
    public static final String FROM = "TOSIMPLE_SJTU@163.com";//发件人的email
    public static final String PWD = "2simple";//发件人密码--邮箱密码
    public static final String URL = "http://localhost:8080/ToSimple";//项目主页
    public static final int LIMITHOUR = 2; //激活邮件过期时间2小时
    public static final int LIMITTIME = 1000 * 60 * 60 * 2;
    public static String TITLE = "ToSimple账户激活邮件";
    public static final String HOST = "smtp.163.com";
    public static final String SMTP = "smtp";

    //---------------自定义函数-----------------
    public static User activateMail(User u) throws MessagingException {
        //注册邮箱
        String to = u.getEmail();
        //当前时间戳
        Long curTime = System.currentTimeMillis();
        //激活的有效时间
        Long activateTime = curTime + LIMITTIME;
        //激活码--用于激活邮箱账号
        String token = curTime+to ;
        u.setToken(MD5Utils.getEncoded(token));
        u.setCreateTime(new Date(activateTime));
        token = u.getToken();
        //发送的邮箱内容
        String content = "<p>您好 O(∩_∩)O~~<br><br>欢迎加入ToSimple!<br><br>帐户需要激活才能使用，赶紧激活成为ToSimple正式的一员吧:)<br><br>请在2小时内点击下面的链接立即激活帐户："
                + "<br><a href='" + URL + "/registerValidate?token=" + token + "&email=" + to + "'>"
                + URL + "/registerValidate?token=" + token + "&email=" + to + "</a></p>";
        //调用发送邮箱服务
        MailUtils.sendMail(to, TITLE, content);
        return u;
    }
    
    public static User sendCheckToken(User u) throws MessagingException{
    	//get userful information
        String to = u.getEmail();  //email
        Long curTime = System.currentTimeMillis();  //time
        String token= u.getCheckToken();
        TITLE= "密码找回";
        //发送的邮箱内容
        String content = "<p>您好 O(∩_∩)O~~<br><br>欢迎使用ToSimple!<br><br>这是您请求的验证码，若不是您的操作，请勿点击！<br><br>验证码为："
                + "<br>"+token+"</p>";
        //调用发送邮箱服务
        MailUtils.sendMail(to, TITLE, content);
        return u;
    }
    
    

    //---------------发送邮件-------------------
    public static void sendMail(String to, String title, String content) throws MessagingException {
        Properties props = new Properties(); //可以加载一个配置文件
        // 使用smtp：简单邮件传输协议
        props.put("mail.smtp.host", HOST);//存储发送邮件服务器的信息
        props.put("mail.smtp.auth", "true");//同时通过验证
        Session session = Session.getInstance(props);//根据属性新建一个邮件会话
        //session.setDebug(true); //有他会打印一些调试信息。
        MimeMessage message = new MimeMessage(session);//由邮件会话新建一个消息对象
        message.setFrom(new InternetAddress(FROM));//设置发件人的地址
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));//设置收件人,并设置其接收类型为TO
        message.setSubject(title);//设置标题
        //设置信件内容
        //message.setText(mailContent); //发送 纯文本 邮件 todo
        message.setContent(content, "text/html;charset=gbk"); //发送HTML邮件，内容样式比较丰富
        message.setSentDate(new Date());//设置发信时间
        message.saveChanges();//存储邮件信息
        //发送邮件
        Transport transport = session.getTransport(SMTP);
        //Transport transport = session.getTransport();
        transport.connect(FROM, PWD);
        transport.sendMessage(message, message.getAllRecipients());//发送邮件,其中第二个参数是所有已设好的收件人地址
        transport.close();
    }
}


