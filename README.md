# Teste Web - Blog Agi

Automacao de testes web para a funcionalidade de busca do Blog do Agi, utilizando Selenium WebDriver + TestNG + Java.

---

## Sumario

- [Visao Geral](#visao-geral)
- [Estrutura do Projeto](#estrutura-do-projeto)
- [Pre-requisitos](#pre-requisitos)
- [Instalacao](#instalacao)
- [Configuracao](#configuracao)
- [Executando os Testes](#executando-os-testes)
- [Relatorio de Resultados](#relatorio-de-resultados)
- [Decisoes Tecnicas](#decisoes-tecnicas)
- [Pipeline CI/CD](#pipeline-cicd)

---

## Visao Geral

Este projeto automatiza os cenarios de busca de artigos no Blog do Agi, validando o comportamento da funcionalidade de pesquisa em diferentes situacoes.

### Cenarios Automatizados

| ID   | Cenario                              | Resultado Esperado                                  |
|------|--------------------------------------|-----------------------------------------------------|
| CT01 | Busca por termo valido (investimento)| Pagina retorna artigos relacionados ao termo        |
| CT02 | Busca por termo invalido (aleatorio) | Pagina indica que nenhum resultado foi encontrado   |

---

## Estrutura do Projeto
```
teste-web-agi/
├── .github/
│   └── workflows/
│       └── ci.yml              # Pipeline GitHub Actions
├── src/
│   └── test/
│       └── java/
│           └── com/
│               └── agi/
│                   └── BlogAgiTest.java
├── pom.xml                     # Dependencias e configuracoes Maven
└── README.md
```

---

## Pre-requisitos

| Ferramenta    | Versao minima | Download                                      |
|---------------|---------------|-----------------------------------------------|
| Java JDK      | 17            | [adoptium.net](https://adoptium.net)          |
| Maven         | 3.9.x         | [maven.apache.org](https://maven.apache.org)  |
| Google Chrome | Qualquer      | [google.com/chrome](https://google.com/chrome)|

---

## Instalacao

**1. Clone o repositorio:**
```bash
git clone https://github.com/fernandaaime/teste-web-agi.git
cd teste-web-agi
```

**2. Verifique o Java:**
```bash
java -version
# Esperado: openjdk version "17.x.x"
```

**3. Verifique o Maven:**
```bash
mvn -version
# Esperado: Apache Maven 3.9.x
```

> O WebDriverManager baixa o ChromeDriver automaticamente. Nao e necessario instalar o ChromeDriver manualmente.

---

## Configuracao

O projeto nao requer configuracao adicional. O WebDriverManager detecta automaticamente a versao do Chrome instalado e faz o download do driver compativel.

Para rodar em modo **headless** (sem abrir o navegador), nenhuma configuracao adicional e necessaria — o modo headless ja esta configurado para ambientes de CI/CD.

---

## Executando os Testes

**Executar todos os testes:**
```bash
mvn test
```

**Executar um teste especifico:**
```bash
mvn test -Dtest=BlogAgiTest#deveBuscarTermoValidoERetornarResultados
```

**Executar com log detalhado:**
```bash
mvn test -X
```

---

## Relatorio de Resultados

Apos a execucao, o relatorio e gerado automaticamente em:
```
target/surefire-reports/
├── BlogAgiTest.txt        # Relatorio em texto
└── TEST-com.agi.BlogAgiTest.xml  # Relatorio XML
```

Para visualizar o relatorio HTML:
```bash
mvn surefire-report:report
# Abra: target/site/surefire-report.html
```

---

## Decisoes Técnicas

**Selenium WebDriver**
Framework de automacao web, com suporte oficial para Java e integracao nativa com o Chrome.

**WebDriverManager**
Elimina a necessidade de baixar o ChromeDriver manualmente. A biblioteca detecta a versao do Chrome instalado, garantindo compatibilidade em qualquer maquina (Windows, Linux, macOS).

**TestNG**
Escolhido por oferecer anotacoes como `@BeforeClass` e `@AfterClass`, que garantem que o navegador seja iniciado uma unica vez antes de todos os testes e encerrado ao final.

**Modo Headless**
Os testes rodam sem abrir o navegador visualmente, permitindo execucao em ambientes de CI/CD como o GitHub Actions.

**Estrategia de Busca**
O teste tenta interagir com o botao de busca (lupa) do blog. Caso o seletor nao seja encontrado por atualizacoes no layout, utiliza a URL de busca padrao do WordPress (`?s=termo`), garantindo estabilidade da execucao.

---

## 🔄 Pipeline CI/CD

O projeto possui pipeline configurada no GitHub Actions que executa automaticamente os testes a cada `push` ou `pull request` na branch `main`.
```yaml
```

Acesse os resultados em:
https://github.com/fernandaaime/teste-web-agi/actions