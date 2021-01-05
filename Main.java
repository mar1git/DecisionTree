package app;
//https://zestedesavoir.com/tutoriels/962/les-arbres-de-decisions/premiere-version-id13/#1-10802_lalgorithme-id3
public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		TreeID3 weatherTree = new TreeID3("datasets/dataset01.csv");
		
//		Node node1=new Node("Node1");
//		Node node2=new Node("Node2");
//		Node node3=new Node("Node3");
//		Node node4=new Node("Node4");
//		Leaf leaf1=new Leaf("OUI1");
//		Leaf leaf2=new Leaf("OUI2");
//		Leaf leaf3=new Leaf("OUI3");
//		Leaf leaf4=new Leaf("OUI4");
//		Leaf leaf5=new Leaf("OUI5");
//		Leaf leaf6=new Leaf("OUI6");
//		node2.setChildren("A2",leaf1);
//		node2.setChildren("B2",leaf2);
//		node2.setChildren("C2",leaf3);
//		node4.setChildren("A4",leaf5);
//		node4.setChildren("B4",leaf6);
//		node3.setChildren("A3",leaf4);
//		node3.setChildren("B3",node4);
//		node1.setChildren("A1",node2);
//		node1.setChildren("B1",node3);
//		weatherTree.root=node1;
		weatherTree.build();
		weatherTree.display();
		
//		weatherTree.dataset.displayDatas();
//		System.out.println(weatherTree.dataset.optimalAttribute());
//		weatherTree.dataset.getSubsetByAttribute("Temperature", "Hot").displayDatas();
//		weatherTree.dataset.getSubset("No").displayDatas();

	}

}
