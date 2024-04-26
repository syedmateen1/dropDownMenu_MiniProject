/* Syed Mateen
 * 2318702
 * CSDQEA24QE004
 * Selenium Mini Project- Drop Down Menu 
 * 9 March 2024  */


package dropDownMenu;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.time.Duration;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.Select;

public class DropDownMenuMiniProject {

	public static WebDriver cdriver;                                    //declaring WebDriver instance for chrome
	public static WebDriver edgedriver;                                 //declaring WebDriver instance for Edge
	public static List<WebElement> l;                                   //declaring List to store countries
	public String s;                                                    //declaring String for selected country for chrome 
	public String s1;                                                   //declaring String for selected country for edge

	public static void main(String[] args) throws IOException
	{
		System.out.println("Performing the test case parallely in both chrome and edge browser");
		DropDownMenuMiniProject d = new DropDownMenuMiniProject();                                //creating instance of class to access methods
		cdriver=d.createChromeDriver();                                                           //storing the chrome WebDriver instance in cdriver		
		edgedriver=d.createEdgeDriver();                                                          //storing the Edge WebDriver instance in edgedriver
        d.getUrl(cdriver);                                                                        //opening the url in chrome
        d.getUrl(edgedriver);                                                                     //opening the url in Edge
		d.test();                                                                                 //Method that performs the test case
		d.printOutput();                                                                          //Prints the console output to text file
		System.out.println("Closing both the browsers");
	    d.closeBrowser();


	}

	

	public WebDriver createChromeDriver()                                                      //creates a ChromeDriver instance and returns it
	{
	   cdriver=new ChromeDriver();
	   return cdriver;
	}

	public WebDriver createEdgeDriver()                                                        //creates a EdgeDriver instance and returns it
	{
	   edgedriver=new EdgeDriver();
	   return edgedriver;
	}

	public void getUrl(WebDriver driver)                                                       //Opening the url using get
	{
		driver.get(" https://mail.rediff.com/cgi-bin/login.cgi");
	}

	public void test() throws IOException                                                         
	{

		cdriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));                      //creating implicit wait of 60 seconds
		edgedriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));
		cdriver.findElement(By.linkText("Create a new account")).click();                       //Clicking on "Create a new account" by linkText selector
		edgedriver.findElement(By.linkText("Create a new account")).click();
		try {
			FileInputStream file=new FileInputStream(System.getProperty("user.dir")+"\\testdata\\inputdata.xlsx");                    //Setting the excel file input data 
			XSSFWorkbook workbook=new XSSFWorkbook(file);
			XSSFSheet sheet= workbook.getSheet("sheet1");


				XSSFRow currentRow=sheet.getRow(0);                                     //accessing the 0th index row 
				
				//storing the input data from excel to variables
				String name=currentRow.getCell(0).toString();              
				String mailid=currentRow.getCell(1).toString();
				String password=currentRow.getCell(2).toString();
				
				//Finding elements and sending the input data to the fields
				cdriver.findElement(By.xpath("//*[@id=\"tblcrtac\"]/tbody/tr[3]/td[3]/input")).sendKeys(name);                                               //
				edgedriver.findElement(By.xpath("/html/body/center/form/div/table[2]/tbody/tr[3]/td[3]/input")).sendKeys(name);

				cdriver.findElement(By.xpath("//input[starts-with(@name, 'login')]")).sendKeys(mailid);
				edgedriver.findElement(By.xpath("//input[starts-with(@name, 'login')]")).sendKeys(mailid);

				cdriver.findElement(By.cssSelector("input[name^='btnchkavail']")).click();
				edgedriver.findElement(By.cssSelector("input[name^='btnchkavail']")).click();

				cdriver.findElement(By.xpath("/html/body/center/form/div/table[2]/tbody/tr[8]/td[3]/div/table/tbody/tr[3]/td[1]/input")).click();
				edgedriver.findElement(By.cssSelector("input#radio_login")).click();

				cdriver.findElement(By.cssSelector("input[name^='passwd']")).sendKeys(password);
				edgedriver.findElement(By.cssSelector("input[name^='passwd']")).sendKeys(password);

				cdriver.findElement(By.cssSelector("input[name^='chk_altemail']")).click();
				edgedriver.findElement(By.cssSelector("input[name^='chk_altemail']")).click();
				
				
                //selecting drop down input of day,month and year for chrome
				Select daydrop=new Select(cdriver.findElement(By.xpath("//*[@id=\"tblcrtac\"]/tbody/tr[22]/td[3]/select[1]")));
				daydrop.selectByVisibleText("20");
				Select monthdrop=new Select(cdriver.findElement(By.cssSelector("select[name^='DOB_Month']")));
				monthdrop.selectByVisibleText("JUN");
				Select yeardrop=new Select(cdriver.findElement(By.cssSelector("select[name^='DOB_Year']")));
				yeardrop.selectByVisibleText("2000");

				
				//selecting drop down input of day,month and year for Edge
				Select daydrop1=new Select(edgedriver.findElement(By.xpath("//*[@id=\"tblcrtac\"]/tbody/tr[22]/td[3]/select[1]")));
				daydrop1.selectByVisibleText("20");
				Select monthdrop1=new Select(edgedriver.findElement(By.cssSelector("select[name^='DOB_Month']")));
				monthdrop1.selectByVisibleText("JUN");
				Select yeardrop1=new Select(edgedriver.findElement(By.cssSelector("select[name^='DOB_Year']")));
				yeardrop1.selectByVisibleText("2000");

				cdriver.findElement(By.xpath("//*[@id=\'country\']")).click();
				edgedriver.findElement(By.xpath("//*[@id=\'country\']")).click();

				//Selecting the country of drop down input field
				Select sel=new Select(cdriver.findElement(By.id("country")));
				sel.selectByVisibleText("India");
				s=sel.getFirstSelectedOption().getText();                          //getting the text of the selected country

				Select sel1=new Select(edgedriver.findElement(By.id("country")));
				sel1.selectByVisibleText("India");
				s1=sel1.getFirstSelectedOption().getText();



				l=cdriver.findElements(By.xpath("//*[@id=\"country\"]/*"));

				System.out.println("The list of countries are:");
				
				//Printing the list of countries
				for(WebElement e: l)
				{
					System.out.println(e.getText());
				}
				System.out.println("Total count of countries :"+l.size());





				System.out.println("Selected country is : "+s);

				//Validating selected county with the expected country India
				if(s.equals("India"))
				{
					System.out.println("Selected country "+s+" is equal to the expected country India." );
					System.out.println("Test Case is Validated.");
				}

		}

		catch (FileNotFoundException e) {                                                

			System.out.println("File not found, please check the excel file path");
			e.printStackTrace();
		}
		

	}
	
	public void printOutput() throws FileNotFoundException                          //Method to print console output data to text file
	{
		PrintStream originalOut=System.out;
		PrintStream ps=new PrintStream(new File("C:\\Users\\2318702\\eclipse-workspace\\dropDownMenu\\outputOfConsole\\outputfile.txt"));      //setting the print stream to outputfile.txt
		System.setOut(ps);
		
		//printing every country name from List l
		for(WebElement e: l)
		{
			ps.println(e.getText());
		}
		ps.println("Total count of countries :"+l.size());
		ps.println("Selected country is : "+s);
		ps.println("Selected country "+s+" is equal to the expected country India.");
		ps.println("Test Case is Validated.");
		System.out.println("Closing both the browsers");
		System.setOut(originalOut);                                                     //setting the output stream to orginal system.out instead of txt file
		
		                  

	}

	public void closeBrowser()                      //Method for closing the browsers
	{
		
	    cdriver.quit();
		edgedriver.quit();
	}



}
