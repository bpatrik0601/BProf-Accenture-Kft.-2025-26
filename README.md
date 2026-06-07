# BProf-Accenture-Kft.-2025-26
University project for test automation framework development

---

# **Playwright Test Automation Framework (Java + Cucumber + Maven)**

## **Project Overview**
This project is a **Java‑based Playwright test automation framework** designed to test an Angular web application using **BDD (Gherkin + Cucumber)**.  
The framework focuses on maintainability, scalability, and CI‑integration through **GitHub Actions**.

It was developed as part of a thesis project, extending a previously created base framework with improved structure, stability, and AI‑assisted development workflows.

---

## **Key Features**
- Java + Playwright browser automation  
- BDD test specification using Gherkin  
- Cucumber test execution  
- Page Object Model architecture  
- Maven‑based dependency management  
- JUnit 5 integration  
- GitHub Actions CI pipeline  
- AI‑assisted code generation (GitHub Copilot, Gemini)

---

## **Technology Stack**
- **Java 17+**  
- **Playwright for Java**  
- **Cucumber (Gherkin)**  
- **JUnit 5**  
- **Maven (pom.xml)**  
- **GitHub Actions (YAML)**  
- **Page Object Model (POM)**

---

## **Project Structure**

```
BPROF-ACCENTURE-KFT.-2025-26/
│
├── .github/
│   └── workflows/
│       └── ci.yml                     # CI pipeline (UI tests + static analysis)
│
├── src/
│   └── main/java/com/bprof/playwright/
│       ├── base/                      # Playwright setup, base classes
│       ├── clients/                   # API clients (optional)
│       ├── elements/                  # Custom element wrappers
│       ├── pages/                     # Page Object Model classes
│       └── wrappers/                  # Utility wrappers
│
├── test/
│   ├── java/
│   │   ├── api/                       # API test examples
│   │   ├── hooks/                     # Cucumber hooks (Before/After)
│   │   ├── runners/                   # Cucumber test runners
│   │   ├── steps/                     # Step definitions
│   │   └── ui/                        # UI test logic
│   │
│   └── resources/
│       ├── features/                  # Gherkin feature files
│       └── data_JSON_for_offline_fallback/  # Static test data
│
├── TECH_NOTES.md                      # Deep technical explanations
├── Brainstorming.md                   # Optional notes
├── README.md                          # This file
└── pom.xml                            # Maven configuration
```

---

## **How to Run Tests**

### **1) Start the Angular application**
Before running the Playwright tests, start the Angular frontend:

```bash
ng serve
```

The tests expect the application to be available at:

```
http://localhost:4200/
```

---

### **2) Run all tests**
```bash
mvn clean test
```

### **3) Run a specific feature**
```bash
mvn clean test -Dcucumber.filter.tags="@dashboard"
```

### **4) View Cucumber HTML report**
Reports are generated automatically under:

```
target/cucumber-report/
```

---

## **Continuous Integration (GitHub Actions)**
The project includes a CI pipeline that:

- installs dependencies  
- builds the Angular application  
- serves the production build  
- runs Playwright UI tests  
- performs static analysis (Checkstyle, SpotBugs, ESLint)  
- uploads test reports  
- supports parallel execution  

Workflow file:

```
.github/workflows/ci.yml
```

---

## **AI‑Assisted Development**
The framework was enhanced using:

- **GitHub Copilot** → step definitions, POM scaffolding, refactoring suggestions  
- **Google Gemini** → Gherkin generation, error analysis, locator stabilization ideas  

All AI‑generated content was manually validated for correctness and maintainability.

---

## **Future Improvements**
- AI‑based locator stabilization  
- Automatic test generation from requirements  
- Visual regression testing  
- Self‑healing test execution  
- Extended CI analytics  
