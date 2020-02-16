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
	
	//使用注解DataProvider,将数据集合命名为“testData”
	@DataProvider(name = "testData")
	public static Object[][] words() throws IOException {
		//调用类中的静态方法 getTestData,获取MySQL的测试数据
		return getTestData("testData");		
	}

	@Test(dataProvider = "testData")
	public void testSearch(String searchWord1,String searchWord2,String searchResult) {
		driver.get(baseUrl+"/");
		driver.findElement(By.id("query")).sendKeys(searchWord1+" "+searchWord2);
		driver.findElement(By.id("stb")).click();
		
		//显示等待，确认页面上显示制定的文案，确认页面已经加载完成
		 (new WebDriverWait(driver, 10)).until(
				 new ExpectedCondition<Boolean>() {
						@Override
						public Boolean apply(WebDriver d) {
							// TODO Auto-generated method stub
							System.out.println(d.findElement(By.id("s_footer")).getText());
							return d.findElement(By.id("s_footer")).getText().contains("意见反馈及投诉");
						}			 
				 	}
				 );		
		Assert.assertTrue(driver.getPageSource().contains(searchResult));
	}
	
	  @BeforeMethod
	  public void beforeMethod() {
		  System.setProperty("WebDriver.firefox.bin", "C:\\eclipse-workspace\\Con_Browser\\firefox.exe"); 
		  driver = new FirefoxDriver();
		  myLog.info("打开火狐浏览器");
	  }
	
	  @AfterMethod
	  public void afterMethod() {
		  driver.quit();
		  myLog.endTestCase("数据驱动测试");
	  }
	
	  @BeforeClass
	  public void beforeClass() {
		  DOMConfigurator.configure("log4j.xml");
	  } 
	
	private static Object[][] getTestData(String string) {
		// 声明MySQL驱动
		String driver = "com.mysql.cj.jdbc.Driver";
		String url = "jdbc:mysql://127.0.0.1:3306/risedark?useUnicode=true&characterEncoding=utf8&serverTimezone=GMT";
		//声明数据库的用户名。
		String user = "root";
		String password = "root";
		String tablename = "testsql";
		//声明存储测试数据的List对象
		List<Object> records = new ArrayList<Object>();
		try {
			Class.forName(driver);
			Connection conn = DriverManager.getConnection(url,user,password);
			if(!conn.isClosed()) {
				System.out.println("连接数据库成功");
				//开发存储实体，statement对象
				Statement statement = conn.createStatement();
				String sql = "select * from " + tablename;
				//存储查询结果的结果集，ResultSet对象
				ResultSet rSet = statement.executeQuery(sql);
				
				//抽出结果集，单独做一个对象
				ResultSetMetaData rSetMetaData = rSet.getMetaData();
				//获取数据行的列数
				int cols = rSetMetaData.getColumnCount();
				
				//遍历rSet所有数据行
				while(rSet.next()) {
					//每一行数据，都存到数组中。
					//数组大小是数据行的数量cols
					String fields[]  = new String[cols];
					int col = 0;
					for(int colIdx=0;colIdx<cols;colIdx++) {
						fields[col] = rSet.getString(colIdx+1);
						col++;
					}
					//将每一行的数据存储到string数据后，存储到records
					records.add(fields);
					//打印数组的前三列元素
					System.out.println(rSet.getString(1)+" "+rSet.getString(2)+" "+rSet.getString(3));		
				}
				rSet.close();
				conn.close();					
			}
		}catch (ClassNotFoundException e) {
			System.out.println("未能找到MySQL的驱动类");
			e.printStackTrace();
		}catch (SQLException eSqlException) {
			eSqlException.printStackTrace();
		}catch (Exception exception ) {
			exception.printStackTrace();
		}
		
		//主函数返回值Object[][]
		//将records 从list转化成二维数组 Object[][]
		Object[][] results = new Object[records.size()][]; 
		//设置二维数组的值，每行一个Object对象	
		for (int i = 0;i<records.size();i++) {
			results[i] = (Object[]) records.get(i);
		}		
		return results;
	}
	
	
}
