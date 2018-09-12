package com.fihoca.dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.fihoca.model.Alumno;
import com.fihoca.util.FileUtil;

public class AlumnoDao {

	private static final String PATH = "alumno.xml";

	public void add(Alumno alumno) throws Exception {
		File fileXml = FileUtil.createFile(PATH);

		BufferedReader reader = new BufferedReader(new FileReader(fileXml));

		if (reader.readLine() == null) {
			reader.close();

			try {
				DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder builder = dbFactory.newDocumentBuilder();
				Document document = builder.newDocument();

				Element rootElement = document.createElement("alumnos");
				document.appendChild(rootElement);

				addToXml(document, rootElement, alumno, fileXml);

			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			}

		} else {

			reader.close();
		}
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = dbFactory.newDocumentBuilder();
			Document document = builder.parse(fileXml);

			Node rootElement = document.getFirstChild();

			addToXml(document, rootElement, alumno, fileXml);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}

	}

	public void addToXml(Document document, Node rootElement, Alumno alumno, File fileXml) throws Exception {

		try {

			Element eAlumno = document.createElement("Alumno");
			rootElement.appendChild(eAlumno);

			Element id = document.createElement("id");
			id.appendChild(document.createTextNode(String.valueOf(alumno.getIdAlumno())));
			eAlumno.appendChild(id);

			Element nombre = document.createElement("Nombre");
			nombre.appendChild(document.createTextNode(alumno.getNombre()));
			eAlumno.appendChild(nombre);

			Element apellidos = document.createElement("Apellido");
			apellidos.appendChild(document.createTextNode(alumno.getApellidos()));
			eAlumno.appendChild(apellidos);

			Element dni = document.createElement("DNI");
			dni.appendChild(document.createTextNode(alumno.getDni()));
			eAlumno.appendChild(dni);

			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(document);
			StreamResult result = new StreamResult(fileXml);
			transformer.transform(source, result);

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}

	}

	public Alumno[] getAlumno(File fileName) throws SAXException, IOException {

		List<Alumno> alumnoList = new ArrayList<>();

		try {
			File file = new File(PATH);

			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(file);

			document.getDocumentElement().normalize();

			NodeList nList = document.getElementsByTagName("alumno");

			for (int temp = 0; temp < nList.getLength(); temp++) {

				Node nNode = nList.item(temp);

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element element = (Element) nNode;

					alumnoList.add(new Alumno(
							Integer.parseInt(element.getElementsByTagName("idAlumno").item(0).getTextContent()),
							(element.getElementsByTagName("nombre").item(0).getTextContent()),
							(element.getElementsByTagName("apellidos").item(0).getTextContent()),
							(element.getElementsByTagName("dni").item(0).getTextContent())));
				}
			}

		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}

		return (Alumno[]) alumnoList.toArray();

	}

}
