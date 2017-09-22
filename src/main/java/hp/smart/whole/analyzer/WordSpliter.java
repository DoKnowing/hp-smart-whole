package hp.smart.whole.analyzer;

import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

public class WordSpliter {
//    public void readFile(String filePath) throws IOException {
//        File file=new File(filePath);
//        if(file.isFile()){
//            FileInputStream inputStream=new FileInputStream(file);
//            BufferedInputStream bf=new BufferedInputStream(inputStream);
//            bf.read();
//        }
//    }

    public List<String> splitWord(String sentence) {
        List<String> splitedWords = new LinkedList<String>();
        String[] compWords = sentence.split(" ");
        for (String compWord : compWords) {
            int start = 0;
            int end = 0;
            while (start < compWord.length()) {
                char firstWord = compWord.charAt(start);
                if (firstWord >= 'A' && firstWord <= 'z') {
                    while (end < compWord.length() && compWord.charAt(end) >= 'A' && compWord.charAt(end) <= 'z') {
                        end++;
                    }
                    splitedWords.add(compWord.substring(start, end).toLowerCase());
                } else if (firstWord <= '9') {
                    while (end < compWord.length() && compWord.charAt(end) <= '9') {
                        end++;
                    }
                    splitedWords.add(compWord.substring(start, end));
                } else {
                    end++;
                    splitedWords.add(compWord.substring(start, end));
                }
                start = end;
            }
        }
        return splitedWords;
    }

    @Test
    public void test() {
        String sentence = "as218s  sf,#DDD我的世界s23c";
        List<String> splitedWords = splitWord(sentence);
        for (int i = 0; i < splitedWords.size(); i++) {
            System.out.println(splitedWords.get(i));
        }

    }
}
