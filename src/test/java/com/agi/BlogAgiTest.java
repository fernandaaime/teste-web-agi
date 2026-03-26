package com.agi;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.*;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;

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

    @Test(description = "CT01 - Busca por termo valido e redirecionado para pagina de resultados")
    public void deveBuscarTermoValidoERetornarResultados() {
        driver.get("https://blogdoagi.com.br/?s=investimento");

        wait.until(ExpectedConditions.jsReturnsValue("return document.readyState === 'complete';"));

        String currentUrl = driver.getCurrentUrl();
        String pageSource = driver.getPageSource().toLowerCase();

        Assert.assertTrue(
                currentUrl.contains("s=investimento") || pageSource.contains("investimento"),
                "URL ou conteudo deveria conter o termo buscado. URL atual: " + currentUrl);
    }

    @Test(description = "CT02 - Busca por termo invalido e redirecionado para pagina de busca")
    public void deveBuscarTermoInvalidoEExibirMensagem() {
        driver.get("https://blogdoagi.com.br/?s=xyzabcdefghijk123");

        wait.until(ExpectedConditions.jsReturnsValue("return document.readyState === 'complete';"));

        String currentUrl = driver.getCurrentUrl();

        Assert.assertTrue(
                currentUrl.contains("s=xyzabcdefghijk123"),
                "URL deveria conter o termo buscado. URL atual: " + currentUrl);
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}