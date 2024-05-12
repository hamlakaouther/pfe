/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dz.elit.pfe.commun.util;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;


public abstract class StaticUtil {

    public static String UNIT_NAME;

    public static String getDefaultEncryptPassword() {
        return getEncryptPassword("Sonelgaz.1");
    }

    /**
     * *************************************************************************
     * @param password
     * @return hashString
     * ************************************************************************
     */
    public static String getEncryptPassword(String password) {

        byte[] uniqueKey = password.getBytes();
        byte[] hash = null;

        try {
            hash = MessageDigest.getInstance("SHA-256").digest(uniqueKey);
        } catch (NoSuchAlgorithmException e) {
            throw new Error("No SHA-256 support in this VM.");
        }

        StringBuilder hashString = new StringBuilder();
        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(hash[i]);
            if (hex.length() == 1) {
                hashString.append('0');
                hashString.append(hex.charAt(hex.length() - 1));
            } else {
                hashString.append(hex.substring(hex.length() - 2));
            }
        }
        return hashString.toString();
    }

    public static String returnNumbersWithSeparateurMillier(BigDecimal number) {
        if (number != null) {
            BigInteger numberN = number.toBigInteger();
            String hh = number.toString();
            String v = "";
            if (hh.contains(".")) {
                v = number.toString().substring(number.toString().lastIndexOf("."));
            } else {
                v = ".00";
            }
            String nn = "" + numberN;
            String dd = new StringBuilder(nn).reverse().toString();
            dd = dd.replaceAll("...(?!$)", "$0 ");
            String ee = new StringBuilder(dd).reverse().toString() + v;
            return ee;
        } else {
            return null;
        }
    }

    public static String getPourcentage(int nb, int total) {
        float division;
        division = nb * 100 / total;
        DecimalFormat nf1 = new DecimalFormat("#.##");
        nf1.setMinimumFractionDigits(2);
        float pourcentageFl = 0;
        if (division == 0) {
            pourcentageFl = 0;
        } else {
            pourcentageFl = division;
        }
        String pourcentage = nf1.format(pourcentageFl) + " %";
        return pourcentage;
    }

    public static String generateUniqueId() {
        UUID idOne = UUID.randomUUID();
        String str = "" + idOne;
        int uid = str.hashCode();
        String filterStr = "" + uid;
        str = filterStr.replaceAll("-", "");
        return str;
    }

    /*public static List listUnitOfBoloughine(){
        return new ArrayList(Arrays.asList(23,17,20,19,21,690,16,564,18,15,22));
    }
    
    public static List listUnitOfSidiAbdAllah(){
        return new ArrayList(Arrays.asList(719,720,721,722));
    }*/
    public static List listUnitOfScindementEnCoursDD_0() {
        //DD GHARDAIA
        return new ArrayList(Arrays.asList(108, 109, 110, 111, 112, 113, 114, 709, 710));
    }

    public static List listUnitOfScindementEnCoursDD_1() {
        //DD BECHAR
        return new ArrayList(Arrays.asList(328, 329, 330, 331, 694, 695, 696, 697));
    }

    public static List listUnitOfScindementEnCoursDD_2() {
        return new ArrayList(Arrays.asList());
    }

    public static List listUnitOfScindementEnCoursDD_3() {
        return new ArrayList(Arrays.asList());
    }
    public static String montantPaiementMixte(String line, String delimiter, String paiement) {
        if (paiement.equals("espece")) {
            return line.substring(0, line.indexOf(delimiter));
        } else {
            return line.substring(line.indexOf(delimiter) + 1, line.length());
        }

    }
}
