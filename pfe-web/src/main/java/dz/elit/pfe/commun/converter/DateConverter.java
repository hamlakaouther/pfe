/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dz.elit.pfe.commun.converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.ConverterException;
import jakarta.faces.convert.FacesConverter;

/**
 *
 * @author leghettas.rabah
 */
@FacesConverter(value="dateConverter")
public class DateConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            
            return dateFormat.parse(value);
        } catch (ParseException ex) {
            throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion ParseException", "Impossibe de converter le type " + value + " en date"));
           // throw new UnsupportedOperationException("ParseException: Impossibe de converter le type " + value + " en date");
        } catch (Exception ex) {
            throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Exception", "Impossibe de converter le type " + value + " en date"));
            //throw new UnsupportedOperationException("Exception: Impossibe de converter le type " + value + " en date");
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value == null) {
            return null;
        } else if (value instanceof Date) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            return dateFormat.format(value);
        } else {
            throw new UnsupportedOperationException("Impossibe de converter le type " + value.getClass());
        }

    }
}
