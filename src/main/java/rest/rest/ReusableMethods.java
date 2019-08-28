package rest.rest;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import io.restassured.path.json.JsonPath;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;

public class ReusableMethods {
	
	public static XmlPath rawToXml(Response response) {
		String res=response.asString();
		XmlPath xmlpath=new XmlPath(res);
		return xmlpath;
	}
	
	public static JsonPath rawToJson(Response response) {
		String res=response.asString();
		JsonPath jsonpath=new JsonPath(res);
		return jsonpath;
	}
	
	public static String generateStringFromResource(String path) throws IOException {
		return new String(Files.readAllBytes(Paths.get(path)));
	}
	
	public static void SerializeToFile(Object classObject, String fileName) throws IOException {
		
		FileOutputStream fos=new FileOutputStream(fileName);
		ObjectOutputStream oos=new ObjectOutputStream(fos);
		oos.writeObject(classObject);
		oos.close();
		fos.close();
	}
	
	public static Object DeserializedFromFileToObject(String fileName) throws IOException, ClassNotFoundException {
		FileInputStream fis=new FileInputStream(fileName);
		ObjectInputStream ois=new ObjectInputStream(fis);
		Object deserializedObject=ois.readObject();
		ois.close();
		fis.close();
		return deserializedObject;
	}

}
