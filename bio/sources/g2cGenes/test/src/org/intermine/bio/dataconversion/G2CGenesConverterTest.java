package org.intermine.bio.dataconversion;

/*
 * Copyright (C) 2002-2012 FlyMine
 *
 * This code may be freely distributed and modified under the
 * terms of the GNU Lesser General Public Licence.  This should
 * be distributed with the code.  See the LICENSE file for more
 * information or http://www.gnu.org/copyleft/lesser.html.
 *
 */

import java.io.File;
import java.io.FileOutputStream;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Set;

import org.apache.commons.io.IOUtils;
import org.intermine.dataconversion.ItemsTestCase;
import org.intermine.dataconversion.MockItemWriter;
import org.intermine.metadata.Model;
import org.intermine.model.fulldata.Item;

/**
 * Unit test for TreefamConverter
 * @author IM
 *
 */
public class G2CGenesConverterTest extends ItemsTestCase
{
    private G2cGenesConverter converter;
    private MockItemWriter itemWriter;

    /**
     * Constructor
     * @param arg argument
     */
    public G2CGenesConverterTest(String arg) {
        super(arg);
    }

    @Override
    public void setUp() throws Exception {

        itemWriter = new MockItemWriter(new HashMap<String, Item>());
        converter = new G2cGenesConverter(itemWriter, Model.getInstanceByName("genomic"));
        super.setUp();
    }

    /**
     * Test process
     * @throws Exception e
     */
    public void testProcess() throws Exception {
        ClassLoader loader = getClass().getClassLoader();
        String input = IOUtils.toString(loader.getResourceAsStream("DataToIntermine.tsv"));
        converter.process(new StringReader(input));
        converter.close();

        // uncomment to write out a new target items file
        writeItemsFile(itemWriter.getItems(), "g2c-tgt-items.xml");

        Set<org.intermine.xml.full.Item> expected = readItemSet("G2CGenesConverterTest_tgt.xml");

        assertEquals(expected, itemWriter.getItems());
    }
}
