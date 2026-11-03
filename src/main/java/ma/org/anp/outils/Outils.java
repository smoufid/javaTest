package ma.org.anp.outils;


import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
 
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Outils implements Serializable {
    private static final long serialVersionUID = 8294143554719021767L;
 private static final Logger logger = LoggerFactory.getLogger(Outils.class);
    public static int getAnneeDate(final Date date) {
        if (date != null) {
            final Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            return cal.get(Calendar.YEAR);
        } else {
            return 0;
        }
    }
    public static Date dateFinAnnee() {
        final Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, Outils.getAnneeDate(new Date()));
        cal.set(Calendar.MONTH, 11);
        cal.set(Calendar.DAY_OF_MONTH, 31);
        final Date firstDay = cal.getTime();
        return firstDay;
    }
    public static String dateSysteme(final String format) {
        final Date date = new Date();
        Format formatter;
        formatter = new SimpleDateFormat(format);
        return formatter.format(date);
    }
    public static String formatDate(final Date date, final String format) {
        final DateFormat dateFormat = new SimpleDateFormat(format);
        final String strDate = dateFormat.format(date);
        return strDate;
    }
    private static int getMoisDate(final Date date) {
        final Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.MONTH) + 1;
    }

    private static int getJourDate(final Date date) {
        final Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.DAY_OF_MONTH);
    }

    public static long diffDateMois(final Date d1, final Date d2) {
        final long ret = java.time.temporal.ChronoUnit.MONTHS.between(LocalDate.of(Outils.getAnneeDate(d1), Outils.getMoisDate(d1), Outils.getJourDate(d1)),
                LocalDate.of(Outils.getAnneeDate(d2), Outils.getMoisDate(d2), Outils.getJourDate(d2)));
        return ret;
    }

    public static long getDateFormate(final Date date) {
        final String pattern = "yyyyMMdd";
        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        final String datef = simpleDateFormat.format(date);
        return Long.parseLong(datef);
    }

    public boolean isContainerNumberValid(final String pCid) {
        if (pCid == null || pCid.length() != 11) {
            return false;
        }
        final String char2num = "0123456789A?BCDEFGHIJK?LMNOPQRSTU?VWXYZ";
        int sum = 0;
        for (int i = 0; i < 10; i++) {
            int n = char2num.indexOf(pCid.charAt(i));
            n *= Math.pow(2, i);
            sum += n;
        }
        final int rem = sum % 11 % 10;
        return char2num.indexOf(pCid.charAt(10)) == rem;
    }

    public static String completerEspaces(final String val, final int nbrespaces) {
        if (val == null) {
            return "";
        }
        String valret = val.trim();
        final int lenval = valret.length();
        for (int i = lenval; i < nbrespaces; i++) {
            valret = valret.concat(String.valueOf(' '));
        }
        return valret;
    }

    public static String completerZeros(final String val, final int nbrzeros) {
        if (val == null || val.trim().equals("0")) {
            return "";
        }
        String valret = val.trim();
        final int lenval = valret.length();
        for (int i = lenval; i < nbrzeros; i++) {
            valret = "0".concat(valret);
        }
        return valret;
    }

 public static double reviserTarifDouble(final long anneeDebMajoration, final double tauxMajoration, final long delaiMajoration, final long anneeFacturation,
            final double ancienTarif) {
        BigDecimal parmout = BigDecimal.valueOf(ancienTarif);
        if (anneeFacturation >= anneeDebMajoration) {
            final long wDiffAnn = anneeFacturation - anneeDebMajoration + 1;
            long nbCycles = 0;
            if (delaiMajoration > 0 && (delaiMajoration == 1 || delaiMajoration == 2 || delaiMajoration == 3 || delaiMajoration == 5)) {
                nbCycles = (long) Math.ceil((double) wDiffAnn / delaiMajoration);
            }
            logger.info("wDiffAnn : " +  wDiffAnn);
            logger.info("nbCycle : " + nbCycles);
            final BigDecimal facteurTaux = BigDecimal.ONE.add(BigDecimal.valueOf(tauxMajoration).divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP));
            logger.info("facteurTaux : " + facteurTaux);
            if (nbCycles > 0) {
                for (int i = 1; i <= nbCycles; i++) {
                    parmout = parmout.multiply(facteurTaux);
                    logger.info("Tarif Revisé en cours : "+parmout);
                   // System.out.println("boucle :" + parmout);
                }
            }
        }
         logger.info("Tarif Revisé Final : "+parmout);
        return parmout.setScale(2, RoundingMode.CEILING).doubleValue();
    }

/* public static double reviserTarifDouble(
        long anneeDebMajoration,
        double tauxMajoration,
        long delaiMajoration,
        long anneeFacturation,
        double ancienTarif) {
    BigDecimal parmout = BigDecimal.valueOf(ancienTarif);
    if (anneeFacturation >= anneeDebMajoration) {
        long wDiffAnn = (anneeFacturation - anneeDebMajoration) + 1;
        long nbCycles = 0;
        if (delaiMajoration > 0
                && (delaiMajoration == 1 || delaiMajoration == 2
                || delaiMajoration == 3 || delaiMajoration == 5)) {
            nbCycles = (long) Math.ceil((double) wDiffAnn / delaiMajoration);
        }
        if (nbCycles > 0) {
            BigDecimal facteurTaux = BigDecimal.ONE.add(
                    BigDecimal.valueOf(tauxMajoration)
                            .divide(BigDecimal.valueOf(100), 10, RoundingMode.HALF_UP)
            );
            BigDecimal facteurMajoration = facteurTaux.pow((int) nbCycles);
            parmout = parmout.multiply(facteurMajoration);
            parmout = parmout.setScale(2, RoundingMode.HALF_UP);
        }
    }

    return parmout.doubleValue();
} */



}
