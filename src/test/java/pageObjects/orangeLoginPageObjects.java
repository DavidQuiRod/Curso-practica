/**
 * User: David Quiroz
 * Date: 07/08/2026
 * Time: 04:27 p. m.
 *
 * @author: David Quiroz
 */

package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import utils.utilities;

public class orangeLoginPageObjects {
    utilities utilsFuntions;
    WebDriver driver;

    //Inicio de seccion de objetos web
    @FindBy(how= How.XPATH, using = "//*[@id=\"app\"]/div[1]/div/div[1]/div/div[2]/div[2]/form/div[1]/div/div[2]/input")
    public WebElement txtUsername;
    @FindBy(how= How.XPATH, using = "//*[@id=\"app\"]/div[1]/div/div[1]/div/div[2]/div[2]/form/div[2]/div/div[2]/input")
    public WebElement txtPassword;
    @FindBy(how= How.XPATH, using = "//*[@id=\"app\"]/div[1]/div/div[1]/div/div[2]/div[2]/form/div[3]/button")
    public WebElement btnLogin;
    //Finde seccion de objetos web
    //Constructor de la clase
    public orangeLoginPageObjects(WebDriver driver){
        this.driver=driver;
        PageFactory.initElements(driver,this);  //Sin el codigo anterior no logra agarrar los xpath que declares con @FindBy
    }

    public void ingresarValorEnUsername(String valorDeUsername){
        utilsFuntions = new utilities(driver);
        try{
            utilsFuntions.elemtIsVisible(txtUsername);
            utilsFuntions.hightLight(txtUsername);
            txtUsername.sendKeys(valorDeUsername);
            utilsFuntions.takeScrenShot("Se ingresa valor en el campo username");
            utilsFuntions.unHightLight(txtUsername);
        }catch (Exception e){
            System.out.println("No se logro encontrar el campo donde ibas a igresar el texto");
            utilsFuntions.finPrueba();
            throw  e;
        }
    }

    public void ingresarValorEnPassword(String valorDePassword){
        utilsFuntions = new utilities(driver);
        try{
            utilsFuntions.elemtIsVisible(txtPassword);
            utilsFuntions.hightLight(txtPassword);
            txtPassword.sendKeys(valorDePassword);
            utilsFuntions.takeScrenShot("Se ingresa valor en el campo password");
            utilsFuntions.unHightLight(txtPassword);
        }catch (Exception e){
            System.out.println("No se logro encontrar el campo donde ibas a igresar el texto");
            utilsFuntions.finPrueba();
            throw  e;
        }
    }

    public void elUsuarioDaClicEnBotonLogin(){
        utilsFuntions= new utilities(driver);
        try {
            utilsFuntions.elemtIsVisible(btnLogin);
            utilsFuntions.hightLight(btnLogin);
            utilsFuntions.takeScrenShot("Se da clic en el boton login");
            btnLogin.click();
        }catch (Exception e){
            System.out.println("No se logro dar clic en el boton login");
            utilsFuntions.finPrueba();
            throw  e;
        }
    }
    public void elUsuarioIngresaValor(String textValue, String nameTxt){
        String inputName=nameTxt.toLowerCase();
        String xpathCampo="//input[@name='"+inputName+"' and @placeholder='"+nameTxt+"']";
        WebElement txtCampo=driver.findElement(By.xpath(xpathCampo));
        utilsFuntions = new utilities(driver);
        try{
            utilsFuntions.elemtIsVisible(txtCampo);
            utilsFuntions.hightLight(txtCampo);
            txtCampo.sendKeys(textValue);
            utilsFuntions.takeScrenShot("Se ingresa valor en el campo "+nameTxt);
            utilsFuntions.unHightLight(txtCampo);
        }catch (Exception e){
            System.out.println("No se logro encontrar el campo "+nameTxt+" donde ibas a igresar el texto");
            utilsFuntions.finPrueba();
            throw  e;
        }
    }
}
