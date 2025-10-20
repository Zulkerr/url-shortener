# URL Shortener Service – Java + DevOps Challenge

## ✅ Projektübersicht

 Java + DevOps Challenge – Frontend & Backend 💙. Es handelt sich um einen einfachen URL-Shortener-Service mit REST-API, Containerisierung, Kubernetes-Deployment und CI-Pipeline.

---

## 🚀 Technologien

- Java 17
- Gradle
- Spring Boot
- Docker
- Kubernetes + Helm
- GitHub Actions (CI)

---

## 📦 Aufbau & Funktionen

### 🔹 Part 1: Java + Gradle

- **POST /shorten**  
  → Nimmt eine lange URL entgegen und gibt einen verkürzten Identifier zurück

- **GET /{id}**  
  → Leitet zur Original-URL weiter, falls vorhanden, sonst `404 Not Found`

- **Speicherung:**  
  → URLs werden **in-memory** gespeichert (keine Datenbank)

- **Tests:**  
  → Für beide Endpoints existieren **Unit-Tests** (`ShortenerControllerTest.java`)

---

### 🔹 Part 2: Docker

- Enthält ein **Dockerfile** zur Containerisierung der Anwendung
- Image kann lokal oder in CI gebaut werden:
  ```bash
  docker build -t url-shortener .
  docker run -p 8080:8080 url-shortener
  ````

---
  
### 🔹 Part 3: Kubernetes + Helm

  - Helm-Chart im Verzeichnis helm/url-shortener
  - Enthält: 
     - Chart.yaml
     - values.yaml
     - templates/deployment.yaml
     - templates/service.yaml

  - Deployment per Helm
     -  helm install url-shortener ./helm/url-shortener

---
    
### 🔹 Part 4: CI/CD

   -  CI-Pipeline mit **GitHub Actions**
   -  Läuft bei jedem **Push** und **Pull Request** auf main
   -  Führt automatisch ./gradlew test aus
   -  Datei: .github/workflows/ci.yml

---

###  🔗 Projektstruktur

url-shortener/   
├── src/   
├── build.gradle   
├── Dockerfile   
├── helm/   
│   └── url-shortener/   
│       ├── Chart.yaml   
│       ├── values.yaml   
│       └── templates/   
├── .github/   
│   └── workflows/   
│       └── ci.yml   
└── README.md   

---

  
