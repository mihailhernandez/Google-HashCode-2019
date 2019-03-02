import java.io.*;
import java.time.Duration;
import java.time.Instant;
import java.util.*;

public class Solution {

    static class Photo {

        private boolean isHorizontal;
        private int tagNumber;
        private HashSet<String> tags;
        private String id;

        Photo(boolean isHorizontal, int tagNumber, HashSet<String> tags, String id) {
            this.isHorizontal = isHorizontal;
            this.tagNumber = tagNumber;
            this.tags = tags;
            this.id = id;
        }

        int getTagNumber() {
            return tagNumber;
        }

        HashSet<String> getTags() {
            return tags;
        }

        String getId() {
            return id;
        }

        @Override
        public String toString() {
            return "Photo{" +
                    "isHorizontal=" + isHorizontal +
                    ", tagNumber=" + tagNumber +
                    ", tags=" + tags +
                    ", id=" + id +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Photo photo = (Photo) o;
            return id.equals(photo.id);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id);
        }
    }

    static class Slideshow {
        private ArrayList<Photo> slideshow = new ArrayList<>();
        private ArrayList<Photo> vertical = new ArrayList<>();

        Slideshow(BufferedReader reader) throws IOException {
            String line = reader.readLine();
            int photoSize = Integer.parseInt(line);
            for (int i = 0; i < photoSize; i++) {
                line = reader.readLine();
                String[] info = line.split(" ");
                boolean isHorizontal = info[0].equals("H");
                int tagNum = Integer.parseInt(info[1]);
                HashSet<String> tags = new HashSet<>(Arrays.asList(info).subList(2, tagNum + 2));
                Photo photo = new Photo(isHorizontal, tagNum, tags, Integer.toString(i));
                if (isHorizontal) {
                    slideshow.add(photo);
                } else {
                    vertical.add(photo);
                }
            }
        }

        private ArrayList<Photo> mergeVerticalPhotos() {
            ArrayList<Photo> merged = new ArrayList<>();
            boolean[] map = new boolean[vertical.size()];

            for (int i = 0; i < vertical.size(); i++) {
                map[i] = true;
            }

            for (int i = 0; i < vertical.size() - 1; i++) {
                if (!map[i]) {
                    continue;
                }
                int current_j = i + 1;
                HashSet<String> bestTags = new HashSet<>();
                for (int j = i + 1; j < vertical.size(); j++) {
                    if (!map[j]) {
                        continue;
                    }
                    HashSet<String> current = new HashSet<>(vertical.get(i).getTags());
                    current.addAll(vertical.get(j).getTags());
                    if (current.size() > bestTags.size()) {
                        bestTags = current;
                        map[i] = false;
                        map[j] = false;
                        current_j = j;
                    }
                }
                merged.add(new Photo(false, bestTags.size(), bestTags,
                        vertical.get(i).getId() + " " + vertical.get(current_j).getId()));
            }
            vertical = null;
            return merged;
        }

        void solve() {
            slideshow.addAll(mergeVerticalPhotos());
            int offset = slideshow.size() / 100 < 50 ? slideshow.size() : slideshow.size() / 100;
            for (int i = 0; i < slideshow.size() - 1; i += 1 + offset) {
                for (int j = i + 1; j < slideshow.size() - 1; j += 1 + offset) {
                    if (i >= slideshow.size() - 1 || j >= slideshow.size() -1) {
                        continue;
                    }
                    if (isChangeGood(i, j)) {
                        Collections.swap(slideshow, i, j);
                    }
                }
            }
        }

        private boolean isChangeGood(final int p1Index, final int p2Index) {
            int beforeInterest = interest(p1Index, p1Index) + interest(p2Index, p2Index);
            int afterInterest = interest(p1Index, p2Index) + interest(p2Index, p1Index);
            return afterInterest > beforeInterest;
        }

        private int interest(final int photoIndex, final int pos) {
            final Photo left = pos > 0 ? slideshow.get(pos - 1) : null;
            final Photo current = slideshow.get(photoIndex);
            final Photo right = pos < slideshow.size() - 1 ? slideshow.get(pos + 1) : null;

            int leftCurrentPhotoTags = commonTagCount(left, current);
            int rightCurrentPhotoTags = commonTagCount(current, right);

            int leftPhotoTags = left == null ? current.getTagNumber() : left.getTagNumber();
            leftPhotoTags -= leftCurrentPhotoTags;
            int rightPhotoTags = right == null ? current.getTagNumber() : right.getTagNumber();
            rightPhotoTags -= rightCurrentPhotoTags;
            int currentPhotoTags = current.getTagNumber() - leftCurrentPhotoTags - rightCurrentPhotoTags;

            return Math.min(
                    Math.min(
                            Math.min(
                                    leftPhotoTags,
                                    leftCurrentPhotoTags),
                            Math.min(
                                    rightCurrentPhotoTags,
                                    rightPhotoTags)),
                    currentPhotoTags);
        }

        private int commonTagCount(Photo p1, Photo p2) {
            if (p1 == null || p2 == null) {
                return 0;
            }

            HashSet<String> iterate, compare;

            if (p1.getTagNumber() >= p2.getTagNumber()) {
                iterate = p1.getTags();
                compare = p2.getTags();
            } else {
                iterate = p2.getTags();
                compare = p1.getTags();
            }

            int commonTags = 0;
            for (String tag : iterate) {
                if (compare.contains(tag)) {
                    commonTags++;
                }
            }
            return commonTags;
        }

        ArrayList<Photo> getSlideshow() {
            return slideshow;
        }
    }

    private static BufferedReader reader(String filepath) throws FileNotFoundException {
        return new BufferedReader(new InputStreamReader(new FileInputStream(filepath)));
    }

    private static void createResult(Slideshow slideshow, String filepath) throws IOException {
        File file = new File(filepath.split("\\.")[0] + "_result.txt");
        FileWriter fr = new FileWriter(file, false);
        fr.write(slideshow.getSlideshow().size() + "\n");
        for (Photo photo : slideshow.getSlideshow()) {
            fr.append(photo.getId()).append("\n");
        }
        fr.close();
    }

    public static void main(String[] args) throws IOException {
        for (String file : args)  {
            Instant start = Instant.now();
            Slideshow slideshow = new Slideshow(reader(file));
            slideshow.solve();
            createResult(slideshow, file);
            Instant finish = Instant.now();
            long timeElapsed = Duration.between(start, finish).toMillis();
            System.out.println("Processing file " + file + " took " + timeElapsed + " milliseconds.");
        }
    }
}
