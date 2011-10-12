/*
 *  Copyright (C) 2010 Amon
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.magicpwd.m.mail;

import com.magicpwd._cons.ConsEnv;
import com.magicpwd._util.Char;
import com.magicpwd._bean.mail.Connect;
import com.magicpwd._comn.S1S1;
import com.magicpwd._cons.mail.MailEnv;
import com.sun.mail.imap.IMAPFolder;
import com.sun.mail.pop3.POP3Folder;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.Store;
import javax.mail.internet.ContentType;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 *
 * @author Amon
 */
public class Reader extends Mailer
{

    private String messageId;// 消息索引
    private boolean needReply;//是否需要回复
    private String attachmentPath;//附件下载目录

    public Reader(Connect connect)
    {
        super(connect);
        attachmentPath = ConsEnv.DIR_MAIL;
    }

    public boolean read(Folder folder, Message message) throws Exception
    {
        if (message == null)
        {
            return false;
        }

        setFrom(decodeAddress(message.getFrom()));
        setTo(decodeAddress(message.getRecipients(Message.RecipientType.TO)));
        setCc(decodeAddress(message.getRecipients(Message.RecipientType.CC)));
        setBcc(decodeAddress(message.getRecipients(Message.RecipientType.BCC)));
        setSubject(decodeMessage(message.getSubject()));
        setSentDate(message.getSentDate());
        needReply = message.getHeader("Disposition-Notification-To") != null;
        Object obj = message.getContent();
        if (obj instanceof MimeMultipart)
        {
            Multipart part = (MimeMultipart) obj;
            for (int i = 0; i < part.getCount(); i++)
            {
                decodePart(part.getBodyPart(i));
            }
        }

        messageId = getMessageId(folder, message);
        getConnect().setMailReaded(messageId, getConnect().isMailExists(messageId));

        return true;
    }

    private String getMessageId(Folder folder, Message message) throws Exception
    {
        if (folder instanceof POP3Folder)
        {
            return ((POP3Folder) folder).getUID(message);
        }
        if (folder instanceof IMAPFolder)
        {
            return Long.toString(((IMAPFolder) folder).getUID(message));
        }

        String msgId = null;
        if (message instanceof MimeMessage)
        {
            msgId = ((MimeMessage) message).getMessageID();
        }
        if (!com.magicpwd._util.Char.isValidate(msgId))
        {
            msgId = Long.toHexString(com.magicpwd._util.Hash.ber(getFrom() + getSubject() + getSentDate()));
        }
        return msgId;
    }

    /**
     * 解析邮件，把得到的邮件内容保存到一个StringBuffer对象中，解析邮件 主要是根据MimeType类型的不同执行不同的操作，一步一步的解析
     */
    private void decodePart(Part part) throws Exception
    {
        // 附件
        String disposition = part.getDisposition();
        if (disposition != null && (disposition.equalsIgnoreCase(Part.ATTACHMENT) || disposition.equalsIgnoreCase(Part.INLINE)))
        {
            saveAttach(decodeMessage(part.getFileName()), part.getInputStream());//保存附件
            return;
        }

        String sType = getContentType(part, "UTF-8");
        boolean conname = sType.indexOf("name") != -1;
        if (part.isMimeType(MailEnv.TEXT_PLAIN) && !conname)
        {
            setContentType(MailEnv.TEXT_PLAIN + ';' + MailEnv.CHARSET + '=' + sType);
            appendContent((String) part.getContent());
            return;
        }
        if (part.isMimeType(MailEnv.TEXT_HTML) && !conname)
        {
            setContentType(MailEnv.TEXT_HTML + ';' + MailEnv.CHARSET + '=' + sType);
            appendContent((String) part.getContent());
            return;
        }
        if (part.isMimeType(MailEnv.MULTIPART))
        {
            Multipart multipart = (Multipart) part.getContent();
            for (int i = 0, j = multipart.getCount(); i < j; i++)
            {
                decodePart(multipart.getBodyPart(i));
            }
            return;
        }
        if (part.isMimeType(MailEnv.MESSAGE))
        {
            decodePart((Part) part.getContent());
            return;
        }
        Object obj = part.getContent();
        if (obj instanceof String)
        {
            appendContent((String) obj);
        }
    }

    /**
     * 【真正的保存附件到指定目录里】
     */
    private void saveAttach(String fileName, java.io.InputStream inStream) throws Exception
    {
        if (!Char.isValidate(fileName))
        {
            return;
        }
        java.io.File file = new java.io.File(getAttachmentPath(), fileName);

        java.io.BufferedOutputStream bos = null;
        java.io.BufferedInputStream bis = null;
        try
        {
            bos = new java.io.BufferedOutputStream(new java.io.FileOutputStream(file));
            bis = new java.io.BufferedInputStream(inStream);
            byte[] b = new byte[2048];
            int c = bis.read(b);
            while (c >= 0)
            {
                bos.write(b, 0, c);
                c = bis.read(b);
            }
            bos.flush();

            addAttachment(fileName, file.getAbsolutePath());
        }
        finally
        {
            if (bis != null)
            {
                bis.close();
            }
            if (bos != null)
            {
                bos.close();
            }
        }
    }

    private static String getContentType(Part part, String type) throws Exception
    {
        String sType = part.getContentType();
        if (com.magicpwd._util.Char.isValidate(sType))
        {
            ContentType cType = new ContentType(sType);
            sType = cType.getParameter(MailEnv.CHARSET);
            if (!com.magicpwd._util.Char.isValidate(sType))
            {
                String[] sTemp = part.getHeader("from");
                if (sTemp != null && sTemp.length > 0)
                {
                    sType = sTemp[0];
                    if (com.magicpwd._util.Char.isValidate(sType))
                    {
                        java.util.regex.Matcher matcher = java.util.regex.Pattern.compile("=\\?\\w+\\?", java.util.regex.Pattern.CASE_INSENSITIVE).matcher(sType);
                        if (matcher.find())
                        {
                            sType = matcher.group();
                            sType = sType.substring(2, sType.length() - 1);
                        }
                    }
                }
            }
        }
        return sType != null ? sType : type;
    }

    public java.util.List<S1S1> getUnReadMail() throws Exception
    {
        java.util.ArrayList<S1S1> mailList = new java.util.ArrayList<S1S1>();
        Store store = getConnect().getStore();
        Folder folder = store.getDefaultFolder().getFolder("inbox");
        if (folder.isOpen())
        {
            folder.close(false);
        }

        folder.open(Folder.READ_ONLY);
        Message[] messages = folder.getMessages();
//        FetchProfile fp = new FetchProfile();
//        fp.add(FetchProfile.Item.ENVELOPE);
//        fp.add("X-mailer");
//        folder.fetch(messages, fp);

        if (messages != null)
        {
            String mesgId;
            getConnect().loadMailInfo();
            for (Message mesg : messages)
            {
                mesgId = getMessageId(folder, mesg);
                if (getConnect().isMailExists(mesgId))
                {
                    continue;
                }
                mailList.add(new S1S1(mesgId, mesg.getSubject()));
                getConnect().setMailReaded(mesgId, true);
            }
            getConnect().saveMailInfo();
        }
        folder.close(false);
        store.close();
        return mailList;
    }

    public String getMessageId()
    {
        return messageId;
    }

    /**
     * 【获得附件存放路径】
     */
    public String getAttachmentPath()
    {
        return attachmentPath != null ? attachmentPath : System.getProperty("java.io.tmpdir");
    }

    /**
     * 【设置附件存放路径】
     */
    public void setAttachmentPath(String attachmentPath)
    {
        this.attachmentPath = attachmentPath;
    }

    /**
     * 判断此邮件是否需要回执，如果需要回执返回"true",否则返回"false"
     */
    public boolean needReply()
    {
        return needReply;
    }

    public boolean isReaded()
    {
        return getConnect().isMailReaded(messageId);
    }
}
