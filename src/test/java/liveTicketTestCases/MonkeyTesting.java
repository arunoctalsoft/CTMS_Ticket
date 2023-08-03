/**
 * 
 */
package liveTicketTestCases;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

/**
 * @author Octalsoft-86
 *
 */

public class MonkeyTesting {
	static WebDriver driver;
	public static void attack() {
		JavascriptExecutor js =(JavascriptExecutor)driver;
		String Script=" javascript: (function() {\r\n"
				+ "    function callback() {\r\n"
				+ "        gremlins.createHorde({\r\n"
				+ "            species: [gremlins.species.clicker(),gremlins.species.formFiller(),gremlins.species.scroller(),gremlins.species.typer()],\r\n"
				+ "            mogwais: [gremlins.mogwais.alert(),gremlins.mogwais.fps(),gremlins.mogwais.gizmo()],\r\n"
				+ "            strategies: [gremlins.strategies.distribution(),gremlins.strategies.allTogether(),gremlins.strategies.bySpecies()]\r\n"
				+ "        }).unleash();\r\n"
				+ "    }\r\n"
				+ "    var s = document.createElement(\"script\");\r\n"
				+ "    s.src = \"https://unpkg.com/gremlins.js\";\r\n"
				+ "    if (s.addEventListener) {\r\n"
				+ "        s.addEventListener(\"load\", callback, false);\r\n"
				+ "    } else if (s.readyState) {\r\n"
				+ "        s.onreadystatechange = callback;\r\n"
				+ "    }\r\n"
				+ "    document.body.appendChild(s);\r\n"
				+ "    })()";
		js.executeAsyncScript(Script);
	}
	
	@Test
	public static void monkey1() {
		driver =new ChromeDriver();
		driver.manage().window().maximize();
		driver.get("www.googl.com");
		attack();
		
	}
	
}