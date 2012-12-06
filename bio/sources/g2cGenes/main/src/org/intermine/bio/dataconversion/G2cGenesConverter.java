package org.intermine.bio.dataconversion;

/*
 * Copyright (C) 2002-2011 FlyMine
 *
 * This code may be freely distributed and modified under the
 * terms of the GNU Lesser General Public Licence.  This should
 * be distributed with the code.  See the LICENSE file for more
 * information or http://www.gnu.org/copyleft/lesser.html.
 *
 */


import java.io.Reader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.Logger;
import org.intermine.dataconversion.ItemWriter;
import org.intermine.metadata.Model;
import org.intermine.objectstore.ObjectStoreException;
import org.intermine.util.FormattedTextParser;
import org.intermine.xml.full.Item;


/**
 * 
 * @author
 */
public class G2cGenesConverter extends BioFileConverter
{
    private static final String DATASET_TITLE = "G2C data set";
    private static final String DATA_SOURCE_NAME = "G2C";
    private static final String TAXON_ID = "10090";
    private static final Logger LOG = Logger.getLogger(G2cGenesConverter.class);
    
    private Map<String, String> mouses = new HashMap<String, String>();
    private Map<String, String> strains = new HashMap<String, String>();
    private Map<String, String> genes = new HashMap<String, String>();
    private Map<String, String> backgroundStrains = new HashMap<String, String>();
    
    
    /**
     * Constructor
     * @param writer the ItemWriter used to handle the resultant items
     * @param model the Model
     */
    public G2cGenesConverter(ItemWriter writer, Model model) {
        super(writer, model, DATA_SOURCE_NAME, DATASET_TITLE);
    }

    static final String[] EXPERIMENTS = { 
            "EPM_5_dist_total",
            "EPM_5_dist_max",
            "EPM_5_percent_time_in_open",
            "EPM_5_central_duration",
            "OF_5_dist_max",
            "NOE_5_dist_total",
            "NOE_5_inner_avidity",
            "RR_naive",
            "RR_learning",
            "RR_memory",
            "FC_AQ_trial",
            "FC_AQ_trial.tone",
            "FC_CT_session",
            "FC_CT_session.bin",
            "FC_CU_tone",
            "FC_CU_bin"
        };
    
    /**
     * 
     *
     * {@inheritDoc}
     */
    public void process(Reader reader) throws Exception {
        Iterator<String[]> lineIter = FormattedTextParser.parseTabDelimitedReader(reader);
        while (lineIter.hasNext()) {

            String[] bits = lineIter.next();
            if (bits.length < 30) {
                LOG.warn("wrong line length, expected 32 columns, was " + bits.length);
                continue;
            }
                        
            String doc = bits[1];
            String strainIdentifier = bits[2];
            String strainName = bits[4];
            String backgroundStrain = bits[5];
            String dob = bits[9];
            String genotype = bits[10];
            String sex = bits[11];
            String mouseIdentifier = bits[12];

            String strain = getStrain(strainIdentifier, strainName, backgroundStrain);
            String mouse = getMouse(mouseIdentifier, doc, dob, genotype, sex, strain);

            // columns 14 to 29
            for (int i = 1; i <= 15; i++) {
                int increment = 13; // get the right part of the array
                String score = bits[i + increment];
                Item item = createItem("Behaviour");
                item.setAttribute("score", score);
                item.setAttribute("name", EXPERIMENTS[i]);
                item.setReference("subject", mouse);
                item.setReference("strain", strain);
                store(item);
            }
        }
    }
    
    private String getMouse(String mouseIdentifier, String doc, String dob, String genotype, 
            String sex, String strain) throws ObjectStoreException {
        String refId = mouses.get(mouseIdentifier);
        if (refId == null) {
            Item mouse = createItem("Subject");
            mouse.setAttribute("doc", doc);
            mouse.setAttribute("dob", dob);
            mouse.setAttribute("genotype", genotype);
            mouse.setAttribute("sex", sex);
            mouse.setReference("strain", strain);
            store(mouse);
            refId = mouse.getIdentifier();
            mouses.put(mouseIdentifier, refId);
        }
        return refId;
    }
    
    private String getStrain(String strainIdentifier, String strainName, String backgroundStrain) 
            throws ObjectStoreException {
        String refId = strains.get(strainIdentifier);
        if (refId == null) {
            Item strain = createItem("Allele");
            strain.setAttribute("primaryIdentifier", strainIdentifier);
            strain.setAttribute("symbol", strainName);
            strain.setReference("backgroundStrain", getBackgroundStrain(backgroundStrain));
            strain.setReference("organism", getOrganism(TAXON_ID));
            strain.setReference("gene", getGene(strainName));
            store(strain);
            refId = strain.getIdentifier();
            strains.put(strainIdentifier, refId);
        }
        return refId;
    }
    
    private String getBackgroundStrain(String identifier) 
            throws ObjectStoreException {
        String refId = backgroundStrains.get(identifier);
        if (refId == null) {
            Item item = createItem("Allele");
            item.setAttribute("primaryIdentifier", identifier);
            item.setAttribute("symbol", identifier);
            item.setReference("organism", getOrganism(TAXON_ID));
            store(item);
            refId = item.getIdentifier();
            backgroundStrains.put(identifier, refId);
        }
        return refId;
    }
    
    private String getGene(String identifier) throws ObjectStoreException {
        String refId = genes.get(identifier);
        if (refId == null) {
            Item item = createItem("Gene");
            item.setAttribute("symbol", identifier);
            item.setReference("organism", getOrganism(TAXON_ID));
            store(item);
            refId = item.getIdentifier();
            genes.put(identifier, refId);
        }
        return refId;
    }
}
