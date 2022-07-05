package de.uni_hamburg.informatik.swt.se2.kino.fachwerte;

import java.util.HashMap;

public class Geldbetrag
{
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
     */
    public static Geldbetrag select(int eurocent)
    {
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
    	String vorzeichen = "";

    	if (Math.signum(_eurocent) == -1.0)
    		vorzeichen = "-";

        return vorzeichen + Math.abs(_eurocent / 100) + "," + String.format("%02d", Math.abs(_eurocent) % 100);
    }

    /**
     * Adds one money value to another and returns the new Geldbetrag.
     *
     * @param geldbetrag1
     * @param geldbetrag2
     * @return The money depicted by this object
     *
     * @require geldbetrag1 != null && geldbetrag2 != null
     * @require istAddierenMoeglich(geldbetrag1, geldbetrag2)
     */
    public static Geldbetrag addiere(Geldbetrag geldbetrag1, Geldbetrag geldbetrag2)
    {
        assert geldbetrag1 != null && geldbetrag2 != null: "Vorbedingung verletzt";
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
     * @require geldbetrag1 != null && geldbetrag2 != null
     * @require Geldbetrag.istSubtrahierenMoeglich(geldbetrag1, geldbetrag2)
     */
    public static Geldbetrag subtrahiere(Geldbetrag geldbetrag1, Geldbetrag geldbetrag2)
    {
        assert geldbetrag1 != null && geldbetrag2 != null: "Vorbedingung verletzt";
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
     * @require geldbetrag != null && multiplikator > 0
     * @require Geldbetrag.istMulitplizierenMoeglich(geldbetrag, multiplikator)
     */
    public static Geldbetrag multipliziere(Geldbetrag geldbetrag, int multiplikator)
    {
        assert geldbetrag != null && multiplikator > 0: "Vorbedingung verletzt";
        assert Geldbetrag.istMulitplizierenMoeglich(geldbetrag, multiplikator): "Vorbedingung verletzt";
        
        return Geldbetrag.select(geldbetrag._eurocent * multiplikator);
    }

    /**
     * Prueft, ob ein String einen validen Geldbetrag repraesentiert.
     * @param geldbetragString String, der gecheckt werden soll
     * @return true wenn moeglich, sonst false
     */
    public static boolean istValiderGeldbetragString(String geldbetragString)
    {
    	return geldbetragString.matches("^-?([1-9]\\d{1,}|\\d),\\d{2}$");
    }

    /**
     * Prueft, ob ein Addieren zweier Geldbetraege moeglich ist.
     * @param geldbetrag1 der Geldbetrag, auf den draufaddiert werden soll
     * @param geldbetrag2 der Geldbetrag, der addiert werden soll
     * @return true wenn moeglich, sonst false
     */
    public static boolean istAddierenMoeglich(Geldbetrag geldbetrag1, Geldbetrag geldbetrag2)
    {   	
    	if (geldbetrag1.istPositiv() && geldbetrag2.istPositiv())  // beide positiv --> overflow
    	{
    		return (geldbetrag1._eurocent + geldbetrag2._eurocent >= Math.max(geldbetrag1._eurocent, geldbetrag2._eurocent));
    	}
    	else if (!geldbetrag1.istPositiv() && !geldbetrag2.istPositiv())  // beide negativ --> underflow
    	{
    		return (geldbetrag1._eurocent + geldbetrag2._eurocent <= Math.min(geldbetrag1._eurocent, geldbetrag2._eurocent));
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
    	if (geldbetrag1.istPositiv() && geldbetrag2.istPositiv())  // beide positiv --> underflow
    	{
    		return (geldbetrag1._eurocent - geldbetrag2._eurocent <= geldbetrag1._eurocent);
    	}
    	else if (!geldbetrag1.istPositiv() && !geldbetrag2.istPositiv())  // beide negativ --> overflow
    	{
    		return (geldbetrag1._eurocent - geldbetrag2._eurocent >= geldbetrag1._eurocent);
    	}
    	else
    	{
    		int minVal = Math.min(geldbetrag1._eurocent, geldbetrag2._eurocent);
    		int maxVal = Math.max(geldbetrag1._eurocent, geldbetrag2._eurocent);
    		
    		int deltaLow = minVal - Integer.MIN_VALUE;
    		int deltaHigh = Integer.MAX_VALUE - maxVal;
    		
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
    	return geldbetrag._eurocent * multiplikator >= geldbetrag._eurocent;
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
