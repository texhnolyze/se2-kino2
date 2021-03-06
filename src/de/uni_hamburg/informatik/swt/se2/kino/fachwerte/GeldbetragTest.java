package de.uni_hamburg.informatik.swt.se2.kino.fachwerte;

import org.junit.Test;

import static org.junit.Assert.*;

public class GeldbetragTest
{

    @Test
    public void testSelectEurocentSameReferences()
    {
        Geldbetrag betrag1 = Geldbetrag.select(100);
        Geldbetrag betrag2 = Geldbetrag.select(100);
        Geldbetrag betrag3 = Geldbetrag.select(101);

        assertEquals(betrag1, betrag2);
        assertNotEquals(betrag1, betrag3);
    }

    @Test
    public void testParse()
    {
        assertEquals(Geldbetrag.select(0), Geldbetrag.parse("00,00"));
        assertEquals(Geldbetrag.select(100), Geldbetrag.parse("01,00"));
        assertEquals(Geldbetrag.select(-150), Geldbetrag.parse("-01,50"));
        assertEquals(Geldbetrag.select(-150099), Geldbetrag.parse("-1500,99"));
        assertEquals(Geldbetrag.select(150099), Geldbetrag.parse("1500,99"));
    }

    @Test
    public void testToString()
    {
        assertEquals("00,00", Geldbetrag.select(0).toString());
        assertEquals("01,00", Geldbetrag.select(100).toString());
        assertEquals("-01,50", Geldbetrag.select(-150).toString());
        assertEquals("-1500,99", Geldbetrag.select(-150099).toString());
        assertEquals("1500,99", Geldbetrag.select(150099).toString());
    }

    @Test
    public void testAddierePositiveGeldbetraege()
    {
        Geldbetrag geldbetrag1 = Geldbetrag.select(100);
        Geldbetrag geldbetrag2 = Geldbetrag.select(200);
        Geldbetrag ergebnisGeldbetrag = Geldbetrag.select(300);

        assertEquals(Geldbetrag.addiere(geldbetrag1, geldbetrag2), ergebnisGeldbetrag);
    }

    @Test
    public void testAddiereMitLeeremGeldbetrag()
    {
        Geldbetrag geldbetrag = Geldbetrag.select(1000);
        Geldbetrag leererGeldbetrag = Geldbetrag.select(0);

        assertEquals(Geldbetrag.addiere(geldbetrag, leererGeldbetrag), geldbetrag);
        assertEquals(Geldbetrag.addiere(geldbetrag, leererGeldbetrag), geldbetrag);
    }

    @Test
    public void testAddiereMitNegativemGeldbetrag()
    {
        Geldbetrag geldbetrag1 = Geldbetrag.select(100);
        Geldbetrag geldbetrag2 = Geldbetrag.select(-200);
        Geldbetrag ergebnisGeldbetrag = Geldbetrag.select(-100);

        assertEquals(Geldbetrag.addiere(geldbetrag1, geldbetrag2), ergebnisGeldbetrag);
    }

    @Test
    public void testSubtrahierePositiveGeldbetraege()
    {
        Geldbetrag geldbetrag1 = Geldbetrag.select(300);
        Geldbetrag geldbetrag2 = Geldbetrag.select(100);
        Geldbetrag ergebnisGeldbetrag = Geldbetrag.select(200);

        assertEquals(Geldbetrag.addiere(geldbetrag1, geldbetrag2), ergebnisGeldbetrag);
    }

    @Test
    public void testSubtrahiereMitLeeremGeldbetrag()
    {
        Geldbetrag geldbetrag = Geldbetrag.select(1000);
        Geldbetrag leererGeldbetrag = Geldbetrag.select(0);

        assertEquals(Geldbetrag.subtrahiere(geldbetrag, leererGeldbetrag), geldbetrag);
        assertEquals(Geldbetrag.subtrahiere(geldbetrag, leererGeldbetrag), geldbetrag);
    }

    @Test
    public void testSubtrahiereMitNegativemGeldbetrag()
    {
        Geldbetrag geldbetrag1 = Geldbetrag.select(100);
        Geldbetrag geldbetrag2 = Geldbetrag.select(-200);
        Geldbetrag ergebnisGeldbetrag = Geldbetrag.select(300);

        assertEquals(Geldbetrag.subtrahiere(geldbetrag1, geldbetrag2), ergebnisGeldbetrag);
    }

    @Test
    public void testMulitpliziere()
    {
        Geldbetrag geldbetrag = Geldbetrag.select(1000);
        Geldbetrag ergebnisGeldbetrag = Geldbetrag.select(33000);

        assertEquals(Geldbetrag.multipliziere(geldbetrag, 33), ergebnisGeldbetrag);
    }


    @Test public void isValidGeldbetragString()
    {
        String validerGeldString = "9999,88";
        String invaliderGeldString1 = "9999,8";
        String invaliderGeldString2 = ",8";
        String invaliderGeldString3 = "1,8";
        String invaliderGeldString4 = "10,88???";

        assertTrue(Geldbetrag.istValiderGeldbetragString(validerGeldString));
        assertFalse(Geldbetrag.istValiderGeldbetragString(invaliderGeldString1));
        assertFalse(Geldbetrag.istValiderGeldbetragString(invaliderGeldString2));
        assertFalse(Geldbetrag.istValiderGeldbetragString(invaliderGeldString3));
        assertFalse(Geldbetrag.istValiderGeldbetragString(invaliderGeldString4));
    }

    @Test public void istAddierenMoeglich()
    {
        Geldbetrag geldbetrag = Geldbetrag.select(1000);
        Geldbetrag positiverGeldbetrag = Geldbetrag.select(3);
        Geldbetrag negativerGeldbetrag = Geldbetrag.select(-3);

        Geldbetrag maxGeldbetrag = Geldbetrag.select(Integer.MAX_VALUE);
        Geldbetrag minGeldbetrag = Geldbetrag.select(Integer.MIN_VALUE);

        assertTrue(Geldbetrag.istAddierenMoeglich(geldbetrag, positiverGeldbetrag));
        assertTrue(Geldbetrag.istAddierenMoeglich(geldbetrag, positiverGeldbetrag));
        assertTrue(Geldbetrag.istAddierenMoeglich(positiverGeldbetrag, negativerGeldbetrag));

        assertFalse(Geldbetrag.istAddierenMoeglich(maxGeldbetrag, positiverGeldbetrag));
        assertFalse(Geldbetrag.istAddierenMoeglich(minGeldbetrag, negativerGeldbetrag));
    }

    @Test public void istSubtrahierenMoeglich()
    {
        Geldbetrag geldbetrag = Geldbetrag.select(1000);
        Geldbetrag positiverGeldbetrag = Geldbetrag.select(3);
        Geldbetrag negativerGeldbetrag = Geldbetrag.select(-3);

        Geldbetrag maxGeldbetrag = Geldbetrag.select(Integer.MAX_VALUE);
        Geldbetrag minGeldbetrag = Geldbetrag.select(Integer.MIN_VALUE);

        assertTrue(Geldbetrag.istSubtrahierenMoeglich(geldbetrag, positiverGeldbetrag));
        assertTrue(Geldbetrag.istSubtrahierenMoeglich(geldbetrag, positiverGeldbetrag));
        assertTrue(Geldbetrag.istSubtrahierenMoeglich(geldbetrag, negativerGeldbetrag));

        assertFalse(Geldbetrag.istSubtrahierenMoeglich(minGeldbetrag, positiverGeldbetrag));
        assertFalse(Geldbetrag.istSubtrahierenMoeglich(maxGeldbetrag, negativerGeldbetrag));
    }

    @Test public void istMulitplizierenMoeglich()
    {
        Geldbetrag geldbetrag = Geldbetrag.select(1000);
        int multiplikator = 3;

        Geldbetrag maxGeldbetrag = Geldbetrag.select(Integer.MAX_VALUE);

        assertTrue(Geldbetrag.istMulitplizierenMoeglich(geldbetrag, multiplikator));
        assertFalse(Geldbetrag.istMulitplizierenMoeglich(maxGeldbetrag, multiplikator));
    }
}
