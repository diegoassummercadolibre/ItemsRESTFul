# ItemsRESTful
API RESTful desarrollada en Java, la cual permite realizar operaciones CRUD de Items. 

## Tecnologias utilizadas
Esta API esta desarrollada en **Java 8** y utiliza una base de datos no relacional llamada **Elastic Search**. Además hace uso de otras librerías y frameworks como **Spark**, **Gson**, etc.

## Instrucciones de uso
1. Instalar y ejecutar ElasticSearch. Puede descargarlo desde [aquí](https://www.elastic.co/downloads/elasticsearch).
2. Abrir y ejecutar el proyecto Java.

## Métodos
La API posee un único recurso llamado **Items** el cual posee los siguientes métodos:

### GET /items
Con este método se podra obtener un listado de todos los items creados en la base de datos.

**Parameter Content Type:** application/json
**____________________________________________________________________________________________**

### GET /items/{id}
Este método devuelve el item correspondiente a id enviado como query parameter.

**Parameter Content Type:** application/json
**____________________________________________________________________________________________**

### POST /items 
Este método crea un item en la base de datos, utilziando los datos enviados en el body.

**Body:**

    {
      "id": "MLA1527625776195",
      "title": "Item de test - No Ofertar",
      "category_id": "MLA5529",
      "price": 10,
      "currency_id": "ARS",
      "available_quantity": 1,
      "buying_mode": "buy_it_now",
      "listing_type_id": "bronze",
      "condition": "new",
      "description": "Item:,  Ray-Ban WAYFARER Gloss Black RB2140 901  Model: RB2140. Size: 50mm. Name: WAYFARER. Color: Gloss Black. Includes Ray-Ban Carrying Case and Cleaning Cloth. New in Box",
      "video_id": "YOUTUBE_ID_HERE",
      "warranty": "12 months by Ray Ban",
      "pictures": [
      {
          "source": "http://upload.wikimedia.org/wikipedia/commons/f/fd/Ray_Ban_Original_Wayfarer.jpg"
      },
      {
          "source": "http://en.wikipedia.org/wiki/File:Teashades.gif"
      }]
    }

**Parameter Content Type:** application/json
**____________________________________________________________________________________________**

### PUT /items/{id}
Este método actualiza el item correspondiente a id enviado como query parameter.

**Body:**

    {
      "id": "MLA1527625776195",
      "title": "Item de test - Actualizado",
      "category_id": "MLA5529",
      "price": 10,
      "currency_id": "ARS",
      "available_quantity": 1,
      "buying_mode": "buy_it_now",
      "listing_type_id": "bronze",
      "condition": "new",
      "description": "Item:,  Ray-Ban WAYFARER Gloss Black RB2140 901  Model: RB2140. Size: 50mm. Name: WAYFARER. Color: Gloss Black. Includes Ray-Ban Carrying Case and Cleaning Cloth. New in Box",
      "video_id": "YOUTUBE_ID_HERE",
      "warranty": "12 months by Ray Ban",
      "pictures": [
      {
          "source": "http://upload.wikimedia.org/wikipedia/commons/f/fd/Ray_Ban_Original_Wayfarer.jpg"
      },
      {
          "source": "http://en.wikipedia.org/wiki/File:Teashades.gif"
      }]
    }

**Parameter Content Type:** application/json
**____________________________________________________________________________________________**

### DELETE /items/{id}
Este método elimina el item correspondiente a id enviado como query parameter.

**Parameter Content Type:** application/json
