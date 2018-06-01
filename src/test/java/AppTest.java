import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Header;
import com.jayway.restassured.response.Headers;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.response.ResponseBody;
import com.jayway.restassured.specification.RequestSpecification;
import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AppTest {

    private final String URI = "http://localhost:8080";
    private final String RESOURCE = "/items";
    private final String SUCCESS_MESSAGE = "\"status\":\"SUCCESS\"";
    private static String _resourceId;

    @Test
    public void a_validar_add() throws InterruptedException {
        RestAssured.baseURI = URI;
        RequestSpecification httpRequest = RestAssured.given();
        httpRequest.body(ItemMock.getJsonItemForAdd());
        Response response = httpRequest.post(RESOURCE);
        ResponseBody body = response.getBody();
        String bodyAsString = body.asString();
        Assert.assertEquals(bodyAsString.contains(SUCCESS_MESSAGE), true);

        Matcher resourceId = Pattern.compile("\"data\":\"(.*)\"}").matcher(bodyAsString);
        resourceId.find();
        _resourceId = resourceId.group(1);
    }

    @Test
    public void b_validar_status_code_200() throws InterruptedException {
        while (_resourceId == null)
            TimeUnit.MICROSECONDS.sleep(500);

        RestAssured.baseURI = URI;
        RequestSpecification httpRequest = RestAssured.given();
        Response response = httpRequest.get(RESOURCE + "/" + _resourceId);
        int statusCode = response.getStatusCode();
        Assert.assertEquals(String.valueOf(statusCode), "200");
    }

    @Test
    public void c_validar_status_line() throws InterruptedException {
        while (_resourceId == null)
            TimeUnit.MICROSECONDS.sleep(500);

        RestAssured.baseURI = URI;
        RequestSpecification httpRequest = RestAssured.given();
        Response response = httpRequest.get(RESOURCE + "/" + _resourceId);
        String statusLine = response.getStatusLine();
        Assert.assertEquals(statusLine, "HTTP/1.1 200 OK");
    }

    @Test
    public void d_imprimir_headers() throws InterruptedException {
        while (_resourceId == null)
            TimeUnit.MICROSECONDS.sleep(500);

        RestAssured.baseURI = URI;
        RequestSpecification httpRequest = RestAssured.given();
        Response response = httpRequest.get(RESOURCE + "/" + _resourceId);
        Headers allHeaders = response.headers();
        for (Header header : allHeaders) {
            System.out.println("Key: " + header.getName()
                    + " Value: " + header.getValue());
        }
    }

    @Test
    public void e_validar_headers() throws InterruptedException {
        while (_resourceId == null)
            TimeUnit.MICROSECONDS.sleep(500);

        RestAssured.baseURI = URI;
        RequestSpecification httpRequest = RestAssured.given();
        Response response = httpRequest.get(RESOURCE + "/" + _resourceId);
        String contentType = response.header("Content-Type");
        Assert.assertEquals(contentType, "application/json");
    }

    @Test
    public void f_validar_body() throws InterruptedException {
        while (_resourceId == null)
            TimeUnit.MICROSECONDS.sleep(500);

        RestAssured.baseURI = URI;
        RequestSpecification httpRequest = RestAssured.given();
        Response response = httpRequest.get(RESOURCE + "/" + _resourceId);
        ResponseBody body = response.getBody();
        String bodyAsString = body.asString();
        Assert.assertEquals(bodyAsString.contains("title"), true);
    }

    @Test
    public void g_validar_get() throws InterruptedException {
        while (_resourceId == null)
            TimeUnit.MICROSECONDS.sleep(500);

        RestAssured.baseURI = URI;
        RequestSpecification httpRequest = RestAssured.given();
        Response response = httpRequest.get(RESOURCE + "/" + _resourceId);
        ResponseBody body = response.getBody();
        String bodyAsString = body.asString();
        Assert.assertEquals(bodyAsString.contains(SUCCESS_MESSAGE), true);
    }

    @Test
    public void h_validar_getAll() throws InterruptedException {
        while (_resourceId == null)
            TimeUnit.MICROSECONDS.sleep(500);

        RestAssured.baseURI = URI;
        RequestSpecification httpRequest = RestAssured.given();
        Response response = httpRequest.get(RESOURCE);
        ResponseBody body = response.getBody();
        String bodyAsString = body.asString();
        Assert.assertEquals(bodyAsString.contains(SUCCESS_MESSAGE), true);
    }

    @Test
    public void i_validar_edit() throws InterruptedException {
        while (_resourceId == null)
            TimeUnit.MICROSECONDS.sleep(500);

        RestAssured.baseURI = URI;
        RequestSpecification httpRequest = RestAssured.given();
        httpRequest.body(ItemMock.getJsonItemForEdit());
        Response response = httpRequest.put(RESOURCE + "/" + _resourceId);
        ResponseBody body = response.getBody();
        String bodyAsString = body.asString();
        Assert.assertEquals(bodyAsString.contains(SUCCESS_MESSAGE), true);
    }

    @Test
    public void w_validar_delete() throws InterruptedException {
        while (_resourceId == null)
            TimeUnit.MICROSECONDS.sleep(500);

        RestAssured.baseURI = URI;
        RequestSpecification httpRequest = RestAssured.given();
        Response response = httpRequest.delete(RESOURCE + "/" + _resourceId);
        ResponseBody body = response.getBody();
        String bodyAsString = body.asString();
        Assert.assertEquals(bodyAsString.contains(SUCCESS_MESSAGE), true);
    }

}


