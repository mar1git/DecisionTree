package app;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map.Entry;


public class TreeID3 {
	
	public NodeOrLeaf root;
	public Dataset dataset;

	public TreeID3(String chemin) {
		this.dataset=new Dataset(chemin);
		this.root=null;
	}
	
	public void build() {
		this.root = this.buildTree(this.dataset);
	}

	private NodeOrLeaf buildTree(Dataset dataset) {

	    if (dataset.getClass()!=Dataset.class) throw new IllegalArgumentException("This is not a dataset");
	    if (dataset.getSize()==0) throw new IllegalArgumentException("This dataset is empty");

	    if (dataset.getEntropy() == 0)
	        //on retourne l'étiquette en question
        return new Leaf(dataset.getDatas().get(0).getLabel());
	    
	    //s'il ne reste d'attribut à tester
	    if (this.dataset.getAttributes_name().size() == 0) {
	        int max=0;
	        String lastLabel=null;
	        //on teste toutes les étiquettes possibles de l'ens emble
	        ArrayList<String> labelSet=dataset.getLabelset();
	        Iterator<String> iterator = labelSet.iterator();
			while (iterator.hasNext()) {
				String temp=iterator.next();
				Dataset subset =dataset.getSubset(temp);
		           //si c'est la plus fréquente, c'est celle qu'on choisit
		           if (subset.getSize() > max) {
		            max=subset.getSize();
		            lastLabel=temp;		  
			}
	        }          
	       //et on la retourne dans une feuille
	        return new Leaf(lastLabel);
	    }
	    
	    String bestAttribute=dataset.optimalAttribute();
	    
	    //si on arrive ici, on retourne d'office un noeud et pas une feuille
	    Node node =new Node(bestAttribute);
	    //pour chaque valeur que peut prendre l'attribut  tester
	    ArrayList<String> valuesSet=new ArrayList<String>(dataset.getAttributeValuesSet(bestAttribute));
	    Iterator<String> iterator = valuesSet.iterator();
		while (iterator.hasNext()) {
			String temp=iterator.next();
	        //on cree un sous-ensemble
	    	
	        Dataset subset = dataset.getSubsetByAttribute(bestAttribute, temp);
	        //et on en cree un nouveau noeud
	        node.setChildren(temp,this.buildTree(subset));
	    //on retourne le noeud que l'on vient de creer
		}
		
	    return node;

	}
	
	public void display() {
		this.display(this.root,0);
	}

	private void display(NodeOrLeaf node, int tabCount ) {
		
        //on affiche le nom de l'attribut teste
		node.displayAttribute(tabCount);
        //on parcourt ses enfants
		if (node instanceof Leaf) return;
		Iterator<Entry<String, NodeOrLeaf>> iterator = node.getChildren().entrySet().iterator();
		while (iterator.hasNext()) {
			Entry<String, NodeOrLeaf> temp=iterator.next();
         	for(int i=0;i<=tabCount;i++)
        		System.out.print("	");
            System.out.println("__"+temp.getKey()+"__");
            this.display(temp.getValue(), tabCount+1);
		}
	}
		
	    
}
