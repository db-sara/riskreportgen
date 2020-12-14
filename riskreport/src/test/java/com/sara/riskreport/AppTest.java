package com.sara.riskreport;

import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Test;

import com.itextpdf.text.DocumentException;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     * @throws DocumentException 
     * @throws IOException 
     */
    @Test
    public void shouldAnswerWithTrue() throws IOException, DocumentException
    {
    	String[] args = new String[0];
    	App.main(args);
        assertTrue( true );
    }
}
