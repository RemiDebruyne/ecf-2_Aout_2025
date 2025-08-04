# **Sujet ECF 1 – CDA**

## **Contexte**

Une application Java permettant de signaler et de visualiser des observations d’espèces dans la nature a été en grande partie développée.
Votre mission est de **compléter la modélisation et certaines parties du code** pour finaliser l’application.

---

## **1. Objectifs**

Vous devez :

1. **Compléter la modélisation** (diagramme UML et MLD) à partir des spécifications fournies.
2. **Compléter certaines classes Java** manquantes ou incomplètes pour assurer le bon fonctionnement de l’application.
3. **Vérifier et compléter la logique métier**, notamment le calcul des émissions de CO₂.

---

## **2. Pré-requis techniques**

* **Java**
* **Maven**
* **MySQL**

  * Créer une base de données nommée **`environement_db`**
  * Utilisateur par défaut : `root`
  * Mot de passe : vide (ou votre configuration locale)
* Un outil UML pour compléter les fichiers fournis :
  * Les fichiers UML et MLD se trouvent dans à la racine du dépôt


---

## **3. Partie modélisation**

* **Compléter le diagramme de classes UML** partiellement rempli.
* **Compléter le MLD** (Modèle Logique de Données) en respectant :

  * Les types
  * Les relations
  * Les cardinalités

---

## **4. Partie développement**

Dans le code fourni :

1. **Compléter les classes manquantes** décrites dans les spécifications (voir entités plus bas).
2. **Compléter les endpoints REST** inachevés

---

## **5. Spécifications fonctionnelles utiles**

### **Entités**

#### Espèce (`Specie`)

* id : Long
* commonName : String
* scientificName : String
* category : Category (enum)

#### Enum `Category`

```java
public enum Category {
    BIRD, MAMMAL, INSECT, PLANT, OTHER
}
```

#### Observation (`Observation`)

* id : Long
* specie : Specie
* observerName : String
* location : String
* latitude / longitude : Double
* observationDate : LocalDate
* comment : String (optionnel)

#### Déplacement (`TravelLog`)

* id : Long
* observation : Observation
* distanceKm : Double
* mode : TravelMode (enum)
* estimatedCo2Kg : Double

#### Enum `TravelMode`

```java
public enum TravelMode {
    WALKING, BIKE, CAR, BUS, TRAIN, PLANE
}
```

---

## **6. Facteurs d’émission CO₂**

| Mode de transport | Émission CO₂ (kg/km) |
| ----------------- | -------------------- |
| Walking / Bike    | 0                    |
| Car               | 0.22                 |
| Bus               | 0.11                 |
| Train             | 0.03                 |
| Plane             | 0.259                |

Formule :

```
estimatedCo2Kg = distanceKm × facteurEmission
```

---

## **7. Endpoints REST CO₂**

##### Espèces

* `GET /species` → Liste des espèces connues
* `POST /species` → Ajouter une espèce
* `GET /species/{id}` → Détails d’une espèce

##### Observations

* `GET /observations` → Toutes les observations (avec filtres possibles)
* `POST /observations` → Ajouter une observation
* `GET /observations/{id}` → Voir une observation
* `GET /observations/by-location?location=Paris` → Filtrer par lieu
* `GET /observations/by-species/{speciesId}` → Filtrer par espèce

#####  Déplacement
* `POST /travel-logs`
  Créer un déplacement lié à une observation (inclut le calcul CO₂).
* `GET /travel-logs`
  Liste des déplacements + émissions totales CO₂.
* `GET /travel-logs/stats/{idObservation}`
  Renvoie :

  ```json
  {
    "totalDistanceKm": 45.5,
    "totalEmissionsKg": 8.4,
    "byMode": {
      "CAR": 5.5,
      "TRAIN": 2.9
    }
  }
---

📌 **Dépôt de travail** : [https://github.com/LeoLhommeUtopios/Ecf\_Java/](https://github.com/LeoLhommeUtopios/Ecf_Java/)

