package love.simba;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.xml.DOMConfigurator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import org.apache.log4j.xml.DOMConfigurator;
import java.io.IOException;

public class TestDataDriversByMysqltestSQL {
	public WebDriver driver;
	String baseUrl = "http://www.sogou.com";
	
	//ʹ��ע��DataProvider,�����ݼ�������Ϊ��testData��
	@DataProvider(name = "testData")
	public static Object[][] words() throws IOException {
		//�������еľ�̬���� getTestData,��ȡMySQL�Ĳ�������
		return getTestData("testData");		
	}

	@Test(dataProvider = "testData")
	public void testSearch(String searchWord1,String searchWord2,String searchResult) {
		driver.get(baseUrl+"/");
		driver.findElement(By.id("query")).sendKeys(searchWord1+" "+searchWord2);
		driver.findElement(By.id("stb")).click();
		
		//��ʾ�ȴ���ȷ��ҳ������ʾ�ƶ����İ���ȷ��ҳ���Ѿ��������
		 (new WebDriverWait(driver, 10)).until(
				 new ExpectedCondition<Boolean>() {
						@Override
						public Boolean apply(WebDriver d) {
							// TODO Auto-generated method stub
							System.out.println(d.findElement(By.id("s_footer")).getText());
							return d.findElement(By.id("s_footer")).getText().contains("���������Ͷ��");
						}			 
				 	}
				 );		
		Assert.assertTrue(driver.getPageSource().contains(searchResult));
	}
	
	  @BeforeMethod
	  public void beforeMethod() {
		  System.setProperty("WebDriver.firefox.bin", "C:\\eclipse-workspace\\Con_Browser\\firefox.exe"); 
		  driver = new FirefoxDriver();
		  myLog.info("�򿪻�������");
	  }
	
	  @AfterMethod
	  public void afterMethod() {
		  driver.quit();
		  myLog.endTestCase("������������");
	  }
	
	  @BeforeClass
	  public void beforeClass() {
		  DOMConfigurator.configure("log4j.xml");
	  } 
	
	private static Object[][] getTestData(String string) {
		// ����MySQL����
		String driver = "com.mysql.cj.jdbc.Driver";
		String url = "jdbc:mysql://127.0.0.1:3306/risedark?useUnicode=true&characterEncoding=utf8&serverTimezone=GMT";
		//�������ݿ���û�����
		String user = "root";
		String password = "root";
		String tablename = "testsql";
		//�����洢�������ݵ�List����
		List<Object> records = new ArrayList<Object>();
		try {
			Class.forName(driver);
			Connection conn = DriverManager.getConnection(url,user,password);
			if(!conn.isClosed()) {
				System.out.println("�������ݿ�ɹ�");
				//�����洢ʵ�壬statement����
				Statement statement = conn.createStatement();
				String sql = "select * from " + tablename;
				//�洢��ѯ����Ľ������ResultSet����
				ResultSet rSet = statement.executeQuery(sql);
				
				//����������������һ������
				ResultSetMetaData rSetMetaData = rSet.getMetaData();
				//��ȡ�����е�����
				int cols = rSetMetaData.getColumnCount();
				
				//����rSet����������
				while(rSet.next()) {
					//ÿһ�����ݣ����浽�����С�
					//�����С�������е�����cols
					String fields[]  = new String[cols];
					int col = 0;
					for(int colIdx=0;colIdx<cols;colIdx++) {
						fields[col] = rSet.getString(colIdx+1);
						col++;
					}
					//��ÿһ�е����ݴ洢��string���ݺ󣬴洢��records
					records.add(fields);
					//��ӡ�����ǰ����Ԫ��
					System.out.println(rSet.getString(1)+" "+rSet.getString(2)+" "+rSet.getString(3));		
				}
				rSet.close();
				conn.close();					
			}
		}catch (ClassNotFoundException e) {
			System.out.println("δ���ҵ�MySQL��������");
			e.printStackTrace();
		}catch (SQLException eSqlException) {
			eSqlException.printStackTrace();
		}catch (Exception exception ) {
			exception.printStackTrace();
		}
		
		//����������ֵObject[][]
		//��records ��listת���ɶ�ά���� Object[][]
		Object[][] results = new Object[records.size()][]; 
		//���ö�ά�����ֵ��ÿ��һ��Object����	
		for (int i = 0;i<records.size();i++) {
			results[i] = (Object[]) records.get(i);
		}		
		return results;
	}
	
	
}
