package love.simba;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import org.openqa.selenium.logging.Logs;


/*
 * ������־��Ϣ���𼰸�ʽ
 * @date:2020-01-29 ������־���ã���ֹ��־й¶
 * �ο���
 * https://stackoverflow.com/questions/37539001/getting-stack-overflow-error-when-writing-log-by-using-log4j
 * https://logging.apache.org/log4j/2.x/manual/api.html
 * */
public class myLog {
	
	private static Logger Application_Log = Logger.getLogger(myLog.class.getName());
	       	
	//����������ʼִ��ʱ����־��Ϣ
	public static void startTestCase(String sTestCaseName) {
		Application_Log.info("-------------------------------------------------");
		Application_Log.info("************  "+sTestCaseName+"    **************");		
	}
	
	//������������ִ��ʱ����־��Ϣ
	public static void endTestCase(String sTestCaseName) {
		Application_Log.info("*************  "+"��������ִ�н���"+"    **************");
		Application_Log.info("--------------------------------------------------");
	}
	
	
	//info:�Զ����info������־��Ϣ
	static void info(String message) {
		// TODO Auto-generated method stub
		Application_Log.info(message);
	}
	
	
	//warn:warn�������־��Ϣ
	public static void warn (String message) {
		Application_Log.warn(message);
	}
	
	//error:error�������־��Ϣ
	public static void error (String message) {
		Application_Log.error(message);
	}
	
	//fatal:fatal�������־��Ϣ
	public static void fatal (String message) {
		Application_Log.error(message);
	}

	//debug:debug�������־��Ϣ
	public static void debug (String message) {
		Application_Log.error(message);
	}


}
