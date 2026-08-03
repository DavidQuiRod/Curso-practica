/**
 * User: David Quiroz
 * Date: 03/08/2026
 * Time: 01:30 p. m.
 *
 * @author: David Quiroz
 */

package generalSteps;

import io.cucumber.java.en.Given;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Properties;

public class LoginGeneral {
    Properties props = new Properties(); //properties para leer un documento
    WebDriver driver; //Inicializar driver
    String directorio = System.getProperty("user.dir");
    WebDriver dvr;
    ChromeOptions options= new ChromeOptions();
    EdgeOptions optionn=new EdgeOptions();

    @Given("El usuario abre la pagina {string} en el navegador de {string}")
    public void openBrowser(String urlName, String browsername) throws  InterruptedException{
        String navegador=browsername.toUpperCase();
        if(navegador.equals("CHROME")){
            String rutaFinalChrome=directorio+"\\chromedriver_v150.exe";
            System.setProperty("webdriver.chrome.driver", rutaFinalChrome);
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--remote-allow-origins=*");
            driver= new ChromeDriver(options);
        }/* Se comenta esta seccion para validar la funcionalidad del driver de chrome
        else if(navegador.equals("EDGE") || navegador.equals("MICROSOFT EDGE") || navegador.equals("MICROSOFTEDGE")){
            String rutaFinalEDGE=directorio+"\\Config\\Drivers\\Edge\\msedgedriver.exe";
            System.setProperty("webdriver.edge.driver",rutaFinalEDGE);
            EdgeOptions option=new EdgeOptions();
            options.addArguments("--remote-allow-origins=*");
            driver= new EdgeDriver(option);
        }*/ else {
            System.out.println("Ingresar uno de los dos nombres de los navegador 'EDGE' o 'CHROME'");
            System.exit(0);
        }
        driver.manage().window().maximize();
        //El filepathenviroment tiene que aputar a al ruta donde se encuentra tu archivo
        String filePathEnviroment=directorio+"\\src\\test\\java\\config\\environments.properties";
        try{
            /* se declaro un varible global props para poder generar una ruta de lectura de variables y ambientes en los directorios que nosotros indiquemos*/
            props.load(Files.newInputStream(new File(filePathEnviroment).toPath())); //Se genera el objecto para leer el archivo
            String url=String.valueOf(props.get(urlName)); // Se declara una variable cadena para que este obtenga el valor del objeto en forma de cadena de la ruta donde la indicamos
            Thread.sleep(4000);
            driver.get(url); // una vez obtenido y convertido el valor a cadena se lo pasamos a driver.get para ingresar la ruta
        } catch (IOException e){ // en caso de no encontrarse el directorio se genera una excepcion para no fallar el caso
            e.printStackTrace();
        }
    }

    /*
       public void openUrlEnNavegador(String urlName){
        String rutaFinalChrome= directorio+"\\chromedriver_v150.exe"; //Ruta de dond obtenemos el driver en este caso es el chrome driver.
        String rutaDeDondeObtenemosLaUrl=directorio+"\\src\\test\\java\\config\\environments.properties"; //Se declara esta variable para extraer la url del aplicativo que vamos a utilizar
        System.setProperty("webdriver.chrome.driver", rutaFinalChrome); //Ingresamos los terminos para utilizar el driver y de donde lo obtenemos para utilizarlo
        ChromeOptions options= new ChromeOptions(); //Se utilizan las para ingresar mas acciones
        options.addArguments("--remote-allow-origins=*"); // Se agregan argumentos
        dvr= new ChromeDriver(options); //Inicializamos el driver
        dvr.manage().window().maximize(); //Maximizamos la pagina del navegador
        try{
            props.load(Files.newInputStream(new File(rutaDeDondeObtenemosLaUrl).toPath()));//Se genera el objecto para leer el archivo
    String url=String.valueOf(props.get(urlName)); // Se declara una variable cadena para que este obtenga el valor del objeto en forma de cadena de la ruta donde la indicamos
            dvr.get(url); // una vez obtenido y convertido el valor a cadena se lo pasamos a driver.get para ingresar la ruta
} catch (IOException|RuntimeException e) {
        System.out.println("No se logro abrir el navegador revisa tu driver");
finPrueba();
            throw new RuntimeException(e);
        }
                }
    */

}
