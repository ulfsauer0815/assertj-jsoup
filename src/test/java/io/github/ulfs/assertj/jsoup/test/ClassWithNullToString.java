package io.github.ulfs.assertj.jsoup.test;

/**
 * Class to test handling of nullable toString method
 */
public class ClassWithNullToString {
    @Override
    public String toString() {
        return null;
    }
}
