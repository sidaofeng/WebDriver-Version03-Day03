package love.simba;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import org.openqa.selenium.logging.Logs;


/*
 * 定制日志信息级别及格式
 * @date:2020-01-29 调整日志配置，防止日志泄露
 * 参考：
 * https://stackoverflow.com/questions/37539001/getting-stack-overflow-error-when-writing-log-by-using-log4j
 * https://logging.apache.org/log4j/2.x/manual/api.html
 * */
public class myLog {
	
	private static Logger Application_Log = Logger.getLogger(myLog.class.getName());
	       	
	//测试用例开始执行时的日志信息
	public static void startTestCase(String sTestCaseName) {
		Application_Log.info("-------------------------------------------------");
		Application_Log.info("************  "+sTestCaseName+"    **************");		
	}
	
	//测试用例结束执行时的日志信息
	public static void endTestCase(String sTestCaseName) {
		Application_Log.info("*************  "+"测试用例执行结束"+"    **************");
		Application_Log.info("--------------------------------------------------");
	}
	
	
	//info:自定义的info级别日志信息
	static void info(String message) {
		// TODO Auto-generated method stub
		Application_Log.info(message);
	}
	
	
	//warn:warn级别的日志信息
	public static void warn (String message) {
		Application_Log.warn(message);
	}
	
	//error:error级别的日志信息
	public static void error (String message) {
		Application_Log.error(message);
	}
	
	//fatal:fatal级别的日志信息
	public static void fatal (String message) {
		Application_Log.error(message);
	}

	//debug:debug级别的日志信息
	public static void debug (String message) {
		Application_Log.error(message);
	}


}
