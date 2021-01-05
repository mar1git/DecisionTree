package app;
import java.util.Map;

public abstract class NodeOrLeaf {
	
	abstract String getLabel();
	abstract String getTestedAttribute();
	abstract Map<String,NodeOrLeaf> getChildren();
	protected abstract void displayAttribute(int tabCount);
}
