package app;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;


public class Dataset {
	
	private static final String DELIMITER =";";
	private ArrayList<String> attributes_name;
	private ArrayList<DataPoint> datas;
	
	public Dataset() {
		this.attributes_name=new ArrayList<String> ();
	    this.datas=new ArrayList<DataPoint>();
	}
	public Dataset(String chemin){
	    Map<String, String> attributes = new HashMap<String, String>();
	    this.attributes_name=new ArrayList<String>();
	    this.datas=new ArrayList<DataPoint>();
	    
	    
		try (Scanner scanner = new Scanner(new File(chemin));) {
			
			this.attributes_name=this.getAttributesFromLine(scanner.nextLine());
			
		    while (scanner.hasNextLine()) {
		        attributes=getDataFromLine(scanner.nextLine());
		        DataPoint dataTemp=new DataPoint();
		        dataTemp.setLabel(attributes.get(attributes_name.get(attributes_name.size()-1))); //On affecte à l'etiquette de DateTemp la dernière valeur de Map attributes
		        attributes.remove(attributes_name.get(attributes_name.size()-1));//On supprime la dernière colonne de attributes car sa valeur a été stocké dans data.label
		        dataTemp.setAttributes(attributes);
		        this.datas.add(dataTemp);
		    }
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.attributes_name.remove(attributes_name.size()-1);
	}
	
	
	/////////////////////////////////////METHODS////////////////////////////////////////////
	
	public ArrayList<String> getAttributes_name() {
		return attributes_name;
	}
	
	public ArrayList<DataPoint> getDatas() {
		return datas;
	}
	public void setDatas(ArrayList<DataPoint> datas) {
		this.datas = datas;
	}
  	private Map<String, String> getDataFromLine(String line) {
  		Map<String, String> values = new HashMap<String, String>();
	    int i=0;
	    try (Scanner rowScanner = new Scanner(line)) {
	        rowScanner.useDelimiter(DELIMITER);
	        while (rowScanner.hasNext()) {
	        	values.put(attributes_name.get(i),rowScanner.next());
	            i++;
	        }
	    }
	    return values;
	}
	
	private ArrayList<String> getAttributesFromLine(String line) {
		ArrayList<String> values = new ArrayList<String>();
	    try (Scanner rowScanner = new Scanner(line)) {
	        rowScanner.useDelimiter(DELIMITER);
	        while (rowScanner.hasNext()) {
	            values.add(rowScanner.next());
	        }
	    }
	    return values;
	}
	
	public int getSize() {
		return datas.size();
	}
	
	public double getEntropy() {
//        retourne l'entropie de Shannon de l'ensemble
	    //initialisation de la variable retournée
	    double result = 0;
	    //pour chaque étiquette de l'ensemble
	    
	    Iterator<String> iterator =this.getLabelset().iterator();
	    
		while (iterator.hasNext()) {
			String temp=iterator.next();
	        //on crée un sous-ensemble qui ne contient que les éléments de
	        //self ayant etiquette comme étiquette
	        Dataset subset = this.getSubset(temp);
	        //on ajoute |c| * log_2(|c|) à ret
	        int subsetLenght = subset.getSize();
	        result += subsetLenght * Math.log(subsetLenght)/Math.log(2);
	    //on retourne log_2(|S|) - ret/|S|
		}

	    return Math.log(this.getSize())/Math.log(2) - result/this.getSize();
	}
	
	public ArrayList<String> getLabelset() {
	    //on initialise la valeur de retour
	    ArrayList<String> result =new ArrayList<String>();
	    
	    //pour chaque exemple de l'ensemble
	    
	    Iterator<DataPoint> iterator =this.datas.iterator();
		while (iterator.hasNext()) {
			DataPoint temp=iterator.next();
	    	if (!result.contains(temp.getLabel()))
		            result.add(temp.getLabel());	
		}
	       
	    return result;
	}
	
	public Dataset getSubset(String label) {
		
		Dataset result= new Dataset();
		result.attributes_name=this.attributes_name;
		
		Iterator<DataPoint> iterator =this.datas.iterator();
		while (iterator.hasNext()) {
			DataPoint temp=iterator.next();
			if (temp.getLabel().equals(label)) {
				result.datas.add(temp);
			}
		}
		
		return result;
	}
	
	
	public String optimalAttribute() {
        //retourne un string avec le nom de l'attribut à tester
		double max=0;
		String result =null;
		//pour chaque attribut
		//System.out.println()
		Iterator<String> iterator =this.attributes_name.iterator();
		while (iterator.hasNext()) {
			String temp=iterator.next();
			double gain = this.getGain(temp);
			
	        //si le gain d'entropie est la plus grande
	        if (gain >= max) {
	            //on le garde en memoire
	            max = gain;
	            result = temp;
	        }
		}
    //et on le retourne
		return result;
	}

	public double getGain(String attribute) {
        //retourne la perte d'entropie selon la définition de Ross Quinlan
		double sigma = 0;
		//pour chaque valeur de l'attribut en question
		
		Iterator<String> iterator =this.getAttributeValuesSet(attribute).iterator();
		while (iterator.hasNext()) {
			String temp=iterator.next();
	    	//déclaration de Sv
	        Dataset subSet= this.getSubsetByAttribute(attribute,temp);
	        //somme = somme sur v de |Sv| * Entropie(Sv)
	        sigma += subSet.getSize() * subSet.getEntropy();
		}
        
	    //Gain(S, A) = Entropie(S) - 1/|S| * somme
		
	    return this.getEntropy() - sigma/this.getSize();
	}
	

	public Dataset getSubsetByAttribute(String attribute, String value) {
		
//		retourne un sous-ensemble contenant uniquement les exemples ayant
//        la bonne valeur pour l'attribut
    Dataset result = new Dataset();
    //on prend tous les attributs sauf celui passe en parametre
    result.attributes_name=new ArrayList<String> (this.attributes_name);
    result.attributes_name.remove(attribute);
    //#pour chaque exemple de l'ensemble
	Iterator<DataPoint> iterator =this.datas.iterator();
	while (iterator.hasNext()) {
		DataPoint temp=iterator.next();
        //s'il a la bonne valeur
        if (temp.getAttribute(attribute).contentEquals(value))
        	result.datas.add(temp);
    }
    //et on retourne la liste
    return result;
	}
	
	
	public ArrayList<String> getAttributeValuesSet(String attribute) {

//	        retourne une liste contenant toutes les
//	        valeurs possibles de l'attribut
	 
	    ArrayList<String> result = new ArrayList<String>(); 
	    //pour chaque exemple
	     //si cette valeur n'est pas encore dans la liste
		Iterator<DataPoint> iterator = this.datas.iterator();
		while (iterator.hasNext()) {
			DataPoint temp=iterator.next();
	        if (!result.contains(temp.getAttribute(attribute))) {
	        	result.add(temp.getAttribute(attribute));
	        }
		}
	    return result;
	}
	
	public void displayDatas() {
	    Iterator<DataPoint> iterator =this.datas.iterator();
		while (iterator.hasNext()) {
			DataPoint temp=iterator.next();
			System.out.print("Etiquette: "+temp.label+"	");
			temp.display();
		}
	}
	
	
	 
}
