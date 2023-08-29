package bg.tu_varna.sit.rostislav.parsers;

import bg.tu_varna.sit.rostislav.models.MyCalendar;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;

public class JAXBParser {

    public MyCalendar readFromFile (File file) throws JAXBException {

        JAXBContext jaxbContext = JAXBContext .newInstance (MyCalendar.class) ;

        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

        return (MyCalendar) unmarshaller. unmarshal(file);

    }

    public void writeToFile (MyCalendar myCalendar, File file) throws JAXBException {

            JAXBContext jaxbContext = JAXBContext .newInstance (MyCalendar.class) ;

            Marshaller marshaller = jaxbContext.createMarshaller () ;

            marshaller. setProperty (Marshaller.JAXB_FORMATTED_OUTPUT, true);

            marshaller.marshal (myCalendar, file);
    }

}
