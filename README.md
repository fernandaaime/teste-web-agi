# Teste Web - Blog Agi

Automacao de testes web para a funcionalidade de busca do Blog do Agi, utilizando Selenium WebDriver + TestNG + Java.

## Cenarios Automatizados

| ID   | Cenario                                      | Resultado Esperado                              |
|------|----------------------------------------------|-------------------------------------------------|
| CT01 | Busca por termo valido (ex: "investimento")  | Pagina retorna artigos relacionados ao termo    |
| CT02 | Busca por termo invalido (ex: caracteres aleatorios) | Pagina indica que nenhum resultado foi encontrado |

## Tecnologias

| Tecnologia        | Versao  | Finalidade                              |
|-------------------|---------|-----------------------------------------|
| Java              | 17      | Linguagem de programacao                |
| Selenium WebDriver| 4.18.1  | Automacao do navegador                  |
| WebDriverManager  | 5.7.0   | Gerenciamento automatico do ChromeDriver|
| TestNG            | 7.9.0   | Framework de testes                     |
| Maven             | 3.9.x   | Gerenciamento de dependencias e build   |

## Pre-requisitos

- Java JDK 17+
- Maven 3.9+
- Google Chrome instalado

## Como executar

Clone o repositorio:
```bash
git clone https://github.com/fernandaaime/teste-web-agi.git
cd teste-web-agi
```

Execute os testes:
```bash
mvn test
```

## Estrutura do projeto
```
teste-web-agi/
├── .github/
│   └── workflows/
│       └── ci.yml          # Pipeline GitHub Actions
├── src/
│   └── test/
│       └── java/
│           └── com/
│               └── agi/
│                   └── BlogAgiTest.java
├── pom.xml
└── README.md
```

## Decisoes tecnicas

**WebDriverManager:** Elimina a necessidade de baixar o ChromeDriver manualmente.

**TestNG:** Escolhido por oferecer anotacoes como `@BeforeClass` e `@AfterClass`, que garantem que o navegador seja iniciado uma unica vez antes de todos os testes e encerrado ao final.

**Modo Headless:** Os testes rodam sem abrir o navegador visualmente, o que permite a execucao em ambientes de CI/CD como o GitHub Actions, onde nao ha interface grafica disponivel.

**Estrategia de busca:** O teste tenta interagir com o botao de busca (lupa) do blog. Caso o seletor nao seja encontrado em razao de atualizacoes no layout, o teste utiliza a URL de busca padrao do WordPress (`?s=termo`), garantindo estabilidade e continuidade da execucao.

## Pipeline CI/CD

O projeto possui pipeline configurada no GitHub Actions que executa automaticamente os testes a cada `push` ou `pull request` na branch `main`.

Acesse os resultados em:
https://github.com/fernandaaime/teste-web-agi/actions