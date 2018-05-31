import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Header;
import com.jayway.restassured.response.Headers;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.response.ResponseBody;
import com.jayway.restassured.specification.RequestSpecification;
import org.apache.http.HttpStatus;
import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class appTest {

    private final String URI = "http://localhost:8080";
    private final String RESOURCE = "/items";
    private final String SUCCESS_MESSAGE ="\"status\":\"SUCCESS\"";
    private static String _resourceId;

    @Test
    public void a_validar_add() throws InterruptedException {
        RestAssured.baseURI = URI;
        RequestSpecification httpRequest = RestAssured.given();
        httpRequest.body(getJsonItemForAdd());
        Response response = httpRequest.post(RESOURCE);
        ResponseBody body = response.getBody();
        String bodyAsString = body.asString();
        Assert.assertEquals(bodyAsString.contains(SUCCESS_MESSAGE), true);

        Matcher resourceId = Pattern.compile("\"data\":\"(.*)\"}").matcher(bodyAsString);
        resourceId.find();
        _resourceId =  resourceId.group(1);

        //Este timer es para asegurar que la api devuelva el resourceId necesario para los demas tests
        TimeUnit.SECONDS.sleep(1);
    }

    @Test
    public void b_validar_status_code_200(){
        RestAssured.baseURI = URI;
        RequestSpecification httpRequest = RestAssured.given();
        Response response = httpRequest.get(RESOURCE + "/" + _resourceId);
        int statusCode = response.getStatusCode();
        Assert.assertEquals(String.valueOf(statusCode), "200");
    }

    @Test
    public void c_validar_status_line(){
        RestAssured.baseURI = URI;
        RequestSpecification httpRequest = RestAssured.given();
        Response response = httpRequest.get(RESOURCE + "/" + _resourceId);
        String statusLine = response.getStatusLine();
        Assert.assertEquals(statusLine, "HTTP/1.1 200 OK");
    }

    @Test
    public void d_imprimir_headers(){
        RestAssured.baseURI = URI;
        RequestSpecification httpRequest = RestAssured.given();
        Response response = httpRequest.get(RESOURCE + "/" + _resourceId);
        Headers allHeaders = response.headers();
        for (Header header: allHeaders){
            System.out.println("Key: " + header.getName()
            + " Value: " + header.getValue());
        }
    }

    @Test
    public void e_validar_headers(){
        RestAssured.baseURI = URI;
        RequestSpecification httpRequest = RestAssured.given();
        Response response = httpRequest.get(RESOURCE + "/" + _resourceId);
        String contentType = response.header("Content-Type");
        Assert.assertEquals(contentType, "application/json");
    }

    @Test
    public void f_validar_body(){
        RestAssured.baseURI = URI;
        RequestSpecification httpRequest = RestAssured.given();
        Response response = httpRequest.get(RESOURCE + "/" + _resourceId);
        ResponseBody body = response.getBody();
        String bodyAsString = body.asString();
        Assert.assertEquals(bodyAsString.contains("title"), true);
    }

    @Test
    public void g_validar_get()  {
        RestAssured.baseURI = URI;
        RequestSpecification httpRequest = RestAssured.given();
        Response response = httpRequest.get(RESOURCE + "/" + _resourceId);
        ResponseBody body = response.getBody();
        String bodyAsString = body.asString();
        Assert.assertEquals(bodyAsString.contains(SUCCESS_MESSAGE), true);
    }

    @Test
    public void h_validar_getAll()  {
        RestAssured.baseURI = URI;
        RequestSpecification httpRequest = RestAssured.given();
        Response response = httpRequest.get(RESOURCE);
        ResponseBody body = response.getBody();
        String bodyAsString = body.asString();
        Assert.assertEquals(bodyAsString.contains(SUCCESS_MESSAGE), true);
    }

    @Test
    public void i_validar_edit()  {
        RestAssured.baseURI = URI;
        RequestSpecification httpRequest = RestAssured.given();
        httpRequest.body(getJsonItemForEdit());
        Response response = httpRequest.put(RESOURCE + "/" + _resourceId);
        ResponseBody body = response.getBody();
        String bodyAsString = body.asString();
        Assert.assertEquals(bodyAsString.contains(SUCCESS_MESSAGE), true);
    }

    @Test
    public void w_validar_delete() throws InterruptedException {
        //Este timer es para asegurar que los demas test ya fueron finalizados
        TimeUnit.SECONDS.sleep(2);
        RestAssured.baseURI = URI;
        RequestSpecification httpRequest = RestAssured.given();
        Response response = httpRequest.delete(RESOURCE + "/" + _resourceId);
        ResponseBody body = response.getBody();
        String bodyAsString = body.asString();
        Assert.assertEquals(bodyAsString.contains(SUCCESS_MESSAGE), true);
    }

    private String getJsonItemForAdd(){
        return "{\n" +
                "  \"title\":\"Item de test - No Ofertar\",\n" +
                "  \"category_id\":\"MLA5529\",\n" +
                "  \"price\":10,\n" +
                "  \"currency_id\":\"ARS\",\n" +
                "  \"available_quantity\":1,\n" +
                "  \"buying_mode\":\"buy_it_now\",\n" +
                "  \"listing_type_id\":\"bronze\",\n" +
                "  \"condition\":\"new\",\n" +
                "  \"description\": \"Item:,  Ray-Ban WAYFARER Gloss Black RB2140 901  Model: RB2140. Size: 50mm. Name: WAYFARER. Color: Gloss Black. Includes Ray-Ban Carrying Case and Cleaning Cloth. New in Box\",\n" +
                "  \"video_id\": \"YOUTUBE_ID_HERE\",\n" +
                "  \"warranty\": \"12 months by Ray Ban\",\n" +
                "  \"pictures\":[\n" +
                "    {\"source\":\"http://upload.wikimedia.org/wikipedia/commons/f/fd/Ray_Ban_Original_Wayfarer.jpg\"},\n" +
                "    {\"source\":\"http://en.wikipedia.org/wiki/File:Teashades.gif\"}\n" +
                "  ]\n" +
                "}";
    }

    private String getJsonItemForEdit(){
        return "{\n" +
                "  \"title\":\"Item de test - Modificado\",\n" +
                "  \"category_id\":\"MLA5529\",\n" +
                "  \"price\":10,\n" +
                "  \"currency_id\":\"ARS\",\n" +
                "  \"available_quantity\":1,\n" +
                "  \"buying_mode\":\"buy_it_now\",\n" +
                "  \"listing_type_id\":\"bronze\",\n" +
                "  \"condition\":\"new\",\n" +
                "  \"description\": \"Item:,  Ray-Ban WAYFARER Gloss Black RB2140 901  Model: RB2140. Size: 50mm. Name: WAYFARER. Color: Gloss Black. Includes Ray-Ban Carrying Case and Cleaning Cloth. New in Box\",\n" +
                "  \"video_id\": \"YOUTUBE_ID_HERE\",\n" +
                "  \"warranty\": \"12 months by Ray Ban\",\n" +
                "  \"pictures\":[\n" +
                "    {\"source\":\"http://upload.wikimedia.org/wikipedia/commons/f/fd/Ray_Ban_Original_Wayfarer.jpg\"},\n" +
                "    {\"source\":\"http://en.wikipedia.org/wiki/File:Teashades.gif\"}\n" +
                "  ]\n" +
                "}";
    }
}


