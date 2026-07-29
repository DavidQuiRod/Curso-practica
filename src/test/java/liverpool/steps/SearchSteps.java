package liverpool.steps;

import org.testng.Assert;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import liverpool.core.DriverManager;
import liverpool.pages.HomePage;
import liverpool.pages.SearchResultsPage;

public class SearchSteps {

    private HomePage homePage;
    private SearchResultsPage resultsPage;

    @Given("que abro la pagina de Liverpool")
    public void openLiverpool() {
        homePage = new HomePage(DriverManager.getDriver());
        resultsPage = new SearchResultsPage(DriverManager.getDriver());
        homePage.open();
    }

    @When("busco el producto {string}")
    public void searchProduct(String product) {
        homePage.search(product);
    }

    @Then("deberia ver resultados relacionados")
    public void validateResults() {
        Assert.assertTrue(resultsPage.hasResultsPageLoaded(),
                "No se detectó carga de resultados (URL/título/DOM). Ajustar locators o validación.");
    }

    @When("navego al menu de categorias")
    public void goToCategoriesMenu() {
        homePage.goToMenuCategorias();
    }

    @When("selecciono la categoria Mujer")
    public void selectWomenCategory() {
        homePage.selectWomenCategory();
    }

    @When("selecciono la categoria Chamarras")
    public void selectJacketsCategory() {
        homePage.selectJackets();
    }

    @When("Ingreso texto al text input")
    public void ingresarTextoEnTextInput()  {
        homePage.ingresarValorEnTextInput("Ejemplo");
    }
    @When("Ingreso texto en el campo password")
    public void ingresoPassword() {
        homePage.ingresarValorEnCampoPassword("Pruebas12131212");
    }
    @When("Finaliza el test y se cierra navegador")
    public void finTest()throws Exception{
        homePage.reporteCompleto();
    }

}
