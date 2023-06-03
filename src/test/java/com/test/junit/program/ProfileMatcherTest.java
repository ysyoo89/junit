package com.test.junit.program;

import com.test.junit.util.Match;
import org.junit.Before;
import org.junit.Test;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.list;
import static org.mockito.Mockito.*;

public class ProfileMatcherTest {
    private BooleanQuestion question;
    private Criteria criteria;
    private ProfileMatcher matcher;
    private Profile matchingProfile;
    private Profile nonMatchingProfile;

    @Before
    public void create() {
        question = new BooleanQuestion(1, "");
        criteria = new Criteria();
        criteria.add(new Criterion(matchingAnswer(), Weight.MustMatch));
        matchingProfile = createMatchingProfile("matching");
        nonMatchingProfile = createNonMatchingProfile("nonMatching");
    }

    @Before
    public void createMatcher() {
        matcher = new ProfileMatcher();
    }

    private Profile createNonMatchingProfile(String name) {
        Profile profile = new Profile(name);
        profile.add(nonMatchingAnswer());
        return profile;
    }

    private Answer matchingAnswer() {
        return new Answer(question, Bool.TRUE);
    }
    private Answer nonMatchingAnswer() {
        return new Answer(question, Bool.FALSE);
    }

    private Profile createMatchingProfile(String name) {
        Profile profile = new Profile(name);
        profile.add(matchingAnswer());
        return profile;
    }

    @Test
    public void collectsMatchSets() {
        matcher.add(matchingProfile);
        matcher.add(nonMatchingProfile);

        List<MatchSet> sets = matcher.collectMatchSets(criteria);

        assertThat(sets.stream().map(set -> set.getProfileId()).collect(Collectors.toList()))
                .isEqualTo(new HashSet<>(Arrays.asList(matchingProfile.getId(), nonMatchingProfile.getId())));
    }

    private MatchListener listener;

    @Before
    public void createMatchListener() {
        listener = mock(MatchListener.class);
    }

    @Test
    public void processNotifiesListenerOnMatch() {
        matcher.add(matchingProfile);
        MatchSet set = matchingProfile.getMatchSet(criteria);

        matcher.process(listener, set);

        verify(listener).foundMatch(matchingProfile, set);
    }

    @Test
    public void processDoesNotNotifyListenerWhenNoMatch() {
        matcher.add(nonMatchingProfile);
        MatchSet set = nonMatchingProfile.getMatchSet(criteria);

        matcher.process(listener, set);

        verify(listener, never()).foundMatch(nonMatchingProfile, set);
    }

    @Test
    public void gathersMatchingProfiles() {
        Set<String> processedSets = Collections.synchronizedSet(new HashSet<>());
        BiConsumer<MatchListener, MatchSet> processFunction = (listener, set) -> {
            processedSets.add(set.getProfileId());
        };
        List<MatchSet> matchSets = createMatchSet(100);

        matcher.findMatchingProfiles(criteria, listener, matchSets, processFunction);

        while(!matcher.getExecutor().isTerminated());

        assertThat(processedSets).isEqualTo(matchSets.stream().map(MatchSet::getProfileId).collect(Collectors.toList()));
    }

    private List<MatchSet> createMatchSet(int count) {
        List<MatchSet> sets = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            sets.add(new MatchSet(String.valueOf(i), null, null));
        }
        return sets;
    }


}
