# ItemsRESTFul
API RESTful para realizar operacion CRUD de Items. 

## Tecnologias utilizadas
Esta API esta desarrollada en Java 8 y utiliza una base de datos no relacional llamada Elastic Search. Ademas hace uso de Spark, Gson, etc.

## Instrucciones de uso
1. Instalar y ejecutar ElasticSearch. Puede descargarlo desde [aqui](https://www.elastic.co/downloads/elasticsearch).
2. Abrir el proyecto con algun IDE de Java, como puede ser Intellig.
3. Utlizar un

## Metodos CRUD
La API posee un único recurso llamado **Items** el cual posee los siguientes metodos:

### GET /items
Con este metodo se podra obtener un listado de todos los items creados en la base de datos.

**Parameter Content Type: application/json**
**____________________________________________________________________________________________**

### GET /items/{id}
Este método devuelve el item correspondiente a id enviado como query parameter

**Parameter Content Type: application/json**


### POST /items 
Este método crea un item en la base de datos, con los datos enviados en el body.

**Ejemplo de body**

*{
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
}*

**Parameter Content Type: application/json**
