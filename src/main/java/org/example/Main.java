package org.example;

import java.io.File;
import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.*;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

public class Main {

    private static void afficherListe(XPath xpath, Document doc, String expression, String titre) throws XPathExpressionException {
        System.out.println("\n" + titre + " :");
        XPathExpression expr = xpath.compile(expression);
        NodeList nodes = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
        for (int i = 0; i < nodes.getLength(); i++) {
            System.out.println(" - " + nodes.item(i).getNodeValue());
        }
    }

    public static void main(String[] args) {
        try {
            File xmlFile = new File("C:\\Users\\ziouziou\\Desktop\\SOA\\gestion_ecole\\src\\main\\resources\\gestion_ecole.xml");
            File xsdFile = new File("C:\\Users\\ziouziou\\Desktop\\SOA\\gestion_ecole\\src\\main\\resources\\gestion_ecole.xsd");

            SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = factory.newSchema(xsdFile);
            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(xmlFile));
            System.out.println(" XML valide selon le schéma");

            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(xmlFile);
            XPath xpath = XPathFactory.newInstance().newXPath();

            afficherListe(xpath, doc, "//ADMINISTRATION/nom/text()", "Liste des administrateurs");
            afficherListe(xpath, doc, "//MAITRE/nom/text()", "Liste des maîtres");
            afficherListe(xpath, doc, "//ELEVE/nom/text()", "Liste des élèves");
            afficherListe(xpath, doc, "//MATIERE/nom_matiere/text()", "Liste des matières");

        } catch (SAXException e) {
            System.out.println(" XML invalide : " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
