# 📦 Gestion de Stock

> Application Java de gestion de stock et de commandes (catégories, produits, commandes et lignes de commande) avec **Hibernate 5** et **MySQL**.

![Java](https://img.shields.io/badge/Java-11-orange)
![Hibernate](https://img.shields.io/badge/Hibernate-5.6.15-59666C)
![MySQL](https://img.shields.io/badge/MySQL-8-4479A1)
![Maven](https://img.shields.io/badge/Build-Maven-C71A36)

## 📌 Présentation

Ce projet modélise la gestion d'un stock de produits : classement par catégories, enregistrement de commandes et détail des produits commandés. Il met en pratique le mapping objet-relationnel avec Hibernate / JPA et l'organisation d'une application en couches (entités, DAO, services).

## 🗃️ Modèle de données

| Entité | Rôle |
|---|---|
| `Categorie` | Catégorie de produits |
| `Produit` | Produit du stock |
| `Commande` | Commande enregistrée |
| `LigneCommandeProduit` | Ligne de commande : lien entre une commande et un produit |

## 🏗️ Architecture

```
ma.projet
├── classes/    # Entités JPA (Categorie, Produit, Commande, LigneCommandeProduit)
├── dao/        # Accès aux données
├── service/    # Logique métier (CategorieService, ProduitService, CommandeService, LigneCommandeService)
├── util/       # Utilitaires (configuration Hibernate)
└── test/       # Classes de test exécutables
```

## 🛠️ Stack technique

| Domaine | Technologie |
|---|---|
| Langage | Java 11 |
| Persistance | Hibernate ORM 5.6.15, JPA 2.2 (`javax.persistence`) |
| Base de données | MySQL 8 (connecteur 8.0.33) |
| Build | Maven |

## 🚀 Lancer le projet

### Prérequis
- JDK 11 ou supérieur
- Maven 3.6+
- Un serveur MySQL 8 en fonctionnement

### Installation

```bash
# 1. Cloner le dépôt
git clone https://github.com/safaaOUARD/stock-project.git
cd stock-project

# 2. Compiler
mvn clean compile
```

### Configuration de la base de données

1. Créer une base MySQL vide.
2. Renseigner l'URL de connexion, l'utilisateur et le mot de passe dans le fichier de configuration Hibernate situé dans `src/main/resources`.
3. Exécuter une des classes du package `ma.projet.test` depuis ton IDE (clic droit → *Run*).

> ⚠️ Ne publie jamais ton vrai mot de passe MySQL sur GitHub : utilise une valeur d'exemple dans le fichier versionné.

## 🧠 Concepts mis en pratique

- Mapping objet-relationnel avec Hibernate / JPA
- Relations entre entités (catégorie → produits, commande → lignes de commande)
- Pattern **DAO** et couche **Service**
- Configuration d'Hibernate avec une base MySQL

## 🔭 Améliorations possibles

- Ajouter des tests unitaires JUnit
- Exposer une API REST avec Spring Boot
- Conteneuriser MySQL avec Docker Compose
- Gérer les quantités en stock et les alertes de rupture

## 👤 Auteure

**Safaa OUARD** — Étudiante ingénieure en Systèmes d'Information et de Communication, ENSA El Jadida
[GitHub](https://github.com/safaaOUARD)
