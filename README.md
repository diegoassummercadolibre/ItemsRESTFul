# ItemsRESTful
API RESTful desarrollada en Java, la cual permite realizar operaciones CRUD de Items. 

## Tecnologias utilizadas
Esta API esta desarrollada en **Java 8** y utiliza una base de datos no relacional llamada **Elastic Search**. Además hace uso de otras librerías y frameworks como **Spark**, **Gson**, **JUnit**, etc.

## Instrucciones de uso
1. Instalar y ejecutar ElasticSearch. Puede descargarlo desde [aquí](https://www.elastic.co/downloads/elasticsearch).
2. Abrir y ejecutar el proyecto Java utlizando ItemsController.main como metodo de arranque. El mismo utilizará el puerto 8080 (http://localhost:8080).
3. Ejecutar el archivo AppTest.java para correr los tests.
4. Para ver un listado de los items ingrese a http://localhost:8080/view/items/list.

## Métodos
La API posee un único recurso llamado **Items** el cual posee los siguientes métodos:

### GET /items
Obtiene un listado de todos los items creados en la base de datos.

**Parameter Content Type:** application/json

**Responses:** 

**Code         Example**
________________________________
200

    {
        "status": "SUCCESS",
        "data": [
            {
              "id": "Dfbbt2MBRfE6d63V3e_b",
              "title": "Item de test 1 - No Ofertar",
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
            },
            {
              "id": "Efbbt2MBRfE6d63V6e8-",
              "title": "Item de test 2 - No Ofertar",
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
        ]
    }
                                
                    
***Code***

404

    {
        "status": "ERROR",
        "data": "No se encontró el Item"
    }
**____________________________________________________________________________________________**

### GET /items/{id}
Devuelve el item correspondiente a id enviado como query parameter.

**Parameter Content Type:** application/json

**Responses:** 

***Code***

200

    {
        "status": "SUCCESS",
        "data": {
            "id": "Dfbbt2MBRfE6d63V3e_b",
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
                }
            ]
        }
    }
                                
                    
***Code***

404

    {
        "status": "ERROR",
        "data": "No se encontró el Item"
    }
**____________________________________________________________________________________________**

### POST /items 
Creea un item en la base de datos, utilizando los datos enviados en el body. Devuelve el id del item creado en la propiedad *data*.

**Body:**

    {
      "id": "Dfbbt2MBRfE6d63V3e_b",
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

**Responses:** 

***Code***

200

    {
        "status": "SUCCESS",
        "data": "J_a5uGMBRfE6d63VD-87"
    }
**____________________________________________________________________________________________**

### PUT /items/{id}
Actualiza el item correspondiente a id enviado como query parameter. Devuelve el item actualizado.

**Body:**

    {
      "id": "Dfbbt2MBRfE6d63V3e_b",
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

**Responses:** 

***Code***

200

    {
        "status": "SUCCESS",
        "data": {
            "id": "Dfbbt2MBRfE6d63V3e_b",
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
                }
            ]
        }
    }
                                
                    
***Code***

404

    {
        "status": "ERROR",
        "data": "No se encontró el Item"
    }
**____________________________________________________________________________________________**

### DELETE /items/{id}
Este método elimina el item correspondiente a id enviado como query parameter.

**Parameter Content Type:** application/json

**Responses:** 

***Code***

404

    {
        "status": "ERROR",
        "data": "No se encontró el Item"
    }
