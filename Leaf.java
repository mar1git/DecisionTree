package app;
import java.util.Map;

public class Leaf extends NodeOrLeaf {

	private String label;
	
	public String getLabel() {
		return label;
	}

	public Leaf(String label) {
	 if (label.getClass()!=String.class) throw new IllegalArgumentException("label is not a String");
	 this.label=label;
	}

	@Override
	String getTestedAttribute() {
		return null;
	}

	@Override
	protected void displayAttribute(int tabCount) {
    	for(int i=0;i<=tabCount;i++)
    		System.out.print("	");
        System.out.println(this.label);
		
	}

	@Override
	Map<String, NodeOrLeaf> getChildren() {
		// TODO Auto-generated method stub
		return null;
	}

}
