class Solution {
    public List<Integer> findAnagrams(String s, String p) {
        List<Integer> result = new ArrayList<>();

        if (s.length() < p.length()) return result;

        int[] freq = new int[26];

     
        for (char c : p.toCharArray()) {
            freq[c - 'a']++;
        }

        int left = 0, right = 0;
        int required = p.length();

        while (right < s.length()) {
            char r = s.charAt(right);

     
            if (freq[r - 'a'] > 0) required--;

            freq[r - 'a']--;
            right++;

           
            if (right - left > p.length()) {
                char l = s.charAt(left);

                if (freq[l - 'a'] >= 0) required++;
                freq[l - 'a']++;

                left++;
            }

           
            if (required == 0) {
                result.add(left);
            }
        }

        return result;
    }
}
