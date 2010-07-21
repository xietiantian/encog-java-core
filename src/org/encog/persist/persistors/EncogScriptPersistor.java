/*
 * Encog(tm) Core v2.5 
 * http://www.heatonresearch.com/encog/
 * http://code.google.com/p/encog-java/
 * 
 * Copyright 2008-2010 by Heaton Research Inc.
 * 
 * Released under the LGPL.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 * 
 * Encog and Heaton Research are Trademarks of Heaton Research, Inc.
 * For information on Heaton Research trademarks, visit:
 * 
 * http://www.heatonresearch.com/copyright.html
 */

package org.encog.persist.persistors;

import org.encog.neural.data.TextData;
import org.encog.neural.networks.BasicNetwork;
import org.encog.neural.networks.layers.Layer;
import org.encog.parse.tags.read.ReadXML;
import org.encog.parse.tags.write.WriteXML;
import org.encog.persist.EncogPersistedCollection;
import org.encog.persist.EncogPersistedObject;
import org.encog.persist.Persistor;
import org.encog.script.EncogScript;

/**
 * The Encog persistor used to persist the TextData class.
 * 
 * @author jheaton
 */
public class EncogScriptPersistor implements Persistor {

	public static final String TAG_SOURCE = "source";
	
	/**
	 * Load the specified Encog object from an XML reader.
	 * 
	 * @param in
	 *            The XML reader to use.
	 * @return The loaded object.
	 */
	public EncogPersistedObject load(final ReadXML in) {
		final String name = in.getTag().getAttributeValue("name");
		final String description = in.getTag().getAttributeValue("description");
		final EncogScript result = new EncogScript();
		
		result.setName(name);
		result.setDescription(description);
		
		
		final String end = in.getTag().getName();
		while (in.readToTag()) {
			if (in.is(EncogScriptPersistor.TAG_SOURCE, true)) {
				handleSource(in,result);
			}
			else if (in.is(end, false)) {
				break;
			}
		}

		return result;
	}
	
	private void handleSource(final ReadXML in, EncogScript script)
	{
		in.readToTag();		
		final String text = in.getTag().getName();
		script.setSource(text);

	}

	/**
	 * Save the specified Encog object to an XML writer.
	 * 
	 * @param obj
	 *            The object to save.
	 * @param out
	 *            The XML writer to save to.
	 */
	public void save(final EncogPersistedObject obj, final WriteXML out) {
		PersistorUtil.beginEncogObject(EncogPersistedCollection.TYPE_SCRIPT, out,
				obj, true);
		final EncogScript text = (EncogScript) obj;
		out.beginTag(TAG_SOURCE);
		out.addCDATA(text.getSource());
		out.endTag();
		out.endTag();
	}

}