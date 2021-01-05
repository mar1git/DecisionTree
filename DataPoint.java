package app;
import java.util.HashMap;
import java.util.Map;

public class DataPoint {

		public String label;
		public Map<String, String> attributes;
		

		public String getLabel() {
			return label;
		}

		public void setLabel(String label) {
			this.label = label;
		}
		public String getAttribute(String attribute) {
			return this.attributes.get(attribute);
		}
		public void setAttributes(Map<String, String> attributes) {
			this.attributes = attributes;
		}
		public DataPoint() {
			this.label=null;
			this.attributes=new HashMap<String,String>();
		}
		public void display() {
			System.out.println(attributes);
		}
		
}
