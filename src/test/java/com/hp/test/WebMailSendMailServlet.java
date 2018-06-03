package com.hp.test;

import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Properties;
import java.util.TimeZone;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;

/**
 * WEBメールサービス開始承諾メール送信クラス
 *
 * @author chengqi
 */
public class WebMailSendMailServlet {
	private static final long serialVersionUID = 1L;

	/** タイムゾーン */
	private static final String TIMEZONE_JP = "Japan";
	
	/** ローカル */
	private static final Locale DEFAULT_LOCALE = Locale.JAPAN;
	
	/** 各サーバのFQDN */
	private static String fqdn = "";
	
	public static void main(String[] args) {
		try {
		// WEBメール利用登録受付用のメール内容を作成
		String mailInfo[] = getMailInfo();
		// URLはNSIM（使用セルは固定）に対してSMTPでメール送信する
		sendMail(mailInfo, "wliu@hpe.com", "sdfsfsfsfasdfasdfadfase");
		}
		catch (Exception e) {
			
		}
	}
	
	/**
	 * WEBメール利用登録受付用のメール内容の作成処理
	 *
	 * @param contextPath コンテキストのパス
	 * @param token TOKEN情報
	 * @return メール内容配列
	 * @throws Exception ファイルアクセス異常
	 */
	private static String[] getMailInfo()
			throws Exception {
		String fromAddr = "dummy@dummy.com";
		String subject = "test";
		String name = "testname";
		String content = "testcontent";

		return new String[]{fromAddr, subject, name, content};
	}
	
	/**
	 * SMTPでメール送信する
	 * @param mailInfo メール内容配列
	 * @param mailAddr 宛先メールアドレス
	 * @param sessionId セッションID
	 */
	private static void sendMail(String[] mailInfo, String mailAddr, final String sessionId) throws Exception {
		String fromAddr = mailInfo[0];
		String subject = mailInfo[1];
		String name = mailInfo[2];
		String content = mailInfo[3];

		// サーバのホスト名を取得
		String hostName = "";
		try {
			hostName = InetAddress.getLocalHost().getHostName();
		} catch (Exception e) {
			throw e;
		}
		// サーバのFQDNを作成
		fqdn = hostName + ".dummy";

		// SMTPプロパティの設定
		Properties prop = new Properties();
		prop.put("mail.smtp.host", "127.0.0.1");
		prop.put("mail.smtp.port", "25");
		prop.put("mail.smtp.connectiontimeout", 20000);
		prop.put("mail.smtp.timeout", 20000);
		prop.put("mail.smtp.localhost", fqdn);

		// 送信者と宛先の設定
		Address from = null;
		Address to = null;
		try {
			if( name != null && name.length() > 0 ){
				from = new InternetAddress(fromAddr, name, "iso-2022-jp");
			} else {
				from = new InternetAddress(fromAddr);
			}
			to = new InternetAddress(mailAddr);
		} catch (AddressException e) {
			throw e;
		}

		// SMTP送信セッションの初期化
		Session mailSession = Session.getDefaultInstance(prop);
		// メールメッセージの作成
		Message mailMessage = null;
		try {
			mailMessage = new MimeMessage(mailSession) {
				@Override
				protected void updateMessageID() throws MessagingException {
					// JavaMailが生成する Message-ID の上書
					String msgID = createMessageID(fqdn, sessionId);
					if (msgID != null && !"".equals(msgID)) {
						super.setHeader("Message-ID", msgID);
					} else {
						super.updateMessageID();
					}
				}
			};
			mailMessage.setFrom(from);
			mailMessage.setRecipient(Message.RecipientType.TO, to);
			mailMessage.setSubject(MimeUtility.encodeText(
					subject, "iso-2022-jp", "B"));
			mailMessage.setContent(content, "text/plain;charset=iso-2022-jp");
			mailMessage.setHeader("Content-Transfer-Encoding", "base64");
		} catch (Exception e) {
			throw e;
		}
		try {
			// 送信
			Transport.send(mailMessage);
			// 送信成功の場合、リトライ中止
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * 送信するメールに付与するMessage-IDの作成
	 *
	 * @param domain サーバのFQDN
	 * @param sessionId セッションID
	 * @return 送信するメールに付与するMessage-ID
	 */
	private static String createMessageID(String domain, String sessionId) {
		// ローカルシステム日時を取得
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS", DEFAULT_LOCALE);
		sdf.setTimeZone(TimeZone.getTimeZone(TIMEZONE_JP));
		// スレッドIDを取得
		String threadId = String.format("%04X", Thread.currentThread().getId());
		// Message-IDを作成（システム日時yyyyMMddHHmmssSSS+SessionID(末尾6桁)+ThreadID(4桁)@FQDN）
		StringBuffer msgID = new StringBuffer();
		msgID.append(sdf.format(new Date()));
		msgID.append(sessionId.substring(sessionId.length() - 6));
		msgID.append(threadId.substring(threadId.length() - 4));
		msgID.append("@");
		msgID.append(domain);
		return msgID.toString();
	}
}
