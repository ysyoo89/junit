package com.test.junit.util;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.logging.Level;

import static com.test.junit.util.ContainsMatches.containsMatches;
import static junit.framework.TestCase.fail;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SearchTest {
    private static final String A_TITLE = "1";
    private InputStream stream;

    @Before
    public void turnOffLogging() {
        Search.LOGGER.setLevel(Level.OFF);
    }

    @After
    public void closeResources() throws IOException {
        stream.close();
    }

    @Test
    public void returnsMatchesShowingContextWhenSearchStringInContent() throws IOException {
        /**
         * 올바르지 못한 테스트 코드 (너무 길어서 테스트 결과를 알아보기 힘들다)
        stream = streamOn("There are certain queer times and occasions "
                + "in this strange mixed affair we call life when a man "
                + "takes this whole universe for a vast practical joke, "
                + "though the wit thereof he but dimly discerns, and more "
                + "than suspects that the joke is at nobody's expense but "
                + "his own.");
        */

         stream = streamOn("rest of text here"
                 + "1234567890search term1234567890"
                 + "more rest of text");
        Search search = new Search(stream, "search term", A_TITLE);
        search.setSurroundingCharacterCount(10);

        search.execute();

        assertThat(search.getMatches(), containsMatches(new Match[] {
                new Match(A_TITLE, "search term", "1234567890search term1234567890")
        }));
        List<Match> matches = search.getMatches();
        assertTrue(matches.size() >= 1);
        Match match = matches.get(0);
        assertThat(match.searchString, equalTo("practical joke"));
        assertThat(match.surroundingContext,
                equalTo("or a vast practical joke, though t"));
    }

    @Test
    public void noMatchesReturnedWhenSearchStringNotInContent(){
        stream = streamOn("any text");
        Search search = new Search(stream, "text that doesn't match", A_TITLE);

        search.execute();

        //assertThat(search.getMatches().size(), equalTo(0));
        assertTrue(search.getMatches().isEmpty());
    }

    @Test
    public void returnsErroredWhenUnableToReadStream() {
        stream = createStreamThrowingErrorWhenRead();
        Search search = new Search(stream, "", "");

        search.execute();

        assertTrue(search.errored());
    }

    private InputStream createStreamThrowingErrorWhenRead() {
        return new InputStream() {
            @Override
            public int read() throws IOException {
                throw new IOException();
            }
        };
    }

    @Test
    public void erroredReturnsFalseWhenReadSucceeds() {
        stream = streamOn("");
        Search search = new Search(stream, "", "");

        search.execute();

        assertFalse(search.errored());
    }

    private InputStream streamOn(String pageContent) {
        return new ByteArrayInputStream(pageContent.getBytes());
    }
}
