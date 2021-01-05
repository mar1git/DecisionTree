package app;
import java.util.LinkedHashMap;
import java.util.Map;

public class Node extends NodeOrLeaf{
	
	private Map<String,NodeOrLeaf>children;
	private String testedAttribute;
	
	public Map<String,NodeOrLeaf> getChildren() {
		return this.children;
	}
	
	public Node(String attribute) {
		 if (!(attribute instanceof String)) throw new IllegalArgumentException("label is not a String");
		 
		 this.children=new LinkedHashMap<String,NodeOrLeaf>();
		 this.testedAttribute=attribute;
	}	
	
	//////////////////////////////////////////Methods/////////////////////////////////////////////
	
	
	public void setChildren(String value , NodeOrLeaf child) {
		this.children.put(value,child);
	}
	public String getTestedAttribute() {
		return testedAttribute;
	}

	String getLabel() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	protected void displayAttribute(int tabCount) {
    	for(int i=0;i<=tabCount;i++)
    		System.out.print("	");
        System.out.println(this.testedAttribute);
		
	}
	

}
