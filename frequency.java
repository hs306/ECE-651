package frequency;
import java.io.*;
import java.util.*;
public class frequency {
	//This function will clean the word from raw text, strip punctuation out
	public static String phase(String word) {
		if (word.matches("[A-Za-z0-9]+")) {
			return word;
		}else {
			
			String ans = "";
			for (int i = 0; i< word.length(); ++i) {
				if (Character.isLetter(word.charAt(i))|| Character.isDigit(word.charAt(i))) {
					ans += word.charAt(i);
				}
			}
			return ans;
		}
	}
	
	//This function will check if the word should be added
	public static boolean stop_or_not(String word) {
		boolean ans = true;
		//This is a stop list I search online;
		String[] array = {"i", "me", "my", "myself", 
		                  "we", "our", "ours", "ourselves", "you", "your", "yours", "yourself", "yourselves", 
		                  "he", "him", "his", "himself", "she", "her", "hers", "herself", "it", "its", "itself", "they", 
		                  "them", "their", "theirs", "themselves", "what", "which", "who", "whom", "this", "that",
		                  "these", "those", "am", "is", "are", "was", "were", "be", "been", "being", "have", "has", "had", 
		                  "having", "do", "does", "did", "doing", "a", "an", "the", "and", "but", "if", "or", "because",
		                  "as", "until", "while", "of", "at", "by", "for", "with", "about", "against", "between", "into", 
		                  "through", "during", "before", "after", "above", "below", "to", "from", "up", "down", "in", "out",
		                  "on", "off", "over", "under", "again", "further", "then", "once", "here", "there", "when", "where",
		                  "why", "how", "all", "any", "both", "each", "few", "more", "most", "other", "some", "such", "no",
		                  "nor", "not", "only", "own", "same", "so", "than",
		                  "too", "very", "s", "t", "can", "will", "just", "don", "should", "now"};
		List<String> stop_word_list = Arrays.asList(array);
		if (stop_word_list.contains(word)) {
			return false;
		}
		return ans;
	}
	
	//This function will return the list of most frequent 25 word in sorted order
	public static List<Integer> index_to_print(List<Integer> word_count){
		//index_list record the index to print, count_list record the corresponding count;
		List <Integer> index_list = new ArrayList<Integer>();
		List <Integer> count_list = new ArrayList<Integer>();
		//This part will initialize index_list to proper size;
		if (word_count.size() < 26) {
			for(int i = 0; i < word_count.size(); i++) {
				index_list.add(-1);
				count_list.add(-1);
			}
		}else {
			for(int i = 0; i < 26; i++) {
				index_list.add(-1);
				count_list.add(-1);
			}
		}
		for (int i = 0; i< word_count.size();i++) {
			for (int  j = 0; j < index_list.size();j++) {
				if (word_count.get(i) > count_list.get(j)) {
					for (int k = count_list.size() -1; k > j;k--) {
						count_list.set(k,count_list.get(k -1));
						index_list.set(k, index_list.get(k -1));
					}
					count_list.set(j, word_count.get(i));
					index_list.set(j, i);
					break;
				}
			}
		}
		return index_list;
	}
	public static void main(String[] args) throws IOException {
		FileReader in = null;
		try {
			if (args.length > 0) {
				try {
				in = new FileReader(args[0]);
				}catch (FileNotFoundException a) {
				      System.err.println("No such file");
				      System.exit(1);
				}
			}else {
				System.err.println("Invalid argument amount");
				System.exit(1);
			}
			
			BufferedReader reader = new BufferedReader(in);
			String c;
			List <String> total = new ArrayList <String > ();
			List <Integer> word_count = new ArrayList <Integer> ();
			int count = 0;
			while ((c = reader.readLine())!= null) {
				String temp[] = c.split(" ");
				if (count == 0) {
					for (int i = 0; i < temp.length;i++) {
						temp[i] = temp[i].toLowerCase();
						temp[i] = phase(temp[i]);
						if (temp[i] == "") {
							continue;
						}
						if (i!= 0) {
							if (!total.contains(temp[i]) && stop_or_not(temp[i])) {
								total.add(temp[i]);
								word_count.add(1);
								continue;
							}else {
								int x = total.indexOf(temp[i]);
								if (x == -1) {
									continue;
								}
								int temp_count = word_count.get(x) +1;
								word_count.set(x, temp_count);	
							}
						}else {
							if (stop_or_not(temp[i])) {
								total.add(temp[i]);
								word_count.add(1);
								continue;
							}
						}
					}
					count+=1;
					continue;
				}
				for (int i = 0; i < temp.length;i++) {
					temp[i] = temp[i].toLowerCase();
					temp[i] = phase(temp[i]);
					if (temp[i] == "") {
					continue;
					}
					
					if (!total.contains(temp[i])&& stop_or_not(temp[i])) {						
						total.add(temp[i]);
						word_count.add(1);
					}else {
						int x = total.indexOf(temp[i]);
						if (x == -1) {
							continue;
						}
						int temp_count = word_count.get(x) +1;
						word_count.set(x, temp_count);
					}
				}			
			}
			reader.close();
			//The following part will print according to the frequency
			List<Integer> to_print = index_to_print(word_count);
			System.out.println("Output:");
			for (int i = 0; i < to_print.size();i++) {
				System.out.println(total.get(to_print.get(i))+" - " + word_count.get(to_print.get(i)));
			}
		}finally {
			if (in != null) {
				in.close();
			}
		}
	}

}
