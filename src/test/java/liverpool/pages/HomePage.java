package liverpool.pages;

import java.nio.file.Files;
import java.time.Duration;
import java.io.*;
import java.util.Properties;

import org.openqa.selenium.support.PageFactory;
import utils.utilities;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import liverpool.locators.HomeLocators;

public class HomePage {
    utilities utilsFuntions;
    Properties props = new Properties(); //properties para leer un documento
    private final WebDriver driver;
    private static final String URL = "https://www.liverpool.com.mx/";
    private static final String URLSelenium="https://www.selenium.dev/selenium/web/web-form.html";
    String directorio=System.getProperty("user.dir");
    //Objetos web
    @FindBy(how = How.XPATH, using = "(//*[normalize-space(text())='Categorías'])[1]")
    public WebElement menuCategorias;
    @FindBy(how= How.XPATH, using = "(//*[@class='line-clamp-1 text-start'])[1]")
    public WebElement categoriaMujer;

    @FindBy(how= How.XPATH, using = "//*[@id=\"my-text-id\"]")
    private WebElement txtInputText;
    @FindBy(xpath = "/html/body/main/div/form/div/div[1]/label[2]/input")
    private WebElement txtInputPassword;


    //Constructor
    public HomePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver,this);  //Sin el codigo anterior no logra agarrar los xpath que declares con @FindBy
    }

    public void open() {
        try{
            utilsFuntions = new utilities(driver);
            driver.get(URL);
            utilsFuntions.takeScrenShot("Se abre el navegador");
        } catch (RuntimeException e) {
            System.out.println("No se logro abrir el navegador revisa tu driver");
            utilsFuntions.finPrueba();
            throw new RuntimeException(e);
        }

    }
    public void abrirNavegadorConLaUrlDesdeHomePage(String urlName){
        try{
            utilsFuntions = new utilities(driver);
            String filePathEnviroment=directorio+"\\src\\test\\java\\config\\environments.properties"; //Archivo de donde estraeremos la url
            /* se declaro un varible global props para poder generar una ruta de lectura de variables y ambientes en los directorios que nosotros indiquemos*/
            props.load(Files.newInputStream(new File(filePathEnviroment).toPath())); //Se genera el objecto para leer el archivo
            String url=String.valueOf(props.get(urlName)); // Se declara una variable cadena para que este obtenga el valor del objeto en forma de cadena de la ruta donde la indicamos
            driver.get(url);
            utilsFuntions.takeScrenShot("Se abre el navegador");
        } catch (IOException |RuntimeException e) {
            System.out.println("No se logro abrir el navegador revisa tu driver");
            utilsFuntions.finPrueba();
            throw new RuntimeException(e);
        }

    }
    public void search(String text) {
        WebElement input;
        try {
            input = driver.findElement(HomeLocators.SEARCH_INPUT);
        } catch (Exception e) {
            input = driver.findElement(HomeLocators.SEARCH_INPUT_FALLBACK);
        }

        input.clear();
        input.sendKeys(text);
        input.sendKeys(Keys.ENTER);
    }

    public void goToMenuCategorias() {
        utilsFuntions = new utilities(driver);
        try{
            utilsFuntions.hightLight(menuCategorias);
            utilsFuntions.takeScrenShot("Se da un click en le menu Categorias");
            menuCategorias.click();
            utilsFuntions.unHightLight(menuCategorias);
        }catch (Exception e){
            System.out.println("No se encontro el menu");
            utilsFuntions.finPrueba();
            throw  e;
        }
    }

    public void selectWomenCategory() {
        utilsFuntions = new utilities(driver);
        try{
            utilsFuntions.hightLight(categoriaMujer);
            utilsFuntions.takeScrenShot("Se da un click en le menu de mujeres");
            utilsFuntions.unHightLight(categoriaMujer);
            categoriaMujer.click();
        }catch (Exception e){
            System.out.println("No se encontro la categoria de mujer");
            utilsFuntions.finPrueba();
            throw  e;
        }
    }


    public void selectJackets() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
        WebElement jackets = wait.until(ExpectedConditions.elementToBeClickable(HomeLocators.CHAMARRAS));
        jackets.click();
    }

    public void ingresarValorEnTextInput(String valor){
        utilsFuntions = new utilities(driver);
        try{
            utilsFuntions.elemtIsVisible(txtInputPassword);
            utilsFuntions.hightLight(txtInputText);
            txtInputText.sendKeys(valor);
            utilsFuntions.takeScrenShot("Se ingresa valor en el text input");
            utilsFuntions.unHightLight(txtInputText);
        }catch (Exception e){
            System.out.println("No se logro encontrar el campo donde ibas a igresar el texto");
            utilsFuntions.finPrueba();
            throw  e;
        }

    }
    public void ingresarValorEnCampoPassword(String valor) {
        utilsFuntions = new utilities(driver);
        try {
            utilsFuntions.elemtIsVisible(txtInputPassword);
            utilsFuntions.hightLight(txtInputPassword);
            txtInputPassword.sendKeys(valor);
            utilsFuntions.takeScrenShot("Se ingresa valor en el campo password");
            utilsFuntions.unHightLight(txtInputPassword);
        } catch (Exception e) {
            System.out.println("No se logro encontrar el campo password ibas a igresar el texto");
            utilsFuntions.finPrueba();
            throw e;
        }
    }
    public void reporteCompleto()throws Exception{
        utilsFuntions.crearReporteWord();
    }

}