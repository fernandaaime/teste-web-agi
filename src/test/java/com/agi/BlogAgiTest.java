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
        options.addArguments("--start-maximized");
        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    @Test(description = "CT01 - Busca por termo valido retorna resultados")
    public void deveBuscarTermoValidoERetornarResultados() {
        driver.get("https://blogdoagi.com.br/?s=investimento");

        WebElement resultado = wait.until(ExpectedConditions
                .visibilityOfElementLocated(By.cssSelector("article")));
        Assert.assertTrue(resultado.isDisplayed(),
                "Deveria exibir artigos para busca valida");
    }

    @Test(description = "CT02 - Busca por termo invalido exibe mensagem de nenhum resultado")
    public void deveBuscarTermoInvalidoEExibirMensagem() {
        driver.get("https://blogdoagi.com.br/?s=xyzabcdefghijk123");

        WebElement mensagem = wait.until(ExpectedConditions
                .visibilityOfElementLocated(By.cssSelector(".no-results, .nothing-found, .not-found")));
        Assert.assertTrue(mensagem.isDisplayed(),
                "Deveria exibir mensagem de nenhum resultado encontrado");
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}