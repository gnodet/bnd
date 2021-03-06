package aQute.bnd.metatype;

import java.util.ArrayList;
import java.util.List;

import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.AttributeType;

import aQute.bnd.osgi.Annotation;
import aQute.bnd.xmlattribute.ExtensionDef;
import aQute.bnd.xmlattribute.Namespaces;
import aQute.bnd.xmlattribute.XMLAttributeFinder;
import aQute.lib.tag.Tag;

public class ADDef extends ExtensionDef {
	AttributeDefinition		ad;
	Annotation				a;

	String id;
	String name;
	String description;
	AttributeType type; 
	private String typeString;
	int cardinality;
	String min;
	String max;
	String[] defaults;
	boolean required = true;
	final List<OptionDef> options = new ArrayList<OptionDef>();
	
	public ADDef(XMLAttributeFinder finder) {
		super(finder);
	}

	public void prepare() {
		typeString = (type == null)? "*INVALID*": type.toString();		
	}
	
	Tag getTag(Namespaces namespaces) {
		Tag ad = new Tag("AD").addAttribute("id", id).addAttribute("type", typeString);

		if (cardinality != 0) {
			ad.addAttribute("cardinality", cardinality);
		}
		
		if (!required) {
			ad.addAttribute("required", required);
		}
		
		if (name != null) {
			ad.addAttribute("name", name);
		}

		if (description != null) {
			ad.addAttribute("description", description);
		}

		if (min != null) {
			ad.addAttribute("min", min);
		}

		if (max != null) {
			ad.addAttribute("max", max);
		}

		if (defaults != null) {
			StringBuffer b = new StringBuffer();
			String sep = "";
			for (String defaultValue :defaults) {
				b.append(sep).append(defaultValue);
				sep = ",";
			}
			ad.addAttribute("default", b.toString());
		}
		
		for (OptionDef option: options) {
			ad.addContent(option.getTag());
		}

		addAttributes(ad, namespaces);

		return ad;
	}

}
