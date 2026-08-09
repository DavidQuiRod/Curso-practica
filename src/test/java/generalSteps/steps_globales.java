/**
 * User: David Quiroz
 * Date: 09/08/2026
 * Time: 01:55 p. m.
 *
 * @author: David Quiroz
 */

package generalSteps;

import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;

public class steps_globales {
    @When("El usuario da clic {string} en la pagina {string}")
    public void elUsuarioDaClic(String elementoWebAlQueVanADarClic,String nombreDePagina){
        System.out.println(elementoWebAlQueVanADarClic+" "+nombreDePagina);
        /*String valor = PageService.obtenerValorElemento(nombreDePagina, elementoWebAlQueVanADarClic);
        ScenarioContext.set("ULTIMO_VALOR", valor);
        System.out.println("Valor recuperado: " + valor);*/
    }
}

