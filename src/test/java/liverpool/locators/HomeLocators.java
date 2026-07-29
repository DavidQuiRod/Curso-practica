package liverpool.locators;

import org.openqa.selenium.By;

public class HomeLocators {

    // Nota: estos locators pueden cambiar en Liverpool.
    public static final By SEARCH_INPUT = By.cssSelector("input[type='search']");
    public static final By SEARCH_INPUT_FALLBACK = By.cssSelector("input[name='search']");
    public static final By MENU = By.xpath("(//*[normalize-space(text())='Categorías'])[1]");
    public static final By SELECT_MUJER = By.xpath("(//*[@class='line-clamp-1 text-start'])[1]");
    public static final By CHAMARRAS = By.xpath("//*[@data-src='https://assetspwa.liverpool.com.mx/assets/digital/landing/mujer/2025/blp_mujer_chamarras_1025.jpg']");
}
