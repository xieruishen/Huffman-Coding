import java.util.*;

// Class for a tree node
class HuffmanNode {

    HuffmanNode left; // Pointer to left child
    HuffmanNode right; // Pointer to right child
    double weight; // Frequency
    String label; // Name of symbol 
}


// Class with comparitor function to compare mannitudes of node frequencies
class HuffmanNodeComparator implements Comparator<HuffmanNode> {
    public int compare(HuffmanNode x, HuffmanNode y)
    {
        if (x.weight - y.weight > 0){
            return 1;
        }
        else if(x.weight - y.weight == 0){
            return 0;
        }
        else {
            return -1;
        }
    }
}


public class HuffmanCoding {


	// Function to recursively construct the Huffman Tree
    private static HuffmanNode constructHuffmanTreeHelper(PriorityQueue<HuffmanNode> huffmanNodes){

    	// If there is one node, simply return it
        if(huffmanNodes.size() == 1){
            return huffmanNodes.remove();
        }

        // Pop off two nodes with smalles frequencies
        HuffmanNode smaller = huffmanNodes.remove();
        HuffmanNode larger = huffmanNodes.remove();

        // Create a new node which has these two as children (larger weight on left side)
        HuffmanNode combined = new HuffmanNode();
        combined.left = larger;
        combined.right = smaller;
        combined.weight = larger.weight + smaller.weight;

        // Add this new combined node back to the priority queue
        huffmanNodes.add(combined);

        // Recursivly construct the tree!
        return constructHuffmanTreeHelper(huffmanNodes);
    }

    // Function to set up and kick off recursive creation of the Huffman Tree
    public static HuffmanNode constructHuffmanTree(HashMap<String, Integer> histogram, int numAlphabet, int messageLen){

    	// Instantiate priority queue to be filled with nodes
        PriorityQueue<HuffmanNode> huffmanNodes = new PriorityQueue<>(numAlphabet, new HuffmanNodeComparator());
        
        // For each element in message histogram, create node with label, and weight (frequency) and set
        // children to null and add the node to the priority queue 
        for (HashMap.Entry mapElement : histogram.entrySet()){
            HuffmanNode node = new HuffmanNode();
            node.label = (String)mapElement.getKey();
            node.weight = (Integer)mapElement.getValue() / (double) messageLen;
            node.left = null;
            node.right = null;
            huffmanNodes.add(node);
        }

        // Kick off recursive construction of tree
        return constructHuffmanTreeHelper(huffmanNodes);
    }

    // Depth first search the tree keeping track of left/right child traversals and then store final label in a hash map
    private static void getEncodings(HashMap<String, String> encodings, HuffmanNode current, String encoding){
        if (current.label != null){
            encodings.put(current.label, encoding);
            return;
        }
        if (current.left != null){
            getEncodings(encodings, current.left, encoding+"0");
        }
        if (current.right != null){
            getEncodings(encodings, current.right, encoding+"1");
        }
    }

    // Function to create hashmap where key is symbol and value is number if times appeared in the message
    private static HashMap<String, Integer> getHistogram(String message){
        HashMap<String, Integer> histogram = new HashMap<>();
        for(int i = 0; i < message.length(); i++){
            String alphabet = Character.toString(message.charAt(i));
            if(histogram.containsKey(alphabet)){
                histogram.put(alphabet,histogram.get(alphabet)+1);
            }
            else{
                histogram.put(alphabet,1);
            }
        }
        return histogram;
    }

    // Function to encode a message with Huffman code with a hashmap that has letter to encoding
    private static String encodeMessage(String message, HashMap<String, String> encodingMap){
        StringBuilder encodedMesage = new StringBuilder("");
        for(int i = 0; i < message.length(); i++){
            String letter = Character.toString(message.charAt(i));
            encodedMesage.append(encodingMap.get(letter));
        }
        return  encodedMesage.toString();

    }

    public static void main(String[] args) {
    	// Message to encode
        String message = "mississippi";

        // Create and print message histogram to the terminal
        HashMap<String, Integer> histogram = getHistogram(message);
        System.out.println(histogram);
        
        // Construct the Huffman Tree and return the root node and print its weight to the terminal
        HuffmanNode root = constructHuffmanTree(histogram, histogram.size(), message.length());
        System.out.println(root.weight);

        // DFS the Huffman Tree and store the codes in a hashmap and print to the terminal
        HashMap<String, String> symbolCodes = new HashMap<>();
        getEncodings(symbolCodes, root, "");
        System.out.println(symbolCodes);

        // Encode the message
        System.out.println(encodeMessage(message, symbolCodes));
    }
}
