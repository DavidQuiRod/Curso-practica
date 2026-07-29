package liverpool.pages;

import org.openqa.selenium.WebDriver;

import liverpool.locators.SearchResultsLocators;

public class SearchResultsPage {

    private final WebDriver driver;

    public SearchResultsPage(WebDriver driver) {
        this.driver = driver;
    }

    public boolean hasResultsPageLoaded() {
        // Validación mínima (heurística): que exista el main y haya cambiado el título o URL.
        boolean containerPresent = !driver.findElements(SearchResultsLocators.RESULTS_CONTAINER).isEmpty();
        boolean urlChanged = driver.getCurrentUrl().toLowerCase().contains("search")
                || driver.getCurrentUrl().toLowerCase().contains("s=")
                || driver.getCurrentUrl().toLowerCase().contains("busca")
                || driver.getCurrentUrl().toLowerCase().contains("q=");
        return containerPresent && (urlChanged || driver.getTitle() != null);
    }
}
