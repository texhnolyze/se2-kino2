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
    public static Geldbetrag parse(String geldString)
    {
        assert Geldbetrag.istValiderGeldbetragString(geldString) : "Vorbedingung verletzt";
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

        return vorzeichen + Math.abs(_eurocent / 100) + "," + String.format("%02d", Math.abs(_eurocent) % 100) + "€";
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
    }

    public static boolean istValiderGeldbetragString(String geldbetragString)
    {
    }

    public static boolean istAddierenMoeglich(Geldbetrag geldbetrag1, Geldbetrag geldbetrag2)
    {
    }

    public static boolean istSubtrahierenMoeglich(Geldbetrag geldbetrag1, Geldbetrag geldbetrag2)
    {
    }

    public static boolean istMulitplizierenMoeglich(Geldbetrag geldbetrag1, int mulitplikator)
    {
    }
}
