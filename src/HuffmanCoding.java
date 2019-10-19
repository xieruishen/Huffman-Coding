import java.util.*;

class HuffmanNode {

    HuffmanNode left;
    HuffmanNode right;
    double weight; // Frequency
    String label;
}

// Compare weights between two HuffmanNodes
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

    private static HuffmanNode constructHuffmanTreeHelper(PriorityQueue<HuffmanNode> huffmanNodes){
        if(huffmanNodes.size() == 1){
            return huffmanNodes.remove();
        }
        HuffmanNode smaller = huffmanNodes.remove();
        HuffmanNode larger = huffmanNodes.remove();
        HuffmanNode combined = new HuffmanNode();
        combined.left = larger;
        combined.right = smaller;
        combined.weight = larger.weight + smaller.weight;
        huffmanNodes.add(combined);
        return constructHuffmanTreeHelper(huffmanNodes);
    }

    private static HuffmanNode constructHuffmanTree(HashMap<String, Integer> histogram, int numAlphabet, int messageLen){
        PriorityQueue<HuffmanNode> huffmanNodes = new PriorityQueue<>(numAlphabet, new HuffmanNodeComparator());
        for (HashMap.Entry mapElement : histogram.entrySet()){
            HuffmanNode node = new HuffmanNode();
            node.label = (String)mapElement.getKey();
            node.weight = (Integer)mapElement.getValue() / (double) messageLen;
            node.left = null;
            node.right = null;
            huffmanNodes.add(node);
        }
        return constructHuffmanTreeHelper(huffmanNodes);
    }

    private static void getEncodings(HashMap<String, String> encodings, HuffmanNode current, String encoding){
        if (current.label != null && !encodings.containsKey(current.label)){
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

    public static void main(String[] args) {
        System.out.println("Hello World!");
        String message = "mppa";
        HashMap<String, Integer> histogram = getHistogram(message);
        System.out.println(histogram);
        HuffmanNode root = constructHuffmanTree(histogram, histogram.size(), message.length());
        System.out.println(root.weight);
        HashMap<String, String> encodedMessage = new HashMap<>();
        getEncodings(encodedMessage, root, "");
        System.out.println(encodedMessage);
    }
}
