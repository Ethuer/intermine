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

import org.intermine.dataconversion.ItemWriter;
import org.intermine.metadata.Model;
import org.intermine.xml.full.Item;

import org.apache.commons.lang.StringUtils;
import org.intermine.dataconversion.ItemWriter;
import org.intermine.metadata.Model;
import org.intermine.util.FormattedTextParser;
import org.intermine.xml.full.Item;


import java.io.Reader;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import java.lang.String;
import java.util.HashMap;
import java.util.Map;


/**
 * 
 * @author
 */
public class PichiaannotateConverter extends BioFileConverter
{
    //
    private static final String DATASET_TITLE = "pichia annotate";
    private static final String DATA_SOURCE_NAME = "pichiaannotate1007";
	private Map<String, Item>annotations= new HashMap<String, Item>();	//add parameters

 	public PichiaannotateConverter(ItemWriter writer, Model model) {
        super(writer, model, DATA_SOURCE_NAME, DATASET_TITLE);
    }

    /**
     * Constructor
     * @param writer the ItemWriter used to handle the resultant items
     * @param model the Model
     */
   
    public void process(Reader reader) throws Exception 
	{
	Iterator<?> lineIter = FormattedTextParser.parseDelimitedReader(reader,','); // check delimiter  parseTabDelimitedReader(reader);
		
		//Model model = Model.getInstance("annotation");
		//loop through lines 
		while (lineIter.hasNext())
		{
			String[] line = (String[]) lineIter.next();


			Item anId =annotations.get(line[0]);

			if (anId == null)  // Cellbank custom file column 0 (=1st) 
				{
				Item annotation = createItem("Annotation");  
 					annotation.setAttribute("QueryId",line[0]);
					annotation.setAttribute("Qlenght",line[1]);
					annotation.setAttribute("HitEvalue",line[2]);
					annotation.setAttribute("Hitscore",line[3]);
					annotation.setAttribute("HitDesc",line[4]);
					//annotation.setReference("QueryId",line[0]);
					store(annotation);
					
			/*FileWriter fw = new FileWriter(new File(pichiaannotate.xml));
			fw.write(FullRenderer.render("Annotation"));
			*/
				} 
   		}
	}
/*
    public void process(Writer writer) throws Exception 
	{
	Iterator<?> lineIter = FormattedTextParser.parseDelimitedReader(reader,'\t'); // check delimiter

		//loop through lines 
		while (lineIter.hasNext())
		{
			String[] line = (String[]) lineIter.next();


			Item anId =annotations.get(line[0]);
			
			if (anId == null)  // Cellbank custom file column 0 (=1st) 
				{
				Item annotation = createItem("Annotation");  
 					annotation.setAttribute("QueryId",line[0]);
					annotation.setAttribute("Qlenght",line[1]);
					annotation.setAttribute("HitEvalue",line[2]);
					annotation.setAttribute("Hitscore",line[3]);
					annotation.setAttribute("HitDesc",line[4]);
					//annotation.setReference("QueryId",line[0]);
					store(annotation);

			FileWriter fw = new FileWriter(new File(pichiaannotate1007.xml)):
			fw.write(FullRenderer.render(items)):
			
*/
}
    
