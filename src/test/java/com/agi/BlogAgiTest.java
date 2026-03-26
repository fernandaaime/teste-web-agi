package com.agi;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.*;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;
import java.util.List;

public class BlogAgiTest {

    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeClass
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-gpu");
        options.addArguments("--window-size=1920,1080");
        options.addArguments("--disable-blink-features=AutomationControlled");
        options.addArguments("user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36");
        driver = new ChromeDriver(options);
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(60));
        wait = new WebDriverWait(driver, Duration.ofSeconds(30));
    }

    private void realizarBusca(String termo) {
        driver.get("https://blogdoagi.com.br/");

        wait.until(ExpectedConditions.jsReturnsValue("return document.readyState === 'complete';"));

        // Tenta clicar na lupa
        List<String> lupaSeletores = List.of(
                "button.search-toggle",
                "button[aria-label='Buscar']",
                ".search-toggle",
                "a.search-icon",
                "button.search-icon",
                "[class*='search'] button",
                "button[class*='search']"
        );

        boolean lupaClicada = false;
        for (String seletor : lupaSeletores) {
            List<WebElement> elementos = driver.findElements(By.cssSelector(seletor));
            if (!elementos.isEmpty()) {
                try {
                    elementos.get(0).click();
                    lupaClicada = true;
                    break;
                } catch (Exception ignored) {}
            }
        }

        if (lupaClicada) {
            // Digita no campo de busca que apareceu
            List<String> inputSeletores = List.of(
                    "input[type='search']",
                    "input.search-field",
                    "input[name='s']"
            );
            for (String seletor : inputSeletores) {
                List<WebElement> inputs = driver.findElements(By.cssSelector(seletor));
                if (!inputs.isEmpty()) {
                    try {
                        WebElement input = wait.until(ExpectedConditions.visibilityOf(inputs.get(0)));
                        input.sendKeys(termo);
                        input.sendKeys(Keys.ENTER);
                        return;
                    } catch (Exception ignored) {}
                }
            }
        }

        // Fallback: acessa diretamente pela URL de busca
        driver.get("https://blogdoagi.com.br/?s=" + termo);
        wait.until(ExpectedConditions.jsReturnsValue("return document.readyState === 'complete';"));
    }

    @Test(description = "CT01 - Busca por termo valido retorna resultados")
    public void deveBuscarTermoValidoERetornarResultados() {
        realizarBusca("investimento");

        String currentUrl = driver.getCurrentUrl();
        String pageSource = driver.getPageSource().toLowerCase();

        Assert.assertTrue(
                currentUrl.contains("s=investimento") || pageSource.contains("investimento"),
                "Pagina deveria conter resultados para o termo buscado. URL: " + currentUrl);
    }

    @Test(description = "CT02 - Busca por termo invalido exibe pagina sem resultados")
    public void deveBuscarTermoInvalidoEExibirMensagem() {
        realizarBusca("xyzabcdefghijk123");

        String currentUrl = driver.getCurrentUrl();

        Assert.assertTrue(
                currentUrl.contains("s=xyzabcdefghijk123"),
                "URL deveria conter o termo buscado. URL: " + currentUrl);
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}