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
    @FindBy(how = How.XPATH, using = "//*[@id=\"app\"]/div[1]/div/div[1]/div/div[2]/div[2]/form/div[1]/div/div[2]/input")
    public WebElement txtUsername;
    @FindBy(how = How.XPATH, using = "//*[@id=\"app\"]/div[1]/div/div[1]/div/div[2]/div[2]/form/div[2]/div/div[2]/input")
    public WebElement txtPassword;
    @FindBy(how = How.XPATH, using = "//*[@id=\"app\"]/div[1]/div/div[1]/div/div[2]/div[2]/form/div[3]/button")
    public WebElement btnLogin;

    //Finde seccion de objetos web
    //Constructor de la clase
    public orangeLoginPageObjects(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);  //Sin el codigo anterior no logra agarrar los xpath que declares con @FindBy
    }

    public void ingresarValorEnUsername(String valorDeUsername) {
        utilsFuntions = new utilities(driver);
        try {
            utilsFuntions.elemtIsVisible(txtUsername);
            utilsFuntions.hightLight(txtUsername);
            txtUsername.sendKeys(valorDeUsername);
            utilsFuntions.takeScrenShot("Se ingresa valor en el campo username");
            utilsFuntions.unHightLight(txtUsername);
        } catch (Exception e) {
            System.out.println("No se logro encontrar el campo donde ibas a igresar el texto");
            utilsFuntions.finPrueba();
            throw e;
        }
    }

    public void ingresarValorEnPassword(String valorDePassword) {
        utilsFuntions = new utilities(driver);
        try {
            utilsFuntions.elemtIsVisible(txtPassword);
            utilsFuntions.hightLight(txtPassword);
            txtPassword.sendKeys(valorDePassword);
            utilsFuntions.takeScrenShot("Se ingresa valor en el campo password");
            utilsFuntions.unHightLight(txtPassword);
        } catch (Exception e) {
            System.out.println("No se logro encontrar el campo donde ibas a igresar el texto");
            utilsFuntions.finPrueba();
            throw e;
        }
    }

    public void elUsuarioDaClicEnBotonLogin() {
        utilsFuntions = new utilities(driver);
        try {
            utilsFuntions.elemtIsVisible(btnLogin);
            utilsFuntions.hightLight(btnLogin);
            utilsFuntions.takeScrenShot("Se da clic en el boton login");
            btnLogin.click();
        } catch (Exception e) {
            System.out.println("No se logro dar clic en el boton login");
            utilsFuntions.finPrueba();
            throw e;
        }
    }

    public void elUsuarioIngresaValor(String textValue, String nameTxt) {
        String inputName = nameTxt.toLowerCase();
        String xpathCampo = "//input[@name='" + inputName + "' and @placeholder='" + nameTxt + "']";
        WebElement txtCampo = driver.findElement(By.xpath(xpathCampo));
        utilsFuntions = new utilities(driver);
        try {
            utilsFuntions.elemtIsVisible(txtCampo);
            utilsFuntions.hightLight(txtCampo);
            txtCampo.sendKeys(textValue);
            utilsFuntions.takeScrenShot("Se ingresa valor en el campo " + nameTxt);
            utilsFuntions.unHightLight(txtCampo);
        } catch (Exception e) {
            System.out.println("No se logro encontrar el campo " + nameTxt + " donde ibas a igresar el texto");
            utilsFuntions.finPrueba();
            throw e;
        }
    }

    public void elUsuarioDaClicEnLasOpcionesDelNavbar(String btnOption) {
        utilsFuntions = new utilities(driver);
        String valorDeBotonAElegir = btnOption.toUpperCase();
        System.out.println("El valor de valorDeBotonAElegir es "+valorDeBotonAElegir);
        String xpathDeBoton= "";
        switch (valorDeBotonAElegir) {
            case "ADMIN":
                System.out.println("Admin");
                xpathDeBoton = "//a[contains(@href,'/admin/') and normalize-space()='Admin']";
                break;
            case "PIM":
                System.out.println("PIM");
                xpathDeBoton = "//a[contains(@href,'/pim/') and normalize-space()='PIM']";
                break;
            case "LEAVE":
                System.out.println("Leave");
                xpathDeBoton = "//a[contains(@href,'/leave/') and normalize-space()='Leave']";
                break;
            case "TIME":
                System.out.println("Time");
                xpathDeBoton = "//a[contains(@href,'/time/') and normalize-space()='Time']";
                break;
            case "RECRUITMENT":
                System.out.println("Recruitment");
                xpathDeBoton = "//a[contains(@href,'/recruitment/') and normalize-space()='Recruitment']";
                break;
            case "MY INFO":
                System.out.println("My Info");
                xpathDeBoton = "//a[contains(@href,'viewMyDetails') and normalize-space()='My Info']";
                break;
            case "PERFORMANCE":
                System.out.println("Performance");
                xpathDeBoton = "//a[contains(@href,'/performance/') and normalize-space()='Performance']";
                break;
            case "DASHBOARD":
                System.out.println("Dashboard");
                xpathDeBoton = "//a[contains(@href,'/dashboard/index') and normalize-space()='Dashboard']";
                break;
            case "DIRECTORY":
                System.out.println("Directory");
                xpathDeBoton = "//a[contains(@href,'/directory/') and normalize-space()='Directory']";
                break;
            case "MAINTENANCE":
                System.out.println("Maintenance");
                xpathDeBoton = "//a[contains(@href,'/maintenance/') and normalize-space()='Maintenance']";
                break;
            case "CLAIM":
                System.out.println("Claim");
                xpathDeBoton = "//a[contains(@href,'/claim/') and normalize-space()='Claim']";
                break;
            case "BUZZ":
                System.out.println("Buzz");
                xpathDeBoton = "//a[contains(@href,'/buzz/') and normalize-space()='Buzz']";
                break;
            default:
                System.out.println("Ingresa un valor valido del navbar de opciones del la pagina OrangeHRM en la pagina de inicio de sesion");
                utilsFuntions.finPrueba();
                break;
        }
        try {
            if (xpathDeBoton != "")
                utilsFuntions.darClicEnBoton(xpathDeBoton, btnOption);
        } catch (RuntimeException e) {
            System.out.println("No existe ningun boton con el nombre "+ btnOption);
            utilsFuntions.finPrueba();
            throw new RuntimeException(e);
        }
    }
}
