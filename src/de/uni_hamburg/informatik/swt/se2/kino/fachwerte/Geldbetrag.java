package de.uni_hamburg.informatik.swt.se2.kino.fachwerte;

import java.util.HashMap;

public class Geldbetrag
{
	// Limitieren valider Geldbetraege auf 7 Stellen vor dem Komma, 9 Stellen insgesamt.
	// , Java int kann problemlos 9 Stellen insgesamt.
	private static final int MAX_DIGITS = 9;
	public static final int MAX_VALUE = (int) Math.pow(10, MAX_DIGITS) - 1;
	public static final int MIN_VALUE = -MAX_VALUE;
	
    private final int _eurocent;
    private static final HashMap<Integer, Geldbetrag> UNIVERSUM = new HashMap<>();

    private Geldbetrag(int eurocent)
    {
        _eurocent = eurocent;
    }

    /**
     * Returns a @Geldbetrag object from the @UNIVERSUM pool of possible @Geldbetrag objects.
     * If none exists, a new one is created and added to the pool.
     *
     * @param eurocent The amount of Euro Cents
     * @return A @Geldbetrag object depicting the eurocents passed via argument
     * 
     * @require istValiderGeldbetrag(eurocent)
     */
    public static Geldbetrag select(int eurocent)
    {
    	assert istValiderGeldbetrag(eurocent) : "Vorbedingung verletzt";
    	
        if (!UNIVERSUM.containsKey(eurocent))
        {
            UNIVERSUM.put(eurocent, new Geldbetrag(eurocent));
        }

        return UNIVERSUM.get(eurocent);
    }

    /**
     * Parses a String depicting a human-readable amount of money, e.g.: "3,50"
     * for 3 euros with 50 cents.
     *
     * @param geldString The human-readable string
     * @return A @Geldbetrag object depicting the appropriate money
     *
     * @require isValidGeldbetragString(geldString)
     */
    public static Geldbetrag parse(final String geldString)
    {
        assert Geldbetrag.istValiderGeldbetragString(geldString) : "Vorbedingung verletzt";
        
        String geldStringWoutComma = geldString.replace(",", "");
        return Geldbetrag.select(Integer.valueOf(geldStringWoutComma));
    }

    /**
     * Converts the money that this object depicts into a human-readable string.
     *
     * @return The money depicted by this object
     */
    @Override
    public String toString()
    {
    	String vorzeichen = Math.signum(_eurocent) == -1.0 ? "-" : "";
    	int euro = Math.abs(_eurocent / 100);
    	int cent = Math.abs(_eurocent) % 100;
    	
        return vorzeichen + euro + "," + String.format("%02d", cent);
    }

    /**
     * Adds one money value to another and returns the new Geldbetrag.
     *
     * @param geldbetrag1
     * @param geldbetrag2
     * @return The money depicted by this object
     *
     * @require geldbetrag1 != null
	 * @require geldbetrag2 != null
     * @require istAddierenMoeglich(geldbetrag1, geldbetrag2)
     */
    public static Geldbetrag addiere(Geldbetrag geldbetrag1, Geldbetrag geldbetrag2)
    {
        assert geldbetrag1 != null : "Vorbedingung verletzt";
        assert geldbetrag2 != null : "Vorbedingung verletzt";
        assert Geldbetrag.istAddierenMoeglich(geldbetrag1, geldbetrag2): "Vorbedingung verletzt";
        
        return Geldbetrag.select(geldbetrag1._eurocent + geldbetrag2._eurocent);
    }

    /**
     * Subtract one money value to another and returns the new Geldbetrag.
     *
     * @param geldbetrag1
     * @param geldbetrag2
     * @return The money depicted by this object
     *
     * @require geldbetrag1 != null
     * @require geldbetrag2 != null
     * @require Geldbetrag.istSubtrahierenMoeglich(geldbetrag1, geldbetrag2)
     */
    public static Geldbetrag subtrahiere(Geldbetrag geldbetrag1, Geldbetrag geldbetrag2)
    {
        assert geldbetrag1 != null : "Vorbedingung verletzt";
        assert geldbetrag2 != null : "Vorbedingung verletzt";
        assert Geldbetrag.istSubtrahierenMoeglich(geldbetrag1, geldbetrag2): "Vorbedingung verletzt";
        
        return Geldbetrag.select(geldbetrag1._eurocent - geldbetrag2._eurocent);
    }

    /**
     * Mulitplicates one Geldbetrag by a non negative integer.
     *
     * @param geldbetrag
     * @param multiplikator
     * @return The money depicted by this object
     *
     * @require geldbetrag != null 
	 * @require multiplikator >= 0
     * @require Geldbetrag.istMulitplizierenMoeglich(geldbetrag, multiplikator)
     */
    public static Geldbetrag multipliziere(Geldbetrag geldbetrag, int multiplikator)
    {
        assert geldbetrag != null : "Vorbedingung verletzt";
        assert multiplikator >= 0 : "Vorbedingung verletzt";
        assert Geldbetrag.istMulitplizierenMoeglich(geldbetrag, multiplikator) : "Vorbedingung verletzt";
        
        return Geldbetrag.select(geldbetrag._eurocent * multiplikator);
    }

    /**
     * Prueft, ob ein String einen validen Geldbetrag repraesentiert.
     * @param geldbetragString String, der gecheckt werden soll
     * @return true wenn moeglich, sonst false
     */
    public static boolean istValiderGeldbetragString(String geldbetragString)
    {
    	return geldbetragString.matches("^-?([1-9]\\d{1," + (MAX_DIGITS - 3) + "}|\\d),\\d{2}$");
    }
    
    public static boolean istValiderGeldbetrag(int eurocent)
    {
    	return eurocent >= MIN_VALUE && eurocent <= MAX_VALUE;
    }

    /**
     * Prueft, ob ein Addieren zweier Geldbetraege moeglich ist.
     * @param geldbetrag1 der Geldbetrag, auf den draufaddiert werden soll
     * @param geldbetrag2 der Geldbetrag, der addiert werden soll
     * @return true wenn moeglich, sonst false
     */
    public static boolean istAddierenMoeglich(Geldbetrag geldbetrag1, Geldbetrag geldbetrag2)
    {   	
    	int maxVal = Math.max(geldbetrag1._eurocent, geldbetrag2._eurocent);
    	int minVal = Math.min(geldbetrag1._eurocent, geldbetrag2._eurocent);
    	int summe = geldbetrag1._eurocent + geldbetrag2._eurocent;
    	
    	if (geldbetrag1.istPositiv() && geldbetrag2.istPositiv())  // beide positiv --> overflow
    	{
    		return (summe >= maxVal) &&	(summe <= MAX_VALUE);
    	}
    	else if (!(geldbetrag1.istPositiv() || geldbetrag2.istPositiv()))  // beide negativ --> underflow
    	{
    		return (summe <= minVal) &&	(summe >= MIN_VALUE);
    	}
    	else
    	{
    		return true;
    	}
    }

    /**
     * Prueft, ob ein Subtrahieren zweier Geldbetraege moeglich ist.
     * @param geldbetrag1 der Geldbetrag, von dem abgezogen werden soll
     * @param geldbetrag2 der Geldbetrag, der abgezogen werden soll
     * @return true wenn moeglich, sonst false
     */
    public static boolean istSubtrahierenMoeglich(Geldbetrag geldbetrag1, Geldbetrag geldbetrag2)
    {
    	int maxVal = Math.max(geldbetrag1._eurocent, geldbetrag2._eurocent);
    	int minVal = Math.min(geldbetrag1._eurocent, geldbetrag2._eurocent);
    	int differenz = geldbetrag1._eurocent - geldbetrag2._eurocent;
    	
    	if (geldbetrag1.istPositiv() && geldbetrag2.istPositiv())  // beide positiv --> underflow
    	{
    		return (differenz <= geldbetrag1._eurocent) && (differenz >= MIN_VALUE);
    	}
    	else if (!(geldbetrag1.istPositiv() || geldbetrag2.istPositiv()))  // beide negativ --> overflow
    	{
    		return (differenz >= geldbetrag1._eurocent) && (differenz <= MAX_VALUE);
    	}
    	else
    	{    		
    		int deltaLow = minVal - MIN_VALUE;
    		int deltaHigh = MAX_VALUE - maxVal;
    		
    		return (maxVal <= deltaLow && Math.abs(minVal) <= deltaHigh);
    	}
    }

    /**
     * Prueft, ob ein Multiplizieren von geldbetrag mit multiplikator moeglich ist.
     * @param geldbetrag der zu multiplizierende Geldbetrag
     * @param multiplikator der Multiplikator
     * @return true wenn moeglich, sonst false
     */
    public static boolean istMulitplizierenMoeglich(Geldbetrag geldbetrag, int multiplikator)
    {
    	int product = geldbetrag._eurocent * multiplikator;
    	
    	return (product >= geldbetrag._eurocent && product <= MAX_VALUE) || multiplikator == 0;
    }
    
    /**
     * Prueft, ob ein Geldbetrag positiv ist.
     * 
     * @return true, wenn positiv, sonst false
     */
    public boolean istPositiv()
    {
    	return Math.signum(_eurocent) >= 0;
    }
}
