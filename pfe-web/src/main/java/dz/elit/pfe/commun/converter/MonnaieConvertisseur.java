/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dz.elit.pfe.commun.converter;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.ConverterException;

import jakarta.faces.convert.FacesConverter;

/**
 * Cette classe Converter permettera de gérer la conversion le type de données primetife (monie en BigDecimal) en chaine de characteres (String)
 * el les formater suivant les format standard français.
 *
 * @author xps
 */

@FacesConverter(value = "MonnaieConvertisseur")
public class MonnaieConvertisseur implements Converter{

    /**
     *
     * @param context
     * @param component
     * @param value
     * @return
     */
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        try {
            if (!value.equals("")) {
                value = value.replaceAll(" ", "");
                value=value.replace('.', ',');
                NumberFormat nf = NumberFormat.getInstance(Locale.FRENCH);
                return new BigDecimal(nf.parse(value).toString());
            } else {
                return null;
            }

        } catch (Exception ex) {
            throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR,value +" :doit être un montant Exemple: 10 000.00",null));
            //throw new UnsupportedOperationException("Impossibe de converter le type " + value + " en Monnaie");
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value == null) {
            return null;
        } else if (value instanceof BigDecimal) {
            //NumberFormat nf = NumberFormat.getInstance(Locale.FRENCH);
            
            
                NumberFormat nf = NumberFormat.getCurrencyInstance();
                DecimalFormatSymbols decimalFormatSymbols = ((DecimalFormat) nf).getDecimalFormatSymbols();
                decimalFormatSymbols.setCurrencySymbol("");
                ((DecimalFormat) nf).setDecimalFormatSymbols(decimalFormatSymbols);
                return nf.format(value);
            
//           return value.toString().replace('.', ',');
        } else //  throw new Exception("impossible de converter le type " + value.getClass());
        {//UnsupportedOperationException
            throw new  ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR,value +" :doit être un montant Exemple: 10 000.00",null));
        }
    }
    
}

