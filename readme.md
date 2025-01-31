# 🌴 AGP2025 - Système Intelligent de Construction d'Offres de Séjour  
## Équipe :
- SFAIHI Sabine  
- TAGHELIT Wassim  
- MOKHTARI Rayan  
- BOUHADOUN Lounis  

**Destination :** Îles des Petites Antilles  
**Date :** Janvier 2025  


---

## 📋 Table des matières
- [Description du projet](#-description-du-projet)
- [Fonctionnalités clés](#-fonctionnalités-clés)
- [Architecture technique](#-architecture-technique)
- [Prérequis](#-prérequis)
- [Installation](#-installation)
- [Configuration de la BDe](#-configuration-de-la-bde)
- [Utilisation](#-utilisation)
- [Structure du projet](#-structure-du-projet)
- [API BDA](#-api-bda)
- [Tests](#-tests)
- [Contributions](#-contributions)
- [Livrables](#-livrables)
- [Licence](#-licence)
- [Contacts](#-contacts)

---

## 🚀 Description du projet
Un système intelligent permettant de **construire automatiquement des offres de séjour** pour les Îles Canaries, en fonction des critères des utilisateurs (thème, budget, confort, etc.). Le projet intègre :
- Une interface web (JSF) pour la recherche et la visualisation des offres.
- Une base de données relationnelle étendue (BDe) avec indexation textuelle via **Lucene**.
- Une architecture en 5 couches (présentation, contrôleur MVC, métier, DAO, persistance).

---

## ✨ Fonctionnalités clés
- **Recherche simple** :  
  🔍 Trouver des hôtels/sites touristiques par mots-clés.  
- **Construction intelligente d'offres** :  
  🏨 Génération automatique de séjours avec excursions, hôtels et transports.  
- **Requêtes mixtes SQL-texte** :  
  📊 Combinaison de requêtes relationnelles et de recherche sémantique via Lucene.  

---

## 🛠 Architecture technique
### Stack utilisée
- **Frontend** : JSF (JavaServer Faces), PrimeFaces, CSS.  
- **Backend** : Java EE, Spring IoC (Inversion of Control).  
- **Base de données** : MySQL (+ JDBC), Lucene pour l'indexation textuelle.  
- **Outils** : Git, JUnit.  

### Schéma d'architecture

```plaintext
agp2025/
├── src/
│   ├── main/
│   │   ├── java/          # Code source Java
│   │   ├── resources/     # Fichiers de config (Spring, Lucene)
│   │   └── webapp/        # Pages JSF, CSS, images
│   └── test/              # Tests unitaires
├── database/              # Scripts SQL
├── indexes/               # Index Lucene
└── pom.xml                # Configuration
```

---

## 📦 Prérequis
- Java JDK 1.8
- MySQL 5.016
- Serveur d'applications (Tomcat 9)
- IDE (Eclipse)
- lucene
- Spring
- Junit 4 




---

## 🔧 Installation
1. Cloner le dépôt :
```bash
git clone https://github.com/RayanMokhtar/AGP.git
```

---

## ⚙️ Configuration de la BDe
Avant de lancer l'application, vous devez :

### Installer et configurer MySQL :
- Créer la base de données nécessaire pour le projet (exécuter les scripts SQL dans le dossier `database/` si besoin).

### Configurer les chemins Lucene et les identifiants :
Dans la classe `Database` (fichier `Database.java` situé dans le package `persistence`), mettez à jour les variables suivantes pour pointer vers vos propres chemins et vos identifiants :
```java
// Chemins (paths) vers les ressources Lucene
private static Path sourcePath = Paths.get("C:\\...\\AGP_DB\\Description");
private static Path indexPath = Paths.get("C:\\...\\AGP_DB\\Index");

// Identifiants de base de données
private static String user = "votre_identifiant";
private static String password = "votre_mot_de_passe";
```
Assurez-vous d’indiquer les bons répertoires locaux et de renseigner correctement vos identifiants MySQL.

### Vérifier le driver JDBC :
La classe charge le pilote MySQL via `Class.forName("com.mysql.jdbc.Driver")`. Assurez-vous que le driver est bien présent dans votre classpath.

### Déployer l’application :
Vous pouvez utiliser un serveur d’applications (Tomcat). Ajustez les fichiers de configuration si nécessaire (`web.xml`, `context.xml`, etc.) pour pointer sur votre DataSource.