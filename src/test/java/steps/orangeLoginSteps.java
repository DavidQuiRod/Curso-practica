/**
 * User: David Quiroz
 * Date: 07/08/2026
 * Time: 04:27 p. m.
 *
 * @author: David Quiroz
 */

package steps;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import liverpool.core.DriverManager;
import org.openqa.selenium.WebDriver;
import pageObjects.orangeLoginPageObjects;

public class orangeLoginSteps {
    orangeLoginPageObjects orangeActions;

    @When("El usuario ingresa el valor {string} en el campo username")
    public void ingresarValorEnElCampoUsername(String valorAIngresarEnUsername) {
        orangeActions = new orangeLoginPageObjects(DriverManager.getDriver());
        orangeActions.ingresarValorEnUsername(valorAIngresarEnUsername);
        System.out.println("Dato ingresado en username " + valorAIngresarEnUsername);
    }

    @When("El usuario ingresa el valor {string} en el campo password")
    public void ingresarValorEnELCampoPassword(String valorAIngresarEnPassword) {
        orangeActions = new orangeLoginPageObjects(DriverManager.getDriver());
        orangeActions.ingresarValorEnPassword(valorAIngresarEnPassword);
        System.out.println("Dato ingresado en password " + valorAIngresarEnPassword);
    }

    @Then("El usuario da clic en el boton login")
    public void darClicEnElBotonLogin() {
        orangeActions = new orangeLoginPageObjects(DriverManager.getDriver());
        orangeActions.elUsuarioDaClicEnBotonLogin();
    }
    @When("El usuario ingresa el valor {string} en el campo {string} de la pagina")
    public void ingresarValorEnCampo(String valorAIngresar, String elementoAlQueAPuntoElValorIngresado){
        orangeActions =new orangeLoginPageObjects(DriverManager.getDriver());
        orangeActions.elUsuarioIngresaValor(valorAIngresar, elementoAlQueAPuntoElValorIngresado);
    }
    @Then("El usuario da clic en {string} en el navbar de la pagina Dashboard")
    public void darClicEnOpcionesNavbarPaginaDeInicio(String nameButton){
        orangeActions= new orangeLoginPageObjects(DriverManager.getDriver());
        orangeActions.elUsuarioDaClicEnLasOpcionesDelNavbar(nameButton);
    }
}
